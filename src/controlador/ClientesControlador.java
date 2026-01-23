package controlador;

import dao.DAOcliente;
import dao.impl.Clientes_Hibernate;
import dao.impl.Cursos_Hibernate;

public class ClientesControlador {

	private int tipoBD; // 1=MySQL, 2=Neodatis, 3=eXist-DB

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
