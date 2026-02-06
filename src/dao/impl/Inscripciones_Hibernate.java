package dao.impl;


import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.DAOInscripciones;
import jakarta.persistence.EntityExistsException;

import modelos.Inscripcion;
import util.HibernateUtil;


public class Inscripciones_Hibernate implements DAOInscripciones {
	/*
	 * USamos el Util de getSessionFactory para la creacion de "fabricas" para el
	 * uso de las transacciones
	 */
	private static final SessionFactory fabrica = HibernateUtil.getSessionFactory();
	/*
	 * Metodo para Conseguir todos las Inscripciones Haciendo la query From Inscripcion.list Pero como tiene Relaciones 
	 * A Clientes y Cursos hay que poner join fetch para que recupere los objetos cliente y curso
	 */
	@Override
	public List<Inscripcion> getAll() {
		Session sesion = fabrica.openSession();
		List<Inscripcion> inscripciones = null;
		try {
			inscripciones = sesion.createQuery("FROM Inscripcion i JOIN FETCH i.clientes JOIN FETCH i.cursos", Inscripcion.class).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sesion.close();
		}
		return inscripciones;
	}
	
	/*
	 * Metodo para recuperar una inscripcion, Pasando el id como parametro, 
	 * Aqui tamien usamos el join fetch para recuperar los objetpos y como
	 * solo queremos uno solo usamos el uniqueResult 
	 */
	@Override
	public Inscripcion getOne(int id) {
		Session sesion = fabrica.openSession();
		Inscripcion inscripcion = null;
		try {
			inscripcion = sesion.createQuery(
				"FROM Inscripcion i JOIN FETCH i.clientes JOIN FETCH i.cursos WHERE i.idInscripcion = :id",
				Inscripcion.class
			).setParameter("id", id).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sesion.close();
		}
		return inscripcion;
	}
	/*
	 * Metodo para Actualizar una Inscripcion el cual le pasaremos el objeto inscripcion y
	 * usaremos el merge para actualizarlo
	 */
	@Override
	public Boolean Update(Inscripcion inscripcion) {
		Session sesion = fabrica.openSession();
		Transaction tx = null;

		try {
			tx = sesion.beginTransaction();
			sesion.merge(inscripcion);
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

	// Metodo para borrar Una Inscripcio con el id que nos de el usuario Con el remove
	@Override
	public Boolean Borrar(int id) {
		Session sesion = fabrica.openSession();
		Transaction tx = null;

		try {
			tx = sesion.beginTransaction();
			Inscripcion inscripcion = sesion.get(Inscripcion.class, id);

			if (inscripcion == null) {
				System.err.println("Inscripción no encontrada");
				tx.rollback();
				return false;
			}

			System.out.println("Voy a borrar:\n" + inscripcion.toString());
			
			sesion.remove(inscripcion);
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
	 * Metodo para Crear una Inscripcion nueva pasandole el objeto como parametro y
	 * insertandolo con el persist
	 */
	@Override
	public Boolean Create(Inscripcion inscripcion) {
		Session sesion = fabrica.openSession();
		Transaction tx = null;

		try {
			tx = sesion.beginTransaction();
			sesion.persist(inscripcion);
			tx.commit();

			System.out.println("Inscripción creada con ID: " + inscripcion.getIdInscripcion());
			return true;

		} catch (EntityExistsException e1) {
			System.err.println("La inscripción ya existe");
			if (tx != null)
				tx.rollback();
			return false;

		} catch (IllegalArgumentException e2) {
			System.err.println("Error en la inscripción: " + e2.getMessage());
			if (tx != null)
				tx.rollback();
			return false;

		} finally {
			sesion.close();
		}
	}
}