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

public class Principal {
	public static void main(String[] args) {

		System.out.println("   SISTEMA DE GESTION ITtech");
		System.out.println("Seleccione la base de datos:");
		System.out.println("1. MySQL ");
		System.out.println("2. Neodatis ");
		System.out.println("3. Exist-DB ");

		int tipoBD = Usuario.leerEnteroPositivo();

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
			System.out.println("Opcion invalida");
			return;
		}

		System.out.println("Usando " + nombreBD);

		int opcion = 0;

		do {

			System.out.println("\nQue Desea Hacer?");
			System.out.println("1) Gestionar Clientes");
			System.out.println("2) Gestionar Cursos");
			System.out.println("3) Gestionar Inscripciones");
			System.out.println("0) Salir");

			opcion = Usuario.leerEnteroPositivo();

			switch (opcion) {
			case 1: {
				ClientesControlador controlador = new ClientesControlador(tipoBD);
				DAOcliente clienteDAO = controlador.getClienteDAO();

				if (clienteDAO == null) {
					System.out.println("No se pudo crear el dao");
					break;
				}

				int opcionCliente = 0;

				do {

					System.out.println("\n    GESTION DE CLIENTES  ");
					System.out.println("1) Consultar Todos los Clientes");
					System.out.println("2) Consultar Un Cliente");
					System.out.println("3) Crear un Cliente Nuevo");
					System.out.println("4) Actualizar Un Cliente");
					System.out.println("5) Borrar Un Cliente");
					System.out.println("0) Volver");

					opcionCliente = Usuario.leerEnteroPositivo();

					switch (opcionCliente) {
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
						int id = Usuario.leerEnteroPositivo();

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

						System.out.println("Direccion:");
						String direccion = Usuario.leerAlfanumericos();

						System.out.println("Edad:");
						Integer edad = Usuario.leerEnteroRango(0, 120);

						Cliente cl = new Cliente();
						cl.setNombre(nombre);
						cl.setApellidos(apellidos);
						cl.setDireccion(direccion);
						cl.setEdad(edad);
						Boolean creado = clienteDAO.Create(cl);

						if (creado) {
							System.out.println("Cliente creado");
						} else {
							System.out.println("Error al crear");
						}
						break;
					}

					case 4: {
						System.out.println("ID del cliente:");
						int id = Usuario.leerEnteroPositivo();
						Cliente cl = clienteDAO.getOne(id);
						if (cl == null) {
							System.err.println("Cliente no encontrado");
							break;
						}
						System.out.println("Cliente actual: " + cl.toString());
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
							cl.setNombre(nombre);
							break;

						case 2:
							System.out.println("Nuevos Apellidos:");
							String apellidos = Usuario.leerString();
							cl.setApellidos(apellidos);
							break;

						case 3:
							System.out.println("Nueva Direccion:");
							String direccion = Usuario.leerAlfanumericos();
							cl.setDireccion(direccion);
							break;

						case 4:
							System.out.println("Nueva Edad:");
							Integer edad = Usuario.leerEnteroRango(0, 120);

							cl.setEdad(edad);

							break;

						case 0:
							break;

						default:
							System.err.println("Opcion no valida");
							break;
						}

						if (opcionUpdate >= 1 && opcionUpdate <= 4) {
							Boolean actualizado = clienteDAO.Update(cl);
							if (actualizado) {
								System.out.println("Cliente actualizado");
							} else {
								System.out.println("Error al Actualizar");
							}
						}
						break;
					}

					case 5: {
						System.out.println("ID del cliente:");
						int id = Usuario.leerEnteroPositivo();

						Boolean borrado = clienteDAO.Borrar(id);

						if (borrado) {
							System.out.println("Cliente borrado");
						} else {
							System.out.println("Error al borrar");
						}
						break;
					}

					case 0:
						break;

					default:
						System.out.println("Opcion invalida");
						break;
					}

				} while (opcionCliente != 0);

				break;
			}

			case 2: {
				CursosControlador controladorCurso = new CursosControlador(tipoBD);
				DAOCursos cursoDAO = controladorCurso.getCursosDAO();

				if (cursoDAO == null) {
					System.out.println("No se pudo crear el DAO");
					break;
				}

				int opcionCurso = 0;

				do {

					System.out.println("\n    GESTION DE CURSOS   ");
					System.out.println("1) Consultar Todos los Cursos");
					System.out.println("2) Consultar Un Curso");
					System.out.println("3) Crear un Curso Nuevo");
					System.out.println("4) Actualizar Un Curso");
					System.out.println("5) Borrar Un Curso");
					System.out.println("0) Volver");

					opcionCurso = Usuario.leerEnteroPositivo();

					switch (opcionCurso) {
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
						int id = Usuario.leerEnteroPositivo();

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

						System.out.println("Descripcion:");
						String descripcion = Usuario.leerString();

						System.out.println("Duracion (horas):");
						Integer duracion = Usuario.leerEnteroPositivo();

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
						int id = Usuario.leerEnteroPositivo();

						Curso curso = cursoDAO.getOne(id);
						if (curso == null) {
							System.err.println("Curso no encontrado");
							break;
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
							System.err.println("Opcion no valida");
						}

						if (opcionUpdate >= 1 && opcionUpdate <= 3) {
							Boolean actualizado = cursoDAO.Update(curso);

							if (actualizado) {
								System.out.println("Curso actualizado");
							} else {
								System.out.println("Error al actualizar");
							}
						}
						break;
					}

					case 5: {
						System.out.println("ID del curso:");
						int id = Usuario.leerEnteroPositivo();

						Boolean borrado = cursoDAO.Borrar(id);

						if (borrado) {
							System.out.println("Curso borrado");
						} else {
							System.out.println("Error al borrar");
						}
						break;
					}

					case 0:
						break;

					default:
						System.out.println("Opcion invalida");
						break;
					}

				} while (opcionCurso != 0);

				break;
			}

			case 3: {
				InscripcionesControlador controlador = new InscripcionesControlador(tipoBD);
				DAOInscripciones inscripcionDAO = controlador.getInscripcionDAO();

				ClientesControlador controladorCli = new ClientesControlador(tipoBD);
				DAOcliente clienteDAO = controladorCli.getClienteDAO();

				CursosControlador controladorCur = new CursosControlador(tipoBD);
				DAOCursos cursoDAO = controladorCur.getCursosDAO();

				if (inscripcionDAO == null || clienteDAO == null || cursoDAO == null) {
					System.out.println("No se pudo crear el DAO");
					break;
				}

				int opcionInscripcion = 0;

				do {

					System.out.println("\n    GESTION DE INSCRIPCIONES   ");
					System.out.println("1) Consultar Todas las Inscripciones");
					System.out.println("2) Consultar Una Inscripcion");
					System.out.println("3) Crear una Inscripcion Nueva");
					System.out.println("4) Actualizar Una Inscripcion");
					System.out.println("5) Borrar Una Inscripcion");
					System.out.println("0) Volver");

					opcionInscripcion = Usuario.leerEnteroPositivo();

					switch (opcionInscripcion) {
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
						System.out.println("ID de la inscripcion:");
						int id = Usuario.leerEnteroPositivo();

						Inscripcion inscripcion = inscripcionDAO.getOne(id);
						if (inscripcion != null) {
							System.out.println(inscripcion);
						} else {
							System.out.println("Inscripcion no encontrada");
						}
						break;
					}

					case 3: {
						System.out.println("ID del Cliente:");
						int idCliente = Usuario.leerEnteroPositivo();

						System.out.println("ID del Curso:");
						int idCurso = Usuario.leerEnteroPositivo();

						System.out.println("Fecha de Inscripcion (YYYY-MM-DD):");
						Date fecha = Usuario.leerFechaSQL();

						try {
							Cliente cliente = clienteDAO.getOne(idCliente);

							if (cliente == null) {
								System.err.println("Cliente con ID " + idCliente + " no encontrado");
								break;
							}

							Curso curso = cursoDAO.getOne(idCurso);

							if (curso == null) {
								System.err.println("Curso con ID " + idCurso + " no encontrado");
								break;
							}

							Inscripcion nuevaInscripcion = new Inscripcion(curso, cliente, fecha);

							Boolean creada = inscripcionDAO.Create(nuevaInscripcion);

							if (creada) {
								System.out.println("Inscripcion creada exitosamente");
							} else {
								System.out.println("Error al crear inscripcion");
							}

						} catch (IllegalArgumentException e) {
							System.err.println("Formato de fecha invalido. Use YYYY-MM-DD");
						}
						break;
					}

					case 4: {
						System.out.println("ID de la inscripcion:");
						int id = Usuario.leerEnteroPositivo();

						Inscripcion inscripcion = inscripcionDAO.getOne(id);
						if (inscripcion == null) {
							System.err.println("Inscripcion no encontrada");
							break;
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
							System.out.println("Nuevo ID de Cliente:");
							int idCliente = Usuario.leerEnteroPositivo();
							Cliente cliente = clienteDAO.getOne(idCliente);
							
							if (cliente == null) {
								System.err.println("Cliente no encontrado");
								break;
							}
							inscripcion.setClientes(cliente);
							break;

						case 2:
							System.out.println("Nuevo id de Curso:");
							int idCurso = Usuario.leerEnteroPositivo();
							Curso curso = cursoDAO.getOne(idCurso);

							if (curso == null) {
								System.err.println("Curso no encontrado");
								break;
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
							System.err.println("Opcion no valida");
						}

						if (opcionUpdate >= 1 && opcionUpdate <= 3) {
							Boolean actualizada = inscripcionDAO.Update(inscripcion);

							if (actualizada) {
								System.out.println("Inscripcion actualizada");
							} else {
								System.out.println("Error al actualizar");
							}
						}
						break;
					}

					case 5: {
						System.out.println("ID de la inscripcion:");
						int id = Usuario.leerEnteroPositivo();

						Boolean borrada = inscripcionDAO.Borrar(id);

						if (borrada) {
							System.out.println("Inscripcion borrada");
						} else {
							System.out.println("Error al borrar");
						}
						break;
					}

					case 0:
						break;

					default:
						System.out.println("Opcion invalida");
						break;
					}

				} while (opcionInscripcion != 0);

				break;
			}

			case 0:
				break;

			default:
				System.out.println("Opcion invalida");
				break;
			}

		} while (opcion != 0);
		Usuario.cerrarScanner();
	}
}