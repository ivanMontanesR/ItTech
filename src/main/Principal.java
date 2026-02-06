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

/**
 * Clase principal de gestion ITtech Permite gestionar clientes, cursos e
 * inscripciones
 */
public class Principal {

	public static void main(String[] args) {
		int tipoBD = seleccionarBD();

		if (tipoBD == 0) {
			Usuario.cerrarScanner();
			return;
		}
		String nombreBD = "";
		switch (tipoBD) {

		case 1: {
			nombreBD = "MySQL con Hibernate";
			break;
		}

		case 2: {
			nombreBD = "Neodatis";
			break;
		}

		case 3: {
			nombreBD = "eXist-DB";
			break;
		}

		}
		System.out.println("Usando " + nombreBD);

		int opcion;
		do {
			opcion = menuPrincipal();
			switch (opcion) {
			
			case 1: {
				gestionarClientes(tipoBD);
				break;
			}
			case 2: {
				gestionarCursos(tipoBD);
				break;
			}
			case 3: {
				gestionarInscripciones(tipoBD);
				break;
			}
			case 0 :{
				break;
			}
				

			default:
				System.out.println("Opcion invalida");
			}
		} while (opcion != 0);

		Usuario.cerrarScanner();
	}

	/**
	 * Solicita al usuario que seleccione el tipo de base de datos Repite hasta
	 * obtener una opcion valida (1-3) o salir (0)
	 */
	private static int seleccionarBD() {
		int tipoBD;
		do {
			System.out.println("Seleccione la base de datos:");
			System.out.println("1. MySQL ");
			System.out.println("2. Neodatis ");
			System.out.println("3. Exist-DB ");
			System.out.println("0. Salir ");

			tipoBD = Usuario.leerEnteroPositivo();

			if (tipoBD == 0) {
				return 0;
			}

			if (tipoBD < 1 || tipoBD > 3) {
				System.out.println("Opcion invalida");
			}

		} while (tipoBD < 0 || tipoBD > 3);

		return tipoBD;
	}

	private static int menuPrincipal() {
		// Menu De Opciones para elegir BD
		System.out.println("\nQue Desea Hacer?");
		System.out.println("1) Gestionar Clientes");
		System.out.println("2) Gestionar Cursos");
		System.out.println("3) Gestionar Inscripciones");
		System.out.println("0) Salir");
		int opcion = Usuario.leerEnteroPositivo();
		return opcion;
	}

	/**
	 * Gestiona todas las operaciones de los clientes para crear borrar actualizar y
	 * listar
	 */
	private static void gestionarClientes(int tipoBD) {
		ClientesControlador controlador = new ClientesControlador(tipoBD);
		DAOcliente clienteDAO = controlador.getClienteDAO();

		if (clienteDAO == null) {
			System.out.println("No se pudo crear el dao");
			return;
		}

		int opcionCliente;
		do {
			// Menu Gestor De Clientes
			System.out.println("\n    GESTION DE CLIENTES  ");
			System.out.println("1) Consultar Todos los Clientes");
			System.out.println("2) Consultar Un Cliente");
			System.out.println("3) Crear un Cliente Nuevo");
			System.out.println("4) Actualizar Un Cliente");
			System.out.println("5) Borrar Un Cliente");
			System.out.println("0) Volver");

			opcionCliente = Usuario.leerEnteroPositivo();

			switch (opcionCliente) {
			case 1:
				consultarClientes(clienteDAO);
				break;
			case 2:
				consultarUnCliente(clienteDAO);
				break;
			case 3:
				crearCliente(clienteDAO);
				break;
			case 4:
				actualizarCliente(clienteDAO);
				break;
			case 5:
				borrarCliente(clienteDAO, tipoBD);
				break;
			case 0:
				break;
			default:
				System.out.println("Opcion invalida");
			}
		} while (opcionCliente != 0);
	}

	// Muestra todos los clientes
	private static void consultarClientes(DAOcliente clienteDAO) {
		System.out.println("\n    LISTA DE CLIENTES    ");
		List<Cliente> clientes = clienteDAO.getAll();

		if (clientes != null && !clientes.isEmpty()) {
			for (Cliente c : clientes) {
				System.out.println(c);
			}
		} else {
			System.out.println("No hay clientes");
		}
	}

	// Muestra un cliente Dado por ID
	private static void consultarUnCliente(DAOcliente clienteDAO) {
		System.out.println("ID del cliente:");
		int id = Usuario.leerEnteroPositivo();

		Cliente cliente = clienteDAO.getOne(id);
		if (cliente != null) {
			System.out.println(cliente);
		} else {
			System.out.println("Cliente no encontrado");
		}
	}

	// Creamos un cliente nuevo
	private static void crearCliente(DAOcliente clienteDAO) {
		System.out.println("Nombre:");
		String nombre = Usuario.leerString();

		System.out.println("Apellidos:");
		String apellidos = Usuario.leerString();

		System.out.println("Direccion:");
		String direccion = Usuario.leerAlfanumericos();

		System.out.println("Edad:");
		Integer edad = Usuario.leerEnteroRango(0, 120);

		Cliente cliente = new Cliente();
		cliente.setNombre(nombre);
		cliente.setApellidos(apellidos);
		cliente.setDireccion(direccion);
		cliente.setEdad(edad);
		Boolean creado = clienteDAO.Create(cliente);

		if (creado) {
			System.out.println("Cliente creado");
		} else {
			System.out.println("Error al crear");
		}
	}

	// Actualizamos un cliente
	private static void actualizarCliente(DAOcliente clienteDAO) {
		Cliente cliente = null;
		while (cliente == null) {
			System.out.println("ID del cliente:");
			int id = Usuario.leerEnteroPositivo();
			cliente = clienteDAO.getOne(id);

			if (cliente == null) {
				System.out.println("Cliente no encontrado. Introduce otro ID");
				continue;
			}
		}
		System.out.println("Cliente actual: " + cliente.toString());
		System.out.println("Que cambiar?");
		System.out.println("1. Nombre");
		System.out.println("2. Apellidos");
		System.out.println("3. Direccion");
		System.out.println("4. Edad");
		System.out.println("0. Cancelar");

		int opcionUpdate = Usuario.leerEnteroPositivo();

		switch (opcionUpdate) {
		case 1:
			System.out.println("Nuevo Nombre:");
			String nombre = Usuario.leerString();
			cliente.setNombre(nombre);
			break;

		case 2:
			System.out.println("Nuevos Apellidos:");
			String apellidos = Usuario.leerString();
			cliente.setApellidos(apellidos);
			break;

		case 3:
			System.out.println("Nueva Direccion:");
			String direccion = Usuario.leerAlfanumericos();
			cliente.setDireccion(direccion);
			break;

		case 4:
			System.out.println("Nueva Edad:");
			Integer edad = Usuario.leerEnteroRango(0, 120);
			cliente.setEdad(edad);
			break;

		case 0:
			break;

		default:
			System.out.println("Opcion no valida");
			break;
		}

		if (opcionUpdate >= 1 && opcionUpdate <= 4) {
			Boolean actualizado = clienteDAO.Update(cliente);
			if (actualizado) {
				System.out.println("Cliente actualizado");
			} else {
				System.out.println("Error al Actualizar");
			}
		}
	}

	// Borramos un CLiente Comprobando Previamente que no haya inscripciones activas
	// de ese cliente
	private static void borrarCliente(DAOcliente clienteDAO, int tipoBD) {
		InscripcionesControlador controladorInscripciones = new InscripcionesControlador(tipoBD);
		DAOInscripciones inscripcionDAO = controladorInscripciones.getInscripcionDAO();
		boolean borrado = false;

		while (!borrado) {
			System.out.println("id del cliente:");
			int id = Usuario.leerEnteroPositivo();
			Cliente cliente = clienteDAO.getOne(id);

			if (cliente == null) {
				System.out.println("No existe ningun cliente con ID " + id);
				continue;
			}

			System.out.println("Cliente a borrar\n" + cliente);

			// Buscamos inscripciones activas del cliente para borrarlas ya que son claves
			// foraneas
			List<Inscripcion> inscripciones = inscripcionDAO.getAll();
			int contador = 0;

			for (Inscripcion i : inscripciones) {
				if (i.getClientes().getIdCliente() == id) {
					contador++;
					System.out.println("El Cliente Seleccionado tiene inscripciones activas\n Inscripcion " 
							+ i.getIdInscripcion() + ": del cliente " + i.getClientes().getNombre() + " y curso: "+ i.getCursos().getNombreCurso());
				}
			}

			// Despues de comprobar se lo mostramos al cliente para que decida si quiere
			// borrarlas o no
			if (contador > 0) {
				System.out.println("\n¿Quiere borrar las  inscripciones y luego el cliente? (S/N)");
				boolean confirmacion = Usuario.leerConfirmacion();

				if (confirmacion) {
					for (Inscripcion i : inscripciones) {
						if (i.getClientes().getIdCliente() == id) {
							inscripcionDAO.Borrar(i.getIdInscripcion());
						}
					}

					borrado = clienteDAO.Borrar(id);

					if (borrado) {
						System.out.println("Cliente e inscripciones borrados correctamente");
					}
				} else {
					System.out.println("Operacion cancelada");
				}
			} else {
				System.out.println("¿Desea eliminar este cliente? (S/N)");
				boolean confirmacion = Usuario.leerConfirmacion();

				if (confirmacion) {
					borrado = clienteDAO.Borrar(id);

					if (borrado) {
						System.out.println("Cliente borrado correctamente");
					} else {
						System.out.println("Error al borrar. Intentalo de nuevo");
					}
				} else {
					System.out.println("Borrado Cancelado, introduce otro ID");
				}
			}
		}
	}

	/**
	 * Gestiona todas las operaciones de los cursos para crear borrar actualizar y
	 * listar
	 */
	private static void gestionarCursos(int tipoBD) {
		CursosControlador controladorCurso = new CursosControlador(tipoBD);
		DAOCursos cursoDAO = controladorCurso.getCursosDAO();

		if (cursoDAO == null) {
			System.out.println("No se pudo crear el dao");
			return;
		}

		int opcionCurso;
		do {
			// Menu Gestor De Cursos
			System.out.println("\n    GESTION DE CURSOS   ");
			System.out.println("1) Consultar Todos los Cursos");
			System.out.println("2) Consultar Un Curso");
			System.out.println("3) Crear un Curso Nuevo");
			System.out.println("4) Actualizar Un Curso");
			System.out.println("5) Borrar Un Curso");
			System.out.println("0) Volver");

			opcionCurso = Usuario.leerEnteroPositivo();

			switch (opcionCurso) {
			case 1:
				consultarCursos(cursoDAO);
				break;
			case 2:
				consultarUnCurso(cursoDAO);
				break;
			case 3:
				crearCurso(cursoDAO);
				break;
			case 4:
				actualizarCurso(cursoDAO);
				break;
			case 5:
				borrarCurso(cursoDAO, tipoBD);
				break;
			case 0:
				break;
			default:
				System.out.println("Opcion invalida");
			}
		} while (opcionCurso != 0);
	}

	// Mostramos todos los cursos
	private static void consultarCursos(DAOCursos cursoDAO) {
		System.out.println("\n    LISTA DE CURSOS    ");
		List<Curso> cursos = cursoDAO.getAll();

		if (cursos != null && !cursos.isEmpty()) {
			for (Curso c : cursos) {
				System.out.println(c);
			}
		} else {
			System.out.println("No hay cursos");
		}
	}

	// Mostramos un curso
	private static void consultarUnCurso(DAOCursos cursoDAO) {
		System.out.println("ID del curso:");
		int id = Usuario.leerEnteroPositivo();

		Curso curso = cursoDAO.getOne(id);
		if (curso != null) {
			System.out.println(curso);
		} else {
			System.out.println("Curso no encontrado");
		}
	}

	// Creamos un curso
	private static void crearCurso(DAOCursos cursoDAO) {
		System.out.println("Nombre del Curso:");
		String nombre = Usuario.leerString();

		System.out.println("Descripcion:");
		String descripcion = Usuario.leerString();

		System.out.println("Duracion (horas):");
		Integer duracion = Usuario.leerEnteroPositivo();

		Curso curso = new Curso();
		curso.setNombreCurso(nombre);
		curso.setDescripcion(descripcion);
		curso.setDuracion(duracion);

		Boolean creado = cursoDAO.Create(curso);

		if (creado) {
			System.out.println("Curso creado");
		} else {
			System.out.println("Error al crear");
		}
	}

	// Actualizamos un curso
	private static void actualizarCurso(DAOCursos cursoDAO) {
		Curso curso = null;
		while (curso == null) {
			System.out.println("ID del Curso:");
			int id = Usuario.leerEnteroPositivo();
			curso = cursoDAO.getOne(id);

			if (curso == null) {
				System.out.println("Curso no encontrado. Introduce otro ID");
				continue;
			}
		}

		System.out.println("Curso actual: " + curso.toString());
		System.out.println("Que desea cambiar?");
		System.out.println("1. Nombre");
		System.out.println("2. Descripcion");
		System.out.println("3. Duracion");
		System.out.println("0. Cancelar");

		int opcionUpdate = Usuario.leerEnteroPositivo();

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
			int duracion = Usuario.leerEnteroPositivo();
			curso.setDuracion(duracion);
			break;

		case 0:
			break;

		default:
			System.out.println("Opcion no valida");
		}

		if (opcionUpdate >= 1 && opcionUpdate <= 3) {
			Boolean actualizado = cursoDAO.Update(curso);

			if (actualizado) {
				System.out.println("Curso actualizado");
			} else {
				System.out.println("Error al actualizar");
			}
		}
	}

	// Borramos un curso revisando tambien que el curso no tenga inscripciones
	// activas
	private static void borrarCurso(DAOCursos cursoDAO, int tipoBD) {
		InscripcionesControlador controladorInscripciones = new InscripcionesControlador(tipoBD);
		DAOInscripciones inscripcionDAO = controladorInscripciones.getInscripcionDAO();
		boolean borrado = false;

		while (!borrado) {
			System.out.println("id del curso:");
			int id = Usuario.leerEnteroPositivo();
			Curso curso = cursoDAO.getOne(id);

			if (curso == null) {
				System.out.println("No existe ningun curso con ID " + id);
				continue;
			}

			System.out.println("Curso a borrar\n" + curso);

			// Buscar inscripciones del curso activas por lo mismo de las claves foraneas
			List<Inscripcion> inscripciones = inscripcionDAO.getAll();
			int contador = 0;

			for (Inscripcion i : inscripciones) {
				if (i.getCursos().getIdCurso() == id) {
					contador++;
					System.out.println("El Curso Seleccionado tiene inscripciones activas\n Inscripcion " 
							+ i.getIdInscripcion() + ": del cliente :" + i.getClientes().getNombre() + " y curso: "+ i.getCursos().getNombreCurso());
				}
			}

			if (contador > 0) {
				System.out.println("\n¿Quiere borrar las  inscripciones y luego el curso? (S/N)");
				boolean confirmacion = Usuario.leerConfirmacion();

				if (confirmacion) {
					for (Inscripcion i : inscripciones) {
						if (i.getCursos().getIdCurso() == id) {
							inscripcionDAO.Borrar(i.getIdInscripcion());
						}
					}

					borrado = cursoDAO.Borrar(id);

					if (borrado) {
						System.out.println("Curso e inscripciones borrados correctamente");
					}
				} else {
					System.out.println("Operacion cancelada");
				}
			} else {
				System.out.println("¿Desea eliminar este Curso? (S/N)");
				boolean confirmacion = Usuario.leerConfirmacion();

				if (confirmacion) {
					borrado = cursoDAO.Borrar(id);

					if (borrado) {
						System.out.println("Curso borrado correctamente");
					} else {
						System.out.println("Error al borrar. Intentalo de nuevo");
					}
				} else {
					System.out.println("Borrado Cancelado, introduce otro id");
				}
			}
		}
	}

	/**
	 * Gestiona todas las operaciones de las Inscripciones para crear borrar
	 * actualizar y listar
	 */
	private static void gestionarInscripciones(int tipoBD) {
		InscripcionesControlador controlador = new InscripcionesControlador(tipoBD);
		DAOInscripciones inscripcionDAO = controlador.getInscripcionDAO();

		ClientesControlador controladorCli = new ClientesControlador(tipoBD);
		DAOcliente clienteDAO = controladorCli.getClienteDAO();

		CursosControlador controladorCur = new CursosControlador(tipoBD);
		DAOCursos cursoDAO = controladorCur.getCursosDAO();

		if (inscripcionDAO == null || clienteDAO == null || cursoDAO == null) {
			System.out.println("No se pudo crear el DAO");
			return;
		}

		int opcionInscripcion;
		do {
			// Menu Gestor De Inscripciones
			System.out.println("\n    GESTION DE INSCRIPCIONES   ");
			System.out.println("1) Consultar Todas las Inscripciones");
			System.out.println("2) Consultar Una Inscripcion");
			System.out.println("3) Crear una Inscripcion Nueva");
			System.out.println("4) Actualizar Una Inscripcion");
			System.out.println("5) Borrar Una Inscripcion");
			System.out.println("0) Volver");

			opcionInscripcion = Usuario.leerEnteroPositivo();

			switch (opcionInscripcion) {
			case 1:
				consultarInscripciones(inscripcionDAO);
				break;
			case 2:
				consultarUnaInscripcion(inscripcionDAO);
				break;
			case 3:
				crearInscripcion(inscripcionDAO, clienteDAO, cursoDAO);
				break;
			case 4:
				actualizarInscripcion(inscripcionDAO, clienteDAO, cursoDAO);
				break;
			case 5:
				borrarInscripcion(inscripcionDAO);
				break;
			case 0:
				break;
			default:
				System.out.println("Opcion invalida");
			}
		} while (opcionInscripcion != 0);
	}

	// Mostramos todas las inscripciones
	private static void consultarInscripciones(DAOInscripciones inscripcionDAO) {
		System.out.println("\n    LISTA DE INSCRIPCIONES    ");
		List<Inscripcion> inscripciones = inscripcionDAO.getAll();

		if (inscripciones != null && !inscripciones.isEmpty()) {
			for (Inscripcion i : inscripciones) {
				System.out.println(i);
			}
		} else {
			System.out.println("No hay inscripciones");
		}
	}

	// Mostramos una inscripcion
	private static void consultarUnaInscripcion(DAOInscripciones inscripcionDAO) {
		System.out.println("ID de la inscripcion:");
		int id = Usuario.leerEnteroPositivo();

		Inscripcion inscripcion = inscripcionDAO.getOne(id);
		if (inscripcion != null) {
			System.out.println(inscripcion);
		} else {
			System.out.println("Inscripcion no encontrada");
		}
	}

	// Creamos una inscripcion Nueva y la insertamos en la base de datos
	private static void crearInscripcion(DAOInscripciones inscripcionDAO, DAOcliente clienteDAO, DAOCursos cursoDAO) {
		Cliente cliente = null;
		Curso curso = null;
		try {
			while (cliente == null) {
				System.out.println("ID del cliente:");
				int id = Usuario.leerEnteroPositivo();
				cliente = clienteDAO.getOne(id);

				if (cliente == null) {
					System.out.println("Cliente no encontrado. Introduce otro ID");
					continue;
				}

				System.out.println(cliente);
				System.out.println("¿Es este el cliente correcto? S/N");
				boolean confirmacion = Usuario.leerConfirmacion();

				if (!confirmacion) {
					cliente = null;
				}
			}

			while (curso == null) {
				System.out.println("ID del curso:");
				int idCurso = Usuario.leerEnteroPositivo();
				curso = cursoDAO.getOne(idCurso);

				if (curso == null) {
					System.out.println("Curso no encontrado. Introduce otro ID");
					continue;
				}

				System.out.println(curso);
				System.out.println("¿Es este el curso correcto? S/N");
				boolean confirmacion = Usuario.leerConfirmacion();

				if (!confirmacion) {
					curso = null;
				}
			}

			System.out.println("Fecha de Inscripcion (YYYY-MM-DD):");
			Date fecha = Usuario.leerFechaSQL();

			Inscripcion inscripcion = new Inscripcion(curso, cliente, fecha);

			Boolean creada = inscripcionDAO.Create(inscripcion);

			if (creada) {
				System.out.println("Inscripcion creada exitosamente");
			} else {
				System.out.println("Error al crear inscripcion");
			}

		} catch (IllegalArgumentException e) {
			System.out.println("Formato de fecha invalido. usa YYYY-MM-DD");
		}
	}

	// Actualizamos una inscripcion haciendo comprobaciones de clientes y cursos
	private static void actualizarInscripcion(DAOInscripciones inscripcionDAO, DAOcliente clienteDAO,
			DAOCursos cursoDAO) {
		Inscripcion inscripcion = null;
		while (inscripcion == null) {
			System.out.println("ID de la Inscripcion:");
			int id = Usuario.leerEnteroPositivo();
			inscripcion = inscripcionDAO.getOne(id);

			if (inscripcion == null) {
				System.out.println("Inscripcion no encontrada. Introduce otro ID");
				continue;
			}
		}

		System.out.println("Inscripcion actual: " + inscripcion.toString());
		System.out.println("Que cambiar?");
		System.out.println("1. Cliente");
		System.out.println("2. Curso");
		System.out.println("3. Fecha de Inscripcion");
		System.out.println("0. Cancelar");

		int opcionUpdate = Usuario.leerEnteroPositivo();

		switch (opcionUpdate) {
		case 1:
			Cliente cliente = null;
			while (cliente == null) {
				System.out.println("Nuevo ID de Cliente:");
				int idCliente = Usuario.leerEnteroPositivo();
				cliente = clienteDAO.getOne(idCliente);

				if (cliente == null) {
					System.out.println("Cliente no encontrado. Introduce otro id");
					continue;
				}
			}
			inscripcion.setClientes(cliente);
			break;

		case 2:
			Curso curso = null;
			while (curso == null) {
				System.out.println("Nuevo ID de Curso:");
				int idCurso = Usuario.leerEnteroPositivo();
				curso = cursoDAO.getOne(idCurso);

				if (curso == null) {
					System.out.println("Curso no encontrado. Introduce otro ID");
					continue;
				}
			}
			inscripcion.setCursos(curso);
			break;

		case 3:
			System.out.println("Nueva Fecha (YYYY-MM-DD):");
			Date fecha = Usuario.leerFechaSQL();
			inscripcion.setFechaInscripcion(fecha);
			break;

		case 0:
			break;

		default:
			System.out.println("Opcion no valida");
		}

		if (opcionUpdate >= 1 && opcionUpdate <= 3) {
			Boolean actualizada = inscripcionDAO.Update(inscripcion);

			if (actualizada) {
				System.out.println("Inscripcion actualizada");
			} else {
				System.out.println("Error al actualizar");
			}
		}
	}

	// Borramos Una inscripcion
	private static void borrarInscripcion(DAOInscripciones inscripcionDAO) {
		boolean borrado = false;

		while (!borrado) {
			System.out.println("id de la Inscripcion:");
			int id = Usuario.leerEnteroPositivo();
			Inscripcion inscripcion = inscripcionDAO.getOne(id);

			if (inscripcion == null) {
				System.out.println("No existe ninguna inscripcion con ID " + id);
				continue;
			}

			System.out.println("¿Desea eliminar esta inscripcion? S/N\n" + inscripcion);
			boolean confirmacion = Usuario.leerConfirmacion();

			if (confirmacion) {
				borrado = inscripcionDAO.Borrar(id);

				if (borrado) {
					System.out.println("Inscripcion borrada correctamente");
				} else {
					System.out.println("Error al borrar. Intentalo de nuevo");
				}
			} else {
				System.out.println("Borrado Cancelado introduce otro ID");
			}
		}
	}
}