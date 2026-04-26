import java.util.Date;

public class ProcessaPedido {

    public Pedido processar(int id, Cliente cliente, Vendedor vendedor, Loja loja) {
        Date agora = new Date();
        Date vencimento = new Date(agora.getTime() + 86400000);

        Pedido pedido = new Pedido(id, agora, vencimento, cliente, vendedor, loja);

        pedido.adicionarItem(new Item(1, "Rosa Vermelha", "Flor", 25.0));
        pedido.adicionarItem(new Item(2, "Vaso Decorativo", "Acessório", 40.0));

        if (confirmarPagamento(pedido)) {
            pedido.setDataPagamento(new Date());
            System.out.println("Pagamento confirmado!");
        } else {
            System.out.println("Reserva vencida.");
        }

        return pedido;
    }

    private boolean confirmarPagamento(Pedido pedido) {
        Date hoje = new Date();
        return !hoje.after(pedido.getDataVencimentoReserva());
    }
}