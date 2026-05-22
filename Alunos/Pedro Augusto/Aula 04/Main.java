import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static boolean appRodando = true;

    public static void main(String[] args) throws Exception {
        while (appRodando) {
            exibirMenu();
            System.out.print("Ação: ");

            int acaoUsuario = scanner.nextInt();

            gerenciarAcoes(acaoUsuario);

            scanner.nextLine();

        }
        scanner.close();
    }

    public static void exibirMenu() {
        System.out.println("-----MENU-----");
        System.out.println("1 - Calcular o preço total");
        System.out.println("2 - Calcular troco");
        System.out.println("3 - Realizar Venda");
        System.out.println("4 - Consultar Vendas");
        System.out.println("5 - Sair");
        System.out.println("--------------");
    }

    public static void gerenciarAcoes(int acaoUsuario) {
        switch (acaoUsuario) {
            case 1:
                calcularPrecoTotal();
                break;
            case 2:
                calcularTroco();
                break;
            case 3:
                realizarVenda();
                break;
            case 4:
                consultarVendas();
                break;
            case 5:
                // termina aplicação
                appRodando = false;
                break;
            default:
                System.out.println("Ação inválida.");
                break;
        }
    }

    public static void calcularPrecoTotal() {
        System.out.print("Insira quantidade de plantas: ");
        var qtdPlantas = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Insira o valor da planta: ");
        var valorPlanta = scanner.nextFloat();

        var valorVenda = Calculadora.calcularPreco(qtdPlantas, valorPlanta);

        System.out.println("Preço total: R$ " + valorVenda);
    }

    public static void calcularTroco() {
        System.out.print("Valor pago pelo cliente: ");
        var valorPagoCliente = scanner.nextFloat();
        scanner.nextLine();

        System.out.print("Valor total da compra: ");
        var valorVenda = scanner.nextFloat();
        scanner.nextLine();

        // validação: valor da compra não deve ser menor ou igual a zero
        if (valorVenda <= 0) {
            System.out.println("Erro: Valor da venda deve ser positivo.");
            return;
        }

        var valorTroco = Calculadora.calcularTroco(valorPagoCliente, valorVenda);

        System.out.println("Valor do troco: R$" + valorTroco);
    }

    public static void realizarVenda() {
        System.out.print("Quantidade de plantas: ");
        var qtdPlantas = scanner.nextInt();
        scanner.nextLine();

        // validação: impossivel fazer venda com qtd de plantas negativas ou igual a zero
        if (qtdPlantas <= 0) {
            System.out.println("Erro: insira um número maior que zero");
            return;
        }

        System.out.print("Valor das plantas: R$");
        var precoPlantas = scanner.nextFloat();

        // pegando data com usuário
        System.out.println("Informe a data da venda");
        System.out.print("Dia: ");
        int dia = scanner.nextInt();

        System.out.print("Mês: ");
        int mes = scanner.nextInt();

        System.out.print("Ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine();

        float valorVenda = precoPlantas * qtdPlantas;

        float desconto = 0.0f;
        if (qtdPlantas > 10) {
            desconto = Calculadora.calcularDesconto(valorVenda);
        }

        float valorFinal = valorVenda + desconto;
        
        Venda novaVenda = new Venda(qtdPlantas, valorFinal, desconto, dia, mes, ano);
        Database.registrarVenda(novaVenda);

        System.out.println("--- Venda Realizada ---");
        System.out.println("Valor Original: R$" + precoPlantas);
        System.out.println("Desconto: R$" + desconto);
        System.out.println("Valor Final: R$" + valorFinal);
        System.out.println("-----------------------");
    }

    public static void consultarVendas() {
        // consulta de vendas por dia ou por mês

        System.out.println("Consultar por:");
        System.out.println("1 - Dia do mês");
        System.out.println("2 - Mês");
        System.out.println("3 - Total");
        System.out.print("Escolha: ");

        int escolhaUsuario = scanner.nextInt();
        scanner.nextLine();

        if (escolhaUsuario == 1) {
            // filtra por dia/mes

            System.out.print("Insira o mês (1-12): ");
            int mes = scanner.nextInt();

            System.out.print("Insira o dia: ");
            int dia = scanner.nextInt();
            scanner.nextLine();

            ArrayList<Venda> listaVendas = Database.pegarVendasPorDiaMes(dia, mes);

            if (listaVendas.isEmpty()) {
                System.out.println("Nenhuma venda encontrada");
                return;
            }

            // exibe as vendas encontradas
            for (Venda venda : listaVendas) {
                venda.exibirRegistro();
            }
        } else if (escolhaUsuario == 2) {
            // filtra por mes

            System.out.print("Insira o mês (1-12): ");
            int mes = scanner.nextInt();
            scanner.nextLine();

            ArrayList<Venda> listaVendas = Database.pegarVendasPorMes(mes);

            if (listaVendas.isEmpty()) {
                System.out.println("Nenhuma venda encontrada");
                return;
            }

            // exibe as vendas encontradas
            for (Venda venda : listaVendas) {
                venda.exibirRegistro();
            }
        } else {
            // exibe o total de vendas

            var totalDeVendas = Database.pegarTotalVendas();

            for (var venda : totalDeVendas) {
                venda.exibirRegistro();
            }
        }

    }
}
