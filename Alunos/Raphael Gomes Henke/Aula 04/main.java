import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CalculadoraLoja {

    static class Venda {
        int quantidade;
        double valorVenda;
        double desconto;
        LocalDate data;

        Venda(int quantidade, double valorVenda, double desconto, LocalDate data) {
            this.quantidade = quantidade;
            this.valorVenda = valorVenda;
            this.desconto = desconto;
            this.data = data;
        }

        public String toString() {
            DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            return "Data: " + data.format(f) +
                   " | Quantidade: " + quantidade +
                   ", Valor venda: R$ " + String.format("%.2f", valorVenda) +
                   ", Desconto: R$ " + String.format("%.2f", desconto);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao;
        List<Venda> vendas = new ArrayList<>();

        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        do {
            System.out.println("1 - calcular preco total");
            System.out.println("2 - Calcular troco");
            System.out.println("3 - Mostrar registro de vendas");
            System.out.println("4 - Total de vendas por dia");
            System.out.println("5 - Total de vendas por mes");
            System.out.println("6 - sair");
            System.out.print("escolha: ");
            opcao = sc.nextInt();
            sc.nextLine(); 



            if (opcao == 1) {
                System.out.print("Quantidade: ");
                int quantidade = sc.nextInt();
                System.out.print("Preco unitario: ");
                double preco = sc.nextDouble();
                sc.nextLine();
                System.out.print("Data (dd/MM/yyyy): ");
                String dataStr = sc.nextLine();
                LocalDate data = LocalDate.parse(dataStr, f);

                double total = quantidade * preco;
                double desconto = 0.0;

                if (quantidade > 10) {
                    desconto = total * 0.05;
                    total -= desconto;
                    System.out.println("desconto de 5% aplicado: R$ " + String.format("%.2f", desconto));
                }

                System.out.println("total pra pagar = R$ " + String.format("%.2f", total));


                vendas.add(new Venda(quantidade, total, desconto, data));


            } else if (opcao == 2) {
                System.out.print("Valor pago:");
                double pago = sc.nextDouble();
                System.out.print("Valor da compra: ");
                double totalCompra = sc.nextDouble();


                if (pago < totalCompra) {
                    System.out.println("valor insuficiente");
                } else {
                    double troco = pago - totalCompra;
                    System.out.println("troco = R$ " + String.format("%.2f", troco));
                }


            } else if (opcao == 3) {
                System.out.println("Registro de vendas");


                if (vendas.isEmpty()) {
                    System.out.println("nenhuma venda registrada");
                } 
                
                else {
                    for (int i = 0; i < vendas.size(); i++) {
                        System.out.println("venda " + (i + 1) + ": " + vendas.get(i));
                    }
                }



            } else if (opcao == 4) {
                System.out.print("digite a data (dd/MM/yyyy): ");
                String dataStr = sc.nextLine();
                LocalDate dataBusca = LocalDate.parse(dataStr, f);
                double totalDia = 0;


                for (Venda v : vendas) {
                    if (v.data.equals(dataBusca)) {
                        totalDia += v.valorVenda;
                    }
                }


                System.out.println("total do dia: R$ " + String.format("%.2f", totalDia));


            } else if (opcao == 5) {
                System.out.print("Digite o mes (1-12): ");
                int mes = sc.nextInt();

                System.out.print("digite o ano: ");
                int ano = sc.nextInt();
                double totalMes = 0;

                for (Venda v : vendas) {
                    if (v.data.getMonthValue() == mes && v.data.getYear() == ano) {
                        totalMes += v.valorVenda;
                    }
                }

                System.out.println("Total do mes: R$ " + String.format("%.2f", totalMes));

            } else if (opcao != 6) {
                System.out.println("opcao invalida...");
            }


            System.out.println();


        } while (opcao != 6);
        System.out.println("Programa encerrado!");
        sc.close();
    }
}