package repository;

import entities.Cliente;

import java.util.ArrayList;

public class ClienteRepository extends Repository {
    
    // Adiciona cliente à lista
    public void adicionacliente(Cliente cliente) {
        adicionaObjeto(cliente);
    }
    
    // Retorna lista tipada de clientes
    public ArrayList<Cliente> retornaClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        for (Object obj : retornaObjetos()) {
            clientes.add((Cliente) obj);
        }
        return clientes;
    }
    
    // Retorna a quantidade de clientes
    public int retornaQuantidade() {
        return retornaTamanho();
    }
}
