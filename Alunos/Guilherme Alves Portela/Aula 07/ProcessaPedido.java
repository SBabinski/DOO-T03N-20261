import java.util.ArrayList;
import java.util.Date;

public class ProcessaPedido {
    private ArrayList<Pedido> pedidos;
    private static int proximoIdPedido = 1;

    public ProcessaPedido() {
        this.pedidos = new ArrayList<Pedido>();
    }

    // Método para obter o próximo ID e incrementar
    public static int gerarProximoIdPedido() {
        return proximoIdPedido++;
    }

    // Método para processar pedido
    public Pedido processar(Date dataCriacao, Date dataVencimentoReserva, 
                             Cliente cliente, Vendedor vendedor, Loja loja, ArrayList<Item> itens) {
        
        int id = gerarProximoIdPedido();
        Pedido novoPedido = new Pedido(id, dataCriacao, dataVencimentoReserva, cliente, vendedor, loja);
        
        // Adiciona os itens ao pedido
        for (Item item : itens) {
            novoPedido.adicionarItem(item);
        }
        
        // Confirma o pagamento
        if (confirmarPagamento(dataVencimentoReserva)) {
            novoPedido.setDataPagamento(new Date());
            this.pedidos.add(novoPedido);
            System.out.println("Pedido processado com sucesso!");
            return novoPedido;
        } else {
            System.out.println("Erro: A data de vencimento da reserva já expirou. Pedido não foi processado.");
            return null;
        }
    }

    // Método privado para confirmar pagamento
    private boolean confirmarPagamento(Date dataVencimentoReserva) {
        Date dataAtual = new Date();
        
        // Verifica se a data atual não é superior à data de vencimento da reserva
        if (dataAtual.before(dataVencimentoReserva) || dataAtual.equals(dataVencimentoReserva)) {
            return true;
        } else {
            return false;
        }
    }

    // Getter para lista de pedidos
    public ArrayList<Pedido> getPedidos() {
        return this.pedidos;
    }

    // Método para listar todos os pedidos
    public void listarPedidos() {
        if (this.pedidos.isEmpty()) {
            System.out.println("Nenhum pedido processado.");
            return;
        }
        
        System.out.println("\n===== Lista de Pedidos =====");
        for (Pedido pedido : this.pedidos) {
            System.out.println(pedido);
        }
        System.out.println("============================\n");
    }
}
