import java.util.Scanner;
import java.util.ArrayList;

public class Calculadora {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Vendas> vendas = new ArrayList<>();
        int Valor_total = 0;
        int totalplantas = 0;
        int valorplantas = 0;
        int opcao = 1;
        int Valor_pago = 0;
        int resulTroco = 0;
        do {
            System.out.println("Bem-vindo a loja da Dona Gabrielinha");
            System.out.print("[1] - Realizar nova venda\n" +
                    "[2] - Calcular troco\n" +
                    "[3] - Ver vendas\n" +
                    "[4] - Apagar todas a vendas\n"+
                    "[5] - Sair");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("Digite quantas plantas você pegou:");
                    totalplantas = scanner.nextInt();
                    System.out.println("Digite o valor de cada planta:");
                    valorplantas = scanner.nextInt();
                    Valor_total = Calculos.somar(valorplantas, totalplantas);
                    Vendas venda = new Vendas(valorplantas, totalplantas, 0, Valor_total);
                    vendas.add(venda);
                    System.out.print("o valor total é " + Valor_total);
                    System.out.println("\n-------------------------------------");
                    break;
                case 2:
                    System.out.println("Digite o valor que você tem, para calcular seu troco");
                    Valor_pago = scanner.nextInt();
                    resulTroco = Calculos.resultado_troco(Valor_pago, Valor_total);
                    System.out.println("o seu troco foi de " + resulTroco);
                    System.out.println("\n-------------------------------------");
                    break;
                case 3:
                    if (vendas.isEmpty()) {
                        System.out.println("Nenhuma venda registrada.");
                    }for (Vendas v : vendas) {
                    System.out.println("\nValor das plantas: " + v.valorplantas);
                    System.out.println("Quantidade de planta pega: " + v.totalplantas);
                    System.out.println("Desconto: " + v.desconto);
                    System.out.println("Total de todos os produtos:R$ " + v.total);
                    System.out.println("\n-------------------------------------");
                }
                case 4:
                    vendas.clear();
                    System.out.println("Todas as vendas foram apagadas!");
                    System.out.println("\n-------------------------------------");
                    break;

            }
        }while (opcao != 5) ;
        scanner.close();

    }
}
