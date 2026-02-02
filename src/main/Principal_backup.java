package main;

import controlador.ClientesControlador;
import controlador.CursosControlador;
import controlador.InscripcionesControlador;
import dao.DAOcliente;
import dao.DAOCursos;
import dao.DAOInscripciones;
import modelos.Cliente;
import modelos.Curso;
import modelos.Inscripcion;
import util.Usuario;
import java.sql.Date;
import java.util.List;

public class Principal_backup {
	public static void main(String[] args) {
		System.out.println("   SISTEMA DE GESTION ITtech \n" + "Seleccione la base de datos:\n"
				+ "1. MySQL (Hibernate)\n" + "2. Neodatis (Objetos)\n" + "3. eXist-DB (XML)");

		int tipoBD = Usuario.leerEntero();

		String nombreBD = "";
		switch (tipoBD) {
		case 1:
			nombreBD = "MySQL con Hibernate";
			break;
		case 2:
			nombreBD = "Neodatis";
			break;
		case 3:
			nombreBD = "eXist-DB";
			break;
		default:
			System.out.println("Opción inválida");
			return;
		}

		System.out.println("Usando " + nombreBD);
		
		System.out.println("¿Qué Desea Hacer?" + "\n1) Gestionar Clientes" + "\n2) Gestionar Cursos"
				+ "\n3) Gestionar Inscripciones");
		
		int opcion = Usuario.leerEntero();

		switch (opcion) {
		case 1: {
			// Crear controlador de clientes
			ClientesControlador controlador = new ClientesControlador(tipoBD);
			DAOcliente clienteDAO = controlador.getClienteDAO();

			if (clienteDAO == null) {
				System.out.println("No se pudo crear el DAO");
				return;
			}

			System.out.println(
					"    GESTION DE CLIENTES    " + "\n1) Consultar Todos los Clientes" + "\n2) Consultar Un Cliente"
							+ "\n3) Crear un Cliente Nuevo" + "\n4) Actualizar Un Cliente" + "\n5) Borrar Un Cliente");

			opcion = Usuario.leerEntero();

			switch (opcion) {
			case 1: {
				System.out.println("\n    LISTA DE CLIENTES    ");
				List<Cliente> clientes = clienteDAO.getAll();

				if (clientes != null && !clientes.isEmpty()) {
					for (Cliente c : clientes) {
						System.out.println(c);
					}
				} else {
					System.out.println("No hay clientes");
				}
				break;
			}

			case 2: {
				System.out.println("ID del cliente:");
				int id = Usuario.leerEntero();

				Cliente cliente = clienteDAO.getOne(id);
				if (cliente != null) {
					System.out.println(cliente);
				} else {
					System.out.println("Cliente no encontrado");
				}
				break;
			}

			case 3: {
				System.out.println("Nombre:");
				String nombre = Usuario.leerString();

				System.out.println("Apellidos:");
				String apellidos = Usuario.leerString();

				System.out.println("Dirección:");
				String direccion = Usuario.leerString();

				System.out.println("Edad:");
				Integer edad = Usuario.leerEntero();

				Cliente nuevoCliente = new Cliente();
				nuevoCliente.setNombre(nombre);
				nuevoCliente.setApellidos(apellidos);
				nuevoCliente.setDireccion(direccion);
				nuevoCliente.setEdad(edad);

				Boolean creado = clienteDAO.Create(nuevoCliente);

				if (creado) {
					System.out.println("Cliente creado");
				} else {
					System.out.println("Error al crear");
				}
				break;
			}

			case 4: {

				System.out.println("ID del cliente:");
				int id = Usuario.leerEntero();
				Cliente cl = clienteDAO.getOne(id);
				if (cl == null) {
					System.err.println("Cliente no encontrado");
					break;
				}
				System.out.println("Cliente actual: " + cl.toString());
				System.out.println("¿Qué cambiar?" + "\n1. Nombre" + "\n2. Apellidos" + "\n3. Dirección" + "\n4. Edad");
				int opcionUpdate = Usuario.leerEntero();
				switch (opcionUpdate) {
				case 1:
					System.out.println("Nuevo Nombre:");
					String nombre = Usuario.leerString();
					cl.setNombre(nombre);
					break;

				case 2:
					System.out.println("Nuevos Apellidos:");
					String apellidos = Usuario.leerString();
					cl.setApellidos(apellidos);
					break;

				case 3:
					System.out.println("Nueva Dirección:");
					String direccion = Usuario.leerString();
					cl.setDireccion(direccion);
					break;

				case 4:
					System.out.println("Nueva Edad:");
					Integer edad = Usuario.leerEntero();
					cl.setEdad(edad);
					break;

				default:
					System.err.println("Opción no válida");
					break;
				}
				Boolean actualizado = clienteDAO.Update(cl);
				if (actualizado) {
					System.out.println("Cliente actualizado");
				} else {
					System.out.println("Error al Actualizar");
				}
				break;
			}

			case 5: {
				System.out.println("ID del cliente:");
				int id = Usuario.leerEntero();

				Boolean borrado = clienteDAO.Borrar(id);

				if (borrado) {
					System.out.println("Cliente borrado");
				} else {
					System.out.println("Error al borrar");
				}
				break;
			}

			default:
				System.out.println("Opción inválida");
				break;
			}
			break;
		}

		case 2: {
			CursosControlador controladorCurso = new CursosControlador(tipoBD);
			DAOCursos cursoDAO = controladorCurso.getCursosDAO();

			if (cursoDAO == null) {
				System.out.println("No se pudo crear el DAO");
				return;
			}

			System.out
					.println("    GESTION DE CURSOS    " + "\n1) Consultar Todos los Cursos" + "\n2) Consultar Un Curso"
							+ "\n3) Crear un Curso Nuevo" + "\n4) Actualizar Un Curso" + "\n5) Borrar Un Curso");

			opcion = Usuario.leerEntero();

			switch (opcion) {
			case 1: {
				System.out.println("\n    LISTA DE CURSOS    ");
				List<Curso> cursos = cursoDAO.getAll();

				if (cursos != null && !cursos.isEmpty()) {
					for (Curso c : cursos) {
						System.out.println(c);
					}
				} else {
					System.out.println("No hay cursos");
				}
				break;
			}

			case 2: {
				System.out.println("ID del curso:");
				int id = Usuario.leerEntero();

				Curso curso = cursoDAO.getOne(id);
				if (curso != null) {
					System.out.println(curso);
				} else {
					System.out.println("Curso no encontrado");
				}
				break;
			}

			case 3: {
				System.out.println("Nombre del Curso:");
				String nombre = Usuario.leerString();

				System.out.println("Descripción:");
				String descripcion = Usuario.leerString();

				System.out.println("Duración (horas):");
				Integer duracion = Usuario.leerEntero();

				Curso nuevoCurso = new Curso();
				nuevoCurso.setNombreCurso(nombre);
				nuevoCurso.setDescripcion(descripcion);
				nuevoCurso.setDuracion(duracion);

				Boolean creado = cursoDAO.Create(nuevoCurso);

				if (creado) {
					System.out.println("Curso creado");
				} else {
					System.out.println("Error al crear");
				}
				break;
			}

			case 4: {
				System.out.println("ID del curso:");
				int id = Usuario.leerEntero();

				Curso curso = cursoDAO.getOne(id);
				if (curso == null) {
					System.err.println("Curso no encontrado");
					break;
				}

				System.out.println("Curso actual: " + curso.toString());
				System.out.println("¿Que desea  cambiar?" + "\n1. Nombre" + "\n2. Descripción" + "\n3. Duración");
				int opcionUpdate = Usuario.leerEntero();

				switch (opcionUpdate) {
				case 1:
					System.out.println("Nuevo Nombre del Curso:");
					String nombre = Usuario.leerString();
					curso.setNombreCurso(nombre);
					break;

				case 2:
					System.out.println("Nueva Descripcion:");
					String descripcion = Usuario.leerString();
					curso.setDescripcion(descripcion);
					break;

				case 3:
					System.out.println("Nueva Duracion:");
					int duracion = Usuario.leerEntero();
					curso.setDuracion(duracion);
					break;

				default:
					System.err.println("Opción no válida");

				}

				Boolean actualizado = cursoDAO.Update(curso);

				if (actualizado) {
					System.out.println("Curso actualizado");
				} else {
					System.out.println("Error al actualizar");
				}
				break;
			}

			case 5: {
				System.out.println("ID del curso:");
				int id = Usuario.leerEntero();

				Boolean borrado = cursoDAO.Borrar(id);

				if (borrado) {
					System.out.println("Curso borrado");
				} else {
					System.out.println("Error al borrar");
				}
				break;
			}

			default:
				System.out.println("Opción inválida");
				break;
			}
			break;
		}

		case 3: {
			InscripcionesControlador controlador = new InscripcionesControlador(tipoBD);
			DAOInscripciones inscripcionDAO = controlador.getInscripcionDAO();

			ClientesControlador controladorCli = new ClientesControlador(tipoBD);
			DAOcliente clienteDAO = controladorCli.getClienteDAO();

			CursosControlador controladorCur = new CursosControlador(tipoBD);
			DAOCursos cursoDAO = controladorCur.getCursosDAO();

			System.out.println("    GESTION DE INSCRIPCIONES    " + "\n1) Consultar Todas las Inscripciones"
					+ "\n2) Consultar Una Inscripción" + "\n3) Crear una Inscripción Nueva"
					+ "\n4) Actualizar Una Inscripción" + "\n5) Borrar Una Inscripción");

			opcion = Usuario.leerEntero();

			switch (opcion) {
			case 1: {
				System.out.println("\n    LISTA DE INSCRIPCIONES    ");
				List<Inscripcion> inscripciones = inscripcionDAO.getAll();

				if (inscripciones != null && !inscripciones.isEmpty()) {
					for (Inscripcion i : inscripciones) {
						System.out.println(i);
					}
				} else {
					System.out.println("No hay inscripciones");
				}
				break;
			}

			case 2: {
				System.out.println("ID de la inscripción:");
				int id = Usuario.leerEntero();

				Inscripcion inscripcion = inscripcionDAO.getOne(id);
				if (inscripcion != null) {
					System.out.println(inscripcion);
				} else {
					System.out.println("Inscripción no encontrada");
				}
				break;
			}

			case 3: {

				System.out.println("ID del Cliente:");
				int idCliente = Usuario.leerEntero();

				System.out.println("ID del Curso:");
				int idCurso = Usuario.leerEntero();

				System.out.println("Fecha de Inscripción (YYYY-MM-DD):");
				String fechaStr = Usuario.leerString();

				try {

					Cliente cliente = clienteDAO.getOne(idCliente);

					if (cliente == null) {
						System.err.println("Cliente con ID " + idCliente + " no encontrado");

					}

					Curso curso = cursoDAO.getOne(idCurso);

					if (curso == null) {
						System.err.println("Curso con ID " + idCurso + " no encontrado");

					}

					Date fecha = Date.valueOf(fechaStr);

					Inscripcion nuevaInscripcion = new Inscripcion(curso, cliente, fecha);

					Boolean creada = inscripcionDAO.Create(nuevaInscripcion);

					if (creada) {
						System.out.println("Inscripción creada exitosamente");
					} else {
						System.out.println("Error al crear inscripción");
					}

				} catch (IllegalArgumentException e) {
					System.err.println("Formato de fecha inválido. Use YYYY-MM-DD");
				}
				break;
			}

			case 4: {
				System.out.println("ID de la inscripción:");
				int id = Usuario.leerEntero();

				Inscripcion inscripcion = inscripcionDAO.getOne(id);
				if (inscripcion == null) {
					System.err.println("Inscripción no encontrada");
					break;
				}

				System.out.println("Inscripción actual: " + inscripcion.toString());
				System.out.println("¿Qué cambiar?" + "\n1. Cliente" + "\n2. Curso" + "\n3. Fecha de Inscripción");
				int opcionUpdate = Usuario.leerEntero();

				switch (opcionUpdate) {
				case 1:
					// Cambiar cliente
					System.out.println("Nuevo ID de Cliente:");
					int idCliente = Usuario.leerEntero();
					Cliente cliente = clienteDAO.getOne(idCliente);

					if (cliente == null) {
						System.err.println("Cliente no encontrado");

					}
					inscripcion.setClientes(cliente);
					break;

				case 2:
					// Cambiar curso
					System.out.println("Nuevo ID de Curso:");
					int idCurso = Usuario.leerEntero();
					Curso curso = cursoDAO.getOne(idCurso);

					if (curso == null) {
						System.err.println("Curso no encontrado");

					}
					inscripcion.setCursos(curso);
					break;

				case 3:
					// Cambiar fecha
					System.out.println("Nueva Fecha (YYYY-MM-DD):");
					String fechaStr = Usuario.leerString();
					try {
						Date fecha = Date.valueOf(fechaStr);
						inscripcion.setFechaInscripcion(fecha);
					} catch (IllegalArgumentException e) {
						System.err.println("Formato de fecha inválido. Use YYYY-MM-DD");

					}
					break;

				default:
					System.err.println("Opción no válida");

				}
				Boolean actualizada = inscripcionDAO.Update(inscripcion);

				if (actualizada) {
					System.out.println("Inscripción actualizada");
				} else {
					System.out.println("Error al actualizar");
				}
				break;
			}

			case 5: {
				System.out.println("ID de la inscripción:");
				int id = Usuario.leerEntero();

				Boolean borrada = inscripcionDAO.Borrar(id);

				if (borrada) {
					System.out.println("Inscripción borrada");
				} else {
					System.out.println("Error al borrar");
				}
				break;
			}

			default:
				System.out.println("Opción inválida");
				break;
			}
			break;
		}

		}
	}
}