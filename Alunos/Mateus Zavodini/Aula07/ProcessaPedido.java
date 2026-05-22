import java.util.Date;

public class ProcessaPedido {

    public Pedido processar(int id, Date dataCriacao, Date dataPagamento,
                            Date dataVencimentoReserva, Cliente cliente,
                            Vendedor vendedor, String loja, Item[] itens) {

        Pedido pedido = new Pedido(id, dataCriacao, dataPagamento,
                dataVencimentoReserva, cliente, vendedor, loja, itens);

        if (confirmarPagamento(pedido)) {
            System.out.println("Pedido #" + id + " processado com sucesso!");
        } else {
            System.out.println("Pedido #" + id + " RECUSADO — reserva vencida!");
        }

        return pedido;
    }

    private boolean confirmarPagamento(Pedido pedido) {
        Date agora = new Date();
        return !agora.after(pedido.getDataVencimentoReserva());
    }
}