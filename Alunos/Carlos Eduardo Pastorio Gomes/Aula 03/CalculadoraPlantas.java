import java.util.*;

class Venda {
    int quantidade;
    double valorTotal;
    double desconto;

    public Venda(int quantidade, double valorTotal, double desconto) {
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.desconto = desconto;
    }
}

public class CalculadoraPlantas {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int opcao = 0;

        List<Venda> vendas = new ArrayList<>();

        while (opcao != 4) {
            System.out.println("\n--- Menu Loja da Dona Gabrielinha ---");
            System.out.println("[1] - Calcular Venda (com desconto)");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Ver Registro de Vendas");
            System.out.println("[4] - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = input.nextInt();

            if (opcao == 1) {
                System.out.print("Digite a quantidade de plantas: ");
                int quantidade = input.nextInt();

                System.out.print("Digite o valor unitário: ");
                double precoUnitario = input.nextDouble();

                double total = quantidade * precoUnitario;
                double desconto = 0;

                // 🔥 REGRA DO DESCONTO
                if (quantidade > 10) {
                    desconto = total * 0.05;
                    total -= desconto;
                    System.out.println("Desconto de 5% aplicado!");
                }

                System.out.printf("Valor final: R$ %.2f%n", total);

                // 🔥 REGISTRO DA VENDA
                vendas.add(new Venda(quantidade, total, desconto));

                System.out.println("Venda registrada com sucesso!");

            } else if (opcao == 2) {
                System.out.print("Digite o valor recebido: ");
                double recebido = input.nextDouble();

                System.out.print("Digite o valor da compra: ");
                double total = input.nextDouble();

                double troco = recebido - total;

                System.out.printf("Troco: R$ %.2f%n", troco);

            } else if (opcao == 3) {
                System.out.println("\n--- Registro de Vendas ---");

                if (vendas.isEmpty()) {
                    System.out.println("Nenhuma venda registrada.");
                } else {
                    int i = 1;
                    for (Venda v : vendas) {
                        System.out.println("Venda " + i++);
                        System.out.println("Quantidade: " + v.quantidade);
                        System.out.printf("Valor Final: R$ %.2f%n", v.valorTotal);
                        System.out.printf("Desconto: R$ %.2f%n", v.desconto);
                        System.out.println("----------------------");
                    }
                }

            } else if (opcao == 4) {
                System.out.println("Encerrando o sistema...");
            } else {
                System.out.println("Opção inválida!");
            }
        }

        input.close();
    }
}