import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        mostraMenu();
        }

    private static void mostraMenu() {

        System.out.printf("\n\n[1] - Calcular Preço Total\n" +
                "[2] - Calcular Troco    \n" +
                "[3] - Sair \n" +
                "Entre com opção :");

        int op = scanner.nextInt();

        switch (op){
            case 1:
                calculaPreco();
                mostraMenu();
                break;
            case 2:
                calculaTroco();
                mostraMenu();
                break;
            case 3:
                System.out.println("\nENCERRANDO...");
                break;
            default:
                mostraMenu();
                break;
        }
    }

    private static void calculaPreco() {
        System.out.println("\nQuntidade: ");
        int quant = scanner.nextInt();
        System.out.println("\nValor unitário: ");
        double valUni = scanner.nextDouble();

        double preco = quant * valUni;

        System.out.printf("\nPreço = R$%.2f",preco);
    }

    private static void calculaTroco() {
        System.out.println("\nValor pago: ");
        double valPag = scanner.nextDouble();
        System.out.println("\nValor devido: ");
        double valDev = scanner.nextDouble();

        double troco = valPag - valDev;

        System.out.printf("\nTroco Devido = R$%.2f",troco);
    }
}