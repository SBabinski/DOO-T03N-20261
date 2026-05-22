import java.util.Scanner;

public class CalculadoraPlantas {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        int opcao = 0;

        while (opcao != 3) {

            System.out.println("\n--- Menu Dona Gabrielinha ---");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = leitor.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("Qtd: ");
                    int qtd = leitor.nextInt();
                    System.out.print("Preço: ");
                    double preco = leitor.nextDouble();
                    System.out.println("Total: R$ " + (qtd * preco));
                    break;
                case 2:
                    System.out.print("Pago: ");
                    double pago = leitor.nextDouble();
                    System.out.print("Valor Compra: ");
                    double total = leitor.nextDouble();
                    System.out.println("Troco: R$ " + (pago - total));
                    break;
                case 3:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
        leitor.close();
    }
}
