import java.util.Scanner;

public class CalculadoraLoja {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao;


        do {
            System.out.println("1 - Calcular preco total");
            System.out.println("2 - Calcular troco");
            System.out.println("3 - Sair");
            System.out.print("escolha: ");
            opcao = sc.nextInt();




            if (opcao == 1) {
                System.out.print("Quantidade: ");
                int quantidade = sc.nextInt();
                System.out.print("Preco unitario: ");
                double preco = sc.nextDouble();
                double total = quantidade * preco;
                System.out.println("Total = " + total);
            }



            else if (opcao == 2) {
                System.out.print("Valor pago: ");
                double pago = sc.nextDouble();
                System.out.print("Valor da compra: ");
                double total = sc.nextDouble();

                if (pago < total) {
                    System.out.println("Valor insuficiente!");
                } else {
                    double troco = pago - total;
                    System.out.println("Troco = " + troco);
                }
            }



            else if (opcao != 3) {
                System.out.println("Opcao invalida!");
            }
        
         System.out.println();




        } while (opcao != 3);
        System.out.println("Programa encerrado.");
        sc.close();
    }
}