package dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import dao.DAOCursos;
import modelos.Curso;
import util.ConexionNeodatis;

public class Cursos_Neodatis implements DAOCursos {

	private ODB baseDatos = ConexionNeodatis.getInstancia();

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

	@Override
	public Boolean Create(Curso cur) {

		try {

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

	@Override
	public Curso getOne(int id) {

		Curso cursoEncontrado = null;

		try {

			IQuery consulta = new CriteriaQuery(Curso.class, Where.equal("idCurso", id));
			Objects<Curso> resultado = baseDatos.getObjects(consulta);

			if (resultado.hasNext()) {
				cursoEncontrado = resultado.next();

			}
		} catch (Exception e) {
			System.err.println("Error al buscar Curso: " + e.getMessage());
			e.printStackTrace();
		} 

		return cursoEncontrado;
	}

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

	@Override
	public Boolean Borrar(int id) {

		try {

			Curso cursoBorrar = getOne(id);

			if (cursoBorrar != null) {
				baseDatos.delete(cursoBorrar);
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