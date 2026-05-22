import java.util.Scanner;

public class Calculadora {
    static double total = 0;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        mostrarmenu(scan);
    }

    public static void mostrarmenu(Scanner scan) {
        int opcao = 0;

        System.out.println("------menu------");
        do {
            System.out.println("calcular preço total - 1");
            System.out.println("calcular troco - 2");
            System.out.println("sair - 3");
            System.out.println("escolha uma opção:");
            opcao = scan.nextInt();

            switch (opcao) {
                case 1:
                    calcularprecototal(scan);
                    break;

                case 2:
                    calcularTroco(scan);
                    break;

                default:
                    break;
            }
        } while (opcao != 3);
    }

    public static void calcularprecototal(Scanner scan) {
        int quantidade = 0;
        double preco = 0;
        // double total = quantidade * preco;

        System.out.println("digite a quantidade de produtos:");
        quantidade = scan.nextInt();
        scan.nextLine();

        System.out.println("digite o preço do produto:");
        preco = scan.nextDouble();

        total = quantidade * preco;
        System.out.println("o preço total é: " + total);
        System.out.println("\n");
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

}
