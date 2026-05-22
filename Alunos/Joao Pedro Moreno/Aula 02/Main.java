    import java.util.Scanner;
	import java.util.ArrayList;

public class Main {

	static ArrayList<Planta> historico = new ArrayList<>();
	public static Scanner scan = new Scanner(System.in);
	static double totalCompra;
	public static void main(String[] args) {
		VerMenu();
	}

	//Menu visivel ao cliente
	public static void VerMenu() {
		int escolha = 0;

		do {
			System.out.println("\n\n___________________________________");
			System.out.println("|--------------Menu---------------|");
			System.out.println("|1- Calcular Preço Total          |");
			System.out.println("|2- Calcular troco                |");
			System.out.println("|3- Histórico de Vendas           |");
			System.out.println("|4- Sair                          |");
			System.out.println("|Escolha uma opção:               |");
			System.out.printf("|_________________________________|\n");
			escolha = scan.nextInt();
			scan.nextLine();
			validarEscolha(escolha);
		} while (escolha != 4);
	}

	//leitura da escolha feita e direcionamento
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
			System.out.printf("Você saiu do sistema! Obrigado, volte sempre!");
			return;
		}
		default:
			System.out.printf("Opção incorreta, tente novamente!");
		}
	}

	//cálculo do Preço total
	public static void CalcularPrecoTotal() {

		Planta p = new Planta();

		p.LeituraNome();
		p.LeituraQuant();
    	p.LeituraValor();

   		totalCompra = p.calcularPrecoTotal();

		historico.add(p);//guarda a venda

    	System.out.println("O valor total das plantas: " + totalCompra);
	}

	//cálculo do Troco 
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

	//ArrayList das Vendas
	public static void HistoricoVendas(){
		
		if (historico.isEmpty()) {
        System.out.println("Nenhuma venda registrada.");
    	} else {

        	System.out.println("\n___________________________________");
        	System.out.println("|-------HISTÓRICO DE VENDAS-------|");

        	for (Planta p : historico) {
            System.out.println(p);
        	}

        	System.out.println("|                                 |");
			System.out.println("|_________________________________|");
		}
	}
}
