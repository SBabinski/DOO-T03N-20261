package repository;

import Entities.Cliente;

import java.util.ArrayList;

public class ClienteRepository {
    private ArrayList<Cliente> clientes = new ArrayList<>();
    
    // Retorna lista de clientes
    public ArrayList<Cliente> retornaClientes() {
        return clientes;
    }
    
    // Adiciona cliente a lista
    public void adicionacliente(Cliente cliente) {
        clientes.add(cliente);
    }
    
    // Retorna total de clientes da lista
    public int retornaQuantidade() {
        return clientes.size();
    }
    
    // Exclui cliente da lista
    public void excluiCliente(Cliente cliente){
        for (Cliente m : clientes){
            if (m == cliente){
                clientes.remove(m);
            }
        }
    }
    
    public void adicionaCliente(Cliente cliente) {
    
    }
}
