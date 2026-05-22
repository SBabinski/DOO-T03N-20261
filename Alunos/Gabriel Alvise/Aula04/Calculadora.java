import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Calculadora {

    static class Venda {
        int quantidade;
        double valorTotal;
        double valorDesconto;
        LocalDate data;

        public Venda(int quantidade, double valorTotal, double valorDesconto, LocalDate data) {
            this.quantidade = quantidade;
            this.valorTotal = valorTotal;
            this.valorDesconto = valorDesconto;
            this.data = data;
        }
    }

    public static void calcPrecoTotal(Scanner sc, ArrayList<Venda> vendas) {
        System.out.print("Insira a quantidade de plantas vendidas: ");
        int qtdVendidas = sc.nextInt();
        System.out.print("Insira o valor unitário da planta: ");
        float precoPlanta = sc.nextFloat();

        double valorTotal = 0;
        double valorDesconto = 0;

        if (qtdVendidas > 10) {
            double valorOriginal = precoPlanta * qtdVendidas;
            valorDesconto = valorOriginal * (5.0 / 100.0);
            System.out.println("O cliente recebeu um desconto de R$ " + valorDesconto);
            valorTotal = valorOriginal - valorDesconto;
        } else {
            valorTotal = precoPlanta * qtdVendidas;
        }

        LocalDate dataVenda = LocalDate.now();

        vendas.add(new Venda(qtdVendidas, valorTotal, valorDesconto, dataVenda));
        System.out.printf("O Valor total dessa venda é R$ %.2f\n", valorTotal);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Venda registrada na data: " + dataVenda.format(formatter) + "\n");
    }

    public static void calcTroco(Scanner sc) {

        System.out.print("Insira o valor recebido do cliente: ");
        float valorRecebido = sc.nextFloat();

        System.out.print("Insira o valor total da venda: ");
        float valorVenda = sc.nextFloat();

        float troco = valorRecebido - valorVenda;

        System.out.printf("O troco a dar ao cliente é R$ %.2f\n\n", troco);
    }

    public static void visualizarHistorico(ArrayList<Venda> vendas) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Data       | Qtd | Total   | Descontos");

        for (Venda venda : vendas) {
            System.out.println(
                    venda.data.format(formatter) + " | " +
                    venda.quantidade + " | " +
                    venda.valorTotal + " | " +
                    venda.valorDesconto
            );
        }
        System.out.println();
    }

    public static void buscarVendasDiaMes(Scanner sc, ArrayList<Venda> vendas) {
        sc.nextLine();
        System.out.print("Digite a data (dd/MM/yyyy): ");
        String dataInput = sc.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataBusca = LocalDate.parse(dataInput, formatter);

        int totalVendas = 0;

        for (Venda venda : vendas) {
            if (venda.data.equals(dataBusca)) {
                totalVendas += venda.quantidade;
            }
        }

        System.out.println("Total de plantas vendidas em " + dataInput + ": " + totalVendas + "\n");
    }

    public static int verificarNovaOperacao(Scanner sc) {
        System.out.println("Deseja continuar com uma nova conta?");
        System.out.println("[0] - Não");
        System.out.println("[1] - Sim");
        return sc.nextInt();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Venda> vendas = new ArrayList<>();

        System.out.println("Bem Vindo(a) à Calculadora Loja de Plantas");

        int opcao = 0;
        while (opcao != 5) {
            System.out.println("Selecione a opção desejada: ");
            System.out.println("\nDIGITE APENAS O NÚMERO\n");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Visualizar Histórico");
            System.out.println("[4] - Buscar Vendas por Dia e Mês");
            System.out.println("[5] - Sair");

            int opcaoEscolhida = sc.nextInt();
            int operacao = 1;

            switch (opcaoEscolhida) {
                case 1:
                    calcPrecoTotal(sc, vendas);
                    operacao = verificarNovaOperacao(sc);
                    break;
                case 2:
                    calcTroco(sc);
                    operacao = verificarNovaOperacao(sc);
                    break;
                case 3:
                    visualizarHistorico(vendas);
                    operacao = verificarNovaOperacao(sc);
                    break;
                case 4:
                    buscarVendasDiaMes(sc, vendas);
                    operacao = verificarNovaOperacao(sc);
                    break;
                case 5:
                    System.out.println("Encerrando Programa");
                    opcao = 5;
                    break;
                default:
                    System.out.println("Opção inválida! Digite apenas os números disponíveis no menu.");
            }

            if (operacao == 0) {
                System.out.println("Encerrando Programa");
                break;
            }

            opcao = opcaoEscolhida;
        }

        sc.close();
    }
}