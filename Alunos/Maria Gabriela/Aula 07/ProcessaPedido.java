import java.util.Date;

public class ProcessaPedido {

    public Pedido processar(Pedido pedido) {
        if (confirmarPagamento(pedido)) {
            pedido.setDataPagamento(new Date());
            System.out.println("Pagamento confirmado!");
        } else {
            System.out.println("Reserva vencida!");
        }
        return pedido;
    }

    private boolean confirmarPagamento(Pedido pedido) {
        Date hoje = new Date();
        return hoje.before(pedido.getDataVencimentoReserva());
    }
}
