import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        var appRodando = true;

        while (appRodando) {
            exibirMenu();
            System.out.print("Ação: ");

            int acaoUsuario = scanner.nextInt();
            scanner.nextLine();

            switch (acaoUsuario) {
                case 1:
                    System.out.print("Insira quantidade de plantas: ");
                    var qtdPlantas = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Insira o valor da planta: ");    
                    var valorPlanta = scanner.nextFloat();
                    scanner.nextLine();

                    var valorTotalCompra = Calculadora.calcularPreco(qtdPlantas, valorPlanta);

                    System.out.println("Valor total: R$" + valorTotalCompra);
                    break;
                case 2:
                    System.out.print("Valor pago pelo cliente: ");
                    var valorPagoCliente = scanner.nextFloat();
                    scanner.nextLine();

                    System.out.print("Valor total da compra: ");
                    var valorCompra = scanner.nextFloat();
                    scanner.nextLine();

                    var valorTroco = Calculadora.calcularTroco(valorPagoCliente, valorCompra);

                    System.out.println("Valor do troco: R$" + valorTroco);
                    break;
                case 3:
                    appRodando = false;
                    break;
                default:
                    System.out.println("Ação inválida.");
                    break;
            }
            
        }
        scanner.close();
    }

    public static void exibirMenu() {
        System.out.println("-----MENU-----");
        System.out.println("1 - Calcular o preço total");
        System.out.println("2 - Calcular troco");
        System.out.println("3 - Sair");
        System.out.println("--------------");
    }
}
