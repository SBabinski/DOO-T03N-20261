package service;

import entities.Loja;
import java.util.ArrayList;

public class LojaService {
    
    public Loja buscarPorId(ArrayList<Loja> lojas, int id) {
        for (Loja l : lojas) {
            if (l.getID() == id) return l;
        }
        return null;
    }
}
