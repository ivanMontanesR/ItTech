package util;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;

public class ConexionExistDB {

    private static Collection instancia = null;
    private static final String URI = "xmldb:exist://192.168.56.102:8080/exist/xmlrpc/db/ITtech";
    private static final String DRIVER = "org.exist.xmldb.DatabaseImpl";
    private static final String USER = "ivan";
    private static final String PASSWORD = "openpgpwd";

    private ConexionExistDB() {}

    public static Collection getInstancia() {
        if (instancia == null) {
            try {
                Class<?> cl = Class.forName(DRIVER);
                Database database = (Database) cl.getDeclaredConstructor().newInstance();
                DatabaseManager.registerDatabase(database);
                instancia = DatabaseManager.getCollection(URI, USER, PASSWORD);
            } catch (Exception e) {
                System.err.println("Error al conectar a eXist-DB: " + e.getMessage());
            }
        }
        return instancia;
    }

    @Override
    protected void finalize() throws Throwable {
    	if (instancia != null) {
            try {
                instancia.close();
                instancia = null;
            } catch (XMLDBException e) {
                e.printStackTrace();
            }
        }
    }
    
}