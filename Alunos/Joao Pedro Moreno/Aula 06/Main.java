import java.util.Scanner;
import java.util.ArrayList;

public class Main {

	public static Scanner scan = new Scanner(System.in);
	static ArrayList<Planta> historico = new ArrayList<>();
	static double totalCompra;
	static Loja loja;
	public static void main(String[] args) {

		loja = new Loja("My plant", "My Plant LTDA", "123456789", "Toledo", "Centro", "Rua 5");

		VerMenu();
	}

	// Menu visivel ao cliente
	public static void VerMenu() {
		int escolha = 0;

		do {
			System.out.println("\n\n___________________________________");
			System.out.println("|--------------Menu---------------|");
			System.out.println("|1- Calcular Preço Total          |");
			System.out.println("|2- Calcular troco                |");
			System.out.println("|3- Histórico Geral de Vendas     |");
			System.out.println("|4- Histórico de Vendas Mensal    |");
			System.out.println("|5- Lojas                         |");
			System.out.println("|6- Clientes                      |");
			System.out.println("|7- Vendedores                    |");
			System.out.println("|8- Sair                          |");
			System.out.println("|Escolha uma opção:               |");
			System.out.printf("|_________________________________|\n");
			escolha = scan.nextInt();
			scan.nextLine();
			validarEscolha(escolha);
		} while (escolha != 8);
	}

	// leitura da escolha feita e direcionamento
	public static void validarEscolha(int escolha) {
		switch (escolha) {
			case 1: {
				CalcularPrecoTotal();
				break;
			}
			case 2: {
				CalcularTroco();
				break;
			}
			case 3: {
				HistoricoVendas();
				break;
			}
			case 4: {
				HistoricoVendasMensal();
				break;
			}
			case 5: {
				MenuLoja();
				break;
			}
			case 6: {
				Clientes();
				break;
			}
			case 7: {
				Vendedores();
				break;
			}
			case 8: {
				System.out.printf("Você saiu do sistema! Obrigado, volte sempre!");
				return;
			}
			default:
				System.out.printf("Opção incorreta, tente novamente!");
		}
	}

	// cálculo do Preço total
	public static void CalcularPrecoTotal() {

		Planta p = new Planta();

		p.leituraNome();
		p.leituraQuant();
		p.leituraValor();

		totalCompra = p.calcularPrecoTotal();

		historico.add(p);// guarda a venda

		System.out.println("O valor total das plantas: " + totalCompra);
	}

	// cálculo do Troco
	public static void CalcularTroco() {

		System.out.println("\nInforme o valor pago pelo cliente:");
		double valorPago = scan.nextDouble();

		if (valorPago < totalCompra) {
			System.out.println("Valor insuficiente! O cliente deve pagar pelo menos: " + totalCompra + " reais");
		} else {

			double troco = valorPago - totalCompra;

			System.out.println("Total da compra: " + totalCompra);
			System.out.println("Troco do cliente: " + troco);
		}
	}

	// ArrayList das Vendas
	public static void HistoricoVendas() {

		if (historico.isEmpty()) {
			System.out.println("Nenhuma venda registrada.");
		} else {

			System.out.println("\n___________________________________________________________________");
			System.out.println("|--------------------HISTÓRICO GERAL DE VENDAS--------------------|");

			for (Planta p : historico) {
				System.out.println(p);
			}
		}
	}

	//
	public static void HistoricoVendasMensal() {

		System.out.println("Digite o número do mês (1 a 12): ");
		int mes = scan.nextInt();

		boolean achou = false;

		for (Planta p : historico) {

			System.out.println("\n___________________________________________________________________");
			System.out.println("|--------------------HISTÓRICO DE VENDAS MENSAL--------------------|");

			if (p.getDataVenda().getMonthValue() == mes) {
				System.out.println(p);
				achou = true;
			}
		}
		if (!achou) {
			System.out.println("Nenhuma venda registrada nesse mês.");
		}
	}

	public static void MenuLoja() {
		System.out.println("\nBem-vindo à loja! Aqui estão as informações da loja:");

		loja.mostrarLoja();

		System.out.println("\nNúmero total de clientes: " + loja.contarClientes());
		System.out.println("Número total de vendedores: " + loja.contarVendedores());
	}

	public static void Clientes() {

		System.out.println("\nDados dos clientes:");

		ArrayList<Cliente> c = loja.getClientes();

		for (Cliente cli : c) {
			cli.mostrarCliente();
		}
	}

	public static void Vendedores() {

		System.out.println("\nDados dos vendedores:");

		ArrayList<Vendedor> v = loja.getVendedores();

		for (Vendedor vend : v) {
			vend.mostrarVendedor();
			System.out.println("Média do salário: " + vend.calcularMedia());
			System.out.println("Bônus: " + vend.calcularBonus());
		}
	}
}