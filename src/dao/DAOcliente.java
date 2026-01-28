package dao;

import modelos.Cliente;
import java.util.List;

public interface DAOcliente {
    public List<Cliente> getAll();
    public Boolean Create(Cliente cl);
    public Cliente getOne(int id);
    public Boolean Update(Cliente cl);
    public Boolean Borrar(int id);
    
}