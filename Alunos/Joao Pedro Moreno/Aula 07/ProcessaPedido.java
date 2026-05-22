import java.time.LocalDate;
import java.util.ArrayList;

public class ProcessaPedido {

    // Cria e registra um pedido completo no histórico da loja
    public Pedido processar(Cliente cliente, Vendedor vendedor, Loja loja,
            ArrayList<Item> itens, int diasParaVencer) {

        LocalDate criacao = Date.hoje();
        LocalDate vencimento = Date.daquiEmDias(diasParaVencer);

        Pedido pedido = new Pedido(criacao, vencimento, cliente, vendedor, loja);

        for (Item item : itens) {
            pedido.adicionarItem(item);
        }

        System.out.println("Pedido criado com sucesso!");
        System.out.println("  Criado em:  " + Date.formatar(criacao));
        System.out.println("  Vencimento: " + Date.formatar(vencimento));

        return pedido;
    }

    // Confirma pagamento(só aceita se a reserva não estiver vencida)
    private boolean confirmarPagamento(Pedido pedido) {
        if (Date.estaVencido(pedido.getDataVencimentoReserva())) {
            System.out.println("Pagamento RECUSADO: reserva vencida em "
                    + Date.formatar(pedido.getDataVencimentoReserva()));
            return false;
        }
        pedido.setDataPagamento(Date.hoje());
        System.out.println("Pagamento CONFIRMADO em " + Date.formatar(Date.hoje()));
        return true;
    }

    // Método público que aciona a confirmação (chamado pelo menu)
    public boolean efetuarPagamento(Pedido pedido) {
        return confirmarPagamento(pedido);
    }

    // Testes
    public static void main(String[] args) {
        System.out.println("=== TESTE 1: pedido dentro do prazo ===");
        testarPedidoDentroDoPrazo();

        System.out.println("\n=== TESTE 2: pedido com reserva vencida ===");
        testarPedidoVencido();
    }

    private static void testarPedidoDentroDoPrazo() {
        Cliente cliente = new Cliente("João", 30, new Endereco("SP", "Centro", "Rua A"));
        Vendedor vendedor = new Vendedor("Carlos", 28, "Loja A",
                new double[] { 2500, 2600 }, 2500, new Endereco("SP", "Centro", "Rua B"));
        Loja loja = new Loja("Loja de Plantas", "Plantas LTDA",
                "12.345.678/0001-90", new Endereco("SP", "Centro", "Rua A"));

        ArrayList<Item> itens = new ArrayList<>();
        itens.add(new Item(1, "Samambaia", "Folhagem", 25.0, 2));
        itens.add(new Item(2, "Cacto", "Suculenta", 15.0, 1));

        ProcessaPedido proc = new ProcessaPedido();
        Pedido pedido = proc.processar(cliente, vendedor, loja, itens, 7);

        boolean pago = proc.efetuarPagamento(pedido);
        System.out.println("Resultado: " + (pago ? "APROVADO" : "NEGADO"));
        System.out.println(pedido);
    }

    private static void testarPedidoVencido() {
        Cliente cliente = new Cliente("Maria", 25, new Endereco("RJ", "Copacabana", "Rua B"));
        Vendedor vendedor = new Vendedor("Ana", 35, "Loja B",
                new double[] { 2500, 2600 }, 2500, new Endereco("RJ", "Centro", "Rua C"));
        Loja loja = new Loja("Loja de Plantas", "Plantas LTDA",
                "12.345.678/0001-90", new Endereco("SP", "Centro", "Rua A"));

        ArrayList<Item> itens = new ArrayList<>();
        itens.add(new Item(3, "Rosa", "Florida", 30.0, 3));

        // Simula pedido com vencimento ontem (já vencido)
        Pedido pedido = new Pedido(
                Date.hoje().minusDays(5), // criado há 5 dias
                Date.hoje().minusDays(1), // venceu ontem
                cliente, vendedor, loja);
        pedido.adicionarItem(itens.get(0));

        ProcessaPedido proc = new ProcessaPedido();
        boolean pago = proc.efetuarPagamento(pedido);
        System.out.println("Resultado: " + (pago ? "APROVADO" : "NEGADO"));
    }
}
