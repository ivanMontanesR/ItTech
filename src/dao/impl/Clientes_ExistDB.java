package dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XUpdateQueryService;
import dao.DAOcliente;
import modelos.Cliente;
import util.Usuario;

public class Clientes_ExistDB implements DAOcliente {

    private static final String URI = "xmldb:exist://192.168.56.102:8080/exist/xmlrpc/db/ITtech";
    private static final String DRIVER = "org.exist.xmldb.DatabaseImpl";
    private static final String USER = "ivan";
    private static final String PASSWORD = "openpgpwd";

    private Collection conectar() throws XMLDBException {
        try {
            Class<?> cl = Class.forName(DRIVER);
            Database database = (Database) cl.getDeclaredConstructor().newInstance();
            DatabaseManager.registerDatabase(database);
            return DatabaseManager.getCollection(URI, USER, PASSWORD);
        } catch (Exception e) {
            throw new XMLDBException(0, "Error al conectar: " + e.getMessage());
        }
    }

    @Override
    public List<Cliente> getAll() {
        List<Cliente> clientes = new ArrayList<>();
        Collection col = null;
        
        try {
            col = conectar();
            XPathQueryService service = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            ResourceSet result = service.query("//cliente");
            
            ResourceIterator it = result.getIterator();
            while (it.hasMoreResources()) {
                Resource res = it.nextResource();
                clientes.add(parseCliente(res.getContent().toString()));
            }
            
        } catch (XMLDBException e) {
            e.printStackTrace();
        } finally {
            if (col != null) {
                try { col.close(); } catch (XMLDBException e) {}
            }
        }
        return clientes;
    }

    @Override
    public Cliente getOne(int id) {
        Collection col = null;
        
        try {
            col = conectar();
            XPathQueryService service = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            ResourceSet result = service.query("//cliente[id_cliente=" + id + "]");
            
            if (result.getSize() > 0) {
                Resource res = result.getResource(0);
                return parseCliente(res.getContent().toString());
            }
            
        } catch (XMLDBException e) {
            e.printStackTrace();
        } finally {
            if (col != null) {
                try { col.close(); } catch (XMLDBException e) {}
            }
        }
        return null;
    }

    @Override
    public Boolean Create(Cliente cl) {
        Collection col = null;
        
        try {
            col = conectar();
            
            // Obtener último ID
            int nuevoId = obtenerUltimoId(col) + 1;
            
            // Crear XML
            String xml = "<cliente>" +
                        "<id_cliente>" + nuevoId + "</id_cliente>" +
                        "<nombre>" + cl.getNombre() + "</nombre>" +
                        "<apellidos>" + cl.getApellidos() + "</apellidos>" +
                        "<direccion>" + cl.getDireccion() + "</direccion>" +
                        "<edad>" + cl.getEdad() + "</edad>" +
                        "</cliente>";
            
            // Insertar
            XUpdateQueryService service = (XUpdateQueryService) col.getService("XUpdateQueryService", "1.0");
            String xupdate = "<xu:modifications version=\"1.0\" xmlns:xu=\"http://www.xmldb.org/xupdate\">" +
                            "<xu:append select=\"/clientes\">" +
                            xml +
                            "</xu:append>" +
                            "</xu:modifications>";
            
            service.updateResource("clientes.xml", xupdate);
            
            System.out.println("Cliente creado con ID: " + nuevoId);
            return true;
            
        } catch (XMLDBException e) {
            System.err.println("Error al crear cliente: " + e.getMessage());
            return false;
        } finally {
            if (col != null) {
                try { col.close(); } catch (XMLDBException e) {}
            }
        }
    }

    @Override
    public Boolean Update(Cliente cl) {
        Collection col = null;
        
        try {
            col = conectar();
            Cliente cliente = getOne(id);
            
            if (cliente == null) {
                System.err.println("Cliente no encontrado");
                return false;
            }
            
            System.out.println("Cliente actual:\n" + cliente);
            
            String campo = "";
            String valor = "";
            
            switch (opcion) {
                case 1:
                    System.out.println("Nuevo Nombre:");
                    valor = Usuario.leerString();
                    campo = "nombre";
                    break;
                case 2:
                    System.out.println("Nuevos Apellidos:");
                    valor = Usuario.leerString();
                    campo = "apellidos";
                    break;
                case 3:
                    System.out.println("Nueva Dirección:");
                    valor = Usuario.leerString();
                    campo = "direccion";
                    break;
                case 4:
                    System.out.println("Nueva Edad:");
                    valor = String.valueOf(Usuario.leerEntero());
                    campo = "edad";
                    break;
                default:
                    System.err.println("Opción no válida");
                    return false;
            }
            
            XUpdateQueryService service = (XUpdateQueryService) col.getService("XUpdateQueryService", "1.0");
            String xupdate = "<xu:modifications version=\"1.0\" xmlns:xu=\"http://www.xmldb.org/xupdate\">" +
                            "<xu:update select=\"//cliente[id_cliente=" + id + "]/" + campo + "\">" +
                            valor +
                            "</xu:update>" +
                            "</xu:modifications>";
            
            service.updateResource("clientes.xml", xupdate);
            
            System.out.println("Cliente actualizado correctamente");
            return true;
            
        } catch (XMLDBException e) {
            System.err.println("Error al actualizar: " + e.getMessage());
            return false;
        } finally {
            if (col != null) {
                try { col.close(); } catch (XMLDBException e) {}
            }
        }
    }

    @Override
    public Boolean Borrar(int id) {
        Collection col = null;
        
        try {
            col = conectar();
            Cliente cl = getOne(id);
            
            if (cl == null) {
                System.err.println("Cliente no encontrado");
                return false;
            }
            
            System.out.println("Voy a borrar:\n" + cl.toString());
            
            XUpdateQueryService service = (XUpdateQueryService) col.getService("XUpdateQueryService", "1.0");
            String xupdate = "<xu:modifications version=\"1.0\" xmlns:xu=\"http://www.xmldb.org/xupdate\">" +
                            "<xu:remove select=\"//cliente[id_cliente=" + id + "]\" />" +
                            "</xu:modifications>";
            
            service.updateResource("clientes.xml", xupdate);
            
            return true;
            
        } catch (XMLDBException e) {
            System.err.println("Error al borrar: " + e.getMessage());
            return false;
        } finally {
            if (col != null) {
                try { col.close(); } catch (XMLDBException e) {}
            }
        }
    }

    private int obtenerUltimoId(Collection col) throws XMLDBException {
        XPathQueryService service = (XPathQueryService) col.getService("XPathQueryService", "1.0");
        
        
        String query = "max(doc('ITtech_table_clientes.xml')//id_cliente/xs:integer(.))";
        ResourceSet result = service.query(query);
        
        if (result.getSize() > 0) {
            Resource res = result.getResource(0);
            Object content = res.getContent();
            
            if (content != null && !content.toString().equals("")) {
                // eXist-db puede devolver el número como String o Double
                return (int) Double.parseDouble(content.toString());
            }
        }
        return 0; // Si no hay clientes, empezamos desde 0
    }

    private Cliente parseCliente(String xml) {
        Cliente cliente = new Cliente();
        
        cliente.setIdCliente(extractValue(xml, "id_cliente"));
        cliente.setNombre(extractText(xml, "nombre"));
        cliente.setApellidos(extractText(xml, "apellidos"));
        cliente.setDireccion(extractText(xml, "direccion"));
        cliente.setEdad(extractValue(xml, "edad"));
        
        return cliente;
    }

    private int extractValue(String xml, String tag) {
        String pattern = "<" + tag + ">(.*?)</" + tag + ">";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(xml);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        return 0;
    }

    private String extractText(String xml, String tag) {
        String pattern = "<" + tag + ">(.*?)</" + tag + ">";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(xml);
        if (m.find()) {
            return m.group(1);
        }
        return "";
    }
}