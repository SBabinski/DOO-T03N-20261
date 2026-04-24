import java.util.Scanner;

public class Calculadora {

    public static void CalcPrecoTotal(Scanner sc) {

        System.out.print("Insira a quantidade de plantas vendidas: ");
        int qtdVendidas = sc.nextInt();

        System.out.print("Insira o valor unitário da planta: ");
        float precoPlanta = sc.nextFloat();

        float valorTotal = precoPlanta * qtdVendidas;
        System.out.printf("O Valor total dessa venda é R$ %.2f\n\n", valorTotal);
    }

    public static void CalcTroco(Scanner sc) {

        System.out.print("Insira o valor recebido do cliente: ");
        float valorRecebido = sc.nextFloat();

        System.out.print("Insira o valor total da venda: ");
        float valorVenda = sc.nextFloat();

        float troco = valorRecebido - valorVenda;

        System.out.printf("O troco a dar ao cliente é R$ %.2f\n\n", troco);

    }

    public static int VeriicarNovaOperacao(Scanner sc) {
        System.out.println("Deseja continuar com uma nova conta?");
        System.out.println("[0] - Não");
        System.out.println("[1] - Sim");

        int opcaoEscolhida = sc.nextInt();

        return opcaoEscolhida;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Bem Vindo(a) à Calculadora Loja de Plantas");

        int opcao = 0;

        while (opcao != 3) {
            System.out.println("Selecione a opção desejada: ");
            System.out.println("\nDIGITE APENAS O NÚMERO\n");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Sair");

            int opcaoEscolhida = sc.nextInt();
            int operacao = 0;

            switch (opcaoEscolhida) {
                case 1:
                    CalcPrecoTotal(sc);
                    operacao = VeriicarNovaOperacao(sc);
                    break;
                case 2:
                    CalcTroco(sc);
                    operacao = VeriicarNovaOperacao(sc);
                    break;
                case 3:
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