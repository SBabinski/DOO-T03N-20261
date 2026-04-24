import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static ArrayList<Venda> listaDeVendas = new ArrayList<Venda>();

    public static void main(String[] args) throws Exception {
        var appRodando = true;

        while (appRodando) {
            exibirMenu();
            System.out.print("Ação: ");

            int acaoUsuario = scanner.nextInt();
            scanner.nextLine();

            switch (acaoUsuario) {
                case 1:
                    System.out.print("Insira quantidade de plantas: ");
                    var qtdPlantas = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Insira o valor da planta: ");    
                    var valorPlanta = scanner.nextFloat();
                    scanner.nextLine();

                    
                    var valorCompra = Calculadora.calcularPreco(qtdPlantas, valorPlanta);
            
                    float desconto = 0.0f;

                    // calculo do desconto, em caso de venda com mais de 10 plantas
                    if (qtdPlantas > 10) {
                        desconto = Calculadora.calcularDesconto(valorCompra);
                    }

                    float valorFinal = valorCompra - desconto;

                    // cria venda e armazena no histórico
                    var novaVenda = new Venda(qtdPlantas, valorCompra, desconto);
                    listaDeVendas.add(novaVenda);

                    System.out.println("Valor da venda: R$" + valorCompra);
                    System.out.println("Desconto: R$" + desconto);
                    System.out.println("Valor final: R$" + valorFinal);
                    break;
                case 2:
                    System.out.print("Valor pago pelo cliente: ");
                    var valorPagoCliente = scanner.nextFloat();
                    scanner.nextLine();

                    System.out.print("Valor total da compra: ");
                    var valorVenda = scanner.nextFloat();
                    scanner.nextLine();

                    var valorTroco = Calculadora.calcularTroco(valorPagoCliente, valorVenda);

                    System.out.println("Valor do troco: R$" + valorTroco);
                    break;
                case 3:
                    // listar o histórico de vendas

                    if (listaDeVendas.size() == 0) {
                        System.out.println("- Nenhuma venda realizada -");
                        break;
                    }
                    
                    for (var venda : listaDeVendas) {
                        var indiceVenda = listaDeVendas.indexOf(venda);
                        System.out.print("ID: " + indiceVenda + " - ");
                        venda.exibirRegistro();
                    }

                    break;
                case 4:
                    appRodando = false;
                    break;
                default:
                    System.out.println("Ação inválida.");
                    break;
            }
        }
        scanner.close();
    }

    public static void exibirMenu() {
        System.out.println("-----MENU-----");
        System.out.println("1 - Calcular o preço total");
        System.out.println("2 - Calcular troco");
        System.out.println("3 - Visualizar histórico de vendas");
        System.out.println("4 - Sair");
        System.out.println("--------------");
    }
}
