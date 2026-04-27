import java.util.Date;

public class ProcessaPedido {

    public Pedido processar(int id,
            Cliente cliente,
            Vendedor vendedor,
            Loja loja,
            Item[] itens) {

        Date agora = new Date();

        Date vencimento = new Date(agora.getTime() + (2 * 24 * 60 * 60 * 1000));

        Pedido pedido = new Pedido(
                id,
                agora,
                vencimento,
                cliente,
                vendedor,
                loja,
                itens);

        if (confirmarPagamento(pedido)) {
            pedido.setDataPagamento(new Date());
            System.out.println("Pagamento confirmado");
        } else {
            System.out.println("Reserva vencida");
        }

        return pedido;
    }

    private boolean confirmarPagamento(Pedido pedido) {

        Date hoje = new Date();

        return hoje.before(pedido.getDataVencimentoReserva());
    }
}