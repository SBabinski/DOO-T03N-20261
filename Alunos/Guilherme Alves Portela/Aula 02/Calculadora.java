import java.util.Scanner;

public class Calculadora {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        menuInicialController(scanner);
        scanner.close();
    }
    

    public static void menuInicialController (Scanner scanner){ 
        int opcao = 0;
        
        while (opcao != 3) {
            System.out.println("\n*** Calculadora Loja de Plantas ***");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Sair");
            System.out.print("Escolha uma opção: ");
        
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("Digite a quantidade da planta: ");
                    int quantidade = scanner.nextInt();
                    System.out.print("Digite o preço unitário: ");
                    double precoUnitario = scanner.nextDouble();
                    
                    double precoTotal = quantidade * precoUnitario;
                    System.out.printf("O valor total da venda é: R$ %.2f%n", precoTotal);
                    break;

                case 2:
                    System.out.print("Digite o valor recebido pelo cliente: ");
                    double valorRecebido = scanner.nextDouble();
                    System.out.print("Digite o valor total da compra: ");
                    double valorCompra = scanner.nextDouble();

                    if (valorRecebido < valorCompra) {
                        System.out.println("Erro: Valor recebido é insuficiente.");
                    } else {
                        double troco = valorRecebido - valorCompra;
                        System.out.printf("O troco a ser dado é: R$ %.2f%n", troco);
                    }
                    break;

                case 3:
                    System.out.println("Encerrando o sistema.");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }
}