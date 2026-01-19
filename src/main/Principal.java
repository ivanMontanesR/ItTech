package main;

import dao.*;
import util.Usuario;



public class Principal {

	public static void main(String[] args) {
		
		

		System.out.println("   SISTEMA DE GESTION ITtech \n" + "\"Seleccione la base de datos:\n"
				+ "1. MySQL (Hibernate)\n" + "2. Neodatis (Objetos)\n" + "3. eXist-DB (XML)");

		int opcion = Usuario.leerEntero();

		switch (opcion) {
		case 1:

			System.out.println(" Usando MySQL con Hibernate");
			break;
		case 2:

			System.out.println("Usando Neodatis");
			break;
		case 3:

			System.out.println("Usando eXist-DB");
			break;
		default:
			System.out.println("Opción inválida");
			return;
		}

	}
}