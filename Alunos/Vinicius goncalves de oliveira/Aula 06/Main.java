import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static ArrayList<Plantas> plantas = new ArrayList<>();
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        do {
            System.out.println("\n|===== CALCULADORA DA DONA GABRIELINHA =====|");
            System.out.println("|[1] - Cadastrar Loja/Clientes/Vendedor       |");
            System.out.println("|[2] - Calcular Preco Total                   |");
            System.out.println("|[3] - Calcular Troco                         |");
            System.out.println("|[4] - Listar Vendas                          |");
            System.out.println("|[5] - Buscar Total de Vendas por Dia         |");
            System.out.println("|[6] - Buscar Total de Vendas por Mes         |");
            System.out.println("|[7] - Sair                                   |");
            System.out.print("Escolha uma opcao: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                demonstrarClasses(scanner);
            }
            if (opcao == 2) {
                calcularPreco(scanner);
            }
            if (opcao == 3) {
                calcularTroco(scanner);
            }
            if (opcao == 4) {
                for (Plantas p : plantas) {
                    System.out.println(p);
                }
            }
            if (opcao == 5) {
                buscarVendasPorDia(scanner);
            }
            if (opcao == 6) {
                buscarVendasPorMes(scanner);
            }

        } while (opcao != 7);

        System.out.println("Sistema encerrado. Ate logo, Dona Gabrielinha!");
    }

    public static void demonstrarClasses(Scanner scanner) {
        System.out.println("Cadastro da loja");
        System.out.print("Nome da loja: ");
        String nomeLoja = scanner.nextLine();
        System.out.print("Razao social: ");
        String razaoSocial = scanner.nextLine();
        System.out.print("CNPJ: ");
        String cnpj = scanner.nextLine();
        System.out.print("Cidade da loja: ");
        String cidadeLoja = scanner.nextLine();
        System.out.print("Bairro da loja: ");
        String bairroLoja = scanner.nextLine();
        System.out.print("Rua da loja: ");
        String ruaLoja = scanner.nextLine();

        Loja loja = new Loja(
                nomeLoja,
                razaoSocial,
                cnpj,
                cidadeLoja,
                bairroLoja,
                ruaLoja
        );

        System.out.print("Quantidade de clientes: ");
        int quantidadeClientes = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= quantidadeClientes; i++) {
            System.out.println("Cliente " + i + ":");
            System.out.print("Nome: ");
            String nomeCliente = scanner.nextLine();
            System.out.print("Idade: ");
            int idadeCliente = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Cidade: ");
            String cidadeCliente = scanner.nextLine();
            System.out.print("Bairro: ");
            String bairroCliente = scanner.nextLine();
            System.out.print("Rua: ");
            String ruaCliente = scanner.nextLine();

            Cliente cliente = new Cliente(nomeCliente, idadeCliente, cidadeCliente, bairroCliente, ruaCliente);
            loja.adicionarCliente(cliente);
        }

        System.out.println("Dados do vendedor:");
        System.out.print("Nome: ");
        String nomeVendedor = scanner.nextLine();
        System.out.print("Idade: ");
        int idadeVendedor = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Cidade: ");
        String cidadeVendedor = scanner.nextLine();
        System.out.print("Bairro: ");
        String bairroVendedor = scanner.nextLine();
        System.out.print("Rua: ");
        String ruaVendedor = scanner.nextLine();
        System.out.print("Salario base: ");
        double salarioBase = scanner.nextDouble();
        scanner.nextLine();

        Vendedor vendedor = new Vendedor(
                nomeVendedor,
                idadeVendedor,
                loja,
                cidadeVendedor,
                bairroVendedor,
                ruaVendedor,
                salarioBase
        );

        System.out.println("Digite 3 salarios recebidos:");
        for (int i = 1; i <= 3; i++) {
            System.out.print("Salario " + i + ": ");
            double salarioRecebido = scanner.nextDouble();
            scanner.nextLine();
            vendedor.adicionarSalarioRecebido(salarioRecebido);
        }

        loja.adicionarVendedor(vendedor);

        loja.apresentarse();
        System.out.println("Quantidade de clientes: " + loja.contarClientes());
        System.out.println("Quantidade de vendedores: " + loja.contarVendedores());
        vendedor.apresentarse();
        System.out.println("Media dos salarios: " + vendedor.calcularMedia());
        System.out.println("Bonus do vendedor: " + vendedor.calcularBonus());
        System.out.println();
    }

    public static void calcularPreco(Scanner scanner) {
        System.out.print("Digite a data da venda (dd/MM/yyyy): ");
        LocalDate dataVenda = lerData(scanner);

        System.out.print("Digite a quantidade de plantas: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o preco unitario da planta: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();

        double desconto = 0;
        double total;
        if (quantidade >= 10) {
            System.out.println("Parabens, voce ganhou um desconto de 5%");
            desconto = preco - (preco * 5) / 100;
            total = quantidade * desconto;
        } else {
            total = quantidade * preco;
        }

        Plantas venda = new Plantas(quantidade, preco, total, desconto, dataVenda);
        plantas.add(venda);

        System.out.printf("Preco total da venda: R$ %.2f%n", total);
        System.out.println("Venda registrada automaticamente em: " + dataVenda.format(FORMATTER));
    }

    public static void calcularTroco(Scanner scanner) {
        System.out.print("Digite o valor pago: ");
        double valorPago = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Digite o valor da compra: ");
        double valorCompra = scanner.nextDouble();
        scanner.nextLine();

        double troco = valorPago - valorCompra;
        System.out.printf("Troco: R$ %.2f%n", troco);
    }

    public static void buscarVendasPorDia(Scanner scanner) {
        System.out.print("Digite o dia que deseja buscar (dd/MM/yyyy): ");
        LocalDate dataBusca = lerData(scanner);

        int totalVendas = 0;
        for (Plantas p : plantas) {
            if (p.getDataVenda() != null && p.getDataVenda().equals(dataBusca)) {
                totalVendas++;
            }
        }

        System.out.println("Data: " + dataBusca.format(FORMATTER));
        System.out.println("Total de vendas no dia: " + totalVendas);
    }

    public static void buscarVendasPorMes(Scanner scanner) {
        System.out.print("Digite o mes que deseja buscar (MM/yyyy): ");
        String entrada = scanner.nextLine().trim();

        int mes;
        int ano;
        try {
            String[] partes = entrada.split("/");
            mes = Integer.parseInt(partes[0]);
            ano = Integer.parseInt(partes[1]);
        } catch (Exception e) {
            System.out.println("Formato invalido! Use MM/yyyy.");
            return;
        }

        int totalVendas = 0;
        for (Plantas p : plantas) {
            if (p.getDataVenda() != null
                    && p.getDataVenda().getMonthValue() == mes
                    && p.getDataVenda().getYear() == ano) {
                totalVendas++;
            }
        }

        System.out.println("Mes: " + entrada);
        System.out.println("Total de vendas no mes: " + totalVendas);
    }

    private static LocalDate lerData(Scanner scanner) {
        LocalDate data = null;
        while (data == null) {
            String entrada = scanner.nextLine().trim();
            try {
                data = LocalDate.parse(entrada, FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.print("Data invalida! Use o formato dd/MM/yyyy: ");
            }
        }
        return data;
    }
}
