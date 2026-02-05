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

	// Usamos el Util de ExistDB para obtener la instancia de Collection y usarla en todos los Metodos
	private Collection col = ConexionExistDB.getInstancia();

	/*
	 * Metodo para conseguir todos los clientes haciendo una query XPath
	 * que obtiene todos los id_cliente y posteriormente recupera cada cliente completo
	 * añadiéndolos a una lista
	 */
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

	/*
	 * Metodo para recuperar un Cliente con un id que nos de el usuario
	 * Utiliza XPath para buscar el cliente por id y recupera cada campo individualmente
	 */
	@Override
	public Cliente getOne(int id) {
		Cliente cliente = null;
		try {
			XPathQueryService service = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet result = service.query("//cliente[id_cliente=" + id + "]");
			if (result.getSize() == 0)
				return null;

			cliente = new Cliente();
			cliente.setIdCliente(id);

			result = service.query("//cliente[id_cliente=" + id + "]/nombre/text()");
			if (result.getSize() > 0)
				cliente.setNombre(result.getResource(0).getContent().toString());

			result = service.query("//cliente[id_cliente=" + id + "]/apellidos/text()");
			if (result.getSize() > 0)
				cliente.setApellidos(result.getResource(0).getContent().toString());

			result = service.query("//cliente[id_cliente=" + id + "]/direccion/text()");
			if (result.getSize() > 0)
				cliente.setDireccion(result.getResource(0).getContent().toString());

			result = service.query("//cliente[id_cliente=" + id + "]/edad/text()");
			if (result.getSize() > 0)
				cliente.setEdad(Integer.parseInt(result.getResource(0).getContent().toString()));

		} catch (XMLDBException e) {
			System.err.println("Error al buscar cliente: " + e.getMessage());
			e.printStackTrace();
		}
		return cliente;
	}

	/*
	 * Metodo de creacion de cliente el cual recibe el cliente como parametro
	 * Buscamos primero  el máximo id insertado para insertar el siguiente con maxID + 1
	 * Construimos el XML del cliente y lo insertamos con XQuery usando update insert
	 */
	@Override
	public Boolean Create(Cliente cliente) {
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

			String clienteXML = "<cliente>" + "<id_cliente>" + nuevoId + "</id_cliente>" + "<nombre>" + cliente.getNombre()
					+ "</nombre>" + "<apellidos>" + cliente.getApellidos() + "</apellidos>" + "<direccion>"
					+ cliente.getDireccion() + "</direccion>" + "<edad>" + cliente.getEdad() + "</edad>" + "</cliente>";

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

	/*
	 * Metodo para actualizar un Cliente previamente recuperado en el principal
	 *  construimos el nuevo XML y lo reemplazamos
	 * con XQuery usando update replace
	 */
	@Override
	public Boolean Update(Cliente cliente) {
		try {
			

			String clienteXML = "<cliente>" + "<id_cliente>" + cliente.getIdCliente() + "</id_cliente>" + "<nombre>"
					+ cliente.getNombre() + "</nombre>" + "<apellidos>" + cliente.getApellidos() + "</apellidos>" + "<direccion>"
					+ cliente.getDireccion() + "</direccion>" + "<edad>" + cliente.getEdad() + "</edad>" + "</cliente>";

			XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
			String xquery = "update replace doc('/db/ITtech/clientes.xml')//cliente[id_cliente=" + cliente.getIdCliente()
					+ "] with " + clienteXML;
			service.query(xquery);

			return true;

		} catch (XMLDBException e) {
			System.err.println("Error al actualizar: " + e.getMessage());
			return false;
		}
	}

	/*
	 * Metodo para borrar clientes con un id como parametro
	 *  y lo eliminamos con XQuery usando update delete
	 */
	@Override
	public Boolean Borrar(int id) {
		try {
			

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