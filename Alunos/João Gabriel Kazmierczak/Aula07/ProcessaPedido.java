package fag;

import java.util.Date;

public class ProcessaPedido {

    public Pedido processar(int id, Cliente c, Vendedor v, Loja l) {

        Date agora = new Date();
        Date vencimento = new Date(agora.getTime() + 86400000);

        Pedido p = new Pedido(id, agora, vencimento);

        p.adicionarItem(new Item(1, "Rosa", "Flor", 25));
        p.adicionarItem(new Item(2, "Vaso", "Acessorio", 40));

        if (!new Date().after(vencimento)) {
            p.setDataPagamento(new Date());
        }

        return p;
    }
}