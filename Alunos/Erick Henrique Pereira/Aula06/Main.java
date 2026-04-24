import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private final static Scanner scan = new Scanner(System.in);
    private final static LocalDate data = LocalDate.now();
    private final static List<Calculadora> vendas = new ArrayList<>();
    private static List<Vendedor> vendedores;
    private static List<Cliente> clientes;
    private static List<Loja> lojas;

    public static void main(String[] args) {

        Endereco endereco1 = new Endereco("São Paulo", "Jardins", "Av. Paulista, 1000");
        Endereco endereco2 = new Endereco("São Paulo", "Vila Madalena", "Rua Harmonia, 250");
        Endereco endereco3 = new Endereco("São Paulo", "Pinheiros", "Rua dos Pinheiros, 500");

        Cliente cliente1 = new Cliente("Carlos", 28, endereco1);
        Cliente cliente2 = new Cliente("Mariana", 32, endereco2);
        Cliente cliente3 = new Cliente("João", 24, endereco3);
        clientes = List.of(cliente1, cliente2, cliente3);


        Loja loja1 = new Loja("Loja 1", "Razão Social 1", "12345678000100", endereco1, null, clientes);
        Loja loja2 = new Loja("Loja 2", "Razão Social 2", "22345678000100", endereco2, null, clientes);
        Loja loja3 = new Loja("Loja 3", "Razão Social 3", "32345678000100", endereco3, null, clientes);
        lojas = List.of(loja1, loja2, loja3);

        List<Vendedor> vendedores1 = new ArrayList<>();
        List<Vendedor> vendedores2 = new ArrayList<>();
        List<Vendedor> vendedores3 = new ArrayList<>();
        loja1 .setVendedores(vendedores1);
        loja2 .setVendedores(vendedores2);
        loja3 .setVendedores(vendedores3);

        List<Cliente> clientes1 = new ArrayList<>();
        List<Cliente> clientes2 = new ArrayList<>();
        List<Cliente> clientes3 = new ArrayList<>();
        loja1.setClientes(clientes1);
        loja2.setClientes(clientes2);   
        loja3.setClientes(clientes3);

        Vendedor vendedor1 = new Vendedor("Ana", 30, loja1, endereco1, 2500.0);
        vendedores1.add(vendedor1);
        Vendedor vendedor2 = new Vendedor("Bruno", 27, loja2, endereco2, 2600.0);
        vendedores2.add(vendedor2);
        Vendedor vendedor3 = new Vendedor("Fernanda", 35, loja3, endereco3, 2700.0);
        vendedores3.add(vendedor3);
        
        System.out.println("Bem vindo ao sistema de gerencia de negócio");

        int opcao;
        do {
            opcao = mostrarMenu();
            switch (opcao) {
                case 1 -> menuVendedores();
                case 2 -> menuClientes();
                case 3 -> menuLojas();
                case 4 -> menuCalculadora();
                case 5 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida, tente novamente.");
            }
        } while (opcao != 5);
    }

    private static void menuCalculadora() {
        int opcao;
        do {
            System.out.println("\nMenu Calculadora");
            System.out.println("1 - Registrar venda");
            System.out.println("2 - Calcular troco");
            System.out.println("3 - Registro de vendas");
            System.out.println("4 - Voltar ao menu principal");
            opcao = scan.nextInt();

            switch (opcao) {
                case 1 -> {
                    try {
                        System.out.println("Digite a quantidade do produto:");
                        int quantidade = scan.nextInt();
                        scan.nextLine();
                        
                        System.out.println("Digite o preço do produto:");
                        double preco = scan.nextDouble();
                        scan.nextLine();
                        
                        double total = preco * quantidade;
                        double totalcomdesc = (quantidade > 10) ? total * 0.95 : total;
                        
                        System.out.printf("Total sem desconto: R$ %.2f\n", total);
                        System.out.printf("O preço total é: R$ %.2f\n", totalcomdesc);

                        System.out.println("Digite a data da venda (dd/MM/yyyy):");
                        String dataTexto = scan.next();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate dataVenda = LocalDate.parse(dataTexto, formatter);

                        Calculadora venda = new Calculadora();
                        venda.setQuantidade(quantidade);
                        venda.setPreco(preco);
                        venda.setDescontoAplicado(totalcomdesc);
                        venda.setSaleDate(dataVenda);

                        vendas.add(venda);
                        System.out.println("Venda registrada com sucesso!");

                    } catch (Exception e) {
                        System.err.println("Erro: Entrada inválida ou formato de data incorreto.");
                        scan.nextLine(); 
                    }
                }
                case 2 -> {
                    System.out.println("Digite o valor pago:");
                    double valorPago = scan.nextDouble();
                    System.out.println("Digite o preço total da compra:");
                    double precoTotal = scan.nextDouble();
                    double troco = valorPago - precoTotal;
                    System.out.println("O troco é: " + troco);
                }
                case 3 -> {
                    if (vendas.isEmpty()) {
                        System.out.println("Nenhuma venda registrada.");
                    } else {
                        System.out.println("Digite a operação: ");
                        System.out.println("1 - Exibir vendas de um dia específico");
                        System.out.println("2 - Exibir todas as vendas de um mês específico");
                        System.out.println("3 - Exibir todas as vendas");
                        int option = scan.nextInt();
                        scan.nextLine(); 
                        switch (option) {
                            case 1 -> consultationDay(vendas);
                            case 2 -> consultationMonth(vendas);
                            case 3 -> consultationAll(vendas);
                            default -> System.out.println("Opção inválida.");
                        }
                    }
                }
                case 4 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 4);
    }

    private static void consultationAll(List<Calculadora> vendas) {
        try {
            if (vendas.isEmpty()) {
                System.out.println("Nenhuma venda registrada.");
                return;
            }
        } catch (Exception e) {
            System.err.println("Erro ao acessar vendas: " + e.getMessage());
            return;
        }
        System.out.println("Todas as vendas:");
        for (Calculadora venda : vendas) {
            System.out.println("Quantidade: " + venda.getQuantidade()
                    + ", Data: " + venda.getSaleDate()
                    + " | Preço Unitário: R$ " + venda.getPreco()
                    + ", Total com desconto: " + venda.getDescontoAplicado());
        }
    }

   private static void consultationDay(List<Calculadora> vendas) {
        try {
            System.out.println("Digite a data da venda (dd/MM/yyyy):");
            String dataConsulta = scan.nextLine();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data = LocalDate.parse(dataConsulta, formatter);
            
            boolean encontrou = false;
            System.out.println("\n--- Vendas do dia " + dataConsulta + " ---");
            
            for (Calculadora venda : vendas) {
                if (venda.getSaleDate().isEqual(data)) {
                    System.out.println("Quantidade: " + venda.getQuantidade() 
                        + " | Preço Unitário: R$ " + venda.getPreco()
                        + " | Total: R$ " + venda.getDescontoAplicado());
                    encontrou = true;
                }
            }
            if (!encontrou) {
                System.out.println("Aviso: Nenhuma venda registrada para este dia.");
            }
        } catch (Exception e) {
            System.err.println("Erro: Formato de data inválido. Use dd/MM/yyyy.");
        }
    }

    private static void consultationMonth(List<Calculadora> vendas) {
        try {
            System.out.println("Digite o mês e ano da venda (MM/yyyy):");
            String dataConsulta = scan.nextLine();  
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataRef = LocalDate.parse("01/" + dataConsulta, formatter); //estou passando o dia um como padrão na busca do mês
            boolean encontrou = false;
            System.out.println("\n--- Vendas de " + dataConsulta + " ---");
            for (Calculadora venda : vendas) {
                if (venda.getSaleDate().getMonth() == dataRef.getMonth() && 
                    venda.getSaleDate().getYear() == dataRef.getYear()) {
                    System.out.println("Dia: " + venda.getSaleDate().getDayOfMonth() 
                        + " | Qtd: " + venda.getQuantidade() 
                        + " | Preço Unitário: R$ " + venda.getPreco()
                        + " | Total: R$ " + venda.getDescontoAplicado());
                    encontrou = true;
                }
            }
            if (!encontrou) {
                System.out.println("Aviso: Nenhuma venda registrada para este mês.");
            }
        } catch (Exception e) {
            System.err.println("Erro: Formato inválido. Use MM/yyyy.");
        }finally {
            System.out.println("retornando ao menu...");
        }
    }

    private static void menuLojas() {
        int opcao;
        do {
            System.out.println("\nMenu Lojas");
            System.out.println("1 - Apresentar lojas");
            System.out.println("2 - Contar clientes");
            System.out.println("3 - Contar vendedores");
            System.out.println("4 - Voltar ao menu principal");
            opcao = scan.nextInt();
            switch (opcao) {
                case 1 -> {
                    for (Loja l : lojas) {
                        l.apresentarse();
                        System.out.println();
                    }
                }
                case 2 -> {
                    for (Loja l : lojas) {
                        System.out.println("Número de clientes na " + l.getNomeFantasia() + ": " + l.contarClientes());
                    }
                }
                case 3 -> {
                    for (Loja l : lojas) {
                        System.out.println("Número de vendedores na " + l.getNomeFantasia() + ": " + l.contarVendedores());
                    }
                }
                case 4 -> System.out.println("Voltando...");
            }
        } while (opcao != 4);
    }

    private static void menuClientes() {
        int opcao;
        do {
            System.out.println("\nMenu Clientes");
            System.out.println("1 - Apresentar clientes");
            System.out.println("2 - Voltar ao menu principal");
            opcao = scan.nextInt();
            if (opcao == 1) {
                for (Cliente c : clientes) {
                    c.apresentarse();
                    System.out.println();
                }
            }
        } while (opcao != 2);
    }

    private static void menuVendedores() {
        int opcao;
        do {
            System.out.println("\nMenu Vendedores");
            System.out.println("1 - Apresentar vendedores");
            System.out.println("2 - Calcular média salarial");
            System.out.println("3 - Calcular bônus");
            System.out.println("4 - Voltar ao menu principal");
            opcao = scan.nextInt();
            switch (opcao) {
                case 1 -> {
                    for (Vendedor v : vendedores) {
                        v.apresentarse();
                        System.out.println();
                    }
                }
                case 2 -> {
                    for (Vendedor v : vendedores) {
                        System.out.println("Média salarial de " + v.getName() + ": " + v.calcularMedia());
                    }
                }
                case 3 -> {
                    for (Vendedor v : vendedores) {
                        System.out.println("Bônus de " + v.getName() + ": " + v.calcularBonus());
                    }
                }
            }
        } while (opcao != 4);
    }

    private static int mostrarMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1 - Menu vendedores");
        System.out.println("2 - Menu clientes");
        System.out.println("3 - Menu lojas");
        System.out.println("4 - Calculadora");
        System.out.println("5 - Sair");
        return scan.nextInt();
    }
}