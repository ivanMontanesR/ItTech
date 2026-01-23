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

			System.out.println(" Usando MySQL con Hibernate" + "\n¿Que Desea Hacer?" + "\n1)Gestionar Clientes"
					+ "\n2)Gestionar Cursos" + "\n3)Gestionar Inscripciones");
			opcion = Usuario.leerEntero();

			switch (opcion) {
			case 1: {
					System.out.println("	GESTION DE CIENTES 	"
							+ "\n1)Consultar Todos los  CLientes"
							+ "\n2)Consultar Un CLiente"
							+ "\n3)Crear un Cliente Nuevo"
							+ "\n4)Actualizar Un cliente");
					opcion= Usuario.leerEntero();
					switch (opcion) {
					case 1: {
						System.out.println(" 	LISTA DE CLIENTES 	");
						Cliente_dao
						
					}
					default:
						System.out.println("Opcion Invalida");
					}
				break;
			}
			default:
				System.out.println("Opcion Invalida");
			}
			break;
		case 2:

			System.out.println("Usando Neodatis" + "\n¿Que Desea Hacer?" + "\n1)Gestionar Clientes"
					+ "\n2)Gestionar Cursos" + "\n3)Gestionar Inscripciones");
			break;
		case 3:

			System.out.println("Usando eXist-DB" + "\n¿Que Desea Hacer?" + "\n1)Gestionar Clientes"
					+ "\n2)Gestionar Cursos" + "\n3)Gestionar Inscripciones");
			break;
		default:
			System.out.println("Opción inválida");
			return;
		}

	}
}