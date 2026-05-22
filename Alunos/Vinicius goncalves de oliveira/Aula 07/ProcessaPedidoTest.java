import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProcessaPedidoTest {

    public static void main(String[] args) {
        executar();
    }

    public static void executar() {
        testarPedidoValido();
        testarPedidoVencido();
    }

    private static void testarPedidoValido() {
        ProcessaPedido processaPedido = new ProcessaPedido();
        Loja loja = criarLojaBase();
        Cliente cliente = new Cliente("Ana", 29, new Endereco("PR", "Curitiba", "Centro", "Rua A", "10", "Apto 2"));
        Vendedor vendedor = new Vendedor("Carlos", 32, loja, new Endereco("PR", "Curitiba", "Batel", "Rua B", "20", ""), 2500);
        List<Item> itens = new ArrayList<>();
        itens.add(new Item(1, "Lirio", "Planta", 25.0));
        itens.add(new Item(2, "Vaso", "Acessorio", 15.0));

        Pedido pedido = processaPedido.processar(
                1,
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now().plusDays(2),
                cliente,
                vendedor,
                loja,
                itens
        );

        if (pedido.calcularValorTotal() != 40.0) {
            throw new IllegalStateException("Teste falhou: valor total do pedido valido esta incorreto.");
        }

        System.out.println("Teste 1 OK: pedido valido processado com sucesso.");
    }

    private static void testarPedidoVencido() {
        ProcessaPedido processaPedido = new ProcessaPedido();
        Loja loja = criarLojaBase();
        Cliente cliente = new Cliente("Bia", 24, new Endereco("SC", "Florianopolis", "Trindade", "Rua C", "30", ""));
        Vendedor vendedor = new Vendedor("Diego", 27, loja, new Endereco("SC", "Florianopolis", "Centro", "Rua D", "55", ""), 2400);
        List<Item> itens = new ArrayList<>();
        itens.add(new Item(3, "Orquidea", "Planta", 50.0));

        try {
            processaPedido.processar(
                    2,
                    LocalDate.now().minusDays(5),
                    LocalDate.now(),
                    LocalDate.now().minusDays(1),
                    cliente,
                    vendedor,
                    loja,
                    itens
            );
            throw new IllegalStateException("Teste falhou: pedido vencido deveria lancar excecao.");
        } catch (IllegalStateException e) {
            System.out.println("Teste 2 OK: pedido vencido bloqueado corretamente.");
        }
    }

    private static Loja criarLojaBase() {
        return new Loja(
                "Garden Flores",
                "Garden Flores LTDA",
                "00.000.000/0001-00",
                new Endereco("PR", "Curitiba", "Centro", "Av. Flores", "100", "Sala 1")
        );
    }
}
