package repository;

import java.util.ArrayList;

public class Repository<T> {
    private ArrayList<T> objects = new ArrayList<>();
    
    public ArrayList<T> retornaObjetos() {
        return objects;
    }
    
    public void adicionaObjeto(T object) {
        objects.add(object);
    }
    
    public int retornaTamanho() {
        return objects.size();
    }
    
    public void excluiObjeto(T object) {
        objects.remove(object);
    }
}