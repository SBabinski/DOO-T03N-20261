import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Lojadeflores {
    static double total = 0;
    static int qdvenda = 0;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        List<Vendas> venda = new ArrayList<>();

        mostrarmenu(scan, venda);
    }

    public static void mostrarmenu(Scanner scan, List<Vendas> venda) {
        int opcao = 0;

        System.out.println("------menu------");
        do {
            System.out.println("calcular preço total - 1");
            System.out.println("calcular troco - 2");
            System.out.println("mostrar vendas - 3");
            System.out.println("mostrar vendas por data - 4");
            System.out.println("sair - 5");
            System.out.println("escolha uma opção:");
            opcao = scan.nextInt();

            switch (opcao) {
                case 1:
                    calcularprecototal(scan, venda);
                    break;
                case 2:
                    calcularTroco(scan);
                    break;
                case 3:
                    mostrarvendas(venda);
                    break;
                case 4:
                    PequisarVendasPorData(scan, venda);
                    break;
                case 5:
                    System.out.println("saindo do programa...");
                    break;
                default:
                    break;
            }
        } while (opcao != 4);
    }

    public static void calcularprecototal(Scanner scan, List<Vendas> venda) {
        scan.nextLine();
        System.out.println("qual a data da venda? (DD/MM/AAAA)");
        String datainserida = scan.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(datainserida, formatter);

        int quantidade = 0;
        double preco = 0;
        double desconto = 0;
        // int qdvendida = 0;

        System.out.println("digite a quantidade de produtos:");
        quantidade = scan.nextInt();
        scan.nextLine();

        System.out.println("digite o preço do produto:");
        preco = scan.nextDouble();

        if (quantidade >= 10) {
            total = quantidade * preco;
            desconto = total * 0.05;
            total = total - desconto;
            ;
            System.out.println("desconto de 5% aplicado.");
            System.out.println("o preço total é: " + total);
            System.out.println("o desconto é: " + desconto);
            System.out.println("\n");
        }

        else {
            total = quantidade * preco;
            desconto = 0;

            System.out.println("o preço total é: " + total);
            System.out.println("\n");
        }

        // qdvendida++;

        venda.add(new Vendas(quantidade, preco, desconto, data));

    }

    public static void calcularTroco(Scanner scan) {
        double valorPago = 0;
        double troco = 0;

        System.out.println("digite o valor pago:");
        valorPago = scan.nextDouble();

        troco = valorPago - total;

        if (troco < 0) {
            System.out.println("valor pago é insuficiente.");
        } else {
            System.out.println("o troco é: " + troco);
        }
    }

    public static void mostrarvendas(List<Vendas> vendas) {
        System.out.println("vendas realizadas:");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Vendas venda : vendas) {
            System.out.println("quantidade: " + venda.getQuantidade() + ", preço: " + venda.getPreco() + ", desconto: "
                    + venda.getDesconto() + ", data: " + venda.getdata().format(formatter));
        }
    }

    private static void PequisarVendasPorData(Scanner scan2, List<Vendas> venda) {

        scan2.nextLine();
        System.out.println("digite a data que deseja pesquisar (DD/MM/AAAA):");
        String datainserida = scan2.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataPesquisa = LocalDate.parse(datainserida, formatter);

        int qtdVendas = 0;

        for (Vendas vendaa : venda) {
            if (vendaa.getdata().equals(dataPesquisa)) {
                qtdVendas++;

                System.out.println("Data: " + vendaa.getdata().format(formatter) +
                        " | Quantidade de produtos: " + vendaa.getQuantidade());
            }
        }

        System.out.println("Total de vendas nesse dia: " + qtdVendas);
    }
}
