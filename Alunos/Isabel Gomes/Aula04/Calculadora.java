package fag;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import fag.objeto.Vendas;


public class Calculadora {
	public static Scanner scan = new Scanner(System.in);
	//static double precoTotal=0;
	
	
	private static List<Vendas> venda = new ArrayList<>();
	
	
	public static void main(String[] args) {
		//Client c1 = new Cliente();
		//c1.nome = "fulando"
		//criar classe de armazem de objetos
		mostrarMenu();
		
	}

	private static void mostrarMenu() {
		int escolha = 0;
		
		System.out.println("------BEM VINDO------");
		do {
			System.out.println("\n----- MENU -----\n");
			System.out.println("1 - Calcular preco total");
			System.out.println("2 - Calcular troco");
			System.out.println("3 - Listar vendas");
			System.out.println("4 - Buscar total de vendas por dia");
			System.out.println("0 - Sair");
			System.out.println("\nDigite sua escolha: ");
			escolha = scan.nextInt(); 
			scan.nextLine();
			
			validarEscolha(escolha);
			
		}while(escolha!= 0);
		
		
	}

	public static void validarEscolha(int escolha) {
		switch (escolha) {
		case 1:
			calcularPreco();
			break;
		case 2:
			if(venda.isEmpty()) {
				System.out.println("\nCompre algo antes!\n");
			}else {
				calcularTroco(venda.get(venda.size() -1));
			}
			
			break;
		case 3:
			listarVendas();
			break;
		case 4:
			vendasPorDia();
			break;
		case 0:
			System.out.println("\nEncerrando...");
			System.out.println("\nOperacao concluida!");
			break;
		default:
			System.out.println("\nSelecione uma opcao valida!!!");
			break;
		
		}
		
	}

	public static void vendasPorDia() {
		
		System.out.println("\n Insira a data que deseja buscar (dd/mm/aaaa): ");
		String data = scan.nextLine();
		
		
		LocalDate dataBusca = LocalDate.parse(data, Vendas.FORMATO);
		
		int totVendas = 0;
		
		for(Vendas v: venda){
			
			if(v.getData().equals(dataBusca)) {
				totVendas += v.getQuantidade();
			}
		}
		
		if(totVendas == 0) {
			System.out.println("\nNenhuma venda no dia");
		}else {
			System.out.println("\nO total de vendas no dia " + data + ": " + totVendas + " plantas");
		}
		
	}

	public static void listarVendas() {
		if(venda.isEmpty()) {
			System.out.println("\nA lista esta vazia!!!\n");
			return;
		}else {
			System.out.println("\n------ LISTA DE VENDAS ------\n");
			for(Vendas v: venda) {
				v.mostrarResumo();
				System.out.println("\n-------\n");
			}
			System.out.println("------ FIM DA LISTA ------\n");
		}
		
	}

	public static void calcularPreco() {
		Vendas vendas = new Vendas();
		
		System.out.println("\nINsira a data da venda (dd/mm/aaaa): ");
		String data = scan.nextLine();
		vendas.setData(data);
		
		System.out.println("\nInsira a quantidade de plantas que deseja comprar: ");
		vendas.setQuantidade(scan.nextInt());
		//int quantidade = scan.nextInt();
		scan.nextLine();
		
		System.out.println("\nInsira o valor unitario da planta: ");
		vendas.setValorUnitario(scan.nextDouble());
		
		//double valor = scan.nextDouble();
		
		vendas.setPrecoTotal((vendas.getValorUnitario() * vendas.getQuantidade()));
		
		if(vendas.getQuantidade() > 10) {
			System.out.println("\n Voce recebeu um desconto de 5%!!!\n");
			vendas.alterarDesconto();
			vendas.setPrecoTotal((vendas.getPrecoTotal() -(vendas.getPrecoTotal() * 5/100)))  ;
			System.out.println("\nO valor total da compra com desconto de 5 por cento e R$: " + vendas.getPrecoTotal());
		}
		else {
			System.out.println("\nO valor total da compra e R$: " + vendas.getPrecoTotal());
		}
		
		
		venda.add(vendas);
		
		//calcularTroco(precoTotal);
	}

	public static void calcularTroco(Vendas vendas) {
		
			
			System.out.println("\nDigite o valor do pagamento: ");
			double pagamento = scan.nextDouble();
			
			if(pagamento < vendas.getPrecoTotal()) {
				System.out.println("\nValor de pagamento insuficiente!!!");
				return;
			}else if(pagamento == vendas.getPrecoTotal()){
				System.out.println("\nObrigado, pagamento de acordo!");
			}else {
				double troco = pagamento - vendas.getPrecoTotal();
				System.out.printf("\nSeu troco e R$: %.2f", troco);
			}
		
		

	
	}

}
