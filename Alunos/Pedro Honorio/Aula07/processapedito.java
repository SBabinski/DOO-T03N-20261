 import java.util.Date;

public class ProcessaPedido {
    public void processar(Pedido pedido) {
        if (confirmarPagamento(pedido)) {
            pedido.dataPagamento = new Date();
            System.out.println("Pedido " + pedido.id + " processado!");
        } else {
            System.out.println("Reserva vencida!");
        }
    }

    private boolean confirmarPagamento(Pedido pedido) {
        Date hoje = new Date();
        return !hoje.after(pedido.dataVencimentoReserva);
    }
}
