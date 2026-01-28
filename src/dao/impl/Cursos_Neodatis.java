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


public class Cursos_Neodatis implements DAOCursos{

	private final String nombreBD = "ITtech.odb";
	private final String path = "src\\basedatos\\";
	private final String usuario = "ivan";
	private final String password = "openpgpwd";

	
	private ODB abrirBaseDatos() {
		return ODBFactory.open(path + nombreBD, usuario, password);
	}

	@Override
	public List<Curso> getAll() {
		ODB baseDatos = null;
		List<Curso> cursos = new ArrayList<>();

		try {
			baseDatos = abrirBaseDatos();

			
			Objects<Curso> resultado = baseDatos.getObjects(Curso.class);

			while (resultado.hasNext()) {
				cursos.add(resultado.next());
			}

			

		} catch (Exception e) {
			System.err.println("Error al obtener cursos: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (baseDatos != null && !baseDatos.isClosed()) {
				baseDatos.close();
			}
		}

		return cursos;
	}

	@Override
	public Boolean Create(Curso cur) {
		ODB baseDatos = null;

		try {
			baseDatos = abrirBaseDatos();

			
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
		} finally {
			if (baseDatos != null && !baseDatos.isClosed()) {
				baseDatos.close();
			}
		}
	}

	@Override
	public Curso getOne(int id) {
		ODB baseDatos = null;
		Curso cursoEncontrado = null;

		try {
			baseDatos = abrirBaseDatos();

			
			IQuery consulta = new CriteriaQuery(Curso.class, Where.equal("idCurso", id));
			Objects<Curso> resultado = baseDatos.getObjects(consulta);

			if (resultado.hasNext()) {
				cursoEncontrado = resultado.next();
				
			}
		} catch (Exception e) {
			System.err.println("Error al buscar Curso: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (baseDatos != null && !baseDatos.isClosed()) {
				baseDatos.close();
			}
		}

		return cursoEncontrado;
	}

	@Override
	public Boolean Update(Curso cur) {

		ODB baseDatos = null;

		try {
			baseDatos = abrirBaseDatos();

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
		} finally {
			if (baseDatos != null && !baseDatos.isClosed()) {
				baseDatos.close();
			}
		}
	}

}