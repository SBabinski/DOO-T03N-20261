import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Calculadora {

    public static List<Venda> vendas;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        vendas = new ArrayList<>();

        vendas.add(new Venda(17, 12, 0.05));
        vendas.add(new Venda(12, 12, 0.05));


        menuInicialController(sc);
        sc.close();
    }
    
    //Método que exibe Menu Inicial

    public static void menuInicialController (Scanner sc){ 
        int opcao = 0;

        while (opcao != 3) {
            System.out.println("\n*** Sistema de vendas Loja da Gabrielinha ***");
            System.out.println("[1] - Realizar venda");
            System.out.println("[2] - Exibir Vendas");
            System.out.println("[3] - Sair");
            System.out.print("Escolha uma opção: ");
        
            opcao = sc.nextInt();

            switch (opcao) {
                case 1: 
                    realizarVenda(sc);
                    break;
                case 2:
                    exibirVendas();
                    break;

                case 3:
                    System.out.println("Encerrando o sistema.");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }
    
    //Método para realizar uma nova venda

    public static void realizarVenda(Scanner sc){
        
        System.out.println("----------------------------------------------------");
        System.out.println("\t\tCadastro de Venda");
        System.out.println("----------------------------------------------------");
        
        System.out.print("Digite a quantidade do item a ser vendida: ");
        int quantidade = sc.nextInt();
        System.out.print("Digite o preço por unidade: ");
        double precoUnitario = sc.nextDouble();
        
        //Calculo da Venda
        
        double valorCompra = quantidade * precoUnitario;

        //Aplica Desconto especial
        
        double porcentagemDesconto = 0;
        double valorDesconto = 0;
        
        if(quantidade > 10){
            porcentagemDesconto = 0.05;

            valorDesconto = valorCompra - valorCompra * (1 - porcentagemDesconto);
            valorCompra = valorCompra - valorDesconto;
        }
        
        //Saída das informações da venda para o usuário 

        System.out.println(String.format("O valor total da venda é: R$ %.2f", valorCompra));
        System.out.println(String.format("Desconto aplicado de: R$ %.2f", valorDesconto));
        System.out.println(String.format("Porcentagem do desconto: %.2f",porcentagemDesconto)+"%");

        //Calculo do Troco
        System.out.print("Digite o valor recebido pelo cliente: ");
        double valorRecebido = sc.nextDouble();

        if (valorRecebido < valorCompra) {
            System.out.println("Erro: Valor recebido é insuficiente.");
        } else {
            try {
                vendas.add(new Venda(quantidade, valorCompra, valorDesconto));
            } catch (IllegalArgumentException e) {
                System.out.println("Venda não realizada: " + e.getMessage()); 
                return;
            }
            double troco = valorRecebido - valorCompra;
            System.out.printf("Venda realizada. O troco a ser dado é: R$ %.2f%n", troco);
        }

    }

    //Método para exibir todas as vendas

    public static void exibirVendas(){
        System.out.println("----------------------------------------------------");
        System.out.println("\t\tVendas da loja:");
        System.out.println("----------------------------------------------------");
        for (Venda venda : vendas) {
           System.out.println(venda.toString());
        }
    }

}