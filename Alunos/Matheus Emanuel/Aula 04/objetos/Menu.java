package objetos;
import java.util.Scanner;

public class Menu {

    private static final Scanner scanner = new Scanner(System.in);

    public static void mostrarMenu() {
        int escolha;
        do {
            System.out.println("\n=== Calculadora de Vendas de Plantas ===");
            System.out.println("1. Calcular Preço e Registrar Venda");
            System.out.println("2. Calcular Troco");
            System.out.println("3. Registro de Vendas");
            System.out.println("4. Consultar Vendas por Data");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextInt();
            validarEscolha(escolha);
        } while (escolha != 5);
    }

    public static void validarEscolha(int escolha) {
        switch (escolha) {
            case 1 -> Venda.calcularPreco();
            case 2 -> Venda.calcularTroco();
            case 3 -> Venda.mostrarRegistroVendas();
            case 4 -> Venda.consultarVendasPorData();
            case 5 -> System.out.println("Saindo...");
            default -> System.out.println("Opção inválida. Tente novamente.");
        }
    }
}