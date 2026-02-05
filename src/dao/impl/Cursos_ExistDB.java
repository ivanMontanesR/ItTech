package dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XQueryService;
import dao.DAOCursos;
import modelos.Curso;
import util.ConexionExistDB;

public class Cursos_ExistDB implements DAOCursos {
	// Creamos el Util de ExistDB para obtener la instancia de Collection y usarla
	// en todos los Metodos

	private Collection col = ConexionExistDB.getInstancia();

	/*
	 * Metodo para conseguir todos los cursos haciendo una query XPath que obtiene
	 * todos los id_curso y posteriormente recupera cada curso completo añadiéndolos
	 * a una lista
	 */
	@Override
	public List<Curso> getAll() {
		List<Curso> cursos = new ArrayList<>();
		try {
			XPathQueryService service = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet result = service.query("//curso/id_curso/text()");
			ResourceIterator it = result.getIterator();
			while (it.hasMoreResources()) {
				int id = Integer.parseInt(it.nextResource().getContent().toString());
				cursos.add(getOne(id));
			}
		} catch (XMLDBException e) {
			System.err.println("Error al obtener cursos: " + e.getMessage());
			e.printStackTrace();
		}
		return cursos;
	}

	/*
	 * Metodo para recuperar un curso con un id que nos de el usuario Utiliza XPath
	 * para buscar el curso por id y recupera cada campo individualmente
	 */
	@Override
	public Curso getOne(int id) {
		Curso curso = null;
		try {
			XPathQueryService service = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet result = service.query("//curso[id_curso=" + id + "]");
			if (result.getSize() == 0)
				return null;

			curso = new Curso();
			curso.setIdCurso(id);

			result = service.query("//curso[id_curso=" + id + "]/nombre_curso/text()");
			if (result.getSize() > 0) {
				curso.setNombreCurso(result.getResource(0).getContent().toString());
			}

			result = service.query("//curso[id_curso=" + id + "]/descripcion/text()");
			if (result.getSize() > 0) {
				curso.setDescripcion(result.getResource(0).getContent().toString());
			}

			result = service.query("//curso[id_curso=" + id + "]/duracion/text()");
			if (result.getSize() > 0) {
				curso.setDuracion(Integer.parseInt(result.getResource(0).getContent().toString()));
			}

		} catch (XMLDBException e) {
			System.err.println("Error al buscar curso: " + e.getMessage());
			e.printStackTrace();
		}
		return curso;
	}

	/*
	 * Metodo de creacion de curso el cual recibe el curso como parametro Buscamos
	 * primero el máximo id insertado para insertar el siguiente con maxID + 1
	 * Construimos el XML del curso y lo insertamos con XQuery usando update insert
	 */
	@Override
	public Boolean Create(Curso curso) {
		try {
			XPathQueryService pathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet idResult = pathService.query("max(doc('/db/ITtech/cursos.xml')//id_curso/xs:integer(.))");
			int nuevoId = 1;
			if (idResult.getSize() > 0) {
				Object content = idResult.getResource(0).getContent();
				if (content != null && !content.toString().equals("")) {
					nuevoId = (int) Double.parseDouble(content.toString()) + 1;
				}
			}

			String cursoXML = "<curso>" + "<id_curso>" + nuevoId + "</id_curso>" + "<nombre_curso>"
					+ curso.getNombreCurso() + "</nombre_curso>" + "<descripcion>" + curso.getDescripcion()
					+ "</descripcion>" + "<duracion>" + curso.getDuracion() + "</duracion>" + "</curso>";

			XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
			String xquery = "update insert " + cursoXML + " into doc('/db/ITtech/cursos.xml')/cursos";
			service.query(xquery);

			System.out.println("Curso creado con ID: " + nuevoId);
			return true;

		} catch (XMLDBException e) {
			System.err.println("Error al crear curso: " + e.getMessage());
			return false;
		}
	}

	/*
	 * Metodo para actualizar un curso previamente recuperado en el principal
	 * construimos el nuevo XML y lo reemplazamos con XQuery usando update replace
	 */
	@Override
	public Boolean Update(Curso curso) {
		try {

			String cursoXML = "<curso>" + "<id_curso>" + curso.getIdCurso() + "</id_curso>" + "<nombre_curso>"
					+ curso.getNombreCurso() + "</nombre_curso>" + "<descripcion>" + curso.getDescripcion()
					+ "</descripcion>" + "<duracion>" + curso.getDuracion() + "</duracion>" + "</curso>";

			XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
			String xquery = "update replace doc('/db/ITtech/cursos.xml')//curso[id_curso=" + curso.getIdCurso()
					+ "] with " + cursoXML;
			service.query(xquery);

			return true;

		} catch (XMLDBException e) {
			System.err.println("Error al actualizar curso: " + e.getMessage());
			return false;
		}
	}

	/*
	 * Metodo para borrar cursos con un id como parametro verificamos que el curso
	 * exista y lo eliminamos con XQuery usando update delete
	 */
	@Override
	public Boolean Borrar(int id) {
		try {
			Curso curso = getOne(id);
			

			System.out.println("Voy a borrar:\n" + curso.toString());

			XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
			String xquery = "update delete doc('/db/ITtech/cursos.xml')//curso[id_curso=" + id + "]";
			service.query(xquery);

			return true;

		} catch (XMLDBException e) {
			System.err.println("Error al borrar curso: " + e.getMessage());
			return false;
		}
	}
}