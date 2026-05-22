import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static boolean appRodando = true;
    public static Loja lojaDonaGabrielinha = new Loja(null, null, null, null, null, null);

    // MODO DE TESTE: mude para true para preencher o sistema com dados fictícios
    public static boolean modoTesteAtivo = false;

    public static void main(String[] args) {
        if (modoTesteAtivo) {
            modoTeste();
        }

        while (appRodando) {
            exibirMenu();
            System.out.print("Ação: ");

            int acaoUsuario = scanner.nextInt();

            gerenciarAcoes(acaoUsuario);

            scanner.nextLine();

        }
        scanner.close();
    }

    public static void exibirMenu() {
        System.out.println("-----MENU-----");
        System.out.println("1 - Calcular o preço total");
        System.out.println("2 - Calcular troco");
        System.out.println("3 - Realizar Venda");
        System.out.println("4 - Consultar Vendas");
        System.out.println("5 - Gerenciar Loja");
        System.out.println("6 - Sair");
        System.out.println("--------------");
    }

    public static void gerenciarAcoes(int acaoUsuario) {
        switch (acaoUsuario) {
            case 1:
                calcularPrecoTotal();
                break;
            case 2:
                calcularTroco();
                break;
            case 3:
                realizarVenda();
                break;
            case 4:
                consultarVendas();
                break;
            case 5:
                gerenciarLoja();
                break;
            case 6:
                // termina aplicação
                appRodando = false;
                break;
            default:
                System.out.println("Ação inválida.");
                break;
        }
    }

    public static void calcularPrecoTotal() {
        System.out.print("Insira quantidade de plantas: ");
        var qtdPlantas = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Insira o valor da planta: ");
        var valorPlanta = scanner.nextFloat();

        var valorVenda = Calculadora.calcularPreco(qtdPlantas, valorPlanta);

        System.out.println("Preço total: R$ " + valorVenda);
    }

    public static void calcularTroco() {
        System.out.print("Valor pago pelo cliente: ");
        var valorPagoCliente = scanner.nextFloat();
        scanner.nextLine();

        System.out.print("Valor total da compra: ");
        var valorVenda = scanner.nextFloat();
        scanner.nextLine();

        // validação: valor da compra não deve ser menor ou igual a zero
        if (valorVenda <= 0) {
            System.out.println("Erro: Valor da venda deve ser positivo.");
            return;
        }

        var valorTroco = Calculadora.calcularTroco(valorPagoCliente, valorVenda);

        System.out.println("Valor do troco: R$" + valorTroco);
    }

    public static void realizarVenda() {
        System.out.print("Quantidade de plantas: ");
        var qtdPlantas = scanner.nextInt();
        scanner.nextLine();

        // validação: impossivel fazer venda com qtd de plantas negativas ou igual a
        // zero
        if (qtdPlantas <= 0) {
            System.out.println("Erro: insira um número maior que zero");
            return;
        }

        System.out.print("Valor das plantas: R$");
        var precoPlantas = scanner.nextFloat();

        // pegando data com usuário
        System.out.println("Informe a data da venda");
        System.out.print("Dia: ");
        int dia = scanner.nextInt();

        System.out.print("Mês: ");
        int mes = scanner.nextInt();

        System.out.print("Ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine();

        float valorVenda = precoPlantas * qtdPlantas;

        float desconto = 0.0f;
        if (qtdPlantas > 10) {
            desconto = Calculadora.calcularDesconto(valorVenda);
        }

        float valorFinal = valorVenda + desconto;

        Venda novaVenda = new Venda(qtdPlantas, valorFinal, desconto, dia, mes, ano);
        Database.registrarVenda(novaVenda);

        System.out.println("--- Venda Realizada ---");
        System.out.println("Valor Original: R$" + precoPlantas);
        System.out.println("Desconto: R$" + desconto);
        System.out.println("Valor Final: R$" + valorFinal);
        System.out.println("-----------------------");
    }

    public static void consultarVendas() {
        // consulta de vendas por dia ou por mês

        System.out.println("Consultar por:");
        System.out.println("1 - Dia do mês");
        System.out.println("2 - Mês");
        System.out.println("3 - Total");
        System.out.print("Escolha: ");

        int escolhaUsuario = scanner.nextInt();
        scanner.nextLine();

        if (escolhaUsuario == 1) {
            // filtra por dia/mes

            System.out.print("Insira o mês (1-12): ");
            int mes = scanner.nextInt();

            System.out.print("Insira o dia: ");
            int dia = scanner.nextInt();
            scanner.nextLine();

            ArrayList<Venda> listaVendas = Database.pegarVendasPorDiaMes(dia, mes);

            if (listaVendas.isEmpty()) {
                System.out.println("Nenhuma venda encontrada");
                return;
            }

            // exibe as vendas encontradas
            for (Venda venda : listaVendas) {
                venda.exibirRegistro();
            }
        } else if (escolhaUsuario == 2) {
            // filtra por mes

            System.out.print("Insira o mês (1-12): ");
            int mes = scanner.nextInt();
            scanner.nextLine();

            ArrayList<Venda> listaVendas = Database.pegarVendasPorMes(mes);

            if (listaVendas.isEmpty()) {
                System.out.println("Nenhuma venda encontrada");
                return;
            }

            // exibe as vendas encontradas
            for (Venda venda : listaVendas) {
                venda.exibirRegistro();
            }
        } else {
            // exibe o total de vendas

            var totalDeVendas = Database.pegarTotalVendas();

            for (var venda : totalDeVendas) {
                venda.exibirRegistro();
            }
        }

    }

    public static void gerenciarLoja() {
        int escolhaUsuario = 0;

        while (escolhaUsuario != 8) {

            System.out.println("1 - Listar vendedores");
            System.out.println("2 - Cadastrar vendedor");
            System.out.println("3 - Pagar Vendedor");
            System.out.println("4 - Listar clientes");
            System.out.println("5 - Cadastrar clientes");
            System.out.println("6 - Editar dados da loja");
            System.out.println("7 - Exibir dados da loja");
            System.out.println("8 - Voltar");
            System.out.print("- Ação: ");

            escolhaUsuario = scanner.nextInt();
            scanner.nextLine();
            switch (escolhaUsuario) {
                case 1:
                    listarVendedores();
                    break;
                case 2:
                    cadastrarVendedor();
                    break;
                case 3:
                    pagarVendedor();
                    break;
                case 4:
                    listarClientes();
                    break;
                case 5:
                    cadastrarCliente();
                    break;
                case 6:
                    editarDadosLoja();
                    break;
                case 7:
                    exibirInformacoesLoja();
                    break;
                default:
                    break;
            }
        }
    }

    public static void listarVendedores() {
        // mensagem de aviso caso não exista vendedor cadastrado
        if (lojaDonaGabrielinha.getListaDeVendedores().size() == 0) {
            System.out.println("Nenhum vendedor cadastrado.");
            return;
        }

        for (var vendedor : lojaDonaGabrielinha.getListaDeVendedores()) {
            vendedor.apresentarSe();
        }
    }

    public static void cadastrarVendedor() {
        System.out.println("--- Cadastro de Vendedor ---");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Idade: ");
        int idade = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Bairro: ");
        String bairro = scanner.nextLine();

        System.out.print("Rua: ");
        String rua = scanner.nextLine();

        System.out.print("Salario base: ");
        float salarioBase = scanner.nextFloat();
        scanner.nextLine();

        Vendedor novoVendedor = new Vendedor(nome, idade, lojaDonaGabrielinha, bairro, cidade, rua, salarioBase);
        novoVendedor.setLoja(lojaDonaGabrielinha);
        lojaDonaGabrielinha.cadastrarVendedor(novoVendedor);

        System.out.println("Vendedor cadastrado com sucesso!");
        System.out.println("----------------------------");
    }

    public static void pagarVendedor() {
        System.out.print("Digite o nome do vendedor: ");
        String nomeVendedor = scanner.nextLine();

        Vendedor vendedorEncontrado = null;

        if (lojaDonaGabrielinha.getListaDeVendedores() != null) {
            for (Vendedor vendedor : lojaDonaGabrielinha.getListaDeVendedores()) {
                if (vendedor.getNome().equalsIgnoreCase(nomeVendedor)) {
                    vendedorEncontrado = vendedor;
                    break;
                }
            }
        }

        if (vendedorEncontrado == null) {
            System.out.println("Vendedor não encontrado.");
            return;
        }

        System.out.print("Deseja adicionar bônus? (s/n): ");
        String opcaoBonus = scanner.nextLine();

        float salarioFinal = vendedorEncontrado.getSalarioBase();

        // adiciona bonus ao salário
        if (opcaoBonus.equalsIgnoreCase("s")) {
            salarioFinal += vendedorEncontrado.calcularBonus();
        }

        vendedorEncontrado.pagarSalario(salarioFinal);

        System.out.println("Pagamento de R$" + salarioFinal + " realizado para " + vendedorEncontrado.getNome());
    }

    public static void listarClientes() {
        // mensagem de aviso caso não exista cliente cadastrado
        if (lojaDonaGabrielinha.getListaDeClientes().size() == 0) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        for (var clienteDaLoja : lojaDonaGabrielinha.getListaDeClientes()) {
            clienteDaLoja.apresentarSe();
        }
    }

    public static void cadastrarCliente() {
        System.out.println("--- Cadastro de Cliente ---");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Idade: ");
        int idade = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Bairro: ");
        String bairro = scanner.nextLine();

        System.out.print("Rua: ");
        String rua = scanner.nextLine();

        Cliente novoCliente = new Cliente(nome, idade, cidade, bairro, rua);
        lojaDonaGabrielinha.cadastrarCliente(novoCliente);

        System.out.println("Cliente cadastrado com sucesso!");
        System.out.println("---------------------------");
    }

    public static void editarDadosLoja() {
        System.out.println("--- Edição de Dados da Loja ---");

        System.out.print("Novo Nome Fantasia: ");
        String nomeFantasia = scanner.nextLine();
        lojaDonaGabrielinha.setNomeFantasia(nomeFantasia);

        System.out.print("Nova Razão Social: ");
        String razaoSocial = scanner.nextLine();
        lojaDonaGabrielinha.setRazaoSocial(razaoSocial);

        System.out.print("Novo CNPJ: ");
        String cnpj = scanner.nextLine();
        lojaDonaGabrielinha.setCnpj(cnpj);

        System.out.print("Nova Cidade: ");
        String cidade = scanner.nextLine();
        lojaDonaGabrielinha.setCidade(cidade);

        System.out.print("Novo Bairro: ");
        String bairro = scanner.nextLine();
        lojaDonaGabrielinha.setBairro(bairro);

        System.out.print("Nova Rua: ");
        String rua = scanner.nextLine();
        lojaDonaGabrielinha.setRua(rua);

        System.out.println("Dados da loja atualizados com sucesso!");
        System.out.println("-------------------------------");
    }

    public static void modoTeste() {
        // Configuração da Loja
        lojaDonaGabrielinha.setNomeFantasia("My Bloom - Plantas e Flores");
        lojaDonaGabrielinha.setRazaoSocial("Bloom LTDA");
        lojaDonaGabrielinha.setCnpj("45.678.910/0001-22");
        lojaDonaGabrielinha.setCidade("Florianópolis");
        lojaDonaGabrielinha.setBairro("Itacorubi");
        lojaDonaGabrielinha.setRua("Rod. Admar Gonzaga, 123");

        // Vendedores
        Vendedor v1 = new Vendedor("Sandrine", 28, lojaDonaGabrielinha, "Centro", "Florianópolis", "Rua Bocaiúva",
                2500f);
        Vendedor v2 = new Vendedor("Marcos", 35, lojaDonaGabrielinha, "Trindade", "Florianópolis", "Rua Lauro Linhares",
                2200f);
        Vendedor v3 = new Vendedor("Luana", 22, lojaDonaGabrielinha, "Santa Mônica", "Florianópolis",
                "Av. Madre Benvenuta", 2350f);
        Vendedor v4 = new Vendedor("Ricardo", 40, lojaDonaGabrielinha, "Córrego Grande", "Florianópolis",
                "Rua João Pio Duarte Silva", 2800f);

        lojaDonaGabrielinha.cadastrarVendedor(v1);
        lojaDonaGabrielinha.cadastrarVendedor(v2);
        lojaDonaGabrielinha.cadastrarVendedor(v3);
        lojaDonaGabrielinha.cadastrarVendedor(v4);

        // Clientes
        Cliente c1 = new Cliente("Beatriz Oliveira", 30, "Florianópolis", "Agronômica", "Rua Rui Barbosa");
        Cliente c2 = new Cliente("Fernando Costa", 45, "São José", "Kobrasol", "Rua Koesa");
        Cliente c3 = new Cliente("Patrícia Santos", 27, "Palhoça", "Pagani", "Av. Atílio Pedro Pagani");
        Cliente c4 = new Cliente("Roberto Lima", 52, "Florianópolis", "Campeche", "Av. Pequeno Príncipe");
        Cliente c5 = new Cliente("Carla Mendes", 33, "Biguaçu", "Centro", "Rua Sete de Setembro");

        lojaDonaGabrielinha.cadastrarCliente(c1);
        lojaDonaGabrielinha.cadastrarCliente(c2);
        lojaDonaGabrielinha.cadastrarCliente(c3);
        lojaDonaGabrielinha.cadastrarCliente(c4);
        lojaDonaGabrielinha.cadastrarCliente(c5);

        // Vendas Fictícias
        Database.registrarVenda(new Venda(5, 150f, 0f, 10, 4, 2026));
        Database.registrarVenda(new Venda(12, 324f, 36f, 12, 4, 2026));
        Database.registrarVenda(new Venda(3, 90f, 0f, 13, 4, 2026));
        Database.registrarVenda(new Venda(20, 540f, 60f, 14, 4, 2026));
        Database.registrarVenda(new Venda(1, 45f, 0f, 15, 4, 2026));
        Database.registrarVenda(new Venda(8, 240f, 0f, 15, 4, 2026));
        Database.registrarVenda(new Venda(15, 405f, 45f, 16, 4, 2026));
        Database.registrarVenda(new Venda(4, 120f, 0f, 17, 4, 2026));

        // Pagamentos fictícios
        v1.pagarSalario(2500f);
        v1.pagarSalario(2700f);
        v2.pagarSalario(2200f);
        v3.pagarSalario(2350f);

        System.out.println("-----------------------------------------------------");
        System.out.println("SISTEMA INICIADO EM MODO DE TESTE");
        System.out.println("Para alterar o comportamento, altere para false a variável 'modoTesteAtivo'");
        System.out.println("-----------------------------------------------------");
    }

    public static void exibirInformacoesLoja() {
        System.out.println("--- Informações da Loja ---");
        lojaDonaGabrielinha.apresentarSe();
        lojaDonaGabrielinha.contarClientes();
        lojaDonaGabrielinha.contarVendedores();
        System.out.println("---------------------------");
    }
}
