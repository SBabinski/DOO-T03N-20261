package fag;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
				menuFuncionalidades(scan);
				break;

			case 4:
				System.out.println("Encerrando o sistema...");
				break;
			}

		} while (escolha != 4);
	}

	public static int mostrarMenu(Scanner scan) {
		System.out.println("\nO que deseja fazer?");
		System.out.println("[1] - Calcular preço total");
		System.out.println("[2] - Calcular troco");
		System.out.println("[3] - Funcionalidades");
		System.out.println("[4] - Sair");

		return scan.nextInt();
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

		LocalDate data = LocalDate.now();

		Venda v = new Venda(qtd_produto, valor_total, desconto, data);
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

	public static void menuFuncionalidades(Scanner scan) {

		int opcao;

		do {
			System.out.println("\n===== FUNCIONALIDADES =====");
			System.out.println("[1] - Relatório diário");
			System.out.println("[2] - Relatório mensal");
			System.out.println("[3] - Relatório geral");
			System.out.println("[4] - Voltar");

			opcao = scan.nextInt();

			switch (opcao) {

			case 1:
				buscarPorData(scan);
				break;

			case 2:
				relatorioMensal(scan);
				break;

			case 3:
				relatorioGeral();
				break;

			case 4:
				System.out.println("Voltando...");
				break;

			default:
				System.out.println("Opção inválida.");
			}

		} while (opcao != 4);
	}

	public static void buscarPorData(Scanner scan) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		System.out.println("Digite a data (dd/MM/yyyy): ");
		String dataStr = scan.next();

		LocalDate dataBusca = LocalDate.parse(dataStr, formatter);

		int totalPlantas = 0;
		int totalVendas = 0;

		for (Venda v : historico) {
			if (v.data.equals(dataBusca)) {
				totalPlantas += v.quantidade;
				totalVendas++;
			}
		}

		System.out.println("\n===== RELATÓRIO DIÁRIO =====");

		if (totalVendas == 0) {
			System.out.println("Nenhuma venda encontrada nessa data.");
		} else {
			System.out.println("Data: " + dataBusca);
			System.out.println("Total de vendas: " + totalVendas);
			System.out.println("Total de plantas vendidas: " + totalPlantas);
		}
	}

	public static void relatorioMensal(Scanner scan) {

		System.out.println("Digite o mês (1 a 12): ");
		int mes = scan.nextInt();

		System.out.println("Digite o ano: ");
		int ano = scan.nextInt();

		int totalVendas = 0;
		int totalPlantas = 0;
		double faturamento = 0;
		double totalDescontos = 0;

		for (Venda v : historico) {
			if (v.data.getMonthValue() == mes && v.data.getYear() == ano) {
				totalVendas++;
				totalPlantas += v.quantidade;
				faturamento += v.valorTotal;
				totalDescontos += v.desconto;
			}
		}

		System.out.println("\n===== RELATÓRIO MENSAL =====");

		if (totalVendas == 0) {
			System.out.println("Nenhuma venda encontrada nesse mês.");
		} else {
			System.out.println("Mês/Ano: " + mes + "/" + ano);
			System.out.println("Total de vendas: " + totalVendas);
			System.out.println("Total de plantas vendidas: " + totalPlantas);
			System.out.println("Faturamento total: R$ " + faturamento);
			System.out.println("Total de descontos: R$ " + totalDescontos);
		}
	}

	public static void relatorioGeral() {

		int totalVendas = 0;
		int totalPlantas = 0;
		double faturamento = 0;
		double totalDescontos = 0;

		for (Venda v : historico) {
			totalVendas++;
			totalPlantas += v.quantidade;
			faturamento += v.valorTotal;
			totalDescontos += v.desconto;
		}

		System.out.println("\n===== RELATÓRIO GERAL =====");

		if (totalVendas == 0) {
			System.out.println("Nenhuma venda registrada.");
		} else {
			System.out.println("Total de vendas: " + totalVendas);
			System.out.println("Total de plantas vendidas: " + totalPlantas);
			System.out.println("Faturamento total: R$ " + faturamento);
			System.out.println("Total de descontos: R$ " + totalDescontos);
		}
	}
}

class Venda {
	int quantidade;
	double valorTotal;
	double desconto;
	LocalDate data;

	public Venda(int quantidade, double valorTotal, double desconto, LocalDate data) {
		this.quantidade = quantidade;
		this.valorTotal = valorTotal;
		this.desconto = desconto;
		this.data = data;
	}
}