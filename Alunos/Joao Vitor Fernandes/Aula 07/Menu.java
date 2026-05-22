import java.util.Date;
import java.util.Calendar;
import java.util.Scanner;

public class Menu {
    private Scanner scanner;
    private Pedido[] pedidos;
    private int contadorPedidos;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.pedidos = new Pedido[100];
        this.contadorPedidos = 0;
    }

    public void exibirMenu() {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n ===== MENU MY PLANT =====");
            System.out.println("1. Criar Pedido");
            System.out.println("2. Listar Pedidos");
            System.out.println("3. Ver Detalhes do Pedido");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    criarPedido();
                    break;
                case 2:
                    listarPedidos();
                    break;
                case 3:
                    verDetalhesPedido();
                    break;
                case 4:
                    continuar = false;
                    System.out.println(" Até logo!");
                    break;
                default:
                    System.out.println("❌ Opção inválida!");
            }
        }

        scanner.close();
    }

    private void criarPedido() {
        System.out.println("\n ===== CRIAR NOVO PEDIDO =====");

        // Dados fake para demonstração
        Endereco enderecoCLiente = new Endereco("SP", "São Paulo", "Pinheiros", "Rua das Flores", 123, "Apt 301");
        Endereco enderecoVendedor = new Endereco("SP", "São Paulo", "Centro", "Avenida Principal", 1000, "");
        Endereco enderecoGerente = new Endereco("SP", "São Paulo", "Vila Mariana", "Rua do Comércio", 456, "");

        Cliente cliente = new Cliente("Ana Costa", 42, enderecoCLiente, "ana@myplant.com", "11998765432");
        Vendedor vendedor = new Vendedor("Bruno Silva", 30, enderecoVendedor, "Loja Centro", 3000);
        Gerente gerente = new Gerente("Patricia Alves", 48, enderecoGerente, "Loja Centro", 6000);

        // Adicionar alguns salários de exemplo ao gerente
        gerente.adicionarSalario(6500);
        gerente.adicionarSalario(6200);

        // Criar itens de exemplo
        Item[] itens = {
            new Item(1, "Sementes de Orquídea", "Sementes", 89.90),
            new Item(2, "Adubo NPK Premium", "Adubo", 145.50),
            new Item(3, "Vaso de Cerâmica Grande", "Acessório", 199.00),
            new Item(4, "Fertilizante Líquido", "Adubo", 75.00)
        };

        contadorPedidos++;
        int idPedido = contadorPedidos;

        Date dataCriacao = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataCriacao);
        calendar.add(Calendar.DAY_OF_MONTH, 7); // Vencimento em 7 dias
        Date dataVencimento = calendar.getTime();

        ProcessaPedido processador = new ProcessaPedido();
        Pedido novoPedido = processador.processar(idPedido, dataCriacao, dataVencimento,
                                                   cliente, vendedor, gerente, "Loja Centro", itens);

        if (novoPedido != null) {
            pedidos[contadorPedidos - 1] = novoPedido;
            novoPedido.gerarDescricaoVenda();
            System.out.println(" Pedido #" + idPedido + " criado com sucesso!");
        } else {
            System.out.println(" Não foi possível criar o pedido.");
            contadorPedidos--;
        }
    }

    private void listarPedidos() {
        System.out.println("\n ===== PEDIDOS REGISTRADOS =====");

        if (contadorPedidos == 0) {
            System.out.println("Nenhum pedido registrado.");
            return;
        }

        for (int i = 0; i < contadorPedidos; i++) {
            if (pedidos[i] != null) {
                System.out.println("\n#" + pedidos[i].getId() + " | Cliente: " + 
                                 pedidos[i].getCliente().getNome() + 
                                 " | Valor: R$ " + String.format("%.2f", pedidos[i].calcularValorTotal()) +
                                 " | Data: " + pedidos[i].getDataCriacao());
            }
        }
    }

    private void verDetalhesPedido() {
        System.out.println("\n ===== DETALHES DO PEDIDO =====");
        System.out.print("Digite o ID do pedido: ");
        int idPedido = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < contadorPedidos; i++) {
            if (pedidos[i] != null && pedidos[i].getId() == idPedido) {
                pedidos[i].gerarDescricaoVenda();
                return;
            }
        }

        System.out.println(" Pedido não encontrado.");
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.exibirMenu();
    }
}
