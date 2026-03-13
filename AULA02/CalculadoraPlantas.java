import java.util.Scanner;

public class CalculadoraPlantas {

    public static void main (String args[]) {

        Scanner sc = new Scanner(System.in);
        int opcao = 0;

        while(opcao != 3) {
            System.out.println("--- Calculadora da dona Gabrielinha ---");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Sair ");
            System.out.print("Escolha uma opção: ");

            opcao = sc.nextInt();

            if (opcao == 1) {
                int qtd = 0;
                double precoPlanta = 0;
                double precoTotal = 0.0;

                System.out.println();
                System.out.print("Digite quantas plantas deseja: ");
                qtd = sc.nextInt();
                System.out.print("Digite o valor da planta: ");
                precoPlanta = sc.nextDouble();

                System.out.println();
                precoTotal = precoPlanta * qtd;
                System.out.println("Preço total da compra: " + precoTotal);
                System.out.println();
                
            } else if (opcao == 2) {
                double vlrCliente = 0.0;
                double vlrCompra = 0.0;

                System.out.print("Digite o valor recebido pelo cliente: ");
                vlrCliente = sc.nextDouble();
                System.out.print("Digite o valor total da compra: ");
                vlrCompra = sc.nextDouble();

                System.out.println();
                double vlrTroco = 0.0;
                vlrTroco = vlrCliente - vlrCompra;
                if (vlrTroco > 0){
                    System.out.println("Seu troco será de: " + vlrTroco);
                    System.out.println();
                } else {
                    System.out.println();
                    System.out.println("Valor do cliente é menor do que o produto, cliente deve: " + vlrTroco);
                }
                
            } else if (opcao == 3) {
                System.out.println("Saindo...");
            } else {
                System.out.println("Opção incorreta, tente novamente...");
                System.out.println();
            }
        }

        sc.close();
       

    }

}
