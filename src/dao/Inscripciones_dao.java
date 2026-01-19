package dao;

import java.util.List;


import modelos.Inscripcion;

public interface Inscripciones_dao {
	public List<Inscripcion> getAll();
    public Inscripcion getOne(int id);
    public Boolean Update();
    public Boolean Borrar();
}