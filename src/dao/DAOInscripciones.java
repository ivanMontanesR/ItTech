package dao;

import java.util.List;
import modelos.Inscripcion;

public interface DAOInscripciones {
	public List<Inscripcion> getAll();
    public Boolean Create(Inscripcion ic);
    public Inscripcion getOne(int id);
    public Boolean Update(Inscripcion ic);
    public Boolean Borrar(int id);
}