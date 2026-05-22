package loja;

import java.util.Scanner;

public class CalculadoraLoja {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        while(opcao != 3){

            System.out.println("===== Calculadora da Loja da Dona Gabrielinha =====");
            System.out.println("[1] Calcular Preço Total");
            System.out.println("[2] Calcular Troco");
            System.out.println("[3] Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();

            if(opcao == 1){

                System.out.print("Digite a quantidade de plantas: ");
                int quantidade = scanner.nextInt();

                System.out.print("Digite o preço da planta: ");
                double preco = scanner.nextDouble();

                double total = quantidade * preco;

                System.out.println("Preço total da compra: R$ " + total);
            }

            else if(opcao == 2){

                System.out.print("Digite o valor pago pelo cliente: ");
                double pago = scanner.nextDouble();

                System.out.print("Digite o valor total da compra: ");
                double total = scanner.nextDouble();

                double troco = pago - total;

                System.out.println("Troco: R$ " + troco);
            }

            else if(opcao == 3){
                System.out.println("Encerrando sistema...");
            }

            else{
                System.out.println("Opção inválida!");
            }

            System.out.println();
        }

        scanner.close();
    }
}