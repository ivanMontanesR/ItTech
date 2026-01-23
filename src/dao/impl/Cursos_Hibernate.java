package dao.impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import dao.DAOCursos;
import jakarta.persistence.EntityExistsException;
import modelos.Curso;
import util.HibernateUtil;
import util.Usuario;

public class Cursos_Hibernate implements DAOCursos {
	
	private static final SessionFactory fabrica = HibernateUtil.getSessionFactory();
	
	@Override
	public List<Curso> getAll() {
		Session sesion = fabrica.openSession();
		List<Curso> cursos = null;
		try {
			cursos = sesion.createQuery("FROM Cursos", Curso.class).list();
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
		Curso cliente = sesion.get(Curso.class, id);
		sesion.close();
		return cliente;
	}
	
	@Override
	public Boolean Update(int id, int opcion) {
		Session sesion = fabrica.openSession();
		Transaction tx = null;
		
		try {
			
			Curso curso = sesion.get(Curso.class, id);
			
			if (curso == null) {
				System.err.println("Curso no encontrado");
				return false;
			}
			
			
			System.out.println("Curso actual:\n" + curso);
			
			
			switch (opcion) {
				case 1:
					System.out.println("Nuevo Nombre del Curso:");
					String nombre = Usuario.leerString();
					curso.setNombreCurso(nombre);
					break;
					
				case 2:
					System.out.println("Nueva Descripcion:");
					String descripcion = Usuario.leerString();
					curso.setDescripcion(descripcion);
					break;
					
				case 3:
					System.out.println("Nueva Duracion:");
					int duracion = Usuario.leerEntero();
					curso.setDuracion(duracion);
					break;
					
				
					
				default:
					System.err.println("Opción no válida");
					return false;
			}
			
			
			tx = sesion.beginTransaction();
			sesion.merge(curso);
			tx.commit();
			
			System.out.println("Curso actualizado correctamente");
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
	public Boolean Create(Curso curso) {
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
			if (tx != null) tx.rollback();
			return false;
			
		} catch (IllegalArgumentException e2) {
			System.err.println("Error en el Cliente que se desea guardar");
			System.err.println(e2.getMessage());
			if (tx != null) tx.rollback();
			return false;
			
		} finally {
			sesion.close();
		}
	}
}