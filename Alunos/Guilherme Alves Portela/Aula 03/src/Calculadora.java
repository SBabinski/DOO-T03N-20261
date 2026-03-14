import java.util.ArrayList;
import java.util.Scanner;

public class Calculadora {
    public static ArrayList<Venda> vendas = new ArrayList<Venda>();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        menuInicialController(sc);
        sc.close();
    }
    

    public static void menuInicialController (Scanner sc){ 
        int opcao = 0;

        vendas.add(new Venda(2, 2, 2));
        while (opcao != 3) {
            System.out.println("\n*** Calculadora Loja de Plantas ***");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Sair");
            System.out.print("Escolha uma opção: ");
        
            opcao = sc.nextInt();

            switch (opcao) {
                case 1: 
                    realizarVenda(sc);
                    break;
                case 2:
                    exibirVendas();
                    break;

                case 3:
                    System.out.println("Encerrando o sistema.");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }
    
    public static void exibirVendas(){
        for (Venda venda : vendas) {
            System.out.printf("%f", venda.quantidade, "\n");
            System.out.printf("%f", venda.valor, "\n");
            System.out.printf("%f", venda.desconto, "\n");
            System.out.println();
        }
    }

    public static void realizarVenda(Scanner sc){
        calcularPreco(sc);
        calcularTroco(sc);
    }

    public static void calcularPreco(Scanner sc){
        System.out.print("Digite a quantidade da planta: ");
        int quantidade = sc.nextInt();
        System.out.print("Digite o preço unitário: ");
        double precoUnitario = sc.nextDouble();

        double precoTotal = quantidade * precoUnitario;

        //Desconto especial
        double desconto = 0;
        
        if(quantidade > 10){
            desconto = 0.05;

            precoTotal = precoTotal * (1 - desconto);
        }
        
        System.out.printf("O valor total da venda é: R$ %.2f%n", precoTotal);
    }

    public static void calcularTroco(Scanner sc){
        System.out.print("Digite o valor recebido pelo cliente: ");
        double valorRecebido = sc.nextDouble();
        System.out.print("Digite o valor total da compra: ");
        double valorCompra = sc.nextDouble();

        if (valorRecebido < valorCompra) {
            System.out.println("Erro: Valor recebido é insuficiente.");
        } else {
            double troco = valorRecebido - valorCompra;
            System.out.printf("O troco a ser dado é: R$ %.2f%n", troco);
        }
    }
}