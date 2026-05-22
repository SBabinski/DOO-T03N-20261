package fag;

import java.util.Scanner;

public class Aula02 {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		System.out.println("Bem-vindo a Floricultura!");

		int escolha;

		do {
			escolha = MostrarMenu(scan);

			switch (escolha) {

			case 1:

				CalcTotal(scan);

				break;
			case 2:

				CalcTroco(scan);
				break;

		
			}

			
		} while (escolha != 3);

	}

	public static void CalcTotal(Scanner scan) {
		int qtd_produto;
		double valor_prod;
		double valor_total = 0;
		System.out.println("Qual o valor do produto? ");
		valor_prod = scan.nextDouble();
		scan.nextLine();

		System.out.println("Quantos produtos deseja levar? ");
		qtd_produto = scan.nextInt();
		scan.nextLine();

		valor_total = valor_prod * qtd_produto;
		System.out.println("O valor total da compra ficou em R$ " + valor_total + "!");


	}

	public static void CalcTroco(Scanner scan) {
		double valor_pago;
		double valor_prod;
		System.out.println("Qual o valor do produto? ");
		valor_prod = scan.nextDouble();
		scan.nextLine();
		System.out.println("Quanto você você pagou? ");
		valor_pago = scan.nextDouble();
		scan.nextLine();
		double valor_troco = valor_pago - valor_prod;

		if (valor_troco == 0) {

			System.out.println("Você não possui troco para receber!");

		} else {
			System.out.println("O valor do troco é de: R$ " + valor_troco);
		}
	}

	public static int MostrarMenu(Scanner scan) {
		System.out.println("\nO que deseja fazer?");
		System.out.println("[1] - Calcular preço total");
		System.out.println("[2] - Calcular troco");
		System.out.println("[3] - Sair");

		return scan.nextInt();
		
	}

}
