package controlador;

import dao.DAOcliente;
import dao.impl.Clientes_ExistDB;
import dao.impl.Clientes_Hibernate;
import dao.impl.Clientes_Neodatis;

public class ClientesControlador {

	private int tipoBD;

	public ClientesControlador(int tipoBD) {
		this.tipoBD = tipoBD;
	}

	public DAOcliente getClienteDAO() {
		switch (tipoBD) {
		case 1:
			return new Clientes_Hibernate();
		case 2:

			return new Clientes_Neodatis();
		case 3:
			return new Clientes_ExistDB();

		default:
			System.err.println("Tipo de BD no válido");
			return null;
		}
	}
}
