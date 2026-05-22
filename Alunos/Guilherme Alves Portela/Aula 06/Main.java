import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static List<Venda> vendas = new ArrayList<>();
    private static Loja lojadonagabi = new Loja("My Plant - Dona Gabrielinha", "My Plant LTDA", "10.100.100/0001-11", "Tocantins", "São Mateus", "Rua Cataratas, 6");

    public static void main(String[] args){
        registrosTeste();
        Scanner sc = new Scanner(System.in);
        menuInicialController(sc);
        sc.close();
    }

    // Menus de exibição

    public static void menuInicialController(Scanner sc){
        int opcao = 0;
        while (opcao != 5){
            System.out.println("\n*** Sistema Loja da Gabrielinha ***");
            System.out.println("[1] - Calcular Venda");
            System.out.println("[2] - Registrar venda");
            System.out.println("[3] - Consultar Vendas");
            System.out.println("[4] - Gerenciar Loja");
            System.out.println("[5] - Sair");
            System.out.print("Escolha: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao){
                case 1 -> calcularVenda(sc);
                case 2 -> registrarVenda(sc);
                case 3 -> menuConsultasController(sc);
                case 4 -> menuLojaController(sc);
                case 5 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

     public static void menuConsultasController(Scanner sc){
        int opcao = 0;
        while (opcao != 6){
            System.out.println("\n*** Consultar por: ***");
            System.out.println("[1] - Data");
            System.out.println("[2] - Mês");
            System.out.println("[3] - Todas as vendas");
            System.out.println("[4] - Retornar ao Menu inicial");
            System.out.print("Escolha: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao){
                case 1 -> getVendasPorData(sc);
                case 2 -> getVendasPorMes(sc);
                case 3 -> getVendas();
                case 4 ->  System.out.println("Retornando ao menu inicial...");
                default -> System.out.println("Opção inválida!");
            }
        }
     }
    
    public static void menuLojaController(Scanner sc){
        int opcao = 0;
        while (opcao != 8){
            System.out.println("\n*** Sistema Loja da Gabrielinha ***");
            System.out.println("[1] - Listar vendedores");
            System.out.println("[2] - Cadastrar vendedor");
            System.out.println("[3] - Pagar Vendedor");
            System.out.println("[4] - Listar clientes");
            System.out.println("[5] - Cadastrar clientes");
            System.out.println("[6] - Editar dados da loja");
            System.out.println("[7] - Exibir dados da loja");
            System.out.println("[8] - Voltar");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao){
                case 1 -> listarVendedores();
                case 2 -> cadastrarVendedor(sc);
                case 3 -> pagarVendedor(sc);
                case 4 -> listarClientes();
                case 5 -> cadastrarCliente(sc);
                case 6 -> editarDadosLoja(sc);
                case 7 -> exibirInformacoesLoja();
                case 8 ->  System.out.println("Retornando ao menu inicial...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    // Métodos da calculadora
    
    public static double calcularPreco(int qtdPlantas, double precoPlanta){return qtdPlantas * precoPlanta;}
    
    public static double calcularTroco(double valorPagoCliente, double valorTotalCompra){return valorPagoCliente - valorTotalCompra;}
    
    public static double calcularDesconto(double valorCompra){return valorCompra * 0.05f;}
    
    // Métodos que gerenciam vendas
    
    public static void calcularVenda(Scanner sc){
        System.out.print("Insira quantidade de plantas: ");
        int qtd = sc.nextInt();
        sc.nextLine();

        System.out.print("Insira o valor da planta: ");
        double valorPlanta = sc.nextDouble();
        
        if (qtd <= 0 || valorPlanta <= 0){System.out.println("Valor ou quantidade não podem ser iguais ou menores que zero!"); return;}

        double valorVenda =  calcularPreco(qtd, valorPlanta);
        double valorDesconto = (qtd > 10) ? calcularDesconto(valorVenda) : 0;
        double valorTotal = valorVenda - valorDesconto;
            
        System.out.println("Preço Venda(Sem desconto): R$ " + valorVenda);
        System.out.println("Desconto:): R$ " + valorDesconto);
        System.out.println("Valor a receber: R$ " + valorTotal);

        System.out.print("Valor pago pelo cliente: ");
        double valorPagoCliente = sc.nextDouble();
        sc.nextLine();

        // Valida valor da compra
        if (valorTotal <= 0){
            System.out.println("Erro: Valor da venda deve ser positivo.");
            return;
        }

        var valorTroco = calcularTroco(valorPagoCliente, valorVenda);

        System.out.println("Valor do troco: R$" + valorTroco);

    }
    
    public static void registrarVenda(Scanner sc){
        System.out.print("Quantidade de Plantas: ");
        int qtd = sc.nextInt();
        System.out.print("Preço Unitário: ");
        double preco = sc.nextDouble();

        double valorTotal = calcularPreco(qtd, preco);
        double valorDesconto = (qtd > 10) ? calcularDesconto(valorTotal) : 0;
        valorTotal -= valorDesconto;

        try {
            Venda novaVenda = new Venda(qtd, valorTotal, valorDesconto);
            vendas.add(novaVenda);
            
            // Formatador para a data
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            // Atualizamos o printf para incluir o %s da data
            System.out.printf("Venda salva em %s! Total: R$ %.2f (desconto: R$ %.2f)%n", 
            novaVenda.getData().format(fmt), valorTotal, valorDesconto);
                
        } catch (Exception e){
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    // Métodos para consultar vendas

    public static void getVendas(){
        System.out.println("\n*** Histórico de Vendas ***");
        vendas.forEach(System.out::println);
    }

    public static void getVendasPorMes(Scanner sc){
        System.out.println("*** Busca por Mês  ***");
        System.out.print("Digite o mês (1-12): ");
        int mes = sc.nextInt();
        sc.nextLine();

        double somaVendas = 0;
        int contador = 0;

        
        System.out.println("\nResultados para mês " + mes + ":");
        for (Venda v : vendas){
            if (v.getMes() == mes){
                System.out.println(v);
                somaVendas += v.getValor();
                contador++;
            }
        }

        if (contador == 0){
            System.out.println("Nenhuma venda encontrada para este mês.");
        } else {
            System.out.printf("Total de vendas no mês $d: %d | Faturamento: R$ %.2f%n", mes, contador, somaVendas);
        }
    }

    public static void getVendasPorData(Scanner sc){
        System.out.println("*** Busca por Data ***");
        System.out.print("Digite o dia (1-31): ");
        int dia = sc.nextInt();
        System.out.print("Digite o mês (1-12): ");
        int mes = sc.nextInt();
        int ano = LocalDate.now().getYear(); 

        LocalDate dataBusca = LocalDate.of(ano, mes, dia);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        double somaVendas = 0;
        int contador = 0;

        System.out.println("\nResultados para " + dataBusca.format(fmt) + ":");
        for (Venda v : vendas){
            if (v.getData().equals(dataBusca)){
                System.out.println(v);
                somaVendas += v.getValor();
                contador++;
            }
        }

        if (contador == 0){
            System.out.println("Nenhuma venda encontrada para esta data.");
        } else {
            System.out.printf("Total de vendas no dia: %d | Faturamento: R$ %.2f%n", contador, somaVendas);
        }
    }

    // Métodos para gerenciar loja

    public static void listarVendedores() {
        // mensagem de aviso caso não exista vendedor cadastrado
        if (lojadonagabi.getListaDeVendedores().size() == 0) {
            System.out.println("Nenhum vendedor cadastrado.");
            return;
        }

        int idVendedor = 1;
        for (var vendedor : lojadonagabi.getListaDeVendedores()) {
            System.out.print(idVendedor + ": ");
            vendedor.apresentarSe();
        }
    }

    public static void cadastrarVendedor(Scanner sc) {
        System.out.println("*** Cadastro de Vendedor ***");

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Idade: ");
        int idade = sc.nextInt();
        sc.nextLine();

        System.out.print("Cidade: ");
        String cidade = sc.nextLine();

        System.out.print("Bairro: ");
        String bairro = sc.nextLine();

        System.out.print("Rua: ");
        String rua = sc.nextLine();

        System.out.print("Salario base: ");
        float salarioBase = sc.nextFloat();
        sc.nextLine();

        Vendedor novoVendedor = new Vendedor(nome, idade, lojadonagabi, bairro, cidade, rua, salarioBase);
        novoVendedor.setLoja(lojadonagabi);
        lojadonagabi.cadastrarVendedor(novoVendedor);

        System.out.println("Vendedor cadastrado com sucesso!");
        System.out.println("----------------------------");
    }

    public static void pagarVendedor(Scanner sc) {
        System.out.print("Digite o id do vendedor: ");
        int idVendedor = sc.nextInt();
        sc.nextLine();

        if (lojadonagabi.getListaDeVendedores().size() <= idVendedor) {
            System.out.println("Vendedor não encontrado.");
            return;
        }

        Vendedor vendedorEncontrado = lojadonagabi.getListaDeVendedores().get(idVendedor - 1);

        System.out.print("Deseja adicionar bônus? (s/n): ");
        String opcaoBonus = sc.nextLine();

        double salarioFinal = vendedorEncontrado.getSalarioBase();

        // adiciona bonus ao salário
        if (opcaoBonus.equalsIgnoreCase("s")) {
            salarioFinal += vendedorEncontrado.calcularBonus();
        }

        vendedorEncontrado.pagarSalario(salarioFinal);

        System.out.println("Pagamento de R$" + salarioFinal + " realizado para " + vendedorEncontrado.getNome());
    }

    public static void listarClientes() {
        // mensagem de aviso caso não exista cliente cadastrado
        if (lojadonagabi.getListaDeClientes().size() == 0) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        for (var clienteDaLoja : lojadonagabi.getListaDeClientes()) {
            clienteDaLoja.apresentarSe();
        }
    }

    public static void cadastrarCliente(Scanner sc) {
        System.out.println("*** Cadastro de Cliente ***");

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Idade: ");
        int idade = sc.nextInt();
        sc.nextLine();

        System.out.print("Cidade: ");
        String cidade = sc.nextLine();

        System.out.print("Bairro: ");
        String bairro = sc.nextLine();

        System.out.print("Rua: ");
        String rua = sc.nextLine();

        Cliente novoCliente = new Cliente(nome, idade, cidade, bairro, rua);
        lojadonagabi.cadastrarCliente(novoCliente);

        System.out.println("Cliente cadastrado com sucesso!");
        System.out.println("---------------------------");
    }

    public static void editarDadosLoja(Scanner sc) {
        System.out.println("*** Edição de Dados da Loja ***");

        System.out.print("Novo Nome Fantasia: ");
        String nomeFantasia = sc.nextLine();
        lojadonagabi.setNomeFantasia(nomeFantasia);

        System.out.print("Nova Razão Social: ");
        String razaoSocial = sc.nextLine();
        lojadonagabi.setRazaoSocial(razaoSocial);

        System.out.print("Novo CNPJ: ");
        String cnpj = sc.nextLine();
        lojadonagabi.setCnpj(cnpj);

        System.out.print("Nova Cidade: ");
        String cidade = sc.nextLine();
        lojadonagabi.setCidade(cidade);

        System.out.print("Novo Bairro: ");
        String bairro = sc.nextLine();
        lojadonagabi.setBairro(bairro);

        System.out.print("Nova Rua: ");
        String rua = sc.nextLine();
        lojadonagabi.setRua(rua);

        System.out.println("Dados da loja atualizados com sucesso!");
        System.out.println("-------------------------------");
    }

    public static void exibirInformacoesLoja() {
        System.out.println("*** Informações da Loja ***");
        lojadonagabi.apresentarSe();
        lojadonagabi.contarClientes();
        lojadonagabi.contarVendedores();
        System.out.println("---------------------------");
    }

    //Método que cria registros para testes
    public static void registrosTeste(){
        // Vendedores
        Vendedor v1 = new Vendedor("Joana", 40, lojadonagabi, "Centro", "Tocantins", "Rua 2",2500
        );
        Vendedor v2 = new Vendedor("Alberto", 23, lojadonagabi, "Ouro Verde", "Tocantins","Rua 5", 2800);

        lojadonagabi.cadastrarVendedor(v1);
        lojadonagabi.cadastrarVendedor(v2);

        // Clientes
        Cliente c1 = new Cliente("Fernando Costa", 45, "São José", "Kobrasol", "Rua Koesa");
        Cliente c2 = new Cliente("Roberto Lima", 52, "Florianópolis", "Campeche", "Av. Pequeno Príncipe");
        Cliente c3 = new Cliente("Carla Mendes", 33, "Biguaçu", "Centro", "Rua Sete de Setembro");

        lojadonagabi.cadastrarCliente(c1);
        lojadonagabi.cadastrarCliente(c2);
        lojadonagabi.cadastrarCliente(c3);
        
        // Vendas Fictícias
        vendas.add (new Venda(5, 150, 0));
        vendas.add(new Venda(20, 540, 60));
        vendas.add(new Venda(1, 45, 0f));

        // Pagamentos fictícios
        v1.pagarSalario(2500);
        v1.pagarSalario(2700);
        v2.pagarSalario(2800);
        v2.pagarSalario(3100);
    }
}