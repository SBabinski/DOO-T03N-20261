import java.util.Date;
public class ProcessaPedido {
    public Pedido processar(int id, Date dataCriacao, Date dataPagamento, Date dataVencimentoReserva,
                            Cliente cliente, Vendedor vendedor, String loja, Item[] itens) {
        Pedido pedido = new Pedido(id, dataCriacao, dataPagamento, dataVencimentoReserva,
                                   cliente, vendedor, loja, itens);
        if (confirmarPagamento(dataVencimentoReserva)) {
            System.out.println("Pedido processado com sucesso!");
            pedido.gerarDescricaoVenda();
        } else {
            System.out.println("Pedido NAO processado: reserva vencida!");
        }
        return pedido;
    }
    private boolean confirmarPagamento(Date dataVencimentoReserva) {
        Date hoje = new Date();
        return !hoje.after(dataVencimentoReserva);
    }
}
