import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Calendar;

public class Main {

    private static List<Venda> vendas = new ArrayList<>();
    private static Loja lojadonagabi = new Loja("My Plant - Dona Gabrielinha", "My Plant LTDA", "10.100.100/0001-11", "Tocantins", "São Mateus", "Rua Cataratas, 6");
    private static ProcessaPedido processaPedido = new ProcessaPedido();

    public static void main(String[] args){
        registrosTeste();
        Scanner sc = new Scanner(System.in);
        menuInicialController(sc);
        sc.close();
    }

    // Menus de exibição

    public static void menuInicialController(Scanner sc){
        int opcao = 0;
        while (opcao != 4){
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║    Sistema Loja da Gabrielinha         ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║ [1] - Vendas                           ║");
            System.out.println("║ [2] - Consultas                        ║");
            System.out.println("║ [3] - Gerenciar Loja                   ║");
            System.out.println("║ [4] - Sair                             ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Escolha uma opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao){
                case 1 -> menuVendasController(sc);
                case 2 -> menuConsultasController(sc);
                case 3 -> menuLojaController(sc);
                case 4 -> System.out.println("Encerrando aplicação...");
                default -> System.out.println("❌ Opção inválida! Tente novamente.");
            }
        }
    }

    public static void menuVendasController(Scanner sc){
        int opcao = 0;
        while (opcao != 3){
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║         Menu de Vendas                 ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║ [1] - Calcular Venda                   ║");
            System.out.println("║ [2] - Registrar Venda                  ║");
            System.out.println("║ [3] - Voltar                           ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Escolha uma opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao){
                case 1 -> calcularVenda(sc);
                case 2 -> registrarVenda(sc);
                case 3 -> System.out.println("↩️  Retornando ao menu principal...");
                default -> System.out.println("❌ Opção inválida! Tente novamente.");
            }
        }
    }

     public static void menuConsultasController(Scanner sc){
        int opcao = 0;
        while (opcao != 4){
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║       Menu de Consultas de Vendas      ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║ [1] - Buscar por Data                  ║");
            System.out.println("║ [2] - Buscar por Mês                   ║");
            System.out.println("║ [3] - Ver Todas as Vendas              ║");
            System.out.println("║ [4] - Voltar                           ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Escolha uma opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao){
                case 1 -> getVendasPorData(sc);
                case 2 -> getVendasPorMes(sc);
                case 3 -> getVendas();
                case 4 -> System.out.println("↩️  Retornando ao menu principal...");
                default -> System.out.println("❌ Opção inválida! Tente novamente.");
            }
        }
     }
    
    public static void menuLojaController(Scanner sc){
        int opcao = 0;
        while (opcao != 8){
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║      Menu de Gerenciamento da Loja     ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║ [1] - Gerenciar Vendedores e Gerentes  ║");
            System.out.println("║ [2] - Gerenciar Clientes               ║");
            System.out.println("║ [3] - Pagar Funcionário                ║");
            System.out.println("║ [4] - Gerenciar Pedidos                ║");
            System.out.println("║ [5] - Editar Dados da Loja             ║");
            System.out.println("║ [6] - Exibir Informações da Loja       ║");
            System.out.println("║ [7] - Listar Pedidos Processados       ║");
            System.out.println("║ [8] - Voltar                           ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Escolha uma opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao){
                case 1 -> menuVendedoresController(sc);
                case 2 -> menuClientesController(sc);
                case 3 -> pagarFuncionario(sc);
                case 4 -> menuPedidosController(sc);
                case 5 -> editarDadosLoja(sc);
                case 6 -> exibirInformacoesLoja();
                case 7 -> processaPedido.listarPedidos();
                case 8 -> System.out.println("↩️  Retornando ao menu principal...");
                default -> System.out.println("❌ Opção inválida! Tente novamente.");
            }
        }
    }

    public static void menuVendedoresController(Scanner sc){
        int opcao = 0;
        while (opcao != 5){
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║    Menu de Gerenciamento de Vendedores ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║ [1] - Listar Vendedores                ║");
            System.out.println("║ [2] - Cadastrar Vendedor               ║");
            System.out.println("║ [3] - Mostrar Informações do Gerente   ║");
            System.out.println("║ [4] - Cadastrar Gerente                ║");
            System.out.println("║ [5] - Voltar                           ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Escolha uma opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao){
                case 1 -> listarVendedores();
                case 2 -> cadastrarVendedor(sc);
                case 3 -> exibirInformacoesGerente();
                case 4 -> cadastrarGerente(sc);
                case 5 -> System.out.println("↩️  Retornando ao menu anterior...");
                default -> System.out.println("❌ Opção inválida! Tente novamente.");
            }
        }
    }

    public static void menuClientesController(Scanner sc){
        int opcao = 0;
        while (opcao != 4){
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║    Menu de Gerenciamento de Clientes   ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║ [1] - Listar Clientes                  ║");
            System.out.println("║ [2] - Cadastrar Cliente                ║");
            System.out.println("║ [3] - Voltar                           ║");
            System.out.println("║ [4] - Voltar                           ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Escolha uma opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao){
                case 1 -> listarClientes();
                case 2 -> cadastrarCliente(sc);
                case 3, 4 -> System.out.println("↩️  Retornando ao menu anterior...");
                default -> System.out.println("❌ Opção inválida! Tente novamente.");
            }
        }
    }

    public static void menuPedidosController(Scanner sc){
        int opcao = 0;
        while (opcao != 2){
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║    Menu de Gerenciamento de Pedidos    ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║ [1] - Criar Novo Pedido                ║");
            System.out.println("║ [2] - Voltar                           ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Escolha uma opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao){
                case 1 -> criarPedido(sc);
                case 2 -> System.out.println("↩️  Retornando ao menu anterior...");
                default -> System.out.println("❌ Opção inválida! Tente novamente.");
            }
        }
    }

    // Métodos da calculadora
    
    public static double calcularPreco(int qtdPlantas, double precoPlanta){return qtdPlantas * precoPlanta;}
    
    public static double calcularTroco(double valorPagoCliente, double valorTotalCompra){return valorPagoCliente - valorTotalCompra;}
    
    public static double calcularDesconto(double valorCompra){return valorCompra * 0.05f;}
    
    // Métodos que gerenciam vendas
    
    public static void calcularVenda(Scanner sc){
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       Calcular Venda de Plantas        ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        System.out.print("Insira quantidade de plantas: ");
        int qtd = sc.nextInt();
        sc.nextLine();

        System.out.print("Insira o valor da planta (R$): ");
        double valorPlanta = sc.nextDouble();
        
        if (qtd <= 0 || valorPlanta <= 0){
            System.out.println("❌ Valor ou quantidade não podem ser iguais ou menores que zero!");
            return;
        }

        double valorVenda =  calcularPreco(qtd, valorPlanta);
        double valorDesconto = (qtd > 10) ? calcularDesconto(valorVenda) : 0;
        double valorTotal = valorVenda - valorDesconto;
            
        System.out.println("\n--- Resumo da Venda ---");
        System.out.printf("Preço Venda (Sem desconto): R$ %.2f%n", valorVenda);
        System.out.printf("Desconto (5%%): R$ %.2f%n", valorDesconto);
        System.out.printf("Valor a receber: R$ %.2f%n", valorTotal);

        System.out.print("\nValor pago pelo cliente (R$): ");
        double valorPagoCliente = sc.nextDouble();
        sc.nextLine();

        if (valorTotal <= 0){
            System.out.println("❌ Erro: Valor da venda deve ser positivo.");
            return;
        }

        var valorTroco = calcularTroco(valorPagoCliente, valorVenda);
        System.out.printf("💰 Valor do troco: R$ %.2f%n", valorTroco);
    }
    
    public static void registrarVenda(Scanner sc){
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║        Registrar Nova Venda            ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        System.out.print("Quantidade de Plantas: ");
        int qtd = sc.nextInt();
        System.out.print("Preço Unitário (R$): ");
        double preco = sc.nextDouble();

        if (qtd <= 0 || preco <= 0){
            System.out.println("❌ Valor ou quantidade inválidos!");
            return;
        }

        double valorTotal = calcularPreco(qtd, preco);
        double valorDesconto = (qtd > 10) ? calcularDesconto(valorTotal) : 0;
        valorTotal -= valorDesconto;

        try {
            Venda novaVenda = new Venda(qtd, valorTotal, valorDesconto);
            vendas.add(novaVenda);
            
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            System.out.printf("✅ Venda salva em %s! Total: R$ %.2f (desconto: R$ %.2f)%n", 
            novaVenda.getData().format(fmt), valorTotal, valorDesconto);
                
        } catch (Exception e){
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }
    
    // Métodos para consultar vendas

    public static void getVendas(){
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      Histórico Completo de Vendas      ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        if (vendas.isEmpty()) {
            System.out.println("❌ Nenhuma venda registrada.");
            return;
        }
        
        vendas.forEach(System.out::println);
    }

    public static void getVendasPorMes(Scanner sc){
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       Buscar Vendas por Mês            ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        System.out.print("Digite o mês (1-12): ");
        int mes = sc.nextInt();
        sc.nextLine();

        if (mes < 1 || mes > 12) {
            System.out.println("❌ Mês inválido!");
            return;
        }

        double somaVendas = 0;
        int contador = 0;

        System.out.println("\n--- Resultados para o mês " + mes + " ---");
        for (Venda v : vendas){
            if (v.getMes() == mes){
                System.out.println(v);
                somaVendas += v.getValor();
                contador++;
            }
        }

        if (contador == 0){
            System.out.println("❌ Nenhuma venda encontrada para este mês.");
        } else {
            System.out.printf("✅ Total de vendas: %d | Faturamento: R$ %.2f%n", contador, somaVendas);
        }
    }

    public static void getVendasPorData(Scanner sc){
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       Buscar Vendas por Data           ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        System.out.print("Digite o dia (1-31): ");
        int dia = sc.nextInt();
        System.out.print("Digite o mês (1-12): ");
        int mes = sc.nextInt();
        int ano = LocalDate.now().getYear(); 

        if (dia < 1 || dia > 31 || mes < 1 || mes > 12) {
            System.out.println("❌ Data inválida!");
            return;
        }

        LocalDate dataBusca = LocalDate.of(ano, mes, dia);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        double somaVendas = 0;
        int contador = 0;

        System.out.println("\n--- Resultados para " + dataBusca.format(fmt) + " ---");
        for (Venda v : vendas){
            if (v.getData().equals(dataBusca)){
                System.out.println(v);
                somaVendas += v.getValor();
                contador++;
            }
        }

        if (contador == 0){
            System.out.println("❌ Nenhuma venda encontrada para esta data.");
        } else {
            System.out.printf("✅ Total de vendas: %d | Faturamento: R$ %.2f%n", contador, somaVendas);
        }
    }

    // Métodos para gerenciar loja

    public static void listarVendedores() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         Lista de Vendedores            ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        if (lojadonagabi.getListaDeVendedores().isEmpty()) {
            System.out.println("❌ Nenhum vendedor cadastrado.");
            return;
        }

        int id = 1;
        for (Vendedor vendedor : lojadonagabi.getListaDeVendedores()) {
            System.out.print("[" + id + "] ");
            vendedor.apresentarSe();
            id++;
        }
    }

    public static void cadastrarVendedor(Scanner sc) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      Cadastro de Novo Vendedor         ║");
        System.out.println("╚════════════════════════════════════════╝");

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Idade: ");
        int idade = sc.nextInt();
        sc.nextLine();

        System.out.print("Estado: ");
        String estado = sc.nextLine();

        System.out.print("Cidade: ");
        String cidade = sc.nextLine();

        System.out.print("Bairro: ");
        String bairro = sc.nextLine();

        System.out.print("Rua: ");
        String rua = sc.nextLine();

        System.out.print("Número: ");
        int numero = sc.nextInt();
        sc.nextLine();

        System.out.print("Complemento: ");
        String complemento = sc.nextLine();

        System.out.print("Salário Base: R$ ");
        double salarioBase = sc.nextDouble();
        sc.nextLine();

        Endereco endereco = new Endereco(estado, cidade, bairro, rua, numero, complemento);
        Vendedor novoVendedor = new Vendedor(nome, idade, lojadonagabi, endereco, salarioBase);
        novoVendedor.setLoja(lojadonagabi);
        lojadonagabi.cadastrarVendedor(novoVendedor);

        System.out.println("✅ Vendedor cadastrado com sucesso!");
    }

    public static void pagarVendedor(Scanner sc) {
        if (lojadonagabi.getListaDeVendedores().isEmpty()) {
            System.out.println("❌ Nenhum vendedor cadastrado.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         Pagamento de Vendedor          ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        listarVendedores();
        System.out.print("\nDigite o ID do vendedor: ");
        int idVendedor = sc.nextInt();
        sc.nextLine();

        if (idVendedor <= 0 || idVendedor > lojadonagabi.getListaDeVendedores().size()) {
            System.out.println("❌ Vendedor não encontrado.");
            return;
        }

        Vendedor vendedorEncontrado = lojadonagabi.getListaDeVendedores().get(idVendedor - 1);

        System.out.print("Deseja adicionar bônus? (s/n): ");
        String opcaoBonus = sc.nextLine();

        double salarioFinal = vendedorEncontrado.getSalarioBase();

        if (opcaoBonus.equalsIgnoreCase("s")) {
            salarioFinal += vendedorEncontrado.calcularBonus();
            System.out.println("💰 Bônus adicionado: R$ " + String.format("%.2f", vendedorEncontrado.calcularBonus()));
        }

        vendedorEncontrado.pagarSalario(salarioFinal);
        System.out.println("✅ Pagamento de R$ " + String.format("%.2f", salarioFinal) + " realizado para " + vendedorEncontrado.getNome());
    }

    public static void cadastrarGerente(Scanner sc) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      Cadastro de Novo Gerente          ║");
        System.out.println("╚════════════════════════════════════════╝");

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Idade: ");
        int idade = sc.nextInt();
        sc.nextLine();

        System.out.print("Estado: ");
        String estado = sc.nextLine();

        System.out.print("Cidade: ");
        String cidade = sc.nextLine();

        System.out.print("Bairro: ");
        String bairro = sc.nextLine();

        System.out.print("Rua: ");
        String rua = sc.nextLine();

        System.out.print("Número: ");
        int numero = sc.nextInt();
        sc.nextLine();

        System.out.print("Complemento: ");
        String complemento = sc.nextLine();

        System.out.print("Salário Base: R$ ");
        double salarioBase = sc.nextDouble();
        sc.nextLine();

        Endereco endereco = new Endereco(estado, cidade, bairro, rua, numero, complemento);
        Gerente novoGerente = new Gerente(nome, idade, lojadonagabi, salarioBase, endereco);
        novoGerente.setLoja(lojadonagabi);
        lojadonagabi.setGerente(novoGerente);

        System.out.println("✅ Gerente cadastrado com sucesso!");
    }

    public static void pagarFuncionario(Scanner sc) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      Pagamento de Funcionário          ║");
        System.out.println("╚════════════════════════════════════════╝");

        System.out.println("\n[1] Pagar Vendedor");
        System.out.println("[2] Pagar Gerente");
        System.out.print("Escolha uma opção: ");
        int tipo = sc.nextInt();
        sc.nextLine();

        switch (tipo) {
            case 1:
                pagarVendedor(sc);
                break;
            case 2:
                pagarGerente(sc);
                break;
            default:
                System.out.println("❌ Opção inválida!");
        }
    }

    private static void pagarGerente(Scanner sc) {
        Gerente gerente = lojadonagabi.getGerente();

        if (gerente == null) {
            System.out.println("❌ Nenhum gerente cadastrado.");
            return;
        }

        System.out.println("\n--- Gerente ---");
        System.out.println("[1] " + gerente.getNome() +
                " - R$ " + String.format("%.2f", gerente.getSalarioBase()));

        System.out.print("\nConfirmar pagamento? (s/n): ");
        String confirmacao = sc.nextLine();

        if (!confirmacao.equalsIgnoreCase("s")) {
            System.out.println("❌ Operação cancelada.");
            return;
        }

        double salarioFinal = gerente.getSalarioBase();

        System.out.print("Deseja adicionar bônus? (s/n): ");
        String opcaoBonus = sc.nextLine();

        if (opcaoBonus.equalsIgnoreCase("s")) {
            double bonus = gerente.calcularBonus();
            salarioFinal += bonus;
            System.out.println("💰 Bônus adicionado: R$ " + String.format("%.2f", bonus));
        }

        gerente.pagarSalario(salarioFinal);
        System.out.println("✅ Pagamento de R$ " + String.format("%.2f", salarioFinal) +
            " realizado para " + gerente.getNome());
    }

    public static void exibirInformacoesGerente() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║    Informações do Gerente da Loja      ║");
        System.out.println("╚════════════════════════════════════════╝");

        Gerente gerente = lojadonagabi.getGerente();
        if (gerente == null) {
            System.out.println("❌ Nenhum gerente cadastrado.");
            return;
        }

        gerente.apresentarSe();
        System.out.println("Salário Base: R$ " + String.format("%.2f", gerente.getSalarioBase()));
    }

    public static void listarClientes() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║          Lista de Clientes             ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        if (lojadonagabi.getListaDeClientes().isEmpty()) {
            System.out.println("❌ Nenhum cliente cadastrado.");
            return;
        }

        for (Cliente cliente : lojadonagabi.getListaDeClientes()) {
            cliente.apresentarSe();
        }
    }

    public static void cadastrarCliente(Scanner sc) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      Cadastro de Novo Cliente          ║");
        System.out.println("╚════════════════════════════════════════╝");

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

        System.out.println("✅ Cliente cadastrado com sucesso!");
    }

    public static void editarDadosLoja(Scanner sc) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║     Edição de Dados da Loja            ║");
        System.out.println("╚════════════════════════════════════════╝");

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

        System.out.println("✅ Dados da loja atualizados com sucesso!");
    }

    public static void criarPedido(Scanner sc) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       Criar Novo Pedido                ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        // Verificar se existem vendedores e clientes cadastrados
        if (lojadonagabi.getListaDeVendedores().isEmpty() || lojadonagabi.getListaDeClientes().isEmpty()) {
            System.out.println("❌ É necessário ter pelo menos um vendedor e um cliente cadastrados!");
            return;
        }

        // Listar clientes para seleção
        System.out.println("\n--- Selecione o Cliente ---");
        int countClientes = 0;
        for (Cliente c : lojadonagabi.getListaDeClientes()) {
            countClientes++;
            System.out.println("[" + countClientes + "] - " + c.getNome() + " (" + c.getIdade() + " anos)");
        }
        System.out.print("Digite o número do cliente: ");
        int idCliente = sc.nextInt() - 1;
        sc.nextLine();

        if (idCliente < 0 || idCliente >= lojadonagabi.getListaDeClientes().size()) {
            System.out.println("❌ Cliente inválido!");
            return;
        }

        // Listar vendedores para seleção
        System.out.println("\n--- Selecione o Vendedor ---");
        int countVendedores = 0;
        for (Vendedor v : lojadonagabi.getListaDeVendedores()) {
            countVendedores++;
            System.out.println("[" + countVendedores + "] - " + v.getNome() + " (" + v.getIdade() + " anos)");
        }
        System.out.print("Digite o número do vendedor: ");
        int idVendedor = sc.nextInt() - 1;
        sc.nextLine();

        if (idVendedor < 0 || idVendedor >= lojadonagabi.getListaDeVendedores().size()) {
            System.out.println("❌ Vendedor inválido!");
            return;
        }

        // Criar itens fictícios
        ArrayList<Item> itens = new ArrayList<>();
        itens.add(new Item(1, "Samambaia", "Planta Indoor", 45.00));
        itens.add(new Item(2, "Rosa", "Planta Ornamental", 35.50));
        itens.add(new Item(3, "Suculenta", "Planta Suculenta", 25.00));

        // Datas
        Date dataCriacao = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7); // Vencimento em 7 dias
        Date dataVencimentoReserva = calendar.getTime();

        // Processar pedido (ID será auto-gerado)
        Cliente clienteSelecionado = lojadonagabi.getListaDeClientes().get(idCliente);
        Vendedor vendedorSelecionado = lojadonagabi.getListaDeVendedores().get(idVendedor);
        
        System.out.println("\n⏳ Processando pedido...");
        Pedido pedidoCriado = processaPedido.processar(dataCriacao, dataVencimentoReserva, 
                                                       clienteSelecionado, vendedorSelecionado, lojadonagabi, itens);

        if (pedidoCriado != null) {
            System.out.println("\n✅ Pedido criado com sucesso!");
            pedidoCriado.gerarDescricaoVenda();
        }
    }

    public static void exibirInformacoesLoja() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║    Informações da Loja da Gabrielinha  ║");
        System.out.println("╚════════════════════════════════════════╝");
        lojadonagabi.apresentarSe();
        lojadonagabi.contarClientes();
        lojadonagabi.contarVendedores();
        lojadonagabi.temGerente();
        System.out.println("Total de Funcionários: " + lojadonagabi.totalFuncionarios());
    }

    //Método que cria registros para testes
    public static void registrosTeste(){
        
        Endereco endereco1 = new Endereco("Tocantins", "Palmas", "Rua 1", "Casa", 100, "Apartamento");
        Endereco endereco2 = new Endereco(  "Tocantins", "Araguaína", "Rua 2", "Casa", 200, "Apartamento");
        Endereco endereco3 = new Endereco( "Tocantins", "Gurupi", "Rua 3", "Casa", 300, "Apartamento");
        
        // Gerentes
        Gerente g1 = new Gerente("Maria Silva", 35, lojadonagabi, 4500, endereco3);
        
        // Define apenas um gerente para a loja (modelo: 1 gerente por loja)
        lojadonagabi.setGerente(g1);

        // Vendedores
        Vendedor v1 = new Vendedor("Joana", 40, lojadonagabi, endereco1, 2500);
        Vendedor v2 = new Vendedor("Alberto", 23, lojadonagabi, endereco2, 2800);

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

        // Pagamentos fictícios para Vendedores
        v1.pagarSalario(2500);
        v1.pagarSalario(2700);
        v1.pagarSalario(2800);
        v2.pagarSalario(2800);
        v2.pagarSalario(3100);

        // Pagamentos fictícios para Gerentes
        g1.pagarSalario(4500);
        g1.pagarSalario(5000);
    }
}