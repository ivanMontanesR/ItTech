package util;

import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;

import javax.xml.transform.OutputKeys;

import org.xmldb.api.*;


public class ConectarExistDB {    
    private static String URI = "xmldb:exist://192.168.56.102:8080/exist/xmlrpc";

	 public static void main(String[] args) {
        
        final String driver = "org.exist.xmldb.DatabaseImpl";
        final String nombreColeccion = "/db/ITtech";
        
        
        Collection col = null;
        XMLResource res = null;
        // Inicializar el driver
        try {
            Class cl = Class.forName(driver);
            Database database = (Database) cl.newInstance();
            DatabaseManager.registerDatabase(database);
            
			
            // Obtener la colección
            col = DatabaseManager.getCollection(URI + nombreColeccion);
            col.setProperty(OutputKeys.INDENT, "no");
            res = (XMLResource)col.getResource(recursoLeer);
            
            if(res == null) {
                System.out.println("No ha encontrado el documento " + recursoLeer + " en " + nombreColeccion);
            } else {
                System.out.println(res.getContent());
            }
		} catch (ClassNotFoundException | XMLDBException | InstantiationException | IllegalAccessException | IllegalArgumentException | SecurityException  e) {
			e.printStackTrace();
		} finally {
            // no olvidar liberar los recursos
            if(res != null) {
            	res = null;
            }
            
            if(col != null) {
                try { 
                	col.close(); 
                }
                catch(XMLDBException xe) {
                	xe.printStackTrace();
                }
            }
        }
    }
}