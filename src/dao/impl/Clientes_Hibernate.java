package dao.impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import dao.DAOcliente;
import jakarta.persistence.EntityExistsException;
import modelos.Cliente;
import util.HibernateUtil;

public class Clientes_Hibernate implements DAOcliente {
	/*
	 * USamos el Util de getSessionFactory para la creacion de "fabricas" para el
	 * uso de las transacciones
	 */
	private static final SessionFactory fabrica = HibernateUtil.getSessionFactory();

	/*
	 * Metodo para Conseguir todos los Clientes Haciendo la query From Cliente.list
	 * para crear una lista
	 */
	@Override
	public List<Cliente> getAll() {
		Session sesion = fabrica.openSession();
		List<Cliente> clientes = null;
		try {
			clientes = sesion.createQuery("FROM Cliente", Cliente.class).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sesion.close();
		}
		return clientes;
	}

	/*
	 * Metodo para recuperar un cliente, Pasando el id como parametro, al sesion.get
	 * especificando la clase Cliente y el id que haya introducido El usuario
	 */
	@Override
	public Cliente getOne(int id) {

		Session sesion = fabrica.openSession();
		Cliente cliente = null;
		try {
			cliente = sesion.get(Cliente.class, id);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sesion.close();
		}
		return cliente;
	}

	/*
	 * Metodo para Actualizar un cliente el cual le pasaremos el objeto cliente y
	 * usaremos el merge para actualizarlo
	 */
	@Override
	public Boolean Update(Cliente cliente) {
		Session sesion = fabrica.openSession();
		Transaction tx = null;

		try {

			tx = sesion.beginTransaction();
			sesion.merge(cliente);
			tx.commit();

			return true;

		} catch (Exception e) {
			System.err.println("Error al actualizar: " + e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			return false;

		} finally {
			sesion.close();
		}
	}

	// Metodo para borrar Un cliente con el id que nos de el usuario Con el remove
	@Override
	public Boolean Borrar(int id) {
		Session sesion = fabrica.openSession();
		Transaction tx = null;

		try {
			tx = sesion.beginTransaction();
			Cliente cliente = sesion.get(Cliente.class, id);
			if (cliente == null) {
				tx.rollback();
				return false;
			}
			sesion.remove(cliente);
			tx.commit();
			return true;

		} catch (Exception e) {
			System.err.println("Error al borrar: " + e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			return false;

		} finally {
			sesion.close();
		}
	}

	/*
	 * Metodo para Crear un cliente nuevo pasandole el objeto como parametro y
	 * insertandolo con el persist
	 */
	@Override
	public Boolean Create(Cliente cliente) {
		Session sesion = fabrica.openSession();
		Transaction tx = null;

		try {
			tx = sesion.beginTransaction();
			sesion.persist(cliente);
			tx.commit();
			return true;

		} catch (EntityExistsException e1) {
			System.err.println("El Cliente ya existe");
			System.err.println(e1.getMessage());
			if (tx != null)
				tx.rollback();
			return false;

		} catch (IllegalArgumentException e2) {
			System.err.println("Error en el Cliente que se desea guardar");
			System.err.println(e2.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			return false;

		} finally {
			sesion.close();
		}
	}
}