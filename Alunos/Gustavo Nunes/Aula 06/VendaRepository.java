package repository;

import Entities.Venda;

import java.util.ArrayList;

public class VendaRepository {
    private ArrayList<Venda> vendas = new ArrayList<>();
    
    // Retorna lista de vendas
    public ArrayList<Venda> retornaVendas() {
        return vendas;
    }
    
    // Adiciona venda a lista
    public void adicionaVenda(Venda venda) {
        
        vendas.add(venda);
    }
    
    // Retorna total de vendas da lista
    public int retornaQuantidade() {
        return vendas.size();
    }
    
    // Exclui venda da lista
    public void excluiVenda(Venda venda){
        for (Venda m : vendas){
            if (m == venda){
                vendas.remove(m);
            }
        }
    }
}


