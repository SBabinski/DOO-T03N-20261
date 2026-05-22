import java.util.Date;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    static Scanner scanner      = new Scanner(System.in);
    static RegistroVenda registro = new RegistroVenda();
    static ProcessaPedido processador = new ProcessaPedido();
    static ArrayList<Loja> lojas = new ArrayList<>();
    static ArrayList<Vendedor> vendedores = new ArrayList<>();
    static ArrayList<Gerente> gerentes = new ArrayList<>();
    static ArrayList<Cliente> clientes = new ArrayList<>();

    public static void main(String[] args) {
        inicializarDadosFake();
        
        int opcao;
        do {
            System.out.println("\n===== My Plant - Menu =====");
            System.out.println("[1] Calcular Preço Total");
            System.out.println("[2] Calcular Troco");
            System.out.println("[3] Criar Pedido");
            System.out.println("[4] Buscar Vendas por Mês e Dia");
            System.out.println("[5] Consultar Lojas");
            System.out.println("[6] Consultar Vendedores");
            System.out.println("[7] Consultar Gerentes");
            System.out.println("[8] Consultar Clientes");
            System.out.println("[9] Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                     calcularPrecoTotal();
                        break;
                case 2:
                     calcularTroco();
                        break;
                case 3:
                     criarPedidoFake();
                        break;
                case 4:
                     buscarVendas();
                        break;
                case 5:
                     Loja.consultarLojas(lojas);
                        break;
                case 6:
                     Vendedor.consultarVendedores(vendedores);
                        break;
                case 7:
                     Gerente.consultarGerentes(gerentes);
                        break;
                case 8:
                     Cliente.consultarClientes(clientes);
                        break;
                case 9:
                     System.out.println("Até logo, Dona Gabrielinha!");
                        break;
                default:
                     System.out.println("Opção inválida.");
            }
        } while (opcao != 9);
    }
    
    static void inicializarDadosFake() {
        // Criar endereços
        Endereco end1 = new Endereco("SP", "São Paulo", "Centro", 
                                     "Rua das Flores", "200", "Loja 1");
        Endereco end2 = new Endereco("RJ", "Rio de Janeiro", "Copacabana", 
                                     "Avenida Atlântica", "1200", "Loja 2");
        Endereco end3 = new Endereco("MG", "Belo Horizonte", "Savassi", 
                                     "Rua da Bahia", "500", "Residência");
        Endereco end4 = new Endereco("BA", "Salvador", "Barra", 
                                     "Rua Waldemar Falcão", "150", "Apto 301");
        
        // Criar Vendedores
        double[] salVendedor1 = {2500.0, 2600.0, 2700.0};
        double[] salVendedor2 = {3000.0, 3100.0, 3200.0};
        
        Vendedor v1 = new Vendedor("Ana Lima", 28, end3, "My Plant", 2500.0, salVendedor1);
        Vendedor v2 = new Vendedor("Carlos Mendes", 35, end4, "Flora Brasil", 3000.0, salVendedor2);
        vendedores.add(v1);
        vendedores.add(v2);
        
        // Criar Gerentes
        double[] salGerente1 = {4000.0, 4200.0, 4300.0};
        double[] salGerente2 = {4500.0, 4700.0, 4800.0};
        
        Gerente g1 = new Gerente("Maria Santos", 45, end1, "My Plant", 4000.0, salGerente1);
        Gerente g2 = new Gerente("Roberto Costa", 50, end2, "Flora Brasil", 4500.0, salGerente2);
        gerentes.add(g1);
        gerentes.add(g2);
        
        // Criar Clientes
        Cliente c1 = new Cliente("João Souza", 42, end1);
        Cliente c2 = new Cliente("Fernanda Oliveira", 38, end2);
        clientes.add(c1);
        clientes.add(c2);
        
        // Criar Lojas
        Vendedor[] vends1 = {v1};
        Vendedor[] vends2 = {v2};
        Cliente[] clts1 = {c1};
        Cliente[] clts2 = {c2};
        
        Loja loja1 = new Loja("My Plant", "My Plant LTDA", "12.345.678/0001-99", 
                              end1, vends1, clts1);
        Loja loja2 = new Loja("Flora Brasil", "Flora Brasil S.A.", "98.765.432/0001-00", 
                              end2, vends2, clts2);
        lojas.add(loja1);
        lojas.add(loja2);
    }
    
    static void calcularPrecoTotal() {
        System.out.print("Quantidade de plantas: ");
        int qtd = scanner.nextInt();
        System.out.print("Preço unitário: R$ ");
        double preco = scanner.nextDouble();

        double total = qtd * preco;

        if (qtd > 10) {
            double desconto = total * 0.05;
            total -= desconto;
            System.out.printf("Desconto de 5%% aplicado: -R$ %.2f%n", desconto);
        }

        System.out.printf("Preço total: R$ %.2f%n", total);
    }

    static void calcularTroco() {
        System.out.print("Valor recebido: R$ ");
        double recebido = scanner.nextDouble();
        System.out.print("Valor total da compra: R$ ");
        double total = scanner.nextDouble();
        System.out.printf("Troco: R$ %.2f%n", recebido - total);
    }

    static void criarPedidoFake() {
        Endereco end = new Endereco("SP", "São Paulo", "Centro",
                                    "Rua das Flores", "200", "Loja 1");

        Cliente cliente   = new Cliente("João Souza", 42, end);
        double[] sals     = {2500.0, 2600.0, 2700.0};
        Vendedor vendedor = new Vendedor("Ana Lima", 28, end, "My Plant", 2500.0, sals);
        Loja loja         = new Loja("My Plant", "My Plant LTDA", "12.345.678/0001-99",
                                      end, new Vendedor[]{vendedor}, new Cliente[]{cliente});

        Item[] itens = {
            new Item(1, "Orquidea Negra",  "Exótica", 150.0),
            new Item(2, "Cacto Astronium", "Exótica",  80.0)
        };

        Date agora       = new Date();
        Date vencimento  = new Date(agora.getTime() + 2L * 24 * 60 * 60 * 1000);

        Pedido pedido = processador.processar(
                1, agora, null, vencimento, cliente, vendedor, loja, itens);

        pedido.gerarDescricaoVenda();
        registro.salvarVenda(pedido);
    }

    static void buscarVendas() {
        System.out.print("Mês (1-12): ");
        int mes = scanner.nextInt();
        System.out.print("Dia: ");
        int dia = scanner.nextInt();
        registro.buscarVendasPorMesEDia(mes, dia);
    }
}