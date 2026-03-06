import java.util.Scanner;

public class Calculadora {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        menuPrincipal();
        scanner.close();
    }
    public static void menuPrincipal() {
        int op = 0 ;
        do {
            System.out.print("\n==========================================");
            System.out.print("\n Loja de Plantas da Dona Gabrielinha");
            System.out.print("\n           MENU PRINCIPAL  ");
            System.out.print("\n==========================================");
            System.out.print("\nEscolha uma opcao: ");
            System.out.print("\n[1] - Calcular Preço Total");
            System.out.print("\n[2] - Calcular Troco");
            System.out.print("\n[3] - Sair");
            System.out.print("\n");
            op = scanner.nextInt();
            scanner.nextLine();
            switch (op) {
                case 1:
                    calculaTotal();
                    break;
                case 2:
                    calculaTroco();
                    break;
                case 3:
                    System.out.print("\nSistema finalizado.");
                    System.exit(0);
                    break;
                default:
                    System.out.print("\nDigite uma opcao valida: ");
            }
        }
        while (true);
    }
    public static void calculaTotal() {
        System.out.print("\nDigite a quantidade do produto: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();
        if (quantidade <= 0) {
            System.out.print("Digite uma quantidade valida: ");
            calculaTotal();
        }
        System.out.print("Digite o preco do produto: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();
        if (preco < 1) {
            System.out.print("Digite um preco valido: ");
            calculaTotal();
        }
        double resultado = preco * quantidade;
        System.out.printf("\nO total da sua compra e R$%.2f", resultado);
        menuPrincipal();
    }
    public static void calculaTroco() {
        System.out.print("\nDigite o valor entregue ao caixa: ");
        double dinheiro = scanner.nextDouble();
        scanner.nextLine();
        if (dinheiro <= 0) {
            System.out.print("Digite um valor valido: ");
            calculaTroco();
        }
        System.out.print("\nDigite o valor da compra: ");
        double compra = scanner.nextDouble();
        scanner.nextLine();
        if (compra < 1) {
            System.out.print("Digite um valor valido: ");
            calculaTroco();
        }
        double troco = dinheiro - compra;
        if (troco < 0) {
            troco = troco * -1;
            System.out.printf("\nFaltou R$%.2f", troco);
        } else {
            System.out.printf("A quantidade a ser devolvida e R$%.2f", troco);
        }
        menuPrincipal();
    }
}







