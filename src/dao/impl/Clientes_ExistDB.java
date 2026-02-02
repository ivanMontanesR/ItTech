package dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XQueryService;
import dao.DAOcliente;
import modelos.Cliente;
import util.ConexionExistDB;

public class Clientes_ExistDB implements DAOcliente {

    private Collection col = ConexionExistDB.getInstancia();

    @Override
    public List<Cliente> getAll() {
        List<Cliente> clientes = new ArrayList<>();
        try {
            XPathQueryService service = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            ResourceSet result = service.query("//cliente/id_cliente/text()");
            ResourceIterator it = result.getIterator();
            while (it.hasMoreResources()) {
                int id = Integer.parseInt(it.nextResource().getContent().toString());
                clientes.add(getOne(id));
            }
        } catch (XMLDBException e) {
            System.err.println("Error al obtener clientes: " + e.getMessage());
            e.printStackTrace();
        }
        return clientes;
    }

    @Override
    public Cliente getOne(int id) {
        Cliente cliente = null;
        try {
            XPathQueryService service = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            ResourceSet result = service.query("//cliente[id_cliente=" + id + "]");
            if (result.getSize() == 0) return null;

            cliente = new Cliente();
            cliente.setIdCliente(id);

            result = service.query("//cliente[id_cliente=" + id + "]/nombre/text()");
            if (result.getSize() > 0) cliente.setNombre(result.getResource(0).getContent().toString());

            result = service.query("//cliente[id_cliente=" + id + "]/apellidos/text()");
            if (result.getSize() > 0) cliente.setApellidos(result.getResource(0).getContent().toString());

            result = service.query("//cliente[id_cliente=" + id + "]/direccion/text()");
            if (result.getSize() > 0) cliente.setDireccion(result.getResource(0).getContent().toString());

            result = service.query("//cliente[id_cliente=" + id + "]/edad/text()");
            if (result.getSize() > 0) cliente.setEdad(Integer.parseInt(result.getResource(0).getContent().toString()));

        } catch (XMLDBException e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
            e.printStackTrace();
        }
        return cliente;
    }

    @Override
    public Boolean Create(Cliente cl) {
        try {
            XPathQueryService pathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            ResourceSet idResult = pathService.query("max(doc('/db/ITtech/clientes.xml')//id_cliente/xs:integer(.))");
            int nuevoId = 1;
            if (idResult.getSize() > 0) {
                Object content = idResult.getResource(0).getContent();
                if (content != null && !content.toString().equals("")) {
                    nuevoId = (int) Double.parseDouble(content.toString()) + 1;
                }
            }

            String clienteXML = "<cliente>" +
                    "<id_cliente>" + nuevoId + "</id_cliente>" +
                    "<nombre>" + cl.getNombre() + "</nombre>" +
                    "<apellidos>" + cl.getApellidos() + "</apellidos>" +
                    "<direccion>" + cl.getDireccion() + "</direccion>" +
                    "<edad>" + cl.getEdad() + "</edad>" +
                    "</cliente>";

            XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
            String xquery = "update insert " + clienteXML + " into doc('/db/ITtech/clientes.xml')/clientes";
            service.query(xquery);

            System.out.println("Cliente creado con ID: " + nuevoId);
            return true;

        } catch (XMLDBException e) {
            System.err.println("Error al crear cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean Update(Cliente cl) {
        try {
            XPathQueryService pathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            ResourceSet check = pathService.query("//cliente[id_cliente=" + cl.getIdCliente() + "]");
            if (check.getSize() == 0) {
                System.err.println("Cliente no encontrado");
                return false;
            }

            String clienteXML = "<cliente>" +
                    "<id_cliente>" + cl.getIdCliente() + "</id_cliente>" +
                    "<nombre>" + cl.getNombre() + "</nombre>" +
                    "<apellidos>" + cl.getApellidos() + "</apellidos>" +
                    "<direccion>" + cl.getDireccion() + "</direccion>" +
                    "<edad>" + cl.getEdad() + "</edad>" +
                    "</cliente>";

            XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
            String xquery = "update replace doc('/db/ITtech/clientes.xml')//cliente[id_cliente=" + cl.getIdCliente() + "] with " + clienteXML;
            service.query(xquery);

            System.out.println("Cliente actualizado");
            return true;

        } catch (XMLDBException e) {
            System.err.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean Borrar(int id) {
        try {
            Cliente cl = getOne(id);
            if (cl == null) {
                System.err.println("Cliente no encontrado");
                return false;
            }

            System.out.println("Voy a borrar:\n" + cl.toString());

            XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
            String xquery = "update delete doc('/db/ITtech/clientes.xml')//cliente[id_cliente=" + id + "]";
            service.query(xquery);

            return true;

        } catch (XMLDBException e) {
            System.err.println("Error al borrar: " + e.getMessage());
            return false;
        }
    }
}