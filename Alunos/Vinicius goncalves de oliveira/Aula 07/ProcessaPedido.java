import java.time.LocalDate;
import java.util.List;

public class ProcessaPedido {

    public Pedido processar(int id, LocalDate dataCriacao, LocalDate dataPagamento, LocalDate dataVencimentoReserva,
                            Cliente cliente, Vendedor vendedor, Loja loja, List<Item> itens) {
        Pedido pedido = new Pedido(id, dataCriacao, dataPagamento, dataVencimentoReserva, cliente, vendedor, loja, itens);
        confirmarPagamento(pedido);
        return pedido;
    }

    private void confirmarPagamento(Pedido pedido) {
        if (LocalDate.now().isAfter(pedido.getDataVencimentoReserva())) {
            throw new IllegalStateException("Reserva vencida. Pagamento nao pode ser confirmado.");
        }
    }
}
