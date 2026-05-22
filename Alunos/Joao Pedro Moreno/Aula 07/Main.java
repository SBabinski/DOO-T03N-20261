import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static Scanner scan = new Scanner(System.in);
    static ArrayList<Pedido> historico = new ArrayList<>();
    static double totalCompra = 0;
    static Loja loja;

    public static void main(String[] args) {
        loja = new Loja("Loja de Plantas", "Plantas LTDA",
                "12.345.678/0001-90", new Endereco("São Paulo", "Centro", "Rua A"));
        verMenu();
    }

    // Parte visual do menu
    public static void verMenu() {
        int escolha;
        do {
            System.out.println("\n___________________________________");
            System.out.println("|--------------Menu---------------|");
            System.out.println("|1- Criar Pedido                  |");
            System.out.println("|2- Calcular Preço Total          |");
            System.out.println("|3- Calcular Troco                |");
            System.out.println("|4- Confirmar Pagamento           |");
            System.out.println("|5- Histórico Geral de Vendas     |");
            System.out.println("|6- Histórico Mensal de Vendas    |");
            System.out.println("|7- Loja                          |");
            System.out.println("|8- Clientes                      |");
            System.out.println("|9- Vendedores                    |");
            System.out.println("|10- Gerentes                     |");
            System.out.println("|11- Sair                         |");
            System.out.print("|Escolha: ");
            escolha = scan.nextInt();
            scan.nextLine();
            validarEscolha(escolha);
        } while (escolha != 11);
    }

    // Validação da escolha do menu
    public static void validarEscolha(int escolha) {
        switch (escolha) {
            case 1:
                criarPedido();
                break;
            case 2:
                calcularPrecoTotal();
                break;
            case 3:
                calcularTroco();
                break;
            case 4:
                confirmarPagamento();
                break;
            case 5:
                historicoVendas();
                break;
            case 6:
                historicoMensal();
                break;
            case 7:
                menuLoja();
                break;
            case 8:
                listarClientes();
                break;
            case 9:
                listarVendedores();
                break;
            case 10:
                listarGerentes();
                break;
            case 11:
                System.out.println("Obrigado, volte sempre!");
                break;
            default:
                System.out.println("Opção inválida, tente novamente.");
        }
    }

    // Método Criar Pedido
    public static void criarPedido() {
        ArrayList<Cliente> clientes = loja.getClientes();
        ArrayList<Vendedor> vendedores = loja.getVendedores();

        System.out.println("\n--- Clientes disponíveis ---");
        for (int i = 0; i < clientes.size(); i++)
            System.out.println((i + 1) + ". " + clientes.get(i).getNome());
        System.out.print("Escolha o cliente (número): ");
        int icli = scan.nextInt() - 1;
        scan.nextLine();

        System.out.println("\n--- Vendedores disponíveis ---");
        for (int i = 0; i < vendedores.size(); i++)
            System.out.println((i + 1) + ". " + vendedores.get(i).getNome());
        System.out.print("Escolha o vendedor (número): ");
        int ivend = scan.nextInt() - 1;
        scan.nextLine();

        System.out.print("\nQuantos dias até o vencimento da reserva? ");
        int dias = scan.nextInt();
        scan.nextLine();

        // Monta os itens do pedido
        ArrayList<Item> itens = new ArrayList<>();
        String continuar = "s";
        int idItem = 1;
        while (continuar.equalsIgnoreCase("s")) {
            System.out.print("Nome da planta: ");
            String nomeItem = scan.nextLine();
            System.out.print("Tipo: ");
            String tipo = scan.nextLine();
            System.out.print("Valor unitário: ");
            double valor = scan.nextDouble();
            System.out.print("Quantidade: ");
            int quant = scan.nextInt();
            scan.nextLine();
            itens.add(new Item(idItem++, nomeItem, tipo, valor, quant));
            System.out.print("Adicionar outro item? (s/n): ");
            continuar = scan.nextLine();
        }

        ProcessaPedido proc = new ProcessaPedido();
        Pedido pedido = proc.processar(clientes.get(icli), vendedores.get(ivend), loja, itens, dias);
        historico.add(pedido);
        totalCompra = pedido.calcularPrecoTotal();
    }

    // Método Calcular Preço Total 
    public static void calcularPrecoTotal() {
        if (historico.isEmpty()) {
            System.out.println("Nenhum pedido criado ainda. Use a opção 1 primeiro.");
            return;
        }
        Pedido ultimo = historico.get(historico.size() - 1);
        totalCompra = ultimo.calcularPrecoTotal();
        System.out.printf("Total do último pedido: R$ %.2f%n", totalCompra);
    }

    // Método Calcular Troco
    public static void calcularTroco() {
        if (totalCompra == 0) {
            System.out.println("Crie um pedido primeiro (opção 1).");
            return;
        }
        System.out.print("Valor pago pelo cliente: R$ ");
        double pago = scan.nextDouble();
        scan.nextLine();
        if (pago < totalCompra) {
            System.out.printf("Valor insuficiente! Faltam R$ %.2f%n", totalCompra - pago);
        } else {
            System.out.printf("Total da compra: R$ %.2f%n", totalCompra);
            System.out.printf("Troco: R$ %.2f%n", pago - totalCompra);
        }
    }

    // Método Confirmar Pagamento
    public static void confirmarPagamento() {
        if (historico.isEmpty()) {
            System.out.println("Nenhum pedido encontrado.");
            return;
        }
        Pedido ultimo = historico.get(historico.size() - 1);
        if (ultimo.isPago()) {
            System.out.println("Este pedido já foi pago em " + Date.formatar(ultimo.getDataPagamento()));
            return;
        }
        ProcessaPedido proc = new ProcessaPedido();
        proc.efetuarPagamento(ultimo);
    }

    // Método Histórico Geral
    public static void historicoVendas() {
        if (historico.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }
        System.out.println("\n=== HISTÓRICO GERAL DE VENDAS ===");
        for (Pedido p : historico)
            System.out.println(p);
    }

    // Método Histórico Mensal 
    public static void historicoMensal() {
        if (historico.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }
        int mesAtual = Date.hoje().getMonthValue();
        System.out.println("\n=== HISTÓRICO MENSAL ===");
        boolean achou = false;
        for (Pedido p : historico) {
            if (p.getDataCriacao().getMonthValue() == mesAtual) {
                System.out.println(p);
                achou = true;
            }
        }
        if (!achou)
            System.out.println("Nenhuma venda este mês.");
    }

    // Método Listar Loja
    public static void menuLoja() {
        loja.mostrarLoja();
        System.out.println("Total de clientes:  " + loja.contarClientes());
        System.out.println("Total de vendedores:" + loja.contarVendedores());
        System.out.println("Total de gerentes:  " + loja.contarGerentes());
    }

    // Método Listar Clientes
    public static void listarClientes() {
        System.out.println("\n=== CLIENTES ===");
        for (Cliente c : loja.getClientes())
            c.mostrarCliente();
    }

    // Método Listar Vendedores
    public static void listarVendedores() {
        System.out.println("\n=== VENDEDORES ===");
        for (Vendedor v : loja.getVendedores()) {
            v.mostrarVendedor();
            System.out.printf("  Média salarial: R$ %.2f | Bônus: R$ %.2f%n",
                    v.calcularMedia(), v.calcularBonus());
        }
    }

    // Método Listar Gerentes
    public static void listarGerentes() {
        System.out.println("\n=== GERENTES ===");
        for (Gerente g : loja.getGerentes()) {
            g.mostrarGerente();
            System.out.printf("  Média salarial: R$ %.2f | Bônus: R$ %.2f%n",
                    g.calcularMedia(), g.calcularBonus());
        }
    }
}
