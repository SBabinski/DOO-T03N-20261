package repository;

import entities.Venda;
import java.util.ArrayList;

public class VendaRepository extends Repository<Venda> {
    
    public void adicionaVenda(Venda venda) {
        adicionaObjeto(venda);
    }
    
    public ArrayList<Venda> retornaVendas() {
        return retornaObjetos();
    }
    
    public int retornaQuantidade() {
        return retornaTamanho();
    }
}