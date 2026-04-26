import java.util.ArrayList;
import java.util.Date;

public class ProcessaPedidoTest {
    public static void main(String[] args) {
        testProcessarPedidoComSucesso();
        testProcessarPedidoComReservaVencida();
    }

    public static void testProcessarPedidoComSucesso() {
        System.out.println("Executando: testProcessarPedidoComSucesso");
        
        Endereco emp = new Endereco("SC", "Joinville", "Centro", "100", null);
        Loja loja = new Loja("Teste", "Teste LTDA", "000", emp);
        Cliente cliente = new Cliente("Cliente", 20, emp);
        Vendedor vendedor = new Vendedor("Vendedor", 25, loja, emp, 2000f);
        ArrayList<Item> itens = new ArrayList<>();
        itens.add(new Item(1, "Rosa", "Flor", 10.0f));
        
        Pedido pedido = new Pedido(1, cliente, vendedor, loja, itens);
        
        ProcessaPedido processador = new ProcessaPedido();
        processador.processar(pedido);
        
        if (pedido.getDataPagamento() != null) {
            System.out.println("PASSOU: Pedido processado.");
        } else {
            System.out.println("FALHOU: Pedido não processado.");
        }
        System.out.println("-----------------------------------");
    }

    public static void testProcessarPedidoComReservaVencida() {
        System.out.println("Executando: testProcessarPedidoComReservaVencida");
        
        Endereco emp = new Endereco("SC", "Joinville", "Centro", "100", null);
        Loja loja = new Loja("Teste", "Teste LTDA", "000", emp);
        Cliente cliente = new Cliente("Cliente", 20, emp);
        Vendedor vendedor = new Vendedor("Vendedor", 25, loja, emp, 2000f);
        ArrayList<Item> itens = new ArrayList<>();
        itens.add(new Item(1, "Rosa", "Flor", 10.0f));
        
        Pedido pedido = new Pedido(2, cliente, vendedor, loja, itens);
        
        // Força vencimento da reserva (10 dias atrás)
        long dezDiasAtras = System.currentTimeMillis() - (10L * 24 * 60 * 60 * 1000);
        pedido.setDataVencimentoReserva(new Date(dezDiasAtras));
        
        ProcessaPedido processador = new ProcessaPedido();
        processador.processar(pedido);
        
        if (pedido.getDataPagamento() == null) {
            System.out.println("PASSOU: Pedido não processado corretamente (reserva vencida).");
        } else {
            System.out.println("FALHOU: Pedido processado mesmo com reserva vencida.");
        }
        System.out.println("-----------------------------------");
    }
}
