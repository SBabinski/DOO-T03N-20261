import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Venda> vendas = new ArrayList<>();
        Calculadora calculadora = new Calculadora();

        // NOVO: criando loja
        Loja loja = new Loja("My Plant", "My Plant LTDA", "123456789",
                "Curitiba", "Centro", "Rua das Flores");

        int opcao;

        do {
            System.out.println("\n=== Loja de Plantas ===");
            System.out.println("1. Calcular preço total");
            System.out.println("2. Calcular troco");
            System.out.println("3. Listar vendas");
            System.out.println("4. Buscar vendas por dia");
            System.out.println("5. Buscar vendas por mês");
            System.out.println("6. Cadastrar vendedor");
            System.out.println("7. Cadastrar cliente");
            System.out.println("8. Cadastrar gerente");
            System.out.println("9. Ver dados da loja");
            System.out.println("10. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {

                case 1:
                    System.out.print("Digite o nome da planta: ");
                    String nomePlanta = scanner.nextLine();

                    System.out.print("Digite o preço unitário da planta: ");
                    double precoUnitario = scanner.nextDouble();

                    System.out.print("Digite a quantidade: ");
                    int quantidade = scanner.nextInt();

                    Planta planta = new Planta(nomePlanta, precoUnitario);

                    double totalSemDesconto = precoUnitario * quantidade;
                    double precoTotal = calculadora.calcularPrecoTotal(precoUnitario, quantidade);

                    double desconto = totalSemDesconto - precoTotal;

                    LocalDate dataAtual = LocalDate.now();
                    Venda venda = new Venda(quantidade, precoTotal, desconto, dataAtual);
                    vendas.add(venda);

                    System.out.println("Planta: " + planta.getNome());
                    System.out.println("Preço final: R$ " + precoTotal);

                    if (desconto > 0) {
                        System.out.println("Desconto aplicado: R$ " + desconto);
                    }

                    break;

                case 2:
                    Endereco end = new Endereco("PR", "Cascavel", "Centro", 123, "Apto");
                
                    Cliente cliente = new Cliente("João", 20, end);
                    Vendedor vendedor = new Vendedor("Maria", 25, end, "Loja X", 1500);
                    Gerente gerente = new Gerente("Carlos", 40, end, "Loja X", 3000.0);
                
                    Item i1 = new Item(1, "Vaso", "Planta", 50);
                    Item i2 = new Item(2, "Terra", "Insumo", 30);
                
                    Item[] itens = {i1, i2};
                
                    loja = new Loja("My Plant", "LTDA", "123", "Cascavel", "Centro", "Rua A");
                
                    java.util.Date agora = new java.util.Date();
                    java.util.Date vencimento = new java.util.Date(agora.getTime() + 86400000); // +1 dia
                
                    Pedido pedido = new Pedido(1, agora, vencimento, cliente, vendedor, loja, itens);
                
                    ProcessaPedido proc = new ProcessaPedido();
                    proc.processar(pedido);
                
                    pedido.gerarDescricaoVenda();
                    break;              

            
                case 3:
                    System.out.println("\n=== Registro de Vendas ===");

                    for (Venda v : vendas) {
                        v.exibirVenda();
                    }
                    break;

                case 4:

                    System.out.print("Digite a data (AAAA-MM-DD): ");
                    String dataStr = scanner.nextLine();

                    LocalDate dataBusca = LocalDate.parse(dataStr);

                    int totalDia = 0;

                    for (Venda v : vendas) {
                        if (v.getData().equals(dataBusca)) {
                            totalDia++;
                        }
                    }

                    System.out.println("Total de vendas no dia " + dataBusca + ": " + totalDia);
                    break;

                case 5:
                    System.out.print("Digite o mês (1-12):");
                    int mes = scanner.nextInt();

                    int totalMes = 0;

                    for (Venda v : vendas) {
                        if (v.getData().getMonthValue() == mes) {
                            totalMes++;
                        }
                    }
                    System.out.println("Total de vendas no mês " + mes + ": " + totalMes);
                    break;

                case 6:
                    System.out.print("Nome do vendedor: ");
                    String nomeV = scanner.nextLine();

                    System.out.print("Idade: ");
                    int idadeV = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Salário base: ");
                    double salario = scanner.nextDouble();
                    scanner.nextLine();

                    Endereco endVendedor = new Endereco("PR", "Curitiba", "Centro", 0, "Rua X");
                    Vendedor novoVendedor = new Vendedor(nomeV, idadeV, endVendedor, "My Plant", salario);

                    loja.adicionarVendedor(novoVendedor);

                    System.out.println("Vendedor cadastrado!");
                    break;

                case 7:
                    System.out.print("Nome do cliente: ");
                    String nomeC = scanner.nextLine();

                    System.out.print("Idade: ");
                    int idadeC = scanner.nextInt();
                    scanner.nextLine();

                    Endereco endCliente = new Endereco("PR", "Curitiba", "Centro", 0, "Rua Y");
                    Cliente novoCliente = new Cliente(nomeC, idadeC, endCliente);

                    loja.adicionarCliente(novoCliente);

                    System.out.println("Cliente cadastrado!");
                    break;

                case 8:
                    System.out.print("Nome do gerente: ");
                    String nomeG = scanner.nextLine();

                    System.out.print("Idade: ");
                    int idadeG = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Salário base: ");
                    double salarioG = scanner.nextDouble();
                    scanner.nextLine();

                    Endereco endGerente = new Endereco("PR", "Curitiba", "Centro", 0, "Rua Z");
                    Gerente novoGerente = new Gerente(nomeG, idadeG, endGerente, "My Plant", salarioG);

                    loja.adicionarGerente(novoGerente);

                    System.out.println("Gerente cadastrado!");
                    break;

                case 9:
                    loja.apresentarSe();
                    System.out.println("Total de clientes: " + loja.contarClientes());
                    System.out.println("Total de vendedores: " + loja.contarVendedores());
                    System.out.println("Total de gerentes: " + loja.contarGerentes());
                    break;

                case 10:
                    System.out.println("Encerrando o programa...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 10);

        scanner.close();
    }
}
