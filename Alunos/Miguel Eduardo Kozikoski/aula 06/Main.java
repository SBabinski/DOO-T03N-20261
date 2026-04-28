import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Loja listaLoja = new Loja("My Plant " , "213-5" ,"12.345.678/0001-99" , "Cascavel" , "Neva" , "Primeiro de maio" );
        ArrayList<Vendas> listaVendas = new ArrayList<>();
        ArrayList<Cliente> listaCliente = new ArrayList<>();
        ArrayList<Cliente> listaVendedor = new ArrayList<>();
        int Valor_total = 0;
        int opcao = 0; // para começar toda vez zerado
        do {
            System.out.println("===========================================");
            System.out.println("Bem-Vindo a nova fase da MY PLANT");
            System.out.println("===========================================");
            System.out.print("[1] - Cadastro um vendedor\n" +
                    "[2] - Cadastro de clente\n" +
                    "[3] - Realizar nova venda\n" +
                    "[4] - Calcular troco\n" +
                    "[5] - Ver vendas\n" +
                    "[6] - Apagar todas a vendas\n" +
                    "[7] - Mostra loja e clientes e vendedores\n" +
                    "[8] - Sair\n");
            System.out.println("===========================================");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    cadastroVendedor(listaLoja.vendedors, scanner);
                    System.out.println("DEBUG: Agora a loja tem " + listaLoja.vendedors.size() + " vendedores.");
                    break;
                case 2:
                    cadastroClientes(listaLoja.clientes, scanner);
                    System.out.println("DEBUG: Agora a loja tem " + listaLoja.clientes.size() + " clientes.");
                    break;
                case 3:
                    vendasPlantas(listaVendas, scanner, Valor_total);
                    break;
                case 4:
                    calculoTroco(scanner, Valor_total);
                    break;
                case 5:
                    visualizacaoVendas(listaVendas);
                    break;
                case 6:
                    apagarVendas(listaVendas);
                    break;
                case 7:
                    listaLoja.apresentacaoLoja();
                    listaLoja.contarClientes();
                    listaLoja.contarVendedores();
                    break;

            }
        } while (opcao != 8);
        scanner.close();
    }

    public static void cadastroVendedor(ArrayList<Vendedor> listaVendedor , Scanner sc) {
        // o construdor vendedor gera um novo funcionario e logo depois apresentamos ele no grid
        System.out.println("========= Cadastro de vendedor ============");
        sc.nextLine();//fazer a limpeza de espaço em branco para iniciar a pergunta
        System.out.println("digite o nome do vendedor : ");
        String nome = sc.nextLine();
        System.out.println("digite o cidade do vendedor : ");
        String cidade = sc.nextLine();
        System.out.println("digite o bairro do vendedor : ");
        String bairro = sc.nextLine();
        System.out.println("digite o rua do vendedor : ");
        String rua = sc.nextLine();
        System.out.println("digite a idade do vendedor : ");
        int idade = sc.nextInt();
        Vendedor vendedor = new Vendedor(nome , cidade , bairro , rua , idade);
        listaVendedor.add(vendedor);
        vendedor.Apresentase(); // <- para chamar apenas com construdor do ArrayList
        vendedor.calculoBonus();
        System.out.println("Média de Salario do vendedor(a)" + vendedor.mediaSalrio());
        System.out.println("Com a bonificação fica entre  : " + vendedor.calculoBonus());
    }

    public static void cadastroClientes(ArrayList<Cliente> listaCliente, Scanner sc) {
        System.out.println("========= Cadastro de clientes ============");
        sc.nextLine();//fazer a limpeza de espaço em branco para iniciar a pergunta
        System.out.println("digite o nome do clinte : ");
        String nome = sc.nextLine();
        System.out.println("digite o cidade do clinte : ");
        String cidade = sc.nextLine();
        System.out.println("digite o bairro do clinte : ");
        String bairro = sc.nextLine();
        System.out.println("digite o rua do clinte : ");
        String rua = sc.nextLine();
        System.out.println("digite a idade do clinte : ");
        int idade = sc.nextInt();
        //Criar uma nova class
        Cliente clientes = new Cliente(nome, cidade, bairro, rua, idade);
        // add a uma nova class
        listaCliente.add(clientes);
        // apresentação do cliente
        clientes.aprensetaseClinte();
    }

    public static void vendasPlantas(ArrayList<Vendas> listaVendas, Scanner sc, int Valor_total) {
        System.out.println("Digite quantas plantas você pegou:");
        int totalplantas = sc.nextInt();
        System.out.println("Digite o valor de cada planta:");
        int valorplantas = sc.nextInt();
        Valor_total = Calculos.somar(valorplantas, totalplantas);
        Vendas venda = new Vendas(valorplantas, totalplantas, 0, Valor_total);
        listaVendas.add(venda);
        System.out.print("o valor total é " + Valor_total);
        System.out.println("\n-------------------------------------");
    }

    public static void calculoTroco(Scanner sc, int Valor_total) {
        System.out.println("Digite o valor que você tem, para calcular seu troco");
        int Valor_pago = sc.nextInt();
        int resulTroco = Calculos.resultado_troco(Valor_pago, Valor_total);
        System.out.println("o seu troco foi de " + resulTroco);
        System.out.println("\n-------------------------------------");
    }

    public static void visualizacaoVendas(ArrayList<Vendas> listaVendas) {
        if (listaVendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
        }
        for (Vendas v : listaVendas) {
            System.out.println("\nValor das plantas: " + v.valorplantas);
            System.out.println("Quantidade de planta pega: " + v.totalplantas);
            System.out.println("Desconto: " + v.desconto);
            System.out.println("Total de todos os produtos:R$ " + v.total);
            System.out.println("\n-------------------------------------");
        }
    }

    public static void apagarVendas(ArrayList<Vendas> listaVendas) {
        listaVendas.clear();
        System.out.println("Todas as vendas foram apagadas!");
        System.out.println("\n-------------------------------------");
    }
}
