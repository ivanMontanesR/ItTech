package dao;


import modelos.Curso;
import java.util.List;

public interface DAOCursos {
	//Obtiene todos los cursos de la base de datos
	public List<Curso> getAll();
	
    //Crea un nuevo curso en la base de datos
    public Boolean Create(Curso cl);
    
    //Busca un curso por su ID
    public Curso getOne(int id);
    
    //Actualiza un curso con un objeto que le pasemos
    public Boolean Update(Curso cur);
    
    //Borramos un curso por un id
    public Boolean Borrar(int id);
}