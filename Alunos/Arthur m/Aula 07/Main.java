import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== SISTEMA MY PLANT ===");
            System.out.println("[1] - Criar Pedido (teste)");
            System.out.println("[2] - Sair");
            System.out.print("Escolha: ");

            opcao = scanner.nextInt();

            switch (opcao) {

                case 1:

                    Endereco endereco = new Endereco(
                            "PR",
                            "Cascavel",
                            "Centro",
                            "123",
                            "Apto 1"
                    );

                    Loja loja = new Loja("My Plant");

                    Cliente cliente = new Cliente(
                            "Arthur",
                            20,
                            endereco
                    );

                    Vendedor vendedor = new Vendedor(
                            "Carlos",
                            30,
                            endereco,
                            loja,
                            2000
                    );

                    Item item1 = new Item(1, "Orquídea", "Flor", 50.0);
                    Item item2 = new Item(2, "Bonsai", "Árvore", 80.0);

                    ProcessaPedido processador = new ProcessaPedido();
                    Pedido pedido = processador.processar(cliente, vendedor, loja);

                    pedido.adicionarItem(item1);
                    pedido.adicionarItem(item2);

                    System.out.println("\n=== DADOS DO PEDIDO ===");
                    pedido.gerarDescricaoVenda();

                    break;

                case 2:
                    System.out.println("Encerrando sistema...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 2);

        scanner.close();
    }
}