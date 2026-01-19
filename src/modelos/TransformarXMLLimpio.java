package modelos;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.File;
import java.io.PrintWriter;

public class TransformarXMLLimpio {
    
    public static void main(String[] args) {
        
        try {
            System.out.println("📄 Leyendo XML de phpMyAdmin...\n");
            
            // ========== LEER XML DE PHPMYADMIN ==========
            File archivoXML = new File("src\\ITtech.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(archivoXML);
            doc.getDocumentElement().normalize();
            
            NodeList todasLasTablas = doc.getElementsByTagName("table");
            
            
            // ========== CREAR UN SOLO XML LIMPIO ==========
            System.out.println("📦 Generando ITtech_limpio.xml...");
            
            PrintWriter writer = new PrintWriter("src\\ITtech_limpio.xml");
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<ITtech>");
            
            
            // ========== SECCIÓN CLIENTES ==========
            writer.println("  <clientes>");
            int contadorClientes = 0;
            
            for (int i = 0; i < todasLasTablas.getLength(); i++) {
                Element tabla = (Element) todasLasTablas.item(i);
                String nombreTabla = tabla.getAttribute("name");
                
                if ("clientes".equals(nombreTabla)) {
                    NodeList columnas = tabla.getElementsByTagName("column");
                    
                    writer.println("    <cliente>");
                    
                    for (int j = 0; j < columnas.getLength(); j++) {
                        Element columna = (Element) columnas.item(j);
                        String nombreCol = columna.getAttribute("name");
                        String valor = columna.getTextContent();
                        
                        writer.println("      <" + nombreCol + ">" + 
                                      escaparXML(valor) + 
                                      "</" + nombreCol + ">");
                    }
                    
                    writer.println("    </cliente>");
                    contadorClientes++;
                }
            }
            writer.println("  </clientes>");
            System.out.println("   ✅ " + contadorClientes + " clientes procesados");
            
            
            // ========== SECCIÓN CURSOS ==========
            writer.println("  <cursos>");
            int contadorCursos = 0;
            
            for (int i = 0; i < todasLasTablas.getLength(); i++) {
                Element tabla = (Element) todasLasTablas.item(i);
                String nombreTabla = tabla.getAttribute("name");
                
                if ("cursos".equals(nombreTabla)) {
                    NodeList columnas = tabla.getElementsByTagName("column");
                    
                    writer.println("    <curso>");
                    
                    for (int j = 0; j < columnas.getLength(); j++) {
                        Element columna = (Element) columnas.item(j);
                        String nombreCol = columna.getAttribute("name");
                        String valor = columna.getTextContent();
                        
                        writer.println("      <" + nombreCol + ">" + 
                                      escaparXML(valor) + 
                                      "</" + nombreCol + ">");
                    }
                    
                    writer.println("    </curso>");
                    contadorCursos++;
                }
            }
            writer.println("  </cursos>");
            System.out.println("   ✅ " + contadorCursos + " cursos procesados");
            
            
            // ========== SECCIÓN INSCRIPCIONES ==========
            writer.println("  <inscripciones>");
            int contadorInscripciones = 0;
            
            for (int i = 0; i < todasLasTablas.getLength(); i++) {
                Element tabla = (Element) todasLasTablas.item(i);
                String nombreTabla = tabla.getAttribute("name");
                
                if ("inscripciones".equals(nombreTabla)) {
                    NodeList columnas = tabla.getElementsByTagName("column");
                    
                    writer.println("    <inscripcion>");
                    
                    for (int j = 0; j < columnas.getLength(); j++) {
                        Element columna = (Element) columnas.item(j);
                        String nombreCol = columna.getAttribute("name");
                        String valor = columna.getTextContent();
                        
                        writer.println("      <" + nombreCol + ">" + 
                                      escaparXML(valor) + 
                                      "</" + nombreCol + ">");
                    }
                    
                    writer.println("    </inscripcion>");
                    contadorInscripciones++;
                }
            }
            writer.println("  </inscripciones>");
            System.out.println("   ✅ " + contadorInscripciones + " inscripciones procesadas");
            
            
            writer.println("</ITtech>");
            writer.close();
            
            
            System.out.println("\n🎉 ¡XML único creado exitosamente!");
            System.out.println("📂 Ubicación: src/ITtech_limpio.xml");
            System.out.println("📊 Total:");
            System.out.println("   - " + contadorClientes + " clientes");
            System.out.println("   - " + contadorCursos + " cursos");
            System.out.println("   - " + contadorInscripciones + " inscripciones");
            System.out.println("\n👉 Ahora puedes subirlo a eXist-DB");
            
        } catch (Exception e) {
            System.err.println("❌ Error al transformar XML:");
            e.printStackTrace();
        }
    }
    
    // Escapar caracteres especiales XML
    private static String escaparXML(String texto) {
        if (texto == null) return "";
        return texto.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&apos;");
    }
}