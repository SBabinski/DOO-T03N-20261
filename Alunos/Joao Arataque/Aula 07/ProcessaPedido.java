import java.util.Date;
import java.util.List;

public class ProcessaPedido {
    public Pedido processar(int id, Cliente c, Vendedor v, Loja l, List<Item> itens) {
        Pedido p = new Pedido();
        p.id = id;
        p.cliente = c;
        p.vendedor = v;
        p.loja = l;
        p.itens = itens;
        p.dataCriacao = new Date();
        // Reserva vence em 3 dias (exemplo)
        p.dataVencimentoReserva = new Date(System.currentTimeMillis() + (3 * 24 * 60 * 60 * 1000));

        return p;
    }

    private boolean confirmarPagamento(Pedido pedido) {
        Date hoje = new Date();
        return hoje.before(pedido.dataVencimentoReserva);
    }

    public void testarPagamento(Pedido p) {
        if(confirmarPagamento(p)) {
            System.out.println("Pagamento Confirmado!");
        } else {
            System.out.println("Erro: Reserva Vencida.");
        }
    }
}
