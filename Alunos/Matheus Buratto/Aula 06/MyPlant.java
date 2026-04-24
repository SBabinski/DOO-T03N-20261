import java.util.ArrayList;
import java.util.Scanner;

public class MyPlant {

    public static void main(String[] args) {

        ArrayList<Double> salarios = new ArrayList<>();
        salarios.add(3000.0);
        salarios.add(2700.0);
        salarios.add(2600.0);

        ArrayList<Vendedor> vendedores = new ArrayList<>();
        vendedores.add(new Vendedor("Matheus", 23, "Loja My Plant", "Cascavel", "São Critóvão", "Rua Pato Branco", 1700.0, salarios));
        vendedores.add(new Vendedor("Gabriel", 22, "Loja My Plant", "Cascavel", "São Critóvão", "Rua Raposo Tavares ", 2000.0, salarios));
        vendedores.add(new Vendedor("Eduarado", 28, "Loja My Plant", "Corbelia", "Centro", "Rua Tulipa", 2500.0, salarios));

        ArrayList<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente("Ana Julia", 27, "Cascavel", "Brasilia", "Avenida São Carlos"));
        clientes.add(new Cliente("Ana Paula", 30, "Cascavel", "Interlagos", "Rua Eleodoro"));

        Loja loja = new Loja("My Plant", "PLANTAS LTDA", "55861684651684",
                "Cascavel", "Centro", "Avenida Brasil", vendedores, clientes);

        Calculadora calc = new Calculadora();

        Scanner scan = new Scanner(System.in);

        int opcao = 0;
        while (opcao != 8) {
            System.out.println("\n=============== ===========");
            System.out.println("Bem-vindo à Loja My Plant!");
            System.out.println("========== MENU ===========");
            System.out.println("\n[1] Calcular Preço Total");
            System.out.println("[2] Calcular Troco");
            System.out.println("[3] Histórico de Vendas");
            System.out.println("[4] Buscar Vendas por Data");
            System.out.println("[5] Buscar Vendas por Mês");
            System.out.println("[6] Vendedor");
            System.out.println("[7] Cliente");
            System.out.println("[8] Loja");
            System.out.println("[9] Sair");
            System.out.print("Escolha: ");
            opcao = scan.nextInt();

            switch (opcao) {
                case 1:
                    calc.calcPreco(scan);
                    break;

                case 2:
                    calc.troco(scan);
                    break;

                case 3:
                    calc.mostrarHistoricoVendas();
                    break;

                case 4:
                    calc.buscarVendasPorData(scan);
                    break;

                case 5:
                    calc.buscarVendasPorMes(scan);
                    break;

                case 6:
                    System.out.println("\nEscolha uma opção para VENDEDOR:");
                    System.out.println("[1] Apresentar-se");
                    System.out.println("[2] Calcular Média de Salários");
                    System.out.println("[3] Calcular Bônus");
                    System.out.print("Escolha: ");
                    int opcaoVendedor = scan.nextInt();
                    switch (opcaoVendedor) {
                        case 1:
                            for (Vendedor v : vendedores) v.apresentarse();
                            break;
                        case 2:
                            for (Vendedor v : vendedores) v.calcularMedia();
                            break;
                        case 3:
                            for (Vendedor v : vendedores) v.calcularBonus();
                            break;
                        default:
                            System.out.println("Opção inválida!");
                    }
                    break;

                case 7:
                    System.out.println("\nEscolha uma opção para CLIENTE:");
                    System.out.println("[1] Apresentar-se");
                    System.out.print("Escolha: ");
                    int opcaoCliente = scan.nextInt();
                    switch (opcaoCliente) {
                        case 1:
                            for (Cliente c : clientes) c.apresentarse();
                            break;
                        default:
                            System.out.println("Opção inválida!");
                    }
                    break;

                case 8:
                    System.out.println("\nEscolha uma opção para LOJA:");
                    System.out.println("[1] Apresentar-se");
                    System.out.println("[2] Quantidade de Clientes");
                    System.out.println("[3] Quantidade de Vendedores");
                    System.out.print("Escolha: ");
                    int opcaoLoja = scan.nextInt();
                    switch (opcaoLoja) {
                        case 1:
                            loja.apresentarse();
                            break;
                        case 2:
                            loja.contarClientes();
                            break;
                        case 3:
                            loja.contarVendedores();
                            break;
                        default:
                            System.out.println("Opção inválida!");
                    }
                    opcao = 0;
                    break;

                case 9:
                    System.out.println("Encerrando sistema...");
                    scan.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }

        scan.close();
    }
}