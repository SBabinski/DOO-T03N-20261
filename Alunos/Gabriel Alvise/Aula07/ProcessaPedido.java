import java.util.Date;

public class ProcessaPedido {

    public Pedido processar(int id, Cliente cliente, Vendedor vendedor, Loja loja, Item[] itens, Date dataVencimentoReserva) {
        Pedido pedido = new Pedido();

        pedido.setId(id);
        pedido.setCliente(cliente);
        pedido.setVendedor(vendedor);
        pedido.setLoja(loja);
        pedido.setItens(itens);
        pedido.setDataCriacao(new Date());
        pedido.setDataVencimentoReserva(dataVencimentoReserva);

        if (confirmarPagamento(pedido)) {
            pedido.setDataPagamento(new Date());
            System.out.println("Pagamento confirmado e registrado");
        } else {
            System.out.println("Reserva vencida! Pagamento não realizado");
        }

        return pedido;
    }

    private boolean confirmarPagamento(Pedido pedido) {
        Date dataAtual = new Date();

        return dataAtual.before(pedido.getDataVencimentoReserva());
    }

    public void testeConfirmarPagamento(Pedido pedido) {
        if (confirmarPagamento(pedido)) {
            System.out.println("Pagamento confirmado com sucesso!");
        } else {
            System.out.println("Erro: Reserva vencida");
        }
    }
}