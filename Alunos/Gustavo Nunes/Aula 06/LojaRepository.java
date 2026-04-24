package repository;

import Entities.Loja;

import java.util.ArrayList;

public class LojaRepository {
    private ArrayList<Loja> lojas = new ArrayList<>();
    
    // Retorna lista de lojas
    public ArrayList<Loja> retornaLojas() {
        return lojas;
    }
    
    // Adiciona loja a lista
    public void adicionaLoja(Loja loja) {
        lojas.add(loja);
    }
    
    // Retorna total de lojas da lista
    public int retornaQuantidade() {
        return lojas.size();
    }
    
    // Exclui loja da lista
    public void excluiLoja(Loja loja){
        for (Loja m : lojas){
            if (m == loja){
                lojas.remove(m);
            }
        }
    }
}
