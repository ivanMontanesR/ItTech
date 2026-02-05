package dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.neodatis.odb.ODB;

import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import dao.DAOcliente;
import modelos.Cliente;
import util.ConexionNeodatis;

public class Clientes_Neodatis implements DAOcliente {
	/*
	 * Creamos el Util de neodatis para crear la instancia del ODB para usarlo en
	 * todos los metodos
	 */
	private ODB baseDatos = ConexionNeodatis.getInstancia();

	/*
	 * Metodo para Conseguir todos los Clientes Haciendo un array de objetos CLiente
	 * y añadiendolos Posteriormente a una lista
	 */
	@Override
	public List<Cliente> getAll() {
		List<Cliente> clientes = new ArrayList<>();
		try {
			Objects<Cliente> resultado = baseDatos.getObjects(Cliente.class);
			while (resultado.hasNext()) {
				clientes.add(resultado.next());
			}
		} catch (Exception e) {
			System.err.println("Error al obtener clientes: " + e.getMessage());
			e.printStackTrace();
		}
		return clientes;
	}

	/*
	 * Metodo De Creacion de Cliente el cual recibimos el Cliente como parametro y
	 * Buscamos primero cual es el maximo Id Insertado para insertar el siguiente con
	 * el maxID +1 y insertandolo con el Store
	 */
	@Override
	public Boolean Create(Cliente cliente) {
		try {
			IQuery consulta = new CriteriaQuery(Cliente.class);
			Objects<Cliente> resultado = baseDatos.getObjects(consulta);

			int maxId = 0;
			while (resultado.hasNext()) {
				Cliente c = resultado.next();
				if (c.getIdCliente() != null && c.getIdCliente() > maxId) {
					maxId = c.getIdCliente();
				}
			}

			cliente.setIdCliente(maxId + 1);
			baseDatos.store(cliente);
			baseDatos.commit();
			return true;
		} catch (Exception e) {
			System.err.println("Error al crear cliente: " + e.getMessage());
			e.printStackTrace();
			if (baseDatos != null) {
				baseDatos.rollback();
			}
			return false;
		}
	}

	/*
	 * Metodo para recuperar un Cliente con un id que nos de el usuario el cual
	 * usaremos el criteryaquery Con el where equal para comprobar el id del cliente
	 * con el id que nos han dado
	 */
	@Override
	public Cliente getOne(int id) {
		Cliente cliente = null;
		try {
			IQuery consulta = new CriteriaQuery(Cliente.class, Where.equal("idCliente", id));
			Objects<Cliente> resultado = baseDatos.getObjects(consulta);
			if (resultado.hasNext()) {
				cliente = resultado.next();
			}
		} catch (Exception e) {
			System.err.println("Error al buscar cliente: " + e.getMessage());
			e.printStackTrace();
		}
		return cliente;
	}

	/*
	 * Metodo Para actualizar un cliente previamente recuperado en el principal el
	 * cual insertaremos con el store
	 */
	@Override
	public Boolean Update(Cliente cliente) {
		try {
			baseDatos.store(cliente);
			baseDatos.commit();
			return true;
		} catch (Exception e) {
			System.err.println("Error al actualizar cliente: " + e.getMessage());
			e.printStackTrace();
			if (baseDatos != null) {
				baseDatos.rollback();
			}
			return false;
		}
	}

	/*
	 * Metodo Para Borrar Clientes con un id como parametro, con el comando delete
	 */
	@Override
	public Boolean Borrar(int id) {
		try {
			Cliente cliente = getOne(id);
			if (cliente != null) {
				baseDatos.delete(cliente);
				baseDatos.commit();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.err.println("Error al eliminar cliente: " + e.getMessage());
			e.printStackTrace();
			if (baseDatos != null) {
				baseDatos.rollback();
			}
			return false;
		}
	}
}
