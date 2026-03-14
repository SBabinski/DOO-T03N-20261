package fag;

import java.util.Scanner;

public class Calculadora {
	public static Scanner scan = new Scanner(System.in);
	static double precoTotal=0;
	
	public static void main(String[] args) {
		
		mostrarMenu();
		
	}

	private static void mostrarMenu() {
		int escolha = 0;
		
		System.out.println("------BEM VINDO------");
		do {
			System.out.println("\n----- MENU -----\n");
			System.out.println("1 - Calcular preco total");
			System.out.println("2 - Calcular troco");
			System.out.println("3 - Sair");
			System.out.println("\nDigite sua escolha: ");
			escolha = scan.nextInt(); 
			scan.nextLine();
			
			validarEscolha(escolha);
			
		}while(escolha!= 3);
		
		
	}

	public static void validarEscolha(int escolha) {
		switch (escolha) {
		case 1:
			calcularPreco();
			break;
		case 2:
			calcularTroco(precoTotal);
			break;
		case 3:
			System.out.println("\nEncerrando...");
			System.out.println("\nOperacao concluida!");
			break;
		default:
			System.out.println("\nSelecione uma opcao valida!!!");
			break;
		
		}
		
	}

	public static void calcularPreco() {
		System.out.println("\nInsira a quantidade de plantas que deseja comprar: ");
		int quantidade = scan.nextInt();
		scan.nextLine();
		
		System.out.println("\nInsira o valor unitario da planta: ");
		double valor = scan.nextDouble();
		
		precoTotal = valor * quantidade;
		System.out.println("\nO valor total da compra e R$: " + precoTotal);
		
		//calcularTroco(precoTotal);
	}

	public static void calcularTroco(double precoTotal) {
		
		if(precoTotal == 0) {
			System.out.println("\nCalcule o total antes!");
			return;	
		}
		
		System.out.println("\nDigite o valor do pagamento: ");
		double pagamento = scan.nextDouble();
		
		if(pagamento < precoTotal) {
			System.out.println("\nValor de pagamento insuficiente!!!");
			return;
		}else if(pagamento == precoTotal){
			System.out.println("\nObrigado, pagamento de acordo!");
		}else {
			double troco = pagamento - precoTotal;
			System.out.println("\nSeu troco e R$: " + troco);
		}

	
	}
	
	
	
	
	
	
	
	
	
	
}
