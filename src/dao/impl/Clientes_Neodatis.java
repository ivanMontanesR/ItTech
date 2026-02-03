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

	private ODB baseDatos = ConexionNeodatis.getInstancia();

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

	@Override
	public Boolean Create(Cliente cl) {
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
		        
		        cl.setIdCliente(maxId + 1);
			baseDatos.store(cl);
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

	@Override
	public Cliente getOne(int id) {
		Cliente clienteEncontrado = null;
		try {
			IQuery consulta = new CriteriaQuery(Cliente.class, Where.equal("idCliente", id));
			Objects<Cliente> resultado = baseDatos.getObjects(consulta);
			if (resultado.hasNext()) {
				clienteEncontrado = resultado.next();
			}
		} catch (Exception e) {
			System.err.println("Error al buscar cliente: " + e.getMessage());
			e.printStackTrace();
		}
		return clienteEncontrado;
	}

	@Override
	public Boolean Update(Cliente cl) {
		try {
			baseDatos.store(cl);
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

	@Override
	public Boolean Borrar(int id) {
		try {
			Cliente clienteABorrar = getOne(id);
			if (clienteABorrar != null) {
				baseDatos.delete(clienteABorrar);
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
