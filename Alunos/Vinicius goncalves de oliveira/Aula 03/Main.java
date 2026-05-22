import java.util.ArrayList;
import java.util.Scanner;

public class Main {
public static ArrayList<Plantas> plantas= new ArrayList<>();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        do {
            System.out.println("\n===== CALCULADORA DA DONA GABRIELINHA =====");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Listar vendas");
            System.out.println("[4] - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();

            if (opcao == 1) {
                CalcularPreco();
            }
            if (opcao == 2) {
                CalcularTroco();
            }
            if (opcao == 3) {
                for(Plantas p:plantas){
                    System.out.println(p);
                }
            }
        }
        while (opcao != 4);
        System.out.println("projeto acabou");

    }


    public static void CalcularPreco() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite a quantidade de plantas: ");
        int quantidade = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Digite o preço unitário da planta: ");
        double preco = scanner.nextDouble();
        double desconto = 0;
        double total = 0;
        if (quantidade >= 10) {
            System.out.println("parabens voce ganhou um desconto de 5%");
           desconto = preco;
            desconto = desconto-(desconto * 5)/100;
             total = quantidade * desconto;
        }
else {
             total = quantidade * preco;
        }
        Plantas plantas1 = new Plantas(quantidade, preco, total, desconto);
        plantas.add(plantas1);

        System.out.println("Preço total da venda: R$ " + total);
    }


    //troco
    public static void CalcularTroco() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o valor pago: ");
        double valorPago = scanner.nextDouble();

        System.out.print("Digite o valor da compra: ");
        double valorCompra = scanner.nextDouble();

        double troco = valorPago - valorCompra;

        System.out.println("Troco: R$ " + troco);
    }
}