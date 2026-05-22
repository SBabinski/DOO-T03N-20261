import java.util.Scanner;

public class Principal{
    
    public static Scanner scan = new Scanner(System.in);
    public static double resultado = 0;

    public static void main(String[] args){
        mostrarMenu();
    }

    private static void mostrarMenu(){
        int escolha = 0;
    do{
        System.out.println("=========Menu Geral=========");
        System.out.println("[1] Calcular preço total");
        System.out.println("[2] Calcular troco");
        System.out.println("[3] Sair");
        escolha = scan.nextInt();
        validarEscolha(escolha);
    }while(escolha != 3 );
    }

    private static void validarEscolha(int escolha) {
		switch (escolha) {
		case 1:
			calcularPreco();
			break;
		case 2:
			calcularTroco();
			break;
		case 3:
			System.out.println("Obrigado por utilizar o sistema...");
			break;

		default:
			System.out.println("Escolha inválida");
			break;
		}
		
	}

    public static void calcularPreco(){

        System.out.println("Por favor entre com a quatidade do produto: ");
        int qtd = scan.nextInt();
        System.out.println("Por favor entre com o valor do produto: ");
        double valor = scan.nextDouble();
        

        resultado = (valor * qtd);

        System.out.println("O valor total é:  " + resultado);
    }

    public static void calcularTroco(){
        System.out.println("Por favor entre com o valor pago pelo cliente: ");
        double valorPago = scan.nextDouble();
        System.out.println("Digite o valor total da compra: ");
        double valorCompra = scan.nextDouble();

        double troco = valorPago - valorCompra;

        if(troco < 0){
            System.out.println("Valor pago é insuficiente...");
        }else{
            System.out.println("O valor do troco é: " + troco);
        }
    }

}