package main;

import controlador.ClientesControlador;
import controlador.CursosControlador;
import controlador.InscripcionesControlador;
import dao.DAOcliente;
import modelos.Cliente;
import util.Usuario;
import java.util.List;

public class Principal {
    public static void main(String[] args) {
        System.out.println("   SISTEMA DE GESTION ITtech \n" + 
                          "Seleccione la base de datos:\n" +
                          "1. MySQL (Hibernate)\n" + 
                          "2. Neodatis (Objetos)\n" + 
                          "3. eXist-DB (XML)");
        
        int tipoBD = Usuario.leerEntero();
        
        String nombreBD = "";
        switch (tipoBD) {
            case 1: nombreBD = "MySQL con Hibernate"; break;
            case 2: nombreBD = "Neodatis"; break;
            case 3: nombreBD = "eXist-DB"; break;
            default: 
                System.out.println("Opción inválida");
                return;
        }
        
        System.out.println("Usando " + nombreBD);
        System.out.println("¿Qué Desea Hacer?" + 
                          "\n1) Gestionar Clientes" +
                          "\n2) Gestionar Cursos" + 
                          "\n3) Gestionar Inscripciones");
        
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
            
            System.out.println("    GESTION DE CLIENTES    " +
                              "\n1) Consultar Todos los Clientes" +
                              "\n2) Consultar Un Cliente" +
                              "\n3) Crear un Cliente Nuevo" +
                              "\n4) Actualizar Un Cliente" +
                              "\n5) Borrar Un Cliente");
            
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
                
                System.out.println("¿Qué cambiar?" +
                                  "\n1. Nombre" +
                                  "\n2. Apellidos" +
                                  "\n3. Dirección" +
                                  "\n4. Edad");
                int opcionUpdate = Usuario.leerEntero();
                
                Boolean actualizado = clienteDAO.Update(id, opcionUpdate);
                
                if (actualizado) {
                    System.out.println("Cliente actualizado");
                } else {
                    System.out.println("Error");
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
            }
            break;
        }
        
        case 2: {
            CursosControlador controlador = new CursosControlador(tipoBD);
            System.out.println("Gestión de Cursos - Próximamente");
            break;
        }
            
        case 3: {
            InscripcionesControlador controlador = new InscripcionesControlador(tipoBD);
            System.out.println("Gestión de Inscripciones - Próximamente");
            break;
        }
            
        default:
            System.out.println("Opción inválida");
        }
    }
}