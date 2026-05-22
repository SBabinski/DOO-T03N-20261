import java.util.Scanner;
import java.util.Date;
import java.util.Calendar;
public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n=== MY PLANT - SISTEMA ===");
            System.out.println("1 - Apresentar Cliente");
            System.out.println("2 - Apresentar Vendedor");
            System.out.println("3 - Apresentar Gerente");
            System.out.println("4 - Criar Pedido (dados fake)");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();
            switch (opcao) {
                case 1:
                    demonstrarCliente();
                    break;
                case 2:
                    demonstrarVendedor();
                    break;
                case 3:
                    demonstrarGerente();
                    break;
                case 4:
                    criarPedidoFake();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        }
    }
    static void demonstrarCliente() {
        Endereco end = new Endereco("PR", "Cascavel", "Centro", "Rua XV de Novembro", "123", "Sala 1");
        Cliente cliente = new Cliente("Ana Paula", 25, end, "000.111.222-33", "(44) 99000-1111");
        System.out.println("\n--- Dados do Cliente ---");
        cliente.apresentarse();
    }
    static void demonstrarVendedor() {
        Endereco end = new Endereco("PR", "Cascavel", "Neva", "Av. Brasil", "500", "");
        Vendedor vendedor = new Vendedor("Carlos Souza", 32, end, "MAT-010", "My Plant Centro");
        System.out.println("\n--- Dados do Vendedor ---");
        vendedor.apresentarse();
    }
    static void demonstrarGerente() {
        Endereco end = new Endereco("PR", "Cascavel", "Universitario", "Rua Pernambuco", "88", "");
        Gerente gerente = new Gerente("Roberto Lima", 45, end, "My Plant Centro", 5000.00);
        System.out.println("\n--- Dados do Gerente ---");
        gerente.apresentarse();
        System.out.println("Media Salarial: R$ " + gerente.calcularMedia());
        System.out.println("Bonus: R$ " + gerente.calcularBonus());
    }
    static void criarPedidoFake() {
        Endereco endCliente = new Endereco("PR", "Cascavel", "Centro", "Rua das Flores", "10", "");
        Endereco endVendedor = new Endereco("PR", "Cascavel", "Neva", "Av. Brasil", "200", "");
        Cliente cliente = new Cliente("Lucia Martins", 38, endCliente, "555.666.777-88", "(44) 98555-6666");
        Vendedor vendedor = new Vendedor("Pedro Alves", 29, endVendedor, "MAT-005", "My Plant Norte");
        Item[] itens = {
            new Item(1, "Orquidea", "Planta", 80.00),
            new Item(2, "Fertilizante", "Produto", 25.00),
            new Item(3, "Vaso de Barro", "Acessorio", 40.00)
        };
        Date dataCriacao = new Date();
        Date dataPagamento = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 3);
        Date dataVencimento = cal.getTime();

        ProcessaPedido pp = new ProcessaPedido();
        System.out.println("\n--- Criando Pedido ---");
        Pedido pedido = pp.processar(101, dataCriacao, dataPagamento, dataVencimento,
                                     cliente, vendedor, "My Plant Norte", itens);
        System.out.println("\n--- Itens do Pedido ---");
        for (Item item : itens) {
            item.gerarDescricao();
        }
    }
}
