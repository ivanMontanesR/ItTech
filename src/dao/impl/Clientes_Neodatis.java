package dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import dao.DAOcliente;
import modelos.Cliente;

public class Clientes_Neodatis implements DAOcliente {

	private final String nombreBD = "ITtech.odb";
	private final String path = "src\\basedatos\\";
	private final String usuario = "ivan";
	private final String password = "openpgpwd";

	
	private ODB abrirBaseDatos() {
		return ODBFactory.open(path + nombreBD, usuario, password);
	}

	@Override
	public List<Cliente> getAll() {
		ODB baseDatos = null;
		List<Cliente> clientes = new ArrayList<>();

		try {
			baseDatos = abrirBaseDatos();

			
			Objects<Cliente> resultado = baseDatos.getObjects(Cliente.class);

			while (resultado.hasNext()) {
				clientes.add(resultado.next());
			}

			

		} catch (Exception e) {
			System.err.println("Error al obtener clientes: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (baseDatos != null && !baseDatos.isClosed()) {
				baseDatos.close();
			}
		}

		return clientes;
	}

	@Override
	public Boolean Create(Cliente cl) {
		ODB baseDatos = null;

		try {
			baseDatos = abrirBaseDatos();

			
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
		} finally {
			if (baseDatos != null && !baseDatos.isClosed()) {
				baseDatos.close();
			}
		}
	}

	@Override
	public Cliente getOne(int id) {
		ODB baseDatos = null;
		Cliente clienteEncontrado = null;

		try {
			baseDatos = abrirBaseDatos();

			
			IQuery consulta = new CriteriaQuery(Cliente.class, Where.equal("idCliente", id));
			Objects<Cliente> resultado = baseDatos.getObjects(consulta);

			if (resultado.hasNext()) {
				clienteEncontrado = resultado.next();
				
			}
		} catch (Exception e) {
			System.err.println("Error al buscar cliente: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (baseDatos != null && !baseDatos.isClosed()) {
				baseDatos.close();
			}
		}

		return clienteEncontrado;
	}

	@Override
	public Boolean Update(Cliente cl) {

		ODB baseDatos = null;

		try {
			baseDatos = abrirBaseDatos();

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
		} finally {
			if (baseDatos != null && !baseDatos.isClosed()) {
				baseDatos.close();
			}
		}
	}

	@Override
	public Boolean Borrar(int id) {
		ODB baseDatos = null;

		try {
			baseDatos = abrirBaseDatos();

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
		} finally {
			if (baseDatos != null && !baseDatos.isClosed()) {
				baseDatos.close();
			}
		}
	}

}