package controlador;

import dao.DAOInscripciones;
import dao.impl.Inscripciones_ExistDB;
import dao.impl.Inscripciones_Hibernate;
import dao.impl.Inscripciones_Neodatis;

public class InscripcionesControlador {

	private int tipoBD;

	public InscripcionesControlador(int tipoBD) {
		this.tipoBD = tipoBD;
	}
	// Controlador que gestiona Que implementador de inscripciones  se usara segun el tipo de la base
	// de datos
	public DAOInscripciones getInscripcionDAO() {
		switch (tipoBD) {
		case 1:
			return new Inscripciones_Hibernate();
		case 2:

			return new Inscripciones_Neodatis();
		case 3:
			return new Inscripciones_ExistDB();
		default:
			System.err.println("Tipo de BD no válido");
			return null;
		}
	}
}
