import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Calculadora {

    static int registroQtdPlantas = 0;
    static float registroValorVendas = 0;
    static float registroTotalDescontos = 0;
    static int[][] vendasDiariasQtd = new int[13][32];
    static float[][] descontosDiarios = new float[13][32];


    public static void calcularPrecoTotal(Scanner sc) {
        System.out.println("Digite a quantidade da planta:");
        int qtd = sc.nextInt();

        System.out.println("Digite o valor da planta:");
        float precoPlanta = sc.nextFloat();
        float precoSemDesconto = qtd * precoPlanta;
        float precoFinal = precoSemDesconto;
        float descontoAplicado = 0;

        if (qtd > 10) {
            descontoAplicado = precoFinal * 0.05f;
            precoFinal = precoFinal - descontoAplicado;
            System.out.printf("Desconto de 5%% aplicado! Valor da compra com desconto: %.2f\n", (precoFinal/qtd));
        }

        System.out.printf("O preço total da compra é: %.2f\n", precoFinal);

        // Registro da Venda
        System.out.println("Deseja registrar essa venda? (1-Sim / 2-Não)");
        if (sc.nextInt() == 1) {
            LocalDate hoje = LocalDate.now();
            int dia = hoje.getDayOfMonth();
            int mes = hoje.getMonthValue();

            vendasDiariasQtd[mes][dia] += qtd;

            registroQtdPlantas += qtd;
            registroValorVendas += precoFinal;
            registroTotalDescontos += descontoAplicado;

            System.out.println("Venda registrada com sucesso!\n");
        }
    }

    public static void buscarVendasPorData(Scanner sc) {
        System.out.println("Digite o mês (1-12):");
        int mes = sc.nextInt();
        System.out.println("Digite o dia (1-31):");
        int dia = sc.nextInt();

        try {
            LocalDate dataBusca = LocalDate.of(LocalDate.now().getYear(), mes, dia);
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            System.out.println("\n RELATÓRIO DO DIA " + dataBusca.format(formatador));
            System.out.println("Plantas vendidas: " + vendasDiariasQtd[mes][dia]);
        } catch (Exception e){
            System.out.println("Erro: Data inexistente");
        }
    }

    public static void exibirRegistroGeral() {
        System.out.println("\n BALANÇO GERAL ");
        System.out.println("Total de plantas vendidas: " + registroQtdPlantas);
        System.out.printf("Valor total em caixa: R$ %.2f\n", registroValorVendas);
        System.out.printf("Total de descontos dados: R$ %.2f\n", registroTotalDescontos);
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

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao = 0;

        while (opcao != 5) {
            System.out.println("Escolha uma das opções:");
            System.out.println("1 - Calcular preço da compra");
            System.out.println("2 - Calcular troco");
            System.out.println("3 - Exibir Registro de Vendas");
            System.out.println("4 - Buscar vendas por data específica");
            System.out.println("5 - Sair");

            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    calcularPrecoTotal(sc);
                    break;
                case 2:
                    calcularTroco(sc);
                    break;
                case 3:
                    exibirRegistroGeral();
                    break;
                case 4:
                    buscarVendasPorData(sc);
                    break;
            }
        }
        sc.close();
    }
}