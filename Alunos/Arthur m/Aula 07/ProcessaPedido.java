import java.util.Date;

public class ProcessaPedido {

    public Pedido processar(Cliente cliente, Vendedor vendedor, Loja loja) {

        Pedido pedido = new Pedido(1, cliente, vendedor, loja);

        pedido.dataVencimentoReserva = new Date(System.currentTimeMillis() + 86400000); // +1 dia

        if (confirmarPagamento(pedido)) {
            pedido.dataPagamento = new Date();
            System.out.println("Pagamento confirmado!");
        } else {
            System.out.println("Reserva vencida!");
        }

        return pedido;
    }

    private boolean confirmarPagamento(Pedido pedido) {
        Date hoje = new Date();
        return hoje.before(pedido.dataVencimentoReserva);
    }
}