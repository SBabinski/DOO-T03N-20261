package fag;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import fag.objetos.Loja;
import fag.objetos.Pedido;
import fag.objetos.ProcessaPedido;
import fag.objetos.Vendas;
import fag.objetos.Vendedor;
import fag.objetos.Cliente;
import fag.objetos.Item;
import fag.InicializadorDados;

public class Principal {
	public static Scanner scan = new Scanner(System.in);	
	
	private static List<Pedido> pedidos = new ArrayList<>();
	private static List<Item> itens = new ArrayList<>();
	private static List<Vendas> venda = new ArrayList<>();
	private static List<Vendedor> vendedores = new ArrayList<>();
	
	public static void main(String[] args) {
		List<Loja> lojas = InicializadorDados.criarLojas();
		
		mostrarMenu(lojas);
		
	}
	
	/*menu deve ter ""escolher loja, entra
	 escolhe vendedor, compra e etc;
	 */

	private static void mostrarMenu(List<Loja> lojas) {
		int escolha = 0;
		
		System.out.println("------BEM VINDO------");
		do {
			System.out.println("\n----- MY PLANT MENU -----\n");
			//aqui vai mostra as loja quetem,
			for(int i=0; i<lojas.size(); i++) {
				System.out.println((i+1) + " - " + lojas.get(i).getRazaoRocial());
			}
			
			System.out.println("0 - Sair");
			System.out.println("\nDigite sua escolha: ");
			escolha = scan.nextInt(); 
			scan.nextLine();
			
			if(escolha == 0) {
				System.out.println("\nENCERRANDO OPERACOES...");
				System.out.println("SISTEMA ENCERRADO!!!\n\n");
			}else if (escolha>0 && escolha<=lojas.size()){
				mostrarMenuLoja(lojas.get(escolha - 1));
			}else {
				System.out.println("\nEscolha uma opcao valida!!!");
			}
			
			
			
			//validarEscolha(escolha);
			
		}while(escolha!= 0);
		
		
	}

	private static void mostrarMenuLoja(Loja loja) {
		int escolhaMenu = 0;
		do {
			System.out.println("\n---- MENU DA LOJA " + loja.getRazaoRocial().toUpperCase() + " ----\n");
			System.out.println("1 - Comprar");
	        System.out.println("2 - Listar vendas");
	        System.out.println("3 - Buscar total de vendas por dia");
	        System.out.println("4 - Informacoes da loja");
	        System.out.println("5 - Listar vendedores");
	        System.out.println("6 - Listar clientes");
	        System.out.println("7 - Criar Pedido");
	        System.out.println("8 -  Cadasrar item");
	        //listar gerentes
	        System.out.println("0 - Voltar");
	        System.out.println("\nDigite o que deseja fazer hoje: ");
	        escolhaMenu = scan.nextInt();
	        scan.nextLine();
	        
	        verificarEscolhaMenu(escolhaMenu, loja);
	        
		}while(escolhaMenu != 0);				
	}

	private static void verificarEscolhaMenu(int escolhaMenu, Loja loja) {
		switch(escolhaMenu) {
		case 1:
			mostrarMenuCompras(loja);
		break;
		case 2:
			listarVendas(loja);
		break;
		case 3:
			listarVendasDia(loja);
		break;	
		case 4:
			loja.apresentarSe();
		break;
		case 5:
			listarVendedores(loja);
		break;
		case 6:
			listarClientes(loja);
		break;
		case 7:
			criarPedido(loja);
		break;
		case 8:
			cadastrarItem(loja);
		break;
		case 0:
			System.out.println("\nSAINDO DA LOJA...");
		break;
		default:
			System.out.println("\nInsira uma alternativa valida!!!");
		break;
		}
		
	}

	private static void cadastrarItem(Loja loja) {
		Item item = new Item();
		System.out.println("\n CADASTRO DE ITEM\n");
		
		System.out.println("Digite o ID do item:");
		item.setId(scan.nextInt());
		scan.nextLine();
		
		System.out.println("\nDigite o nome do produto:");
		item.setNome(scan.nextLine());
		
		System.out.println("\nDigite o tipo do produto: ");
		item.setTipo(scan.nextLine());
		
		System.out.println("\nDigite o valor do item:");
		item.setValor(scan.nextDouble());
		
		itens.add(item);
		
	}

	private static void criarPedido(Loja loja) {
		Pedido pedido = new Pedido();
		System.out.println("\n -- CADASTRO DE PEDIDO --\n");
		System.out.println("Digite o ID do pedido a ser cadastrado: ");
		pedido.setId(scan.nextInt());
		scan.nextLine();
		
		System.out.println("Escolha o nome de um desses clientes para cadastrar:");
		listarClientes(loja);
		String nome = scan.nextLine();
		pedido.setCliente(nome);
		
		System.out.println("Escolha o nome de um desses vendedores para cadastrar:");
		listarVendedores(loja);
		String vendedor = scan.nextLine();
		pedido.setVendedor(vendedor);
		
		System.out.println("\nEscolha uma loja: ");
		System.out.println("\nLoja 1 / Loja 2");
		String loj = scan.nextLine();
		pedido.setLoja(loj);
		
		System.out.println("\nData de criacao do pedido: dd/mm/aaaa");
		String criacao = scan.nextLine();
		pedido.setDataCriacao(criacao);
		
		System.out.println("\nDIgite a data de pagamento: dd/mm/aaaa");
		String pagam = scan.nextLine();
		pedido.setDataPagamento(pagam);
		
		
		System.out.println("\nDigite o vencimento da reserva: dd/mm/aaaa");
		String reserva = scan.nextLine();
		pedido.setDataVencimentoReserva(reserva);
		
		System.out.println("\nEscolha os itens que deseja adicionar ao pedido:");
		if (itens.isEmpty()) {
		    System.out.println("\nNenhum item cadastrado!");
		    return;
		}else {
			int opcao=0;
			do {
				for(int i=0; i<itens.size(); i++) {
					System.out.println((i+1) + " - " + itens.get(i).getNome());
					System.out.println("----\n");
				}
			
				int escolha = scan.nextInt();
				scan.nextLine();
			
				Item itemEscolhido = itens.get(escolha-1);
				pedido.adicionarItem(itemEscolhido);
				
				System.out.println("\nDeseja adicionar mais?");
				System.out.println("1-SIM / 0-NAO");
				opcao = scan.nextInt();
				scan.nextLine();
			
			}while(opcao==1);
		}
		
		
		
		pedido.calcularValorTotal();
		pedido.gerarDescricaoVenda();
		
		pedidos.add(pedido);
		
		System.out.println("\nPedido cadastrado com sucesso!");
		
		System.out.println("\nDigite o valor pago:");
		double valorPago = scan.nextDouble();

		ProcessaPedido processador = new ProcessaPedido();

		Vendas venda = processador.processar(pedido, valorPago);

		if (venda != null) {
		    loja.adicionarVenda(venda);
		    System.out.println("\nVenda realizada com sucesso!");
		} else {
		    System.out.println("\nPedido não foi convertido em venda.");
		}
	
	}

	private static void mostrarMenuCompras(Loja loja) {
		int escolhaCompra =0;
		
		do {
			System.out.println("\n----- COMPRAS -----\n");
			System.out.println("1 - Calcular preco");
			System.out.println("2 - Calcular troco");
			System.out.println("0 - Sair");
			System.out.println("\nDigite sua escolha: \n");
			escolhaCompra = scan.nextInt();
			scan.nextLine();
		
			validarCompra(escolhaCompra, loja);
			
		}while(escolhaCompra != 0);
		
	}
	
	private static void validarCompra(int escolhaCompra, Loja loja) {
		switch(escolhaCompra) {
		case 1:
			calcularPreco(loja);
		break;	
		case 2:
			if (loja.getVendas().isEmpty()) {
                System.out.println("\nNenhuma venda registrada nesta loja!");
            } else {
                calcularTroco(loja.getVendas().get(loja.getVendas().size() - 1));
            }
		break;
		case 0:
			System.out.println("\nSaindo...");
		break;
		default:
			System.out.println("\nEscolha uma opcao valida!");
		break;
		}
		
	}
	
	public static void calcularPreco(Loja loja) {
		Vendas vendas = new Vendas();
		
		//escolher vendedoe
		List<Vendedor> vendedores = loja.getVendedores();
		
		System.out.println("\nLista de vendedores para selecionar: \n");
		for(int i=0; i<vendedores.size(); i++) {
			System.out.println((i+1) + " - " + vendedores.get(i).getNome());
		}
		System.out.println("\nDigite o numero do vendedor escolhido: \n");
		
		int escolhaV = scan.nextInt();
		scan.nextLine();
		
		if(escolhaV>0 && escolhaV<=vendedores.size()) {
			vendas.setVendedor(vendedores.get(escolhaV -1));
		}else {
			System.out.println("\nDigite uma escolha valida");
			return;
		}
		
		//escolher o cliente
		List<Cliente> clientes = loja.getClientes();
		
		System.out.println("\nLista de clientes para selecionar:\n");
		for(int i=0; i<clientes.size(); i++) {
			System.out.println((i+1) + " - " + clientes.get(i).getNome());
		}
		System.out.println("\nDigite o numero do cliente escolhido:\n");
		
		int escolhaC = scan.nextInt();
		scan.nextLine();
		
		if(escolhaC>0 && escolhaC<=clientes.size()) {
			vendas.setCliente(clientes.get(escolhaC-1));
		}else {
			System.out.println("\nDigite uma escolha valida");
			return;
		}
		
		System.out.println("\nINsira a data da venda (dd/mm/aaaa): "); //datacriacaopedido
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
		loja.adicionarVenda(vendas);
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

	public static void listarVendas(Loja loja) {
		if(loja.getVendas().isEmpty()) {
			System.out.println("\nA lista esta vazia!!!\n");
			return;
		}else {
			System.out.println("\n------ LISTA DE VENDAS ------\n");
			for(Vendas v: loja.getVendas()) {
				v.mostrarResumo();
				System.out.println("\n-------\n");
			}
			System.out.println("------ FIM DA LISTA ------\n");
		}
		
	}
	
	public static void listarVendasDia(Loja loja) {
		
		System.out.println("\n Insira a data que deseja buscar (dd/mm/aaaa): ");
		String data = scan.nextLine();
		LocalDate dataBusca = LocalDate.parse(data, Vendas.FORMATO);
		
		int totVendas = 0;
		
		for(Vendas v: loja.getVendas()){
			
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
	
	private static void listarVendedores(Loja loja) {
		if(loja.getVendedores().isEmpty()) {
			System.out.println("\nNão há vendedores nesta loja!");
		}else {
			System.out.println("\n---- LISTA DE VENDEDORES ----");
			for(Vendedor v: loja.getVendedores()) {			//usa-se loja.getvendedor pra não juntar totos os objetos de vendedor criados
				v.apresentarse();
				v.CalcularMedia();
				v.calcularBonus();
				System.out.println("\n----------\n");
			}
			System.out.println("\n--- FIM ---");
		}
	}
	
	private static void listarClientes(Loja loja) {
		if(loja.getClientes().isEmpty()) {
			System.out.println("\nA lista esta vazia!");
		}else {
			System.out.println("\n---- LISTA DE CLIENTES ----\n");
			for(Cliente c: loja.getClientes()) {
				c.apresentarse();
				System.out.println("\n----------");
			}
			System.out.println("\n--- FIM ---\n");
		}
	}
	
	

}
