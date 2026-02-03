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

	private static final SessionFactory fabrica = HibernateUtil.getSessionFactory();

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

	@Override
	public Boolean Update(Cliente cl) {
		Session sesion = fabrica.openSession();
		Transaction tx = null;

		try {

			tx = sesion.beginTransaction();
			sesion.merge(cl);
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

	@Override
	public Boolean Borrar(int id) {
		Session sesion = fabrica.openSession();
		Transaction tx = null;

		try {

			Cliente cl = sesion.get(Cliente.class, id);

			if (cl == null) {
				System.err.println("Cliente no encontrado");
				return false;
			}

			System.out.println("Voy a borrar:\n" + cl.toString());
			tx = sesion.beginTransaction();
			sesion.remove(cl);
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

	@Override
	public Boolean Create(Cliente cl) {
		Session sesion = fabrica.openSession();
		Transaction tx = null;

		try {
			tx = sesion.beginTransaction();
			sesion.persist(cl);
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