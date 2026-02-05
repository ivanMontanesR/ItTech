package dao.impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import dao.DAOCursos;
import jakarta.persistence.EntityExistsException;
import modelos.Curso;
import util.HibernateUtil;

public class Cursos_Hibernate implements DAOCursos {
	/*
	 * USamos el Util de getSessionFactory para la creacion de "fabricas" para el
	 * uso de las transacciones
	 */
	private static final SessionFactory fabrica = HibernateUtil.getSessionFactory();

	/*
	 * Metodo para Conseguir todos los Cursos Haciendo la query From Curso.list para
	 * crear una lista
	 */
	@Override
	public List<Curso> getAll() {
		Session sesion = fabrica.openSession();
		List<Curso> cursos = null;
		try {
			cursos = sesion.createQuery("FROM Curso", Curso.class).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sesion.close();
		}
		return cursos;
	}

	/*
	 * Metodo para recuperar un Curso, Pasando el id como parametro, al sesion.get
	 * especificando la clase Curso y el id que haya introducido El usuario
	 */
	@Override
	public Curso getOne(int id) {
		Session sesion = fabrica.openSession();
		Curso curso = null;
		try {
			curso = sesion.get(Curso.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sesion.close();
		}
		return curso;
	}

	/*
	 * Metodo para Actualizar un curso el cual le pasaremos el objeto curso y
	 * usaremos el merge para actualizarlo
	 */
	@Override
	public Boolean Update(Curso curso) {
		Session sesion = fabrica.openSession();
		Transaction tx = null;
		try {
			tx = sesion.beginTransaction();
			sesion.merge(curso);
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
	
	// Metodo para borrar Un Curso con el id que nos de el usuario con el remove
	@Override
	public Boolean Borrar(int id) {
		Session sesion = fabrica.openSession();
		Transaction tx = null;

		try {

			Curso curso = sesion.get(Curso.class, id);

			

			System.out.println("Voy a borrar:\n" + curso.toString());
			tx = sesion.beginTransaction();
			sesion.remove(curso);
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
	 * Metodo para Crear un curso nuevo pasandole el objeto como parametro y
	 * insertandolo con el persist
	 */
	@Override
	public Boolean Create(Curso curso) {
		Session sesion = fabrica.openSession();
		Transaction tx = null;

		try {
			tx = sesion.beginTransaction();
			sesion.persist(curso);
			tx.commit();
			return true;

		} catch (EntityExistsException e1) {
			System.err.println("El Curso ya existe");
			System.err.println(e1.getMessage());
			if (tx != null)
				tx.rollback();
			return false;

		} catch (IllegalArgumentException e2) {
			System.err.println("Error en el Curso que se desea guardar");
			System.err.println(e2.getMessage());
			if (tx != null)
				tx.rollback();
			return false;

		} finally {
			sesion.close();
		}
	}
}