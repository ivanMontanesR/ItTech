package dao;

import java.util.List;
import modelos.Inscripcion;

public interface DAOInscripciones {
	//Obtiene todos los inscripciones de la base de datos
	public List<Inscripcion> getAll();
	  //Crea una nueva inscripcion en la base de datos
    public Boolean Create(Inscripcion ic);
  //Busca una inscripcion por su ID
    public Inscripcion getOne(int id);
    //Actualiza una inscripcion con un objeto que le pasemos
    public Boolean Update(Inscripcion ic);
  //Borramos una inscripcion por un id
    public Boolean Borrar(int id);
    
}