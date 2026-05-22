import java.util.Date;

public class ProcessaPedido {

    public Pedido processar(int id, Date dataCriacao, Date dataPagamento,
                            Date dataVencimentoReserva, Cliente cliente,
                            Vendedor vendedor, Loja loja, Item[] itens) {

        Pedido pedido = new Pedido(id, dataCriacao, dataPagamento,
                                   dataVencimentoReserva, cliente, vendedor, loja, itens);

        if (confirmarPagamento(pedido)) {
            System.out.println("Pagamento confirmado! Pedido processado com sucesso.");
        } else {
            System.out.println("Reserva vencida! Pedido NAO processado.");
        }

        return pedido;
    }

    private boolean confirmarPagamento(Pedido pedido) {
        Date hoje = new Date();
        // Pagamento válido se a data atual NÃO ultrapassou o vencimento da reserva
        return !hoje.after(pedido.dataVencimentoReserva);
    }
}