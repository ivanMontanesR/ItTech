package dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import dao.DAOCursos;
import modelos.Curso;
import util.ConexionNeodatis;

public class Cursos_Neodatis implements DAOCursos {
	/*
	 * Creamos el Util de neodatis para crear la instancia del ODB para usarlo en
	 * todos los metodos
	 */
	private ODB baseDatos = ConexionNeodatis.getInstancia();

	/*
	 * Metodo para Conseguir todos los Cursos Haciendo un array de objetos Curso y
	 * añadiendolos Posteriormente a una lista
	 */
	@Override
	public List<Curso> getAll() {

		List<Curso> cursos = new ArrayList<>();

		try {

			Objects<Curso> resultado = baseDatos.getObjects(Curso.class);

			while (resultado.hasNext()) {
				cursos.add(resultado.next());
			}

		} catch (Exception e) {
			System.err.println("Error al obtener cursos: " + e.getMessage());
			e.printStackTrace();
		}

		return cursos;
	}
	/*
	 * Metodo De Creacion de Curso el cual recibimos el Curso como parametro y
	 * Hayamos primero cual es el maximo Id Insertado para insertar el siguiente con
	 * el maxID +1 y insertandolo con el Store
	 */

	@Override
	public Boolean Create(Curso cur) {
		try {
			IQuery consulta = new CriteriaQuery(Curso.class);
			Objects<Curso> resultado = baseDatos.getObjects(consulta);

			int maxId = 0;
			while (resultado.hasNext()) {
				Curso c = resultado.next();
				if (c.getIdCurso() != null && c.getIdCurso() > maxId) {
					maxId = c.getIdCurso();
				}
			}

			cur.setIdCurso(maxId + 1);

			baseDatos.store(cur);
			baseDatos.commit();
			return true;
		} catch (Exception e) {
			System.err.println("Error al crear curso: " + e.getMessage());
			e.printStackTrace();
			if (baseDatos != null) {
				baseDatos.rollback();
			}
			return false;
		}
	}

	/*
	 * Metodo para recuperar un Curso con un id que nos de el usuario el cual
	 * usaremos el criteryaquery Con el where equal para comprobar el id del curso
	 * con el id que nos han dado
	 */
	@Override
	public Curso getOne(int id) {

		Curso curso = null;

		try {

			IQuery consulta = new CriteriaQuery(Curso.class, Where.equal("idCurso", id));
			Objects<Curso> resultado = baseDatos.getObjects(consulta);

			if (resultado.hasNext()) {
				curso = resultado.next();

			}
		} catch (Exception e) {
			System.err.println("Error al buscar Curso: " + e.getMessage());
			e.printStackTrace();
		}

		return curso;
	}

	/*
	 * Metodo Para actualizar un cliente previamente recuperado en el principal el
	 * cual insertaremos con el store
	 */
	@Override
	public Boolean Update(Curso cur) {

		try {

			baseDatos.store(cur);
			baseDatos.commit();

			return true;

		} catch (Exception e) {
			System.err.println("Error al actualizar curso: " + e.getMessage());
			e.printStackTrace();
			if (baseDatos != null) {
				baseDatos.rollback();
			}
			return false;

		}
	}
	/*
	 * Metodo Para Borrar Curso con un id como parametro, con el comando delete
	 */
	@Override
	public Boolean Borrar(int id) {

		try {

			Curso curso = getOne(id);

			if (curso != null) {
				baseDatos.delete(curso);
				baseDatos.commit();

				return true;
			} else {

				return false;
			}

		} catch (Exception e) {
			System.err.println("Error al eliminar Curso: " + e.getMessage());
			e.printStackTrace();
			if (baseDatos != null) {
				baseDatos.rollback();
			}
			return false;

		}
	}

}