import java.util.List;
import java.util.Scanner;

public class Calculadora{
    
    public static Scanner scan = new Scanner(System.in);
    public static double resultado = 0;
    static List<Planta> registroVendas = new java.util.ArrayList<>();

    public static void main(String[] args){
        mostrarMenu();
    }

    private static void mostrarMenu(){
        int escolha;
    do{
        System.out.println("=========Menu Geral=========");
        System.out.println("[1] Calcular preço e Registrar venda");
        System.out.println("[2] Calcular troco");
        System.out.println("[3] Registro de vendas");
        System.out.println("[4] Sair");
        escolha = scan.nextInt();
        validarEscolha(escolha);
    }while(escolha != 4 );
    }

    private static void validarEscolha(int escolha) {
		switch (escolha) {
		case 1 -> calcularPreco();
		case 2 -> calcularTroco();
		case 3 -> consultarRegistroVendas();
		case 4 -> System.out.println("Saindo do sistema...");

		default -> System.out.println("Escolha inválida");
		}
		
	}

    public static void calcularPreco(){
        System.out.println("Por favor entre com a espécie do produto: ");
        String especie = scan.next();
        if(especie.isEmpty()){
            System.out.println("Espécie não pode ser vazia");
            return;
        }
        System.out.println("Por favor entre com a quatidade do produto: ");
        int qtd = scan.nextInt();
        if(qtd <= 0){
            System.out.println("Quantidade deve ser maior que zero");
            return;
        }
        System.out.println("Por favor entre com o valor do produto: ");
        double valor = scan.nextDouble();
        if(valor <= 0){
            System.out.println("Valor deve ser maior que zero");
            return;
        }
        if(qtd >= 10){
            System.out.println("Desconto de 5% aplicado!");
             resultado = (valor * qtd) * 0.95;
        }else{
             resultado = (valor * qtd);
        }

        System.out.println("O valor total é:  " + resultado);
        Planta venda = new Planta(especie, qtd, resultado);
        RegistroVendas(venda);
    }

    public static void calcularTroco(){
        System.out.println("Por favor entre com o valor pago pelo cliente: ");
        double valorPago = scan.nextDouble();
        if(valorPago <= 0){
            System.out.println("Valor pago deve ser maior que zero");
            return;
        }
        System.out.println("Digite o valor total da compra: ");
        double valorCompra = scan.nextDouble();
        if(valorCompra <= 0){
            System.out.println("Valor da compra deve ser maior que zero");
            return;
        }

        double troco = valorPago - valorCompra;

        if(troco < 0){
            System.out.println("Valor pago é insuficiente...");
        }else{
            System.out.println("O valor do troco é: " + troco);
        }
    }

    public static void RegistroVendas(Planta venda){
        registroVendas.add(venda);
    }

    public static void consultarRegistroVendas(){
        if(registroVendas.isEmpty()){
            System.out.println("Nenhuma venda registrada...");
        }else{
            System.out.println("Registro de vendas: ");
            for(Planta venda : registroVendas){
                venda.mostrarResumo();
            }
        }
    }
}