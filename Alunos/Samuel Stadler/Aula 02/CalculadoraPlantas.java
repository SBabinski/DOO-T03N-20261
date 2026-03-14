import java.util.Scanner;

public class CalculadoraPlantas {
    
    public static void main(String[] args) {
        
        Scanner entrada = new Scanner(System.in);
        
        int opcao = 0;
        
        while (opcao != 3) {
            System.out.println("1 - Calcular Preço Total");
            System.out.println("2 - Calcular Troco");
            System.out.println("3 - Sair");
            System.out.print("Digite sua opção: ");
            
            opcao = entrada.nextInt();
            
            entrada.nextLine(); 
            
            if (opcao == 1) {
                System.out.println("\n--- Calcular Preço Total ---");
                
                System.out.print("Quantas plantas o cliente levou? ");
                int quantidade = entrada.nextInt();
                
                System.out.print("Qual o preço de uma planta? R$ ");
                double preco = entrada.nextDouble();
                
                double total = quantidade * preco;
                
                System.out.println("--------------------------------");
                System.out.println("Total a pagar: R$ " + total);
                System.out.println("--------------------------------\n");
            }
            
            else if (opcao == 2) {
                System.out.println("\n--- Calcular Troco ---");
                
                System.out.print("Valor total da compra: R$ ");
                double valorCompra = entrada.nextDouble();
                
                System.out.print("Valor que o cliente deu: R$ ");
                double valorPago = entrada.nextDouble();
                
                double troco = valorPago - valorCompra;
                
                if (troco < 0) {
                    System.out.println("Falta dinheiro! Ainda faltam R$ " + (troco * -1));
                }
                else if (troco == 0) {
                    System.out.println("Deu certo, sem troco!");
                }
                else {
                    System.out.println("Total de troco: R$ " + troco);
                }
                
                System.out.println("--------------------------------\n");
            }
            
            else if (opcao == 3) {
                System.out.println("\nObrigado por usar a calculadora");
                System.out.println("Obrigado e volte sempre\n");
            }
            
            else {
                System.out.println("\nOpção inválida! Digite 1, 2 ou 3 por favor.\n");
            }
            
        }

        entrada.close();
    }
}