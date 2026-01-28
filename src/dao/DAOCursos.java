package dao;


import modelos.Curso;
import java.util.List;

public interface DAOCursos {
	public List<Curso> getAll();
    public Boolean Create(Curso cl);
    public Curso getOne(int id);
    public Boolean Update(Curso cur);
    public Boolean Borrar(int id);
}