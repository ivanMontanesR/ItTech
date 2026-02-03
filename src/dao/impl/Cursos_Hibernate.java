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

	private static final SessionFactory fabrica = HibernateUtil.getSessionFactory();

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

	@Override
	public Boolean Update(Curso cur) {
		Session sesion = fabrica.openSession();
		Transaction tx = null;
		try {
			tx = sesion.beginTransaction();
			sesion.merge(cur);
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

			Curso cur = sesion.get(Curso.class, id);

			if (cur == null) {
				System.err.println("Curso no encontrado");
				return false;
			}

			System.out.println("Voy a borrar:\n" + cur.toString());
			tx = sesion.beginTransaction();
			sesion.remove(cur);
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