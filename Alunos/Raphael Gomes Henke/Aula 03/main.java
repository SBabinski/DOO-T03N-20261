import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CalculadoraLoja {
    static class Venda {
        int quantidade;
        double valorVenda;
        double desconto;


        Venda(int quantidade, double valorVenda, double desconto) {
            this.quantidade = quantidade;
            this.valorVenda = valorVenda;
            this.desconto = desconto;
        }



        public String toString() {
            return "Quantidade: " + quantidade +
                   ", Valor venda: R$ " + String.format("%.2f", valorVenda) +
                   ", Desconto: R$ " + String.format("%.2f", desconto);
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao;
        List<Venda> vendas = new ArrayList<>(); 


    do {
            System.out.println("1 - calcular preco total");
            System.out.println("2 - Calcular troco");
            System.out.println("3 - Mostrar registro de vendas");
            System.out.println("4 - sair");
            System.out.print("escolha: ");
            opcao = sc.nextInt();

            if (opcao == 1) {
                System.out.print("Quantidade: ");
                int quantidade = sc.nextInt();
                System.out.print("Preco unitario: ");
                double preco = sc.nextDouble();
                double total = quantidade * preco;
                double desconto = 0.0;


                if (quantidade > 10) {
                    desconto = total * 0.05;
                    total -= desconto;
                    System.out.println("Desconto de 5% aplicado: R$ " + String.format("%.2f", desconto));
                }



                System.out.println("Total a pagar = R$ " + String.format("%.2f", total));



                vendas.add(new Venda(quantidade, total, desconto));
            } else if (opcao == 2) {
                System.out.print("Valor pago:");
                double pago = sc.nextDouble();
                System.out.print("Valor da compra: ");
                double totalCompra = sc.nextDouble();


                if (pago < totalCompra) {
                    System.out.println("Valor insuficiente");
                } else {
                    double troco = pago - totalCompra;
                    System.out.println("troco = R$ " + String.format("%.2f", troco));
                }

            } else if (opcao == 3) {
                System.out.println(" Registro de Vendas ");
                
                if (vendas.isEmpty()) {
                    System.out.println("Nenhuma venda registrada.");
                } else {
                    
                    for (int i = 0; i < vendas.size(); i++) {
                        System.out.println("venda " + (i + 1) + ": " + vendas.get(i));
                    }
                }

            } else if (opcao != 4) {
                System.out.println("Opcao iinvalida!");
            }



            System.out.println();



        } while (opcao != 4);
        System.out.println("Programa encerrado.");
        sc.close();
    }
}