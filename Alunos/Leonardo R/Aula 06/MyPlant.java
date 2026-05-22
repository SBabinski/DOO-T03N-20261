import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MyPlant {

    static double total = 0;
    static int qdvenda = 0;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        List<Vendas> vendas = new ArrayList<>();
        Loja loja = new Loja();

        mostrarMenu(scan, vendas, loja);
    }

    public static void mostrarMenu(Scanner scan, List<Vendas> vendas, Loja loja) {

        int opcao;

        do {
            System.out.println("------ MENU ------");
            System.out.println("1 - Calcular preço total");
            System.out.println("2 - Calcular troco");
            System.out.println("3 - Mostrar vendas");
            System.out.println("4 - Mostrar vendas por data");
            System.out.println("5 - Porcentagem vendedores");
            System.out.println("6 - Apresentar vendedores");
            System.out.println("7 - Apresentar clientes");
            System.out.println("8 - Mostrar loja");
            System.out.println("0 - Sair");

            opcao = scan.nextInt();

            switch (opcao) {
                case 1:
                    calcularPrecoTotal(scan, vendas);
                    break;
                case 2:
                    calcularTroco(scan);
                    break;
                case 3:
                    mostrarVendas(vendas);
                    break;
                case 4:
                    pesquisarVendasPorData(scan, vendas);
                    break;
                case 5:
                    Loja.mostrarbonus(loja);
                    break;
                case 6:
                    loja.apresentarVendedores();
                    break;
                case 7:
                    loja.apresentarClientes();
                    break;
                case 8:
                    apresentarLoja(loja);
                    break;
            }

        } while (opcao != 0);
    }

    public static void calcularPrecoTotal(Scanner scan, List<Vendas> vendas) {
        scan.nextLine();

        System.out.println("Data (DD/MM/AAAA):");
        String dataStr = scan.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(dataStr, formatter);

        System.out.println("Quantidade:");
        int quantidade = scan.nextInt();

        System.out.println("Preço:");
        double preco = scan.nextDouble();

        double desconto = 0;

        total = quantidade * preco;

        if (quantidade >= 10) {
            desconto = total * 0.05;
            total -= desconto;
        }

        System.out.println("Total: " + total);
        System.out.println("Desconto: " + desconto);

        qdvenda++;
        vendas.add(new Vendas(quantidade, preco, desconto, data, qdvenda));
    }

    public static void calcularTroco(Scanner scan) {
        System.out.println("Valor pago:");
        double pago = scan.nextDouble();

        double troco = pago - total;

        if (troco < 0) {
            System.out.println("Valor insuficiente!");
        } else {
            System.out.println("Troco: " + troco);
        }
    }

    public static void mostrarVendas(List<Vendas> vendas) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Vendas v : vendas) {
            System.out.println("Qtd: " + v.getQuantidade() +
                    " | Preço: " + v.getPreco() +
                    " | Data: " + v.getdata().format(formatter));
        }
    }

    public static void pesquisarVendasPorData(Scanner scan, List<Vendas> vendas) {
        scan.nextLine();

        System.out.println("Data (DD/MM/AAAA):");
        String dataStr = scan.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataBusca = LocalDate.parse(dataStr, formatter);

        for (Vendas v : vendas) {
            if (v.getdata().equals(dataBusca)) {
                System.out.println("Venda encontrada: " + v.getQuantidade());
            }
        }
    }

    public static void apresentarLoja(Loja loja) {
        System.out.println("Loja: " + loja.getNome_fantasia());
        System.out.println("Cidade: " + loja.getCidade());
        System.out.println();
    }

    public static void calcularbonus(List<Vendedor> vendedores) {
        // Placeholder implementation: print the number of salespeople
        System.out.println("Número de vendedores: " + vendedores.size());
        // Add actual bonus calculation logic here if needed
    }
}