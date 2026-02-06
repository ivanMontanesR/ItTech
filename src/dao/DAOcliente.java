package dao;

import modelos.Cliente;
import java.util.List;

public interface DAOcliente {
	//Obtiene todos los clientes de la base de datos
    public List<Cliente> getAll();
    //Crea un nuevo CLiente en la base de datos
    public Boolean Create(Cliente cl);
    //Busca un CLiente por su ID
    public Cliente getOne(int id);
    //Actualiza un cliente con un objeto que le pasemos
    public Boolean Update(Cliente cl);
    //Borramos un cliente por un id
    public Boolean Borrar(int id);
    
}