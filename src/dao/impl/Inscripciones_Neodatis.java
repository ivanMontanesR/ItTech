package dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import dao.DAOInscripciones;
import modelos.Inscripcion;
import util.ConexionNeodatis;

public class Inscripciones_Neodatis implements DAOInscripciones {
	/*
	 * Creamos el Util de neodatis para crear la instancia del ODB para usarlo en
	 * todos los metodos
	 */
	private ODB baseDatos = ConexionNeodatis.getInstancia();

	/*
	 * Metodo para Conseguir todas Las Inscripciones Haciendo un array de objetos Inscripciones
	 * y añadiendolos Posteriormente a una lista
	 */
	@Override
	public List<Inscripcion> getAll() {

		List<Inscripcion> inscripciones = new ArrayList<>();

		try {

			Objects<Inscripcion> resultado = baseDatos.getObjects(Inscripcion.class);

			while (resultado.hasNext()) {
				inscripciones.add(resultado.next());
			}

		} catch (Exception e) {
			System.err.println("Error al obtener Inscripciones: " + e.getMessage());
			e.printStackTrace();
		}
		return inscripciones;
	}
	/*
	 * Metodo De Creacion de Inscripcion el cual recibimos la Inscripcion como parametro y
	 * Buscamos primero cual es el maximo Id Insertado para insertar el siguiente con
	 * el maxID +1 y insertandolo con el Store
	 */
	@Override
	public Boolean Create(Inscripcion inscripcion) {
	    try {
	        IQuery consulta = new CriteriaQuery(Inscripcion.class);
	        Objects<Inscripcion> resultado = baseDatos.getObjects(consulta);
	        
	        int maxId = 0;
	        while (resultado.hasNext()) {
	            Inscripcion i = resultado.next();  
	            if (i.getIdInscripcion() != null&& i.getIdInscripcion() > maxId) {
	                maxId = i.getIdInscripcion();
	            }
	        }
	        
	        inscripcion.setIdInscripcion(maxId + 1);
	        
	        baseDatos.store(inscripcion);
	        baseDatos.commit();
	        return true;
	    } catch (Exception e) {
	        System.err.println("Error al crear inscripcion: " + e.getMessage());
	        e.printStackTrace();
	        if (baseDatos != null) {
	            baseDatos.rollback();
	        }
	        return false;
	    }
	}
	
	/*
	 * Metodo para recuperar una Inscripcion con un id que nos de el usuario el cual
	 * usaremos el criteryaquery Con el where equal para comprobar el id de la inscripcion
	 * con el id que nos han dado
	 */
	@Override
	public Inscripcion getOne(int id) {

		Inscripcion inscripcion = null;

		try {

			IQuery consulta = new CriteriaQuery(Inscripcion.class, Where.equal("idInscripcion", id));
			Objects<Inscripcion> resultado = baseDatos.getObjects(consulta);

			if (resultado.hasNext()) {
				inscripcion = resultado.next();

			}
		} catch (Exception e) {
			System.err.println("Error al buscar Inscripcion: " + e.getMessage());
			e.printStackTrace();

		}

		return inscripcion;
	}

	/*
	 * Metodo Para actualizar una Inscripcion previamente recuperado en el principal el
	 * cual insertaremos con el store
	 */
	@Override
	public Boolean Update(Inscripcion inscripcion) {

		try {

			baseDatos.store(inscripcion);
			baseDatos.commit();

			return true;

		} catch (Exception e) {
			System.err.println("Error al actualizar Inscripcion: " + e.getMessage());
			e.printStackTrace();
			if (baseDatos != null) {
				baseDatos.rollback();
			}
			return false;
		}
	}

	/*
	 * Metodo Para Borrar Inscripciones con un id como parametro, con el comando delete
	 */
	@Override
	public Boolean Borrar(int id) {

		try {

			Inscripcion inscripcion = getOne(id);

			if (inscripcion != null) {
				baseDatos.delete(inscripcion);
				baseDatos.commit();

				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			System.err.println("Error al eliminar Inscripcion: " + e.getMessage());
			e.printStackTrace();
			if (baseDatos != null) {
				baseDatos.rollback();
			}
			return false;

		}
	}

}