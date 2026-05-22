package repository;

import entities.Pedido;
import java.util.ArrayList;

public class PedidoRepository extends Repository<Pedido> {
    
    public void adicionaPedido(Pedido pedido) {
        adicionaObjeto(pedido);
    }
    
    public ArrayList<Pedido> retornaPedidos() {
        return retornaObjetos();
    }
    
    public int retornaQuantidade() {
        return retornaTamanho();
    }
}