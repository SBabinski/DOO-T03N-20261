import java.util.Scanner;

public class Calculadora {

    static int registroQtdPlantas = 0;
    static float registroValorVendas = 0;
    static float registroTotalDescontos = 0;

    public static void calcularPrecoTotal(Scanner sc) {
        System.out.println("Digite a quantidade da planta:");
        int qtd = sc.nextInt();

        System.out.println("Digite o valor da planta:");
        float precoPlanta = sc.nextFloat();

        float precoFinal = qtd * precoPlanta;
        float descontoAplicado = 0;

        if (qtd > 10) {
            descontoAplicado = precoFinal * 0.05f;
            precoFinal -= descontoAplicado;
            System.out.printf("Desconto de 5%% aplicado! Valor da compra com desconto: %.2f\n", (precoFinal/qtd));
        }

        System.out.printf("O preço total da compra é: %.2f\n", precoFinal);

        // Registro da Venda
        System.out.println("Deseja registrar essa venda? (1-Sim / 2-Não)");
        if (sc.nextInt() == 1) {
            registroQtdPlantas += qtd;
            registroValorVendas += precoFinal;
            registroTotalDescontos += descontoAplicado;
            System.out.println("Venda registrada com sucesso!\n");
        }
    }

    public static void calcularTroco(Scanner sc) {
        System.out.println("Digite o valor total da compra:");
        float valorCompra = sc.nextFloat();

        System.out.println("Digite o valor pago pelo cliente:");
        float valorPago = sc.nextFloat();

        if (valorPago < valorCompra) {
            System.out.println("Erro: Valor pago é insuficiente!");
        } else {
            float troco = valorPago - valorCompra;
            System.out.printf("O valor do troco é %.2f\n\n", troco);
        }
    }

    public static void exibirRegistro() {
        System.out.println("\nRELATÓRIO DE VENDAS");
        System.out.println("Quantidade total de plantas vendidas: " + registroQtdPlantas);
        System.out.printf("Valor total em vendas: R$ %.2f\n", registroValorVendas);
        System.out.printf("Total de descontos: R$ %.2f\n", registroTotalDescontos);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao = 0;

        while (opcao != 4) {
            System.out.println("Escolha uma das opções:");
            System.out.println("1 - Calcular preço da compra");
            System.out.println("2 - Calcular troco");
            System.out.println("3 - Exibir Registro de Vendas");
            System.out.println("4 - Sair");

            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    calcularPrecoTotal(sc);
                    break;
                case 2:
                    calcularTroco(sc);
                    break;
                case 3:
                    exibirRegistro();
                    break;
            }
        }
        sc.close();
    }
}