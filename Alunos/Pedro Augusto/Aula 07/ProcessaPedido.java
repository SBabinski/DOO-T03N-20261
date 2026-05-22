import java.util.Date;

public class ProcessaPedido {
    public void processar(Pedido pedido) {
        if (confirmarPagamento(pedido)) {
            pedido.setDataPagamento(new Date());
            System.out.println("Pedido " + pedido.getId() + " processado com sucesso!");
        } else {
            System.out.println("Erro: Não foi possível processar o pedido. Reserva vencida.");
        }
    }

    private boolean confirmarPagamento(Pedido pedido) {
        Date agora = new Date();
        return agora.before(pedido.getDataVencimentoReserva());
    }
}
