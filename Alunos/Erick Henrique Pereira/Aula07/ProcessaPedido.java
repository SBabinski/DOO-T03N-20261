import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ProcessaPedido {

    public Pedido processar(int id, Cliente cliente, Vendedor vendedor, Loja loja, List<Item> itens, LocalDate dataVencimentoReserva) {
        Pedido pedido = new Pedido();

        pedido.setId(id);
        pedido.setCliente(cliente);
        pedido.setVendedor(vendedor);
        pedido.setLoja(loja);
        pedido.setItens(itens);
        pedido.setDataCriacao(LocalDate.now());
        pedido.setDataVencimentoReserva(dataVencimentoReserva);

        if (confirmarPagamento(pedido)) {
            pedido.setDataPagamento(LocalDate.now());
            System.out.println("Pagamento confirmado e registrado");
        } else {
            System.out.println("Reserva vencida! Pagamento não realizado");
        }

        return pedido;
    }

    private boolean confirmarPagamento(Pedido pedido) {
        LocalDate dataAtual = LocalDate.now();
        return dataAtual.isBefore(pedido.getDataVencimentoReserva());
    }

    public void testeConfirmarPagamento(Pedido pedido) {
        if (confirmarPagamento(pedido)) {
            System.out.println("Pagamento confirmado com sucesso!");
        } else {
            System.out.println("Erro: Reserva vencida");
        }
    }
}