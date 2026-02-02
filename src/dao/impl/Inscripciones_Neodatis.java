package dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import dao.DAOInscripciones;
import modelos.Inscripcion;
import util.ConexionNeodatis;

public class Inscripciones_Neodatis implements DAOInscripciones {

	private ODB baseDatos = ConexionNeodatis.getInstancia();

	@Override
	public List<Inscripcion> getAll() {

		List<Inscripcion> inscripciones = new ArrayList<>();

		try {

			Objects<Inscripcion> resultado = baseDatos.getObjects(Inscripcion.class);

			while (resultado.hasNext()) {
				inscripciones.add(resultado.next());
			}

		} catch (Exception e) {
			System.err.println("Error al obtener Inscripciones: " + e.getMessage());
			e.printStackTrace();
		}
		return inscripciones;
	}

	@Override
	public Boolean Create(Inscripcion in) {

		try {

			baseDatos.store(in);
			baseDatos.commit();

			return true;

		} catch (Exception e) {
			System.err.println("Error al crear Inscripcion: " + e.getMessage());
			e.printStackTrace();
			if (baseDatos != null) {
				baseDatos.rollback();
			}
			return false;
		}
	}

	@Override
	public Inscripcion getOne(int id) {

		Inscripcion inscripcionEncontrada = null;

		try {

			IQuery consulta = new CriteriaQuery(Inscripcion.class, Where.equal("idInscripcion", id));
			Objects<Inscripcion> resultado = baseDatos.getObjects(consulta);

			if (resultado.hasNext()) {
				inscripcionEncontrada = resultado.next();

			}
		} catch (Exception e) {
			System.err.println("Error al buscar Inscripcion: " + e.getMessage());
			e.printStackTrace();

		}

		return inscripcionEncontrada;
	}

	@Override
	public Boolean Update(Inscripcion in) {

		try {

			baseDatos.store(in);
			baseDatos.commit();

			return true;

		} catch (Exception e) {
			System.err.println("Error al actualizar Inscripcion: " + e.getMessage());
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

			Inscripcion inscripcionABorrar = getOne(id);

			if (inscripcionABorrar != null) {
				baseDatos.delete(inscripcionABorrar);
				baseDatos.commit();

				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			System.err.println("Error al eliminar Inscripcion: " + e.getMessage());
			e.printStackTrace();
			if (baseDatos != null) {
				baseDatos.rollback();
			}
			return false;

		}
	}

}