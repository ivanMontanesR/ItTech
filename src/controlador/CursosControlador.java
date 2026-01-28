package controlador;

import dao.DAOCursos;

import dao.impl.Cursos_Hibernate;
import dao.impl.Cursos_Neodatis;

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
			
			
		default:
			System.err.println("Tipo de BD no válido");
			return null;
		}
	}
}
