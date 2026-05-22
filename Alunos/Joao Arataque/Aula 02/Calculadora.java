import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Calculadora {
    public static void CalcularPrecoTotal (Scanner sc){

        System.out.println("Digite a quantidade da planta.");
        int qtdPlanta = sc.nextInt();

        System.out.println("Digite o valor da planta referida.");
        float precoPlanta = sc.nextFloat();

        float precoTotal = (qtdPlanta * precoPlanta);
        System.out.printf("O preço total da compra é %.2f\n\n", precoTotal);
    }

    public static void CalcularTroco(Scanner sc){
        System.out.println("Digite o valor total da compra.");
        float valorCompra = sc.nextFloat();

        System.out.println("Digite o valor pago.");
        float valorPago = sc.nextFloat();

        float troco = (valorPago - valorCompra);
        System.out.printf("O valor do troco é %.2f\n\n", troco);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Bem vindo a calculadora da loja de plantas da dona Gabrielinha");

        int opcao = 0;
        while (opcao != 3) {
            System.out.println("Escolha uma das opções");
            System.out.println("1 - Calcular preço da compra");
            System.out.println("2 - Calcular troco da compra");
            System.out.println("3 - Sair");

            int opcaoSelecionada = sc.nextInt();

            opcao = opcaoSelecionada;

            switch (opcaoSelecionada){
                case 1:
                    CalcularPrecoTotal(sc);
                    break;
                case 2:
                    CalcularTroco(sc);
                    break;
            }
        }
        }
}