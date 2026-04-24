import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Calculadora calculadora = new Calculadora();

        int opcao;

        do {
            System.out.println("\n=== Loja de Plantas da Dona Gabrielinha ===");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (opcao) {

                case 1:
                    System.out.print("Digite o nome da planta: ");
                    String nome = scanner.nextLine();

                    System.out.print("Digite o preço unitário: ");
                    double preco = scanner.nextDouble();

                    System.out.print("Digite a quantidade: ");
                    int quantidade = scanner.nextInt();

                    Planta planta = new Planta(nome, preco);

                    double total = calculadora.calcularPrecoTotal(
                            quantidade,
                            planta.getPrecoUnitario()
                    );

                    System.out.println("Planta: " + planta.getNome());
                    System.out.println("Preço total: R$ " + total);
                    break;

                case 2:
                    System.out.print("Digite o valor pago: ");
                    double valorPago = scanner.nextDouble();

                    System.out.print("Digite o valor total da compra: ");
                    double valorTotal = scanner.nextDouble();

                    double troco = calculadora.calcularTroco(valorPago, valorTotal);

                    if (troco < 0) {
                        System.out.println("Valor insuficiente! Faltam R$ " + (-troco));
                    } else {
                        System.out.println("Troco: R$ " + troco);
                    }
                    break;

                case 3:
                    System.out.println("Encerrando o sistema ");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 3);

        scanner.close();
    }
}