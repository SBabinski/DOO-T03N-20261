package AULA3;//versao de entrega
import java.util.Scanner;

public class CalculadoraDonaGabrielinhaV2 {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        int opcao = 0;


        int totalPlantas = 0;
        double faturamentoTotal = 0;
        double totalDescontos = 0;

        while (opcao != 4) {
            System.out.println("\n--- Menu Dona Gabrielinha V2 ---");
            System.out.println("[1] - Calcular Preço Total (com desconto)");
            System.out.println("[2] - Ver Registro de Vendas");
            System.out.println("[3] - Calcular Troco");
            System.out.println("[4] - Sair");
            System.out.print("Escolha: ");
            opcao = leitor.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("Quantidade: ");
                    int qtd = leitor.nextInt();
                    System.out.print("Preço unitário: ");
                    double preco = leitor.nextDouble();

                    double totalBruto = qtd * preco;
                    double desconto = 0;


                    if (qtd > 10) {
                        desconto = totalBruto * 0.05;
                    }

                    double totalFinal = totalBruto - desconto;
                    System.out.println("Total a pagar: R$ " + totalFinal);
                    if(desconto > 0) System.out.println("Desconto aplicado: R$ " + desconto);

                    totalPlantas += qtd;
                    faturamentoTotal += totalFinal;
                    totalDescontos += desconto;
                    break;

                case 2:
                    System.out.println("\n--- Registro de Vendas ---");
                    System.out.println("Plantas vendidas: " + totalPlantas);
                    System.out.println("Faturamento total: R$ " + faturamentoTotal);
                    System.out.println("Total de descontos: R$ " + totalDescontos);
                    break;

                case 3:
                    System.out.print("Valor pago: ");
                    double pago = leitor.nextDouble();
                    System.out.print("Valor compra: ");
                    double vCompra = leitor.nextDouble();
                    System.out.println("Troco: R$ " + (pago - vCompra));
                    break;
            }
        }
        leitor.close();
    }
}
