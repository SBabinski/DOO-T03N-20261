import java.util.ArrayList;
import java.util.Scanner;

public class Calculadora {

    public static void calcPrecoTotal(Scanner sc, ArrayList<double[]> vendas) {

        System.out.print("Insira a quantidade de plantas vendidas: ");
        int qtdVendidas = sc.nextInt();

        System.out.print("Insira o valor unitário da planta: ");
        float precoPlanta = sc.nextFloat();

        double valorTotal = 0;
        double valorDesconto = 0;
        if (qtdVendidas > 10) {
            double valorOriginal = precoPlanta * qtdVendidas;
            valorDesconto = valorOriginal * (5.0/100.0);
            System.out.println("O cliente recebeu um desconto de R$ " + valorDesconto);
            valorTotal = valorOriginal - valorDesconto;
        }
        else {
            valorTotal = precoPlanta * qtdVendidas;
        }

        vendas.add(new double[]{qtdVendidas, valorTotal, valorDesconto});

        System.out.printf("O Valor total dessa venda é R$ %.2f\n\n", valorTotal);
    }

    public static void calcTroco(Scanner sc) {

        System.out.print("Insira o valor recebido do cliente: ");
        float valorRecebido = sc.nextFloat();

        System.out.print("Insira o valor total da venda: ");
        float valorVenda = sc.nextFloat();

        float troco = valorRecebido - valorVenda;

        System.out.printf("O troco a dar ao cliente é R$ %.2f\n\n", troco);

    }

    public static void visualizarHistorico(ArrayList<double[]> vendas) {
        System.out.println("Qtd | Total | Descontos");
        for (double[] venda:vendas) {
            System.out.print(venda[0] + " | ");
            System.out.print(venda[1] + " | ");
            System.out.print(venda[2] + " | \n");
        }
        System.out.println();
    }

    public static int veriicarNovaOperacao(Scanner sc) {
        System.out.println("Deseja continuar com uma nova conta?");
        System.out.println("[0] - Não");
        System.out.println("[1] - Sim");

        int opcaoEscolhida = sc.nextInt();

        return opcaoEscolhida;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<double[]> vendas = new ArrayList<>();

        System.out.println("Bem Vindo(a) à Calculadora Loja de Plantas");

        int opcao = 0;

        while (opcao != 4) {
            System.out.println("Selecione a opção desejada: ");
            System.out.println("\nDIGITE APENAS O NÚMERO\n");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Visualizar Histórico");
            System.out.println("[4] - Sair");

            int opcaoEscolhida = sc.nextInt();
            int operacao = 2;

            switch (opcaoEscolhida) {
                case 1:
                    calcPrecoTotal(sc, vendas);
                    operacao = veriicarNovaOperacao(sc);
                    break;
                case 2:
                    calcTroco(sc);
                    operacao = veriicarNovaOperacao(sc);
                    break;
                case 3:
                    visualizarHistorico(vendas);
                    operacao = veriicarNovaOperacao(sc);
                    break;
                case 4:
                    System.out.println("Encerrando Programa");
                    break;
                default:
                    System.out.println("Você escolheu uma opção errada, digite apenas o número '1, 2 ou 3' conforme sua necessidade!");
            }
            if (operacao == 0) {
                System.out.println("Encerrando Programa");
                break;
            }

            opcao = opcaoEscolhida;
        }
    }
}