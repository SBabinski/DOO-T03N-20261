import java.util.Scanner;

public class CalculadoraPlantas {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int opcao = 0;

        while (opcao != 3) {
            System.out.println("\n--- Menu Loja da Dona Gabrielinha ---");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = input.nextInt();

            if (opcao == 1) {
                System.out.print("Digite a quantidade da planta: ");
                int quantidade = input.nextInt();
                System.out.print("Digite o valor unitário da planta: ");
                double precoUnitario = input.nextDouble();
                
                double precoTotal = quantidade * precoUnitario;
                System.out.printf("O preço total da venda é: R$ %.2f%n", precoTotal);

            } else if (opcao == 2) {
                System.out.print("Digite o valor recebido pelo cliente: ");
                double valorRecebido = input.nextDouble();
                System.out.print("Digite o valor total da compra: ");
                double valorTotal = input.nextDouble();
                
                double troco = valorRecebido - valorTotal;
                System.out.printf("O valor do troco é: R$ %.2f%n", troco);

            } else if (opcao == 3) {
                System.out.println("Encerrando o sistema...");
            } else {
                System.out.println("Opção inválida! Tente novamente.");
            }
        }
        input.close();
    }
}