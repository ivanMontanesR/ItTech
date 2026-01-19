package dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.Cliente_dao;
import jakarta.persistence.EntityExistsException;
import modelos.Cliente;
import util.HibernateUtil;
import util.Usuario;

public class Clientes_Hibernate implements Cliente_dao {

	private static final SessionFactory fabrica = HibernateUtil.getSessionFactory();

	@Override
	public List<Cliente> getAll() {

		Session sesion = fabrica.openSession();
		List<Cliente> Clientes = null;

		try {
			Clientes = sesion.createQuery("FROM Clientes", Cliente.class).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sesion.close();
		}

		return Clientes;
	}

	@Override
	public Cliente getOne(int id) {
		Session sesion = fabrica.openSession();
		Cliente cliente = sesion.get(Cliente.class, id);
		sesion.close();
		return cliente;
	}

	@Override
	public Boolean Update(int id,int opcion) {
		Session sesion = fabrica.openSession();
		Cliente cliente = sesion.get(Cliente.class, id);
		
		if (cliente != null) {
			System.out.println("Cliente antes:\n" + cliente);
			switch (opcion) {
			case 1: {
				//Nombre
				
				System.out.println("Nuevo Nombre");
				String Nombre = Usuario.leerString();
				cliente.setNombre(Nombre);
				Transaction tx = sesion.beginTransaction();
				sesion.merge(cliente);
				tx.commit();
				sesion.close();
				
			}
			
			case 2 :{
				
			}
			default:
				System.out.println("Opcion no Elegida");
			}
			try {
				tx = sesion.beginTransaction();  // OJO, DESPUÉS DE HABER HECHO UN COMMIT O ROLLBACK INICIAMOS TRANSACCIÓN DE NUEVO
				sesion.merge(l3);
				tx.commit();
				System.out.println("Libro después:\n" + l3);
			
		
		else {
			System.out.println("El libro con id=3 no existe");
			return false;
		}
	}

	@Override
	public Boolean Borrar(int id) {
		Session sesion = fabrica.openSession();
		Transaction tx = null;

		try {
			Cliente cl = getOne(id);
			if (cl == null) {
				return false;
			} else {

				System.out.println("Voy a borrar:\n" + cl.toString());
				tx = sesion.beginTransaction();
				sesion.remove(cl);
				tx.commit();
				return true;
			}
		} catch (IllegalArgumentException e4) {
			System.err.println("Error en el Cliente que se desea borrar");
			System.err.println(e4.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			return false; // Error

		} finally {
			sesion.close();
		}
	}

	@Override
	public Boolean Create(Cliente cl) {
		Session sesion = fabrica.openSession();
		Transaction tx = sesion.beginTransaction();
		try {
			sesion.persist(cl);
			tx.commit();
			return true;

		} catch (EntityExistsException e1) {
			System.err.println("El Cliente ya existe");
			System.err.println(e1.getMessage());
			tx.rollback();
			return false;

		} catch (IllegalArgumentException e2) {
			System.err.println("Error en el Cliente que se desea guardar");
			System.err.println(e2.getMessage());
			tx.rollback();
			return false;
		}
	}

}
