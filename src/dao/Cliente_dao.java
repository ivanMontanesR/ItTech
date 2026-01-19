package dao;

import modelos.Cliente;
import java.util.List;

public interface Cliente_dao {
    public List<Cliente> getAll();
    public Boolean Create(Cliente cl);
    public Cliente getOne(int id);
    public Boolean Update(int id,int opcion);
    public Boolean Borrar(int id);
    
}