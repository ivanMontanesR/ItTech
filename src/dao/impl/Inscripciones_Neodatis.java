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

public class Inscripciones_Neodatis implements DAOInscripciones {

	private final String nombreBD = "ITtech.odb";
	private final String path = "src\\basedatos\\";
	private final String usuario = "ivan";
	private final String password = "openpgpwd";

	
	private ODB abrirBaseDatos() {
		return ODBFactory.open(path + nombreBD, usuario, password);
	}

	@Override
	public List<Inscripcion> getAll() {
		ODB baseDatos = null;
		List<Inscripcion> inscripciones = new ArrayList<>();

		try {
			baseDatos = abrirBaseDatos();

			
			Objects<Inscripcion> resultado = baseDatos.getObjects(Inscripcion.class);

			while (resultado.hasNext()) {
				inscripciones.add(resultado.next());
			}

			

		} catch (Exception e) {
			System.err.println("Error al obtener Inscripciones: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (baseDatos != null && !baseDatos.isClosed()) {
				baseDatos.close();
			}
		}

		return inscripciones;
	}

	@Override
	public Boolean Create(Inscripcion in) {
		ODB baseDatos = null;

		try {
			baseDatos = abrirBaseDatos();

			
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
		} finally {
			if (baseDatos != null && !baseDatos.isClosed()) {
				baseDatos.close();
			}
		}
	}

	@Override
	public Inscripcion getOne(int id) {
		ODB baseDatos = null;
		Inscripcion inscripcionEncontrada = null;

		try {
			baseDatos = abrirBaseDatos();

			
			IQuery consulta = new CriteriaQuery(Inscripcion.class, Where.equal("idInscripcion", id));
			Objects<Inscripcion> resultado = baseDatos.getObjects(consulta);

			if (resultado.hasNext()) {
				inscripcionEncontrada = resultado.next();
				
			}
		} catch (Exception e) {
			System.err.println("Error al buscar Inscripcion: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (baseDatos != null && !baseDatos.isClosed()) {
				baseDatos.close();
			}
		}

		return inscripcionEncontrada;
	}

	@Override
	public Boolean Update(Inscripcion in) {

		ODB baseDatos = null;

		try {
			baseDatos = abrirBaseDatos();

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
		} finally {
			if (baseDatos != null && !baseDatos.isClosed()) {
				baseDatos.close();
			}
		}
	}

	@Override
	public Boolean Borrar(int id) {
		ODB baseDatos = null;

		try {
			baseDatos = abrirBaseDatos();

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
		} finally {
			if (baseDatos != null && !baseDatos.isClosed()) {
				baseDatos.close();
			}
		}
	}

}