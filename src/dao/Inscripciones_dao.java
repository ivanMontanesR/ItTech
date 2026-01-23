package dao;

import java.util.List;
import modelos.Inscripcion;

public interface Inscripciones_dao {
	public List<Inscripcion> getAll();
    public Boolean Create(Inscripcion Ic);
    public Inscripcion getOne(int id);
    public Boolean Update(int id,int opcion);
    public Boolean Borrar(int id);
}