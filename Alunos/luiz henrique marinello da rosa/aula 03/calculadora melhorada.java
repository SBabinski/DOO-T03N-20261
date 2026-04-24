import java.util.Scanner;
import java.util.ArrayList;

public class Calculadora {
 static ArrayList<Venda> registroVendas = new ArrayList<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int opcao = 0;

        while (opcao != 4) {

            System.out.println("\n1 - Calcular valor total");
            System.out.println("2 - Calcular troco");
              System.out.println("3 - Listar vendas");
              System.out.println("4 - Sair");
              System.out.println("Escolha uma opção:");
  
            opcao = sc.nextInt();

            if (opcao == 1) calculaValorTotal();
            if (opcao == 2) calculaTroco();
            if (opcao == 3) listarVendas();
        }

        System.out.println("\nPrograma encerrado.");
    }

    public static void calculaValorTotal() {

        Scanner sc = new Scanner(System.in);

        System.out.println("Digite o valor do produto:");
        double valorProduto = sc.nextDouble();

        System.out.println("Digite a quantidade:");
        int quantidade = sc.nextInt();

        double valorTotal;
        double desconto = 0;

        if (quantidade > 10) {
            desconto = valorProduto * quantidade * 0.05;
            valorTotal = valorProduto * quantidade - desconto;
            System.out.println("Desconto aplicado!");
        } else {
            valorTotal = valorProduto * quantidade;
            System.out.println("Sem desconto.");
        }

        System.out.println("Valor total: " + valorTotal);

        Venda novaVenda = new Venda(valorProduto, quantidade, valorTotal, desconto);
        registroVendas.add(novaVenda);

        System.out.println("Venda registrada com sucesso!");
    }

    public static void calculaTroco() {

        Scanner sc = new Scanner(System.in);

        System.out.println("Digite o valor pago:");
        double valorPago = sc.nextDouble();

        System.out.println("Digite o valor do produto:");
        double valorProduto = sc.nextDouble();

        System.out.println("Troco: " + (valorPago - valorProduto));
    }

    public static void listarVendas() {

        if (registroVendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }

        for (int i = 0; i < registroVendas.size(); i++) {

            Venda v = registroVendas.get(i);

            System.out.println("\nVenda " + (i + 1));
                  System.out.println("Quantidade: " + v.quantidade);
             System.out.println("Valor total: " + v.valorTotal);
            System.out.println("Desconto: " + v.desconto);
        }
    }
}

class Venda {

    double valorProduto;
      int quantidade;
      double valorTotal;
    double desconto;

    public Venda(double valorProduto, int quantidade, double valorTotal, double desconto) {
        this.valorProduto = valorProduto;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.desconto = desconto;
    }
} 