import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Calculadora {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<String> registroVendas = new ArrayList<>();
        int opcao = 0;

        while (opcao != 3) {
            exibirMenu();
            opcao = input.nextInt();

            switch (opcao) {
                case 1:
                    processarVenda(input, registroVendas);
                    break;
                case 2:
                    exibirVendas(registroVendas);
                    break;
                case 3:
                    System.out.println("Encerrando o sistema... Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
        input.close();
    }

    public static void exibirMenu() {
        System.out.println("\n--- Sistema Dona Gabrielinha ---");
        System.out.println("1. Calcular Preço de Venda");
        System.out.println("2. Visualizar Registro de Vendas");
        System.out.println("3. Sair");
        System.out.print("Escolha uma opção: ");
    }

    public static void processarVenda(Scanner input, List<String> registroVendas) {
        System.out.print("Digite a quantidade de plantas: ");
        int quantidade = input.nextInt();

        System.out.print("Digite o preço unitário da planta: ");
        double precoUnitario = input.nextDouble();

        double valorTotal = quantidade * precoUnitario;
        double desconto = 0;

       
        if (quantidade > 10) {
            desconto = valorTotal * 0.05;
            valorTotal -= desconto;
            System.out.println("Desconto de 5% aplicado!");
        }

        System.out.printf("Valor total a pagar: R$ %.2f\n", valorTotal);

     
        String dadosVenda = String.format("Qtd: %d | Valor Final: R$ %.2f | Desconto: R$ %.2f", 
                                          quantidade, valorTotal, desconto);
        registroVendas.add(dadosVenda);
    }

    public static void exibirVendas(List<String> registroVendas) {
        System.out.println("\n--- Histórico de Vendas ---");
        if (registroVendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada até o momento.");
        } else {
            for (String venda : registroVendas) {
                System.out.println(venda);
            }
        }
    }
}