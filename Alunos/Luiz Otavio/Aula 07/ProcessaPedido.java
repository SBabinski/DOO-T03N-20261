import java.util.Date;
import java.time.LocalDate;

public class ProcessaPedido {

    public Pedido processar(int id, Date dataCriacao, Date dataPagamento,
                            Date dataVencimentoReserva, Cliente cliente,
                            Vendedor vendedor, Loja loja,
                            int quantidade, double desconto, LocalDate dataVenda,
                            Item... itens) {

        Pedido pedido = new Pedido(id, dataCriacao, dataPagamento,
                dataVencimentoReserva, cliente, vendedor, loja,
                quantidade, desconto, dataVenda);

        for (Item item : itens) {
            pedido.adicionarItem(item);
        }

        if (confirmarPagamento(pedido)) {
            System.out.println("Pagamento confirmado! Pedido processado com sucesso.");
            pedido.gerarDescricaoVenda();
        } else {
            System.out.println("Reserva vencida! Pedido nao pode ser confirmado.");
        }

        return pedido;
    }

    private boolean confirmarPagamento(Pedido pedido) {
        Date agora = new Date();
        return !agora.after(pedido.getDataVencimentoReserva());
    }
}
