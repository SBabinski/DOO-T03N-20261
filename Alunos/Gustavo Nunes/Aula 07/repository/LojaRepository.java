package repository;

import entities.Loja;
import java.util.ArrayList;

public class LojaRepository extends Repository<Loja> {
    
    public void adicionaLoja(Loja loja) {
        adicionaObjeto(loja);
    }
    
    public ArrayList<Loja> retornaLojas() {
        return retornaObjetos();
    }
    
    public int retornaQuantidade() {
        return retornaTamanho();
    }
}