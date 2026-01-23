package controlador;

import dao.DAOCursos;
import dao.DAOcliente;
import dao.impl.Clientes_Hibernate;
import dao.impl.Cursos_Hibernate;

public class CursosControlador {

	private int tipoBD; 

	public CursosControlador(int tipoBD) {
		this.tipoBD = tipoBD;
	}

	public DAOCursos getCursosDAO() {
		switch (tipoBD) {
		case 1:
			return new Cursos_Hibernate();
		case 2:
			
			return new Cursos_Neodatis();
		case 3:
			return new Cursos_ExistDB();
			
		default:
			System.err.println("Tipo de BD no válido");
			return null;
		}
	}
}
