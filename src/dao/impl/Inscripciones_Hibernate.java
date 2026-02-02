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

	private static final SessionFactory fabrica = HibernateUtil.getSessionFactory();

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

	@Override
	public Boolean Update(Inscripcion ins) {
		Session sesion = fabrica.openSession();
		Transaction tx = null;

		try {
			tx = sesion.beginTransaction();
			sesion.merge(ins);
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
			Inscripcion ins = sesion.get(Inscripcion.class, id);

			if (ins == null) {
				System.err.println("Inscripción no encontrada");
				return false;
			}

			System.out.println("Voy a borrar:\n" + ins.toString());
			tx = sesion.beginTransaction();
			sesion.remove(ins);
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