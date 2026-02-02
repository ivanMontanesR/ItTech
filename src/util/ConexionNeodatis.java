package util;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;

public class ConexionNeodatis {

	private static ODB instancia = null;
	private static final String nombreBD = "ITtech.odb";
	private static final String path = "src\\basedatos\\";
	private static final String usuario = "ivan";
	private static final String password = "openpgpwd";

	private ConexionNeodatis() {
	}

	public static ODB getInstancia() {
		if (instancia == null || instancia.isClosed()) {
			instancia = ODBFactory.open(path + nombreBD, usuario, password);
		}
		return instancia;
	}

	@Override
	protected void finalize() throws Throwable {
		if (instancia != null && !instancia.isClosed()) {
			instancia.close();
			instancia = null;
		}
	}
}
