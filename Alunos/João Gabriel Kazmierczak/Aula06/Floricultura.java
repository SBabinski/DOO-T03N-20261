package fag;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Floricultura {

	static ArrayList<Integer> quantidades = new ArrayList<>();
	static ArrayList<Double> valores = new ArrayList<>();
	static ArrayList<Double> descontos = new ArrayList<>();
	static ArrayList<LocalDate> datas = new ArrayList<>();

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		Loja loja = new Loja("My Plant", "My Plant LTDA", "123456789", "Assis", "Centro", "Rua A");

		Vendedor v1 = new Vendedor("João", 30, loja, "Assis", "Centro", "Rua B", 2000);
		Vendedor v2 = new Vendedor("Maria", 25, loja, "Assis", "Centro", "Rua C", 2500);

		Cliente c1 = new Cliente("Carlos", 40, "Assis", "Centro", "Rua D");
		Cliente c2 = new Cliente("Ana", 35, "Assis", "Centro", "Rua E");

		loja.vendedores.add(v1);
		loja.vendedores.add(v2);
		loja.clientes.add(c1);
		loja.clientes.add(c2);

		int escolha;

		do {
			System.out.println("\n========= MY PLANT =========");
			System.out.println("[1] Calculadora");
			System.out.println("[2] Clientes");
			System.out.println("[3] Vendedores");
			System.out.println("[4] Gestão da Loja");
			System.out.println("[5] Sair");

			escolha = scan.nextInt();

			switch (escolha) {
			case 1:
				menuCalculadora(scan);
				break;
			case 2:
				menuClientes(scan, loja);
				break;
			case 3:
				menuVendedores(scan, loja);
				break;
			case 4:
				menuGestaoLoja(scan, loja);
				break;
			}

		} while (escolha != 5);

		System.out.println("Sistema encerrado.");
	}

	public static void menuCalculadora(Scanner scan) {
		int op;
		do {
			System.out.println("\n--- CALCULADORA ---");
			System.out.println("[1] Calcular preço total");
			System.out.println("[2] Calcular troco");
			System.out.println("[3] Relatórios");
			System.out.println("[4] Voltar");

			op = scan.nextInt();

			switch (op) {
			case 1:
				calcTotal(scan);
				break;
			case 2:
				calcTroco(scan);
				break;
			case 3:
				menuRelatorios(scan);
				break;
			}
		} while (op != 4);
	}

	public static void menuRelatorios(Scanner scan) {
		int op;
		do {
			System.out.println("\n--- RELATÓRIOS ---");
			System.out.println("[1] Diário");
			System.out.println("[2] Mensal");
			System.out.println("[3] Geral");
			System.out.println("[4] Voltar");

			op = scan.nextInt();

			switch (op) {
			case 1:
				relatorioDiario(scan);
				break;
			case 2:
				relatorioMensal(scan);
				break;
			case 3:
				relatorioGeral();
				break;
			}
		} while (op != 4);
	}

	public static void menuClientes(Scanner scan, Loja loja) {
		int op;
		do {
			System.out.println("\n--- CLIENTES ---");
			System.out.println("[1] Apresentar clientes");
			System.out.println("[2] Voltar");

			op = scan.nextInt();

			switch (op) {
			case 1:
				for (Cliente c : loja.clientes) {
					c.apresentarSe();
				}
				break;
			}

		} while (op != 2);
	}

	public static void menuVendedores(Scanner scan, Loja loja) {
		int op;
		do {
			System.out.println("\n--- VENDEDORES ---");
			System.out.println("[1] Apresentar vendedores");
			System.out.println("[2] Calcular média salarial");
			System.out.println("[3] Calcular bônus");
			System.out.println("[4] Voltar");

			op = scan.nextInt();

			switch (op) {
			case 1:
				for (Vendedor v : loja.vendedores) {
					v.apresentarSe();
				}
				break;

			case 2:
				for (Vendedor v : loja.vendedores) {
					System.out.println(v.nome + " Média: " + v.calcularMedia());
				}
				break;

			case 3:
				for (Vendedor v : loja.vendedores) {
					System.out.println(v.nome + " Bônus: " + v.calcularBonus());
				}
				break;
			}

		} while (op != 4);
	}

	public static void menuGestaoLoja(Scanner scan, Loja loja) {
		int op;
		do {
			System.out.println("\n--- GESTÃO DA LOJA ---");
			System.out.println("[1] Apresentar loja");
			System.out.println("[2] Contar clientes");
			System.out.println("[3] Contar vendedores");
			System.out.println("[4] Voltar");

			op = scan.nextInt();

			switch (op) {
			case 1:
				loja.apresentarSe();
				break;
			case 2:
				System.out.println("Total de clientes: " + loja.contarClientes());
				break;
			case 3:
				System.out.println("Total de vendedores: " + loja.contarVendedores());
				break;
			}

		} while (op != 4);
	}

	public static void calcTotal(Scanner scan) {
		System.out.println("Quantidade:");
		int qtd = scan.nextInt();

		System.out.println("Valor:");
		double valor = scan.nextDouble();

		double total = qtd * valor;
		double desconto = 0;

		if (qtd > 10) {
			desconto = total * 0.05;
			total -= desconto;
		}

		System.out.println("Total: " + total);

		quantidades.add(qtd);
		valores.add(total);
		descontos.add(desconto);
		datas.add(LocalDate.now());
	}

	public static void calcTroco(Scanner scan) {
		System.out.println("Total:");
		double total = scan.nextDouble();

		System.out.println("Pago:");
		double pago = scan.nextDouble();

		System.out.println("Troco: " + (pago - total));
	}

	public static void relatorioDiario(Scanner scan) {
		DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		System.out.println("Data:");
		LocalDate data = LocalDate.parse(scan.next(), f);

		for (int i = 0; i < datas.size(); i++) {
			if (datas.get(i).equals(data)) {
				System.out.println("Data: " + datas.get(i));
				System.out.println("Quantidade: " + quantidades.get(i));
				System.out.println("Valor: " + valores.get(i));
				System.out.println("Desconto: " + descontos.get(i));
				System.out.println("------------------");
			}
		}
	}

	public static void relatorioMensal(Scanner scan) {
		System.out.println("Mês:");
		int mes = scan.nextInt();

		for (int i = 0; i < datas.size(); i++) {
			if (datas.get(i).getMonthValue() == mes) {
				System.out.println("Data: " + datas.get(i));
				System.out.println("Quantidade: " + quantidades.get(i));
				System.out.println("Valor: " + valores.get(i));
				System.out.println("Desconto: " + descontos.get(i));
				System.out.println("------------------");
			}
		}
	}

	public static void relatorioGeral() {
		for (int i = 0; i < datas.size(); i++) {
			System.out.println("Data: " + datas.get(i));
			System.out.println("Quantidade: " + quantidades.get(i));
			System.out.println("Valor: " + valores.get(i));
			System.out.println("Desconto: " + descontos.get(i));
			System.out.println("------------------");
		}
	}
}