import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Calculadora calculadora = new Calculadora();
        List<Venda> listaVendas = new ArrayList<>();

        int opcao;

        do {
            System.out.println("\n=== Loja de Plantas da Dona Gabrielinha ===");
            System.out.println("[1] - Realizar Venda");
            System.out.println("[2] - Listar Vendas");
            System.out.println("[3] - Calcular Troco");
            System.out.println("[4] - Demonstração de Loja, Vendedor e Cliente");
            System.out.println("[0] - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {

                case 1:

                    System.out.print("Digite o nome da planta: ");
                    String nome = scanner.nextLine();

                    System.out.print("Digite o preço unitário: ");
                    double preco = scanner.nextDouble();

                    System.out.print("Digite a quantidade: ");
                    int quantidade = scanner.nextInt();

                    Planta planta = new Planta(nome, preco);

                    double valorTotal = calculadora.calcularPrecoTotal(
                            quantidade,
                            planta.getPrecoUnitario()
                    );

                    double desconto = calculadora.calcularDesconto(quantidade, valorTotal);
                    double valorFinal = calculadora.calcularValorFinal(valorTotal, desconto);

                    Venda venda = new Venda(
                            planta.getNome(),
                            quantidade,
                            valorFinal,
                            desconto
                    );

                    listaVendas.add(venda);

                    System.out.println("\n--- Resumo da Venda ---");
                    System.out.println("Planta: " + planta.getNome());
                    System.out.println("Quantidade: " + quantidade);
                    System.out.println("Valor total: R$ " + valorTotal);
                    System.out.println("Desconto: R$ " + desconto);
                    System.out.println("Valor final: R$ " + valorFinal);

                    break;

                case 2:

                    System.out.println("\n=== Registro de Vendas ===");

                    if (listaVendas.isEmpty()) {
                        System.out.println("Nenhuma venda registrada.");
                    } else {
                        for (Venda v : listaVendas) {
                            System.out.println("------------------------");
                            System.out.println("Planta: " + v.getNomePlanta());
                            System.out.println("Quantidade: " + v.getQuantidade());
                            System.out.println("Desconto: R$ " + v.getDescontoAplicado());
                            System.out.println("Valor Final: R$ " + v.getValorTotal());
                        }
                    }
                    break;

                case 3:

                    System.out.print("Digite o valor pago: ");
                    double valorPago = scanner.nextDouble();

                    System.out.print("Digite o valor final da compra: ");
                    double valorCompra = scanner.nextDouble();

                    double troco = calculadora.calcularTroco(valorPago, valorCompra);

                    if (troco < 0) {
                        System.out.println("Valor insuficiente! Faltam R$ " + (-troco));
                    } else {
                        System.out.println("Troco: R$ " + troco);
                    }
                    break;

                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;

                case 4:
                    System.out.println("\n=== Loja e Pessoas ===");

                    Vendedor vendedor1 = new Vendedor(
                            "Ana", 28, "Loja Dona Gabrielinha", "Cidade X", "Jardim das Flores", "Rua das Orquídeas", 1500.00
                    );
                    Vendedor vendedor2 = new Vendedor(
                            "Carlos", 35, "Loja Dona Gabrielinha", "Cidade X", "Jardim das Flores", "Rua das Rosas", 1600.00
                    );

                    Cliente cliente1 = new Cliente("Beatriz", 22, "Cidade X", "Jardim das Flores", "Rua das Orquídeas");
                    Cliente cliente2 = new Cliente("Diego", 30, "Cidade X", "Jardim das Flores", "Rua das Rosas");

                    Loja loja = new Loja(
                            "Plantas da Dona Gabrielinha",
                            "Dona Gabrielinha Ltda",
                            "00.000.000/0001-00",
                            "Cidade X",
                            "Jardim das Flores",
                            "Rua das Orqu�deas",
                            new Vendedor[] {vendedor1, vendedor2},
                            new Cliente[] {cliente1, cliente2}
                    );

                    loja.apresentarse();
                    loja.contarVendedores();
                    loja.contarClientes();

                    System.out.println("\n--- Detalhes do Vendedor 1 ---");
                    vendedor1.apresentarse();
                    System.out.println("Media salarial: R$ " + vendedor1.calcularMedia());
                    System.out.println("Bonus: R$ " + vendedor1.calcularBonus());

                    System.out.println("\n--- Detalhes do Vendedor 2 ---");
                    vendedor2.apresentarse();
                    System.out.println("Media salarial: R$ " + vendedor2.calcularMedia());
                    System.out.println("Bonus: R$ " + vendedor2.calcularBonus());

                    System.out.println("\n--- Detalhes do Cliente 1 ---");
                    cliente1.apresentarse();
                    System.out.println("\n--- Detalhes do Cliente 2 ---");
                    cliente2.apresentarse();
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        scanner.close();
    }
}
