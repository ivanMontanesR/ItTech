

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import modelos.Cliente;
import modelos.Curso;
import modelos.Inscripcion;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.File;
import java.sql.Date;

public class GeneraBDOO {
    
    public static void main(String[] args) {
        
        ODB baseDatosOO = null;
        
        try {
            String nombreBD = "ITtech.odb";
            baseDatosOO = ODBFactory.open("src\\basedatos\\" + nombreBD, "ivan", "Fotografa11!");
            
            // ========== PARSEAR XML ==========
            System.out.println("📄 Leyendo XML de phpMyAdmin...\n");
            
            File archivoXML = new File("src\\ITtech.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(archivoXML);
            doc.getDocumentElement().normalize();
            
            NodeList todasLasTablas = doc.getElementsByTagName("table");
            
            
            // ========== INSERTAR CURSOS ==========
            System.out.println("📚 Insertando cursos...");
            int contadorCursos = 0;
            
            for (int i = 0; i < todasLasTablas.getLength(); i++) {
                Element tabla = (Element) todasLasTablas.item(i);
                String nombreTabla = tabla.getAttribute("name");
                
                if ("cursos".equals(nombreTabla)) {
                    NodeList columnas = tabla.getElementsByTagName("column");
                    
                    Integer idCurso = null;
                    String nombreCurso = null;
                    String descripcion = null;
                    Integer duracion = null;
                    
                    for (int j = 0; j < columnas.getLength(); j++) {
                        Element columna = (Element) columnas.item(j);
                        String nombreCol = columna.getAttribute("name");
                        String valor = columna.getTextContent();
                        
                        switch (nombreCol) {
                            case "id_curso":
                                idCurso = Integer.parseInt(valor);
                                break;
                            case "nombre_curso":
                                nombreCurso = valor;
                                break;
                            case "descripcion":
                                descripcion = valor;
                                break;
                            case "duracion":
                                duracion = Integer.parseInt(valor);
                                break;
                        }
                    }
                    
                    // ✅ Usar constructor vacío y setters (NO tocar el Set)
                    Curso curso = new Curso();
                    curso.setIdCurso(idCurso);
                    curso.setNombreCurso(nombreCurso);
                    curso.setDescripcion(descripcion);
                    curso.setDuracion(duracion);
                    // ❌ NO llamar curso.setInscripcioneses()
                    
                    baseDatosOO.store(curso);
                    contadorCursos++;
                }
            }
            System.out.println("   ✅ " + contadorCursos + " cursos insertados");
            
            
            // ========== INSERTAR CLIENTES ==========
            System.out.println("👥 Insertando clientes...");
            int contadorClientes = 0;
            
            for (int i = 0; i < todasLasTablas.getLength(); i++) {
                Element tabla = (Element) todasLasTablas.item(i);
                String nombreTabla = tabla.getAttribute("name");
                
                if ("clientes".equals(nombreTabla)) {
                    NodeList columnas = tabla.getElementsByTagName("column");
                    
                    Integer idCliente = null;
                    String nombre = null;
                    String apellidos = null;
                    String direccion = null;
                    Integer edad = null;
                    
                    for (int j = 0; j < columnas.getLength(); j++) {
                        Element columna = (Element) columnas.item(j);
                        String nombreCol = columna.getAttribute("name");
                        String valor = columna.getTextContent();
                        
                        switch (nombreCol) {
                            case "id_cliente":
                                idCliente = Integer.parseInt(valor);
                                break;
                            case "nombre":
                                nombre = valor;
                                break;
                            case "apellidos":
                                apellidos = valor;
                                break;
                            case "direccion":
                                direccion = valor;
                                break;
                            case "edad":
                                edad = Integer.parseInt(valor);
                                break;
                        }
                    }
                    
                    // ✅ Usar constructor vacío y setters (NO tocar el Set)
                    Cliente cliente = new Cliente();
                    cliente.setIdCliente(idCliente);
                    cliente.setNombre(nombre);
                    cliente.setApellidos(apellidos);
                    cliente.setDireccion(direccion);
                    cliente.setEdad(edad);
                    // ❌ NO llamar cliente.setInscripcioneses()
                    
                    baseDatosOO.store(cliente);
                    contadorClientes++;
                }
            }
            System.out.println("   ✅ " + contadorClientes + " clientes insertados");
            
            
            // ========== INSERTAR INSCRIPCIONES ==========
            System.out.println("📝 Insertando inscripciones...");
            int contadorInscripciones = 0;
            
            for (int i = 0; i < todasLasTablas.getLength(); i++) {
                Element tabla = (Element) todasLasTablas.item(i);
                String nombreTabla = tabla.getAttribute("name");
                
                if ("inscripciones".equals(nombreTabla)) {
                    NodeList columnas = tabla.getElementsByTagName("column");
                    
                    Integer idInscripcion = null;
                    Integer idCliente = null;
                    Integer idCurso = null;
                    Date fechaInscripcion = null;
                    
                    for (int j = 0; j < columnas.getLength(); j++) {
                        Element columna = (Element) columnas.item(j);
                        String nombreCol = columna.getAttribute("name");
                        String valor = columna.getTextContent();
                        
                        switch (nombreCol) {
                            case "id_inscripcion":
                                idInscripcion = Integer.parseInt(valor);
                                break;
                            case "id_cliente":
                                idCliente = Integer.parseInt(valor);
                                break;
                            case "id_curso":
                                idCurso = Integer.parseInt(valor);
                                break;
                            case "fecha_inscripcion":
                                fechaInscripcion = Date.valueOf(valor);
                                break;
                        }
                    }
                    
                    // ✅ Crear objetos mínimos de Cliente y Curso (solo con ID)
                    Cliente clienteRef = new Cliente();
                    clienteRef.setIdCliente(idCliente);
                    
                    Curso cursoRef = new Curso();
                    cursoRef.setIdCurso(idCurso);
                    
                    // ✅ Crear inscripción usando el constructor con parámetros
                    Inscripcion inscripcion = new Inscripcion(cursoRef, clienteRef, fechaInscripcion);
                    inscripcion.setIdInscripcion(idInscripcion);
                    
                    baseDatosOO.store(inscripcion);
                    contadorInscripciones++;
                }
            }
            System.out.println("   ✅ " + contadorInscripciones + " inscripciones insertadas");
            
            
            // ========== COMMIT ==========
            baseDatosOO.commit();
            
            System.out.println("\n🎉 ¡Base de datos Neodatis creada exitosamente!");
            System.out.println("📂 Ubicación: src\\basedatos\\" + nombreBD);
            System.out.println("📊 Total guardado:");
            System.out.println("   - " + contadorCursos + " cursos");
            System.out.println("   - " + contadorClientes + " clientes");
            System.out.println("   - " + contadorInscripciones + " inscripciones");
            System.out.println("\n👉 Ahora puedes abrir Object Explorer para visualizar los datos");
            
        } catch (Exception e) {
            System.err.println("\n❌ Error al crear la base de datos:");
            e.printStackTrace();
            
            if (baseDatosOO != null) {
                baseDatosOO.rollback();
            }
            
        } finally {
            if (baseDatosOO != null) {
                baseDatosOO.close();
            }
        }
    }
}
