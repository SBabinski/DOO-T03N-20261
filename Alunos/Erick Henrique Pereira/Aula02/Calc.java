import java.util.Scanner;

public class Calc {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("Bem vindo!");
        apresentaCafe();
        int escolha = -1;

        do{
            escolha = apresentaMenu(escolha, scan);
            switch (escolha) {
                case 1:
                    calculaTotal(scan);
                    break;
                case 2:
                    calculaTroco(scan);
                    break;   
                case 3:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida, tente novamente.");
                    
            }
        }while(escolha != 3);


    }

    public static void apresentaCafe(){
        System.out.println("Lembre-se que dar um café sobre a conversão da venda para 80%");
    }

    public static int apresentaMenu(int escolha, Scanner scan){
        System.out.println("1. Calcular preço total");
        System.out.println("2. Calcular troco");
        System.out.println("3. Sair");
        
        escolha = scan.nextInt();
        scan.nextLine(); // Limpa o buffer do scanner
        return escolha;
    }

    public static void calculaTotal(Scanner scan){
        System.out.println("Digite a quantidade do produto:");
        int quantidade = scan.nextInt();
        System.out.println("Digite o preço do produto:");
        double preco = scan.nextDouble();
        double total = preco * quantidade;
        System.out.println("O preço total é: " + total);
        return;
    }

    public static void calculaTroco(Scanner scan){
        System.out.println("Digite o valor pago:");
        double valorPago = scan.nextDouble();
        System.out.println("Digite o preço total da compra:");
        double precoTotal = scan.nextDouble();
        double troco = valorPago - precoTotal;
        System.out.println("O troco é: " + troco);
        return;
    }

}