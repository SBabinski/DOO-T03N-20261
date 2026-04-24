package fag;

import java.util.ArrayList;
import java.util.Scanner;

public class Floricultura {

	static ArrayList<Venda> historico = new ArrayList<>();

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		System.out.println("Bem-vindo a Floricultura!");

		int escolha;

		do {
			escolha = mostrarMenu(scan);

			switch (escolha) {

			case 1:
				calcTotal(scan);
				break;

			case 2:
				calcTroco(scan);
				break;

			case 3:
				verificaHistorico();
				break;

			case 4:
				System.out.println("Encerrando o sistema...");
				break;
			}

		} while (escolha != 4);
	}

	public static void calcTotal(Scanner scan) {
		int qtd_produto;
		double valor_prod;

		System.out.println("Quantos produtos deseja levar?");
		qtd_produto = scan.nextInt();

		System.out.println("Qual o valor do produto?");
		valor_prod = scan.nextDouble();

		double valor_total = valor_prod * qtd_produto;
		double desconto = 0;

		if (qtd_produto > 10) {
			desconto = valor_total * 0.05;
			valor_total -= desconto;
		}

		System.out.println("Valor sem desconto: R$ " + (valor_prod * qtd_produto));
		System.out.println("Desconto aplicado: R$ " + desconto);
		System.out.println("Valor final: R$ " + valor_total);

		
		Venda v = new Venda(qtd_produto, valor_total, desconto);
		historico.add(v);
	}

	public static void calcTroco(Scanner scan) {
		double valor_total;
		double valor_pago;

		System.out.println("Qual o valor total da compra?");
		valor_total = scan.nextDouble();

		System.out.println("Quanto você pagou?");
		valor_pago = scan.nextDouble();

		double troco = valor_pago - valor_total;

		if (troco < 0) {
			System.out.println("Valor insuficiente! Faltam R$ " + (-troco));
		} else if (troco == 0) {
			System.out.println("Sem troco.");
		} else {
			System.out.println("Troco: R$ " + troco);
		}
	}

	public static void verificaHistorico() {

		System.out.println("\n===== HISTÓRICO DE VENDAS =====");

		if (historico.isEmpty()) {
			System.out.println("Nenhuma venda registrada.");
			return;
		}

		for (int i = 0; i < historico.size(); i++) {
			Venda v = historico.get(i);

			System.out.println("Venda " + (i + 1));
			System.out.println("Quantidade: " + v.quantidade);
			System.out.println("Valor total: R$ " + v.valorTotal);
			System.out.println("Desconto: R$ " + v.desconto);
			System.out.println("-----------------------");
		}
	}

	public static int mostrarMenu(Scanner scan) {
		System.out.println("\nO que deseja fazer?");
		System.out.println("[1] - Calcular preço total");
		System.out.println("[2] - Calcular troco");
		System.out.println("[3] - Verificar histórico de compras");
		System.out.println("[4] - Sair");

		return scan.nextInt();
	}
}


class Venda {
	int quantidade;
	double valorTotal;
	double desconto;

	public Venda(int quantidade, double valorTotal, double desconto) {
		this.quantidade = quantidade;
		this.valorTotal = valorTotal;
		this.desconto = desconto;
	}
}