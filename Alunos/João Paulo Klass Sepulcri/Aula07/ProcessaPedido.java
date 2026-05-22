import java.util.Calendar;
import java.util.Date;

public class ProcessaPedido {
    public Pedido processar(int id, Cliente cliente, Vendedor vendedor, Loja loja, Item[] itens) {
        Date dataCriacao = new Date();
        Date dataPagamento = new Date();

        Calendar calendario = Calendar.getInstance();
        calendario.setTime(dataCriacao);
        calendario.add(Calendar.DAY_OF_MONTH, 1);
        Date dataVencimentoReserva = calendario.getTime();

        if (confirmarPagamento(dataVencimentoReserva)) {
            return new Pedido(id, dataCriacao, dataPagamento, dataVencimentoReserva, cliente, vendedor, loja, itens);
        }

        System.out.println("Reserva vencida. Pedido nao foi processado.");
        return null;
    }

    private boolean confirmarPagamento(Date dataVencimentoReserva) {
        Date dataAtual = new Date();
        return !dataAtual.after(dataVencimentoReserva);
    }

    public void testarConfirmarPagamento() {
        Calendar calendario = Calendar.getInstance();

        calendario.add(Calendar.DAY_OF_MONTH, 1);
        Date reservaValida = calendario.getTime();

        calendario = Calendar.getInstance();
        calendario.add(Calendar.DAY_OF_MONTH, -1);
        Date reservaVencida = calendario.getTime();

        System.out.println("Reserva valida confirmada? " + confirmarPagamento(reservaValida));
        System.out.println("Reserva vencida confirmada? " + confirmarPagamento(reservaVencida));
    }
}
