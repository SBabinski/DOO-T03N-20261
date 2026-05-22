package repository;

import entities.Vendedor;
import java.util.ArrayList;

public class VendedorRepository extends Repository<Vendedor> {
    
    public void adicionaVendedor(Vendedor vendedor) {
        adicionaObjeto(vendedor);
    }
    
    public ArrayList<Vendedor> retornaVendedores() {
        return retornaObjetos();
    }
    
    public int retornaQuantidade() {
        return retornaTamanho();
    }
}