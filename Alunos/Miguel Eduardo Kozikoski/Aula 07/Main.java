import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // ----- atributos da LOJA
        Endereco endLoja= new Endereco("PR" , "Cascavel " , "Neva" , "Loja Central" , 100);
        Loja listaLoja = new Loja("My Plant " , "213-5" ,"12.345.678/0001-99" , "Cascavel" , "Neva" , "Primeiro de maio" , endLoja);

        ArrayList<Vendas> listaVendas = new ArrayList<>();
        ArrayList<Cliente> listaCliente = new ArrayList<>();
        ArrayList<Cliente> listaVendedor = new ArrayList<>();
        ArrayList<Gerente> novoGerente = new ArrayList<>();
        ArrayList<Item> listaItem = new ArrayList<>();
        ArrayList<Item> carrinhoDoUsuario = new ArrayList<>();

        int Valor_total = 0;
        int opcao = 0; // para começar toda vez zerado
        do {
            System.out.println("===========================================");
            System.out.println("Bem-Vindo a nova fase da MY PLANT");
            System.out.println("===========================================");
            System.out.print("[1] - cadastro do novo gerente\n"+
                    "[2] - Cadastro um vendedor\n" +
                    "[3] - Cadastro de clente\n" +
                    "[4] - Cadastro de novo item \n"+
                    "[5] - Ver estoque LIMITADO\n"+
                    "[6] - Adicionar produtos ao carrinho\n" +
                    "[7] - Total do carrinho\n" +
                    "[8] - Ver vendas\n" +
                    "[9] - Apagar todas a vendas\n" +
                    "[10] - Mostra loja e clientes e vendedores\n" +
                    "[11] - Sair\n");
            System.out.println("===========================================");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    cadastroGerente(novoGerente , scanner);
                    break;
                case 2:
                    cadastroVendedor(listaLoja.vendedors, scanner);
                    System.out.println("DEBUG: Agora a loja tem " + listaLoja.vendedors.size() + " vendedores.");
                    break;
                case 3:
                    cadastroClientes(listaLoja.clientes, scanner);
                    System.out.println("DEBUG: Agora a loja tem " + listaLoja.clientes.size() + " clientes.");
                    break;
                case 4:
                    cadatroNovoitem(listaItem , scanner);
                    break;
                case 5:
                    System.out.println("========= Estoque da Loja  ============");
                    for (Item i : listaItem ){
                        i.apresentacaoProduto();
                    }
                    break;
                case 6:
                    adiconarItemAoCarrinho(listaItem,carrinhoDoUsuario, scanner, Valor_total);
                    break;
                case 7:
                    //arrumar
                    break;
                case 8:
                    visualizacaoVendas(listaVendas);
                    break;
                case 9:
                    apagarVendas(listaVendas);
                    break;
                case 10:
                    listaLoja.apresentacaoLoja();
                    listaLoja.contarClientes();
                    listaLoja.contarVendedores();
                    break;

            }
        } while (opcao != 11);
        scanner.close();
    }
    public static void cadastroGerente(ArrayList<Gerente> novoGerente ,Scanner sc){
        System.out.println("========= Cadastro do Gerente  ============");
        sc.nextLine();//fazer a limpeza de espaço em branco para iniciar a pergunta
        System.out.println("digite o nome do Gerente  : ");
        String nome = sc.nextLine();
        System.out.println("digite a Loja do Gerente atua : ");
        String loja = sc.nextLine();
        System.out.println("digite o Estado do Gerente");
        String estado = sc.nextLine();
        System.out.println("digite o cidade do Gerente : ");
        String cidade = sc.nextLine();
        System.out.println("digite o bairro do Gerente : ");
        String bairro = sc.nextLine();
        System.out.println("digite o rua do Gerente : ");
        String rua = sc.nextLine();
        System.out.println("se tiver digite um complemento : ");
        String complemento = sc.nextLine();
        System.out.println("digite o numero da casa do Gerente : ");
        int numero = sc.nextInt();
        System.out.println("digite a idade do Gerente : ");
        int idade = sc.nextInt();
        // 1- cria um novo endereço
        Endereco endereco = new Endereco(estado , cidade , bairro , complemento , numero);
        // 2- criar um novo gerente
        Gerente gerente = new Gerente(nome , loja , cidade , bairro , rua , idade , endereco);
        novoGerente.add(gerente);
        gerente.apresentacaoGerente();
        System.out.println("Média de Salario do Gerente(a)" + gerente.mediaSalario());
        System.out.println("Com a bonificação fica entre  : " + gerente.calculoBonus());
    }
    public static void cadastroVendedor(ArrayList<Vendedor> listaVendedor , Scanner sc) {
        // o construdor vendedor gera um novo funcionario e logo depois apresentamos ele no grid
        System.out.println("========= Cadastro de vendedor ============");
        sc.nextLine();//fazer a limpeza de espaço em branco para iniciar a pergunta
        System.out.println("digite o nome do vendedor : ");
        String nome = sc.nextLine();
        System.out.println("digite o Estado do vendedor : ");
        String estado = sc.nextLine();
        System.out.println("digite o cidade do vendedor : ");
        String cidade = sc.nextLine();
        System.out.println("digite o bairro do vendedor : ");
        String bairro = sc.nextLine();
        System.out.println("digite o rua do vendedor : ");
        String rua = sc.nextLine();
        System.out.println("se tiver digite um complemento : ");
        String complementos = sc.nextLine();
        System.out.println("digite o numero da casa do vendedor : ");
        int numero = sc.nextInt();
        System.out.println("digite a idade do vendedor : ");
        int idade = sc.nextInt();
        // 1- cria um novo endereço
        Endereco endereco = new Endereco(estado , cidade , bairro , complementos , numero);
        // 2 - cria um novo vendedor
        Vendedor vendedor = new Vendedor(nome , cidade , bairro , rua , idade , endereco);
        listaVendedor.add(vendedor);
        vendedor.Apresentase(); // <- para chamar apenas com construdor do ArrayList
        System.out.println("Média de Salario do vendedor(a)" + vendedor.mediaSalrio());
        System.out.println("Com a bonificação fica entre  : " + vendedor.calculoBonus());
    }

    public static void cadastroClientes(ArrayList<Cliente> listaCliente, Scanner sc) {
        System.out.println("========= Cadastro de clientes ============");
        sc.nextLine();//fazer a limpeza de espaço em branco para iniciar a pergunta
        System.out.println("digite o nome do clinte : ");
        String nome = sc.nextLine();
        System.out.println("digite o Estadp do clinte : ");
        String estado = sc.nextLine();
        System.out.println("digite o cidade do clinte : ");
        String cidade = sc.nextLine();
        System.out.println("digite o bairro do clinte : ");
        String bairro = sc.nextLine();
        System.out.println("digite o rua do clinte : ");
        String rua = sc.nextLine();
        System.out.println("digite o complementos do clinte : ");
        String complemtos = sc.nextLine();
        System.out.println("digite o numero da casa do clinte : ");
        int numero = sc.nextInt();
        System.out.println("digite a idade do clinte : ");
        int idade = sc.nextInt();
        // 1- cria um novo endereço
        Endereco endereco = new Endereco(estado , cidade , bairro , complemtos , numero);
        //Criar uma nova class
        Cliente clientes = new Cliente(nome, cidade, bairro, rua, idade , endereco);
        // add a uma nova class
        listaCliente.add(clientes);
        // apresentação do cliente
        clientes.aprensetaseClinte();
    }
    public static void cadatroNovoitem(ArrayList<Item> listaItem , Scanner sc){
        System.out.println("========= Cadastro de Item ============");
        sc.nextLine();
        System.out.println("Digite o ID do produto");
        int ID = sc.nextInt();
        sc.nextLine();
        System.out.println("Digite o nome do produto");
        String nome = sc.nextLine();
        System.out.println("Tipo (Ex: Planta, Vaso, Ferramenta):");
        String tipo = sc.nextLine();
        System.out.println("Valor:");
        double valor = sc.nextDouble();
        Item item = new Item(nome , tipo , valor , ID);
        listaItem.add(item);
    }
    public static void adiconarItemAoCarrinho(ArrayList<Item> listaItem, ArrayList<Item> carrinhoDoUsuario, Scanner sc, int Valor_total) {
        System.out.println("========= Caixa  ============");
        double totalDaVenda = 0;
        int idDigitado = -1;
        while (idDigitado != 0) {
            System.out.println("Digite o ID do produto (ou 0 para finalizar): ");
            idDigitado = sc.nextInt();
            sc.nextLine(); // Limpa o buffer

            if (idDigitado != 0) {
                // Lógica para buscar o item no estoque pelo ID
                boolean encontrado = false;
                for (Item item : listaItem) {
                    if (item.ID == idDigitado) {
                        totalDaVenda += item.valor;
                        carrinhoDoUsuario.add(item);
                        System.out.println("✅ Adicionado: " + item.nome + " - R$ " + item.valor);
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) {
                    System.out.println("❌ Produto não encontrado!");
                }
            }

            System.out.println("\n-------------------------------------");
        }
    }

    public static void calcularValorTotalDoCarrinho(Pedidos meuPedido) {
        System.out.println("\n--- RECIBO DO PEDIDO ---");
        double total = meuPedido.calculoValorTotal();
        System.out.println("Total a pagar :" + total);
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
