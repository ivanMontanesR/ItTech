package dao;

import modelos.Cursos;
import java.util.List;

public interface Cursos_dao {
	public List<Cursos> getAll();
    public Cursos getOne(int id);
    public Boolean Update();
    public Boolean Borrar();
}