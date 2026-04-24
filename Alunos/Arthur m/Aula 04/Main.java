import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

public class Main {

    static ArrayList<String> vendas = new ArrayList<>();
    static ArrayList<LocalDate> datas = new ArrayList<>();

    public static double calcularPrecoTotal(int quantidade, double precoUnitario) {
        double total = quantidade * precoUnitario;

        if (quantidade > 10) {
            double desconto = total * 0.05;
            total -= desconto;
            System.out.println("Desconto de 5% aplicado: R$ " + desconto);
        }

        return total;
    }

    public static double calcularTroco(double valorRecebido, double valorTotal) {
        return valorRecebido - valorTotal;
    }

    public static void registrarVenda(int quantidade, double preco, double total) {
        LocalDate dataAtual = LocalDate.now();

        String registro = "Data: " + dataAtual +
                          " | Quantidade: " + quantidade +
                          " | Preço: R$ " + preco +
                          " | Total: R$ " + total;

        vendas.add(registro);
        datas.add(dataAtual);
    }

    public static void mostrarVendas() {
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
        } else {
            System.out.println("\n=== TODAS AS VENDAS ===");
            for (String v : vendas) {
                System.out.println(v);
            }
        }
    }

    public static void vendasDoDia() {
        LocalDate hoje = LocalDate.now();
        int contador = 0;

        for (LocalDate data : datas) {
            if (data.equals(hoje)) {
                contador++;
            }
        }

        System.out.println("Vendas hoje: " + contador);
    }

    public static void vendasDoMes() {
        LocalDate hoje = LocalDate.now();
        int contador = 0;

        for (LocalDate data : datas) {
            if (data.getMonth() == hoje.getMonth() &&
                data.getYear() == hoje.getYear()) {
                contador++;
            }
        }

        System.out.println("Vendas neste mês: " + contador);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== LOJA DONA GABRIELINHA ===");
            System.out.println("[1] Preço Total");
            System.out.println("[2] Troco");
            System.out.println("[3] Todas Vendas");
            System.out.println("[4] Vendas do Dia");
            System.out.println("[5] Vendas do Mês");
            System.out.println("[6] Sair");
            System.out.print("Escolha: ");

            opcao = scanner.nextInt();

            switch (opcao) {

                case 1:
                    System.out.print("Quantidade: ");
                    int quantidade = scanner.nextInt();

                    System.out.print("Preço: ");
                    double preco = scanner.nextDouble();

                    double total = calcularPrecoTotal(quantidade, preco);

                    System.out.println("Total: R$ " + total);

                    registrarVenda(quantidade, preco, total);
                    break;

                case 2:
                    System.out.print("Valor recebido: ");
                    double recebido = scanner.nextDouble();

                    System.out.print("Valor total: ");
                    double valorTotal = scanner.nextDouble();

                    double troco = calcularTroco(recebido, valorTotal);

                    System.out.println("Troco: R$ " + troco);
                    break;

                case 3:
                    mostrarVendas();
                    break;

                case 4:
                    vendasDoDia();
                    break;

                case 5:
                    vendasDoMes();
                    break;

                case 6:
                    System.out.println("Encerrando...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 6);

        scanner.close();
    }
}