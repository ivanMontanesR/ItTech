package dao;


import modelos.Cursos;
import java.util.List;

public interface Cursos_dao {
	public List<Cursos> getAll();
    public Boolean Create(Cursos cl);
    public Cursos getOne(int id);
    public Boolean Update(int id,int opcion);
    public Boolean Borrar(int id);
}