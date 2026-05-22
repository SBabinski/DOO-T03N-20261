import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        ArrayList<Gerente> gerentes = new ArrayList<>();
        ArrayList<Item> catalogo = new ArrayList<>();

        Endereco endPadrao = new Endereco("PR", "Cascavel", "Centro", "Av. Brasil", 1000, "Sl 02");
        Loja minhaLoja = new Loja("My Plant", "My Plant LTDA", "12.345/0001-99", endPadrao);
        Vendedor v1 = new Vendedor("João Gnoatto", 18, "My Plant", endPadrao, 2500.0);
        Cliente c1 = new Cliente("Gabriel", 25, endPadrao);

        catalogo.add(new Item(1, "Vaso de Cerâmica", "Decoração", 45.90));
        catalogo.add(new Item(2, "Fertilizante", "Manutenção", 20.00));

        int opcao = 0;

        while (opcao != 4) {
            System.out.println("\n--- SISTEMA MY PLANT (CTO DASHBOARD) ---");
            System.out.println("1. Cadastrar Gerente");
            System.out.println("2. Consultar Gerentes");
            System.out.println("3. Criar Pedido");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = leitor.nextInt();
            leitor.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Nome do Gerente: ");
                    String nomeG = leitor.nextLine();
                    System.out.print("Idade: ");
                    int idadeG = leitor.nextInt();
                    System.out.print("Salário Base: ");
                    double salG = leitor.nextDouble();

                    Gerente novoG = new Gerente(nomeG, idadeG, minhaLoja.nomeFantasia, endPadrao, salG);

                    novoG.salarioRecebido.add(salG);
                    novoG.salarioRecebido.add(salG * 1.05);
                    novoG.salarioRecebido.add(salG * 0.98);

                    gerentes.add(novoG);
                    System.out.println("Gerente cadastrado com sucesso!");
                    break;

                case 2:
                    Gerente.consultarGerentes(gerentes);
                    break;

                case 3:
                    System.out.println("\nProcessando Pedido de Teste...");
                    ProcessaPedido processador = new ProcessaPedido();

                    Pedido novoPedido = processador.processar(101, c1, v1, minhaLoja, catalogo);

                    System.out.println("===============================");
                    novoPedido.gerarDescricaoVenda();
                    System.out.println("Itens do Pedido:");
                    for (Item item : catalogo) {
                        item.gerarDescricao();
                    }
                    processador.testarPagamento(novoPedido);
                    System.out.println("===============================");
                    break;

                case 4:
                    System.out.println("Encerrando sistema... Até logo, CTO!");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
        leitor.close();
    }
}