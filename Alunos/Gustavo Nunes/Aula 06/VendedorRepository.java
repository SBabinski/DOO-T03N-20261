package repository;

import Entities.Vendedor;

import java.util.ArrayList;

public class VendedorRepository {
    private ArrayList<Vendedor> vendedores = new ArrayList<>();
    
    // Retorna lista de vendedores
    public ArrayList<Vendedor> retornaVendedores() {
        return vendedores;
    }
    
    // Adiciona vendedor a lista
    public void adicionaVendedor(Vendedor vendedor) {
        vendedores.add(vendedor);
    }
   
    // Retorna total de vededores da lista
    public int retornaQuantidade() {
        return vendedores.size();
    }
    
    // Exclui vendedor da lista
    public void excluiVendedor(Vendedor vendedor){
        for (Vendedor m : vendedores){
            if (m == vendedor){
                vendedores.remove(m);
            }
        }
    }
}
