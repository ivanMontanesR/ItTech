package dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XQueryService;
import dao.DAOInscripciones;
import modelos.Cliente;
import modelos.Curso;
import modelos.Inscripcion;
import util.ConexionExistDB;

public class Inscripciones_ExistDB implements DAOInscripciones {

    private Collection col = ConexionExistDB.getInstancia();
    private Clientes_ExistDB clienteDAO = new Clientes_ExistDB();
    private Cursos_ExistDB cursoDAO = new Cursos_ExistDB();

    @Override
    public List<Inscripcion> getAll() {
        List<Inscripcion> inscripciones = new ArrayList<>();
        try {
            XPathQueryService service = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            ResourceSet result = service.query("//inscripcion/id_inscripcion/text()");
            ResourceIterator it = result.getIterator();
            while (it.hasMoreResources()) {
                int id = Integer.parseInt(it.nextResource().getContent().toString());
                inscripciones.add(getOne(id));
            }
        } catch (XMLDBException e) {
            System.err.println("Error al obtener inscripciones: " + e.getMessage());
            e.printStackTrace();
        }
        return inscripciones;
    }

    @Override
    public Inscripcion getOne(int id) {
        Inscripcion inscripcion = null;
        try {
            XPathQueryService service = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            ResourceSet result = service.query("//inscripcion[id_inscripcion=" + id + "]");
            if (result.getSize() == 0) return null;

            result = service.query("//inscripcion[id_inscripcion=" + id + "]/id_cliente/text()");
            int idCliente = Integer.parseInt(result.getResource(0).getContent().toString());
            Cliente cliente = clienteDAO.getOne(idCliente);

            result = service.query("//inscripcion[id_inscripcion=" + id + "]/id_curso/text()");
            int idCurso = Integer.parseInt(result.getResource(0).getContent().toString());
            Curso curso = cursoDAO.getOne(idCurso);

            result = service.query("//inscripcion[id_inscripcion=" + id + "]/fecha_inscripcion/text()");
            Date fecha = Date.valueOf(result.getResource(0).getContent().toString());

            inscripcion = new Inscripcion(curso, cliente, fecha);
            inscripcion.setIdInscripcion(id);

        } catch (XMLDBException e) {
            System.err.println("Error al buscar inscripcion: " + e.getMessage());
            e.printStackTrace();
        }
        return inscripcion;
    }

    @Override
    public Boolean Create(Inscripcion ins) {
        try {
            XPathQueryService pathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            ResourceSet idResult = pathService.query("max(doc('/db/ITtech/inscripciones.xml')//id_inscripcion/xs:integer(.))");
            int nuevoId = 1;
            if (idResult.getSize() > 0) {
                Object content = idResult.getResource(0).getContent();
                if (content != null && !content.toString().equals("")) {
                    nuevoId = (int) Double.parseDouble(content.toString()) + 1;
                }
            }

            String inscripcionXML = "<inscripcion>" +
                    "<id_inscripcion>" + nuevoId + "</id_inscripcion>" +
                    "<id_cliente>" + ins.getClientes().getIdCliente() + "</id_cliente>" +
                    "<id_curso>" + ins.getCursos().getIdCurso() + "</id_curso>" +
                    "<fecha_inscripcion>" + ins.getFechaInscripcion() + "</fecha_inscripcion>" +
                    "</inscripcion>";

            XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
            String xquery = "update insert " + inscripcionXML + " into doc('/db/ITtech/inscripciones.xml')/inscripciones";
            service.query(xquery);

            System.out.println("Inscripcion creada con ID: " + nuevoId);
            return true;

        } catch (XMLDBException e) {
            System.err.println("Error al crear inscripcion: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean Update(Inscripcion ins) {
        try {
            XPathQueryService pathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            ResourceSet check = pathService.query("//inscripcion[id_inscripcion=" + ins.getIdInscripcion() + "]");
            if (check.getSize() == 0) {
                System.err.println("Inscripcion no encontrada");
                return false;
            }

            String inscripcionXML = "<inscripcion>" +
                    "<id_inscripcion>" + ins.getIdInscripcion() + "</id_inscripcion>" +
                    "<id_cliente>" + ins.getClientes().getIdCliente() + "</id_cliente>" +
                    "<id_curso>" + ins.getCursos().getIdCurso() + "</id_curso>" +
                    "<fecha_inscripcion>" + ins.getFechaInscripcion() + "</fecha_inscripcion>" +
                    "</inscripcion>";

            XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
            String xquery = "update replace doc('/db/ITtech/inscripciones.xml')//inscripcion[id_inscripcion=" + ins.getIdInscripcion() + "] with " + inscripcionXML;
            service.query(xquery);

            
            return true;

        } catch (XMLDBException e) {
            System.err.println("Error al actualizar inscripcion: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean Borrar(int id) {
        try {
            Inscripcion ins = getOne(id);
            if (ins == null) {
                System.err.println("Inscripcion no encontrada");
                return false;
            }

            System.out.println("Voy a borrar:\n" + ins.toString());

            XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
            String xquery = "update delete doc('/db/ITtech/inscripciones.xml')//inscripcion[id_inscripcion=" + id + "]";
            service.query(xquery);

            return true;

        } catch (XMLDBException e) {
            System.err.println("Error al borrar inscripcion: " + e.getMessage());
            return false;
        }
    }
}