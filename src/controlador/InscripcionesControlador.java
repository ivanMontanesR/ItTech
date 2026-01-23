package controlador;

import dao.DAOCursos;
import dao.DAOInscripciones;
import dao.DAOcliente;
import dao.impl.Clientes_Hibernate;
import dao.impl.Cursos_Hibernate;

public class InscripcionesControlador {

	private int tipoBD; 

	public InscripcionesControlador(int tipoBD) {
		this.tipoBD = tipoBD;
	}

	public DAOInscripciones getInscripcionesDAO() {
		switch (tipoBD) {
		case 1:
			return new Inscripciones_Hibernate();
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
