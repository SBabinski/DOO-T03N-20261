import java.util.Date;

public class ProcessaPedido {
    private Pedido pedido;

    public ProcessaPedido() {
        this.pedido = null;
    }

    public Pedido processar(int idPedido, Date dataCriacao, Date dataVencimentoReserva,
                            Cliente cliente, Vendedor vendedor, Gerente gerente, 
                            String loja, Item[] itens) {
        // Criar novo pedido
        pedido = new Pedido(idPedido, dataCriacao, dataVencimentoReserva, 
                           cliente, vendedor, gerente, loja);
        
        // Adicionar itens ao pedido
        for (Item item : itens) {
            pedido.adicionarItem(item);
        }

        // Confirmar pagamento
        if (confirmarPagamento()) {
            pedido.setDataPagamento(new Date());
            System.out.println("✅ Pagamento confirmado para o pedido #" + idPedido);
            return pedido;
        } else {
            System.out.println("❌ Falha ao processar pagamento. Reserva vencida!");
            return null;
        }
    }

    private boolean confirmarPagamento() {
        if (pedido == null) {
            return false;
        }

        Date dataAtual = new Date();
        Date dataVencimento = pedido.getDataVencimentoReserva();

        // Verifica se a data atual não é superior à data de vencimento
        if (dataAtual.after(dataVencimento)) {
            return false;
        }

        return true;
    }

    public Pedido getPedido() {
        return pedido;
    }
}
