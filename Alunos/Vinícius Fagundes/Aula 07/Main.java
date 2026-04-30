import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");

    static Loja loja;
    static Cliente cliente;
    static Vendedor vendedor;
    static Gerente gerente;

    static int contadorPedidos = 1;

    public static void main(String[] args) {
        int opcao = 0;

        do {
            System.out.println("=================================");
            System.out.println(" SISTEMA MY PLANT");
            System.out.println("=================================");
            System.out.println("[1] - Cadastrar Loja");
            System.out.println("[2] - Cadastrar Cliente");
            System.out.println("[3] - Cadastrar Vendedor");
            System.out.println("[4] - Cadastrar Gerente");
            System.out.println("[5] - Mostrar Dados Cadastrados");
            System.out.println("[6] - Criar Pedido");
            System.out.println("[7] - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    cadastrarLoja();
                    break;

                case 2:
                    cadastrarCliente();
                    break;

                case 3:
                    cadastrarVendedor();
                    break;

                case 4:
                    cadastrarGerente();
                    break;

                case 5:
                    mostrarDadosCadastrados();
                    break;

                case 6:
                    criarPedido();
                    break;

                case 7:
                    System.out.println("Sistema encerrado.");
                    break;

                default:
                    System.out.println("Opção inválida.");
                    break;
            }

            System.out.println();

        } while (opcao != 7);
    }

    public static void cadastrarLoja() {
        System.out.println("===== CADASTRO DA LOJA =====");

        System.out.print("Nome fantasia: ");
        String nomeFantasia = scanner.nextLine();

        System.out.print("Razão social: ");
        String razaoSocial = scanner.nextLine();

        System.out.print("CNPJ: ");
        String cnpj = scanner.nextLine();

        Endereco endereco = cadastrarEndereco();

        loja = new Loja(nomeFantasia, razaoSocial, cnpj, endereco);

        System.out.println("Loja cadastrada com sucesso!");
    }

    public static void cadastrarCliente() {
        System.out.println("===== CADASTRO DE CLIENTE =====");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Idade: ");
        int idade = lerInteiro();

        Endereco endereco = cadastrarEndereco();

        cliente = new Cliente(nome, idade, endereco);

        System.out.println("Cliente cadastrado com sucesso!");
    }

    public static void cadastrarVendedor() {
        if (loja == null) {
            System.out.println("Cadastre uma loja antes de cadastrar o vendedor.");
            return;
        }

        System.out.println("===== CADASTRO DE VENDEDOR =====");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Idade: ");
        int idade = lerInteiro();

        Endereco endereco = cadastrarEndereco();

        System.out.print("Salário base: R$ ");
        double salarioBase = lerDouble();

        double[] salarioRecebido = cadastrarSalarios();

        vendedor = new Vendedor(nome, idade, loja, endereco, salarioBase, salarioRecebido);

        System.out.println("Vendedor cadastrado com sucesso!");
    }

    public static void cadastrarGerente() {
        if (loja == null) {
            System.out.println("Cadastre uma loja antes de cadastrar o gerente.");
            return;
        }

        System.out.println("===== CADASTRO DE GERENTE =====");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Idade: ");
        int idade = lerInteiro();

        Endereco endereco = cadastrarEndereco();

        System.out.print("Salário base: R$ ");
        double salarioBase = lerDouble();

        double[] salarioRecebido = cadastrarSalarios();

        gerente = new Gerente(nome, idade, loja, endereco, salarioBase, salarioRecebido);

        System.out.println("Gerente cadastrado com sucesso!");
    }

    public static Endereco cadastrarEndereco() {
        System.out.println("===== ENDEREÇO =====");

        System.out.print("Estado: ");
        String estado = scanner.nextLine();

        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Bairro: ");
        String bairro = scanner.nextLine();

        System.out.print("Número: ");
        String numero = scanner.nextLine();

        System.out.print("Complemento: ");
        String complemento = scanner.nextLine();

        return new Endereco(estado, cidade, bairro, numero, complemento);
    }

    public static double[] cadastrarSalarios() {
        double[] salarios = new double[3];

        System.out.println("Informe os 3 últimos salários recebidos:");

        for (int i = 0; i < salarios.length; i++) {
            System.out.print("Salário " + (i + 1) + ": R$ ");
            salarios[i] = lerDouble();
        }

        return salarios;
    }

    public static void mostrarDadosCadastrados() {
        System.out.println("===== DADOS CADASTRADOS =====");

        if (loja != null) {
            loja.apresentarse();
        } else {
            System.out.println("Nenhuma loja cadastrada.");
        }

        System.out.println();

        if (cliente != null) {
            cliente.apresentarse();
            cliente.endereco.apresentarLogradouro();
        } else {
            System.out.println("Nenhum cliente cadastrado.");
        }

        System.out.println();

        if (vendedor != null) {
            vendedor.apresentarse();
            vendedor.endereco.apresentarLogradouro();
            System.out.println("Média salarial: R$ " + vendedor.calcularMedia());
            System.out.println("Bônus: R$ " + vendedor.calcularBonus());
        } else {
            System.out.println("Nenhum vendedor cadastrado.");
        }

        System.out.println();

        if (gerente != null) {
            gerente.apresentarse();
            gerente.endereco.apresentarLogradouro();
            System.out.println("Média salarial: R$ " + gerente.calcularMedia());
            System.out.println("Bônus: R$ " + gerente.calcularBonus());
        } else {
            System.out.println("Nenhum gerente cadastrado.");
        }
    }

    public static void criarPedido() {
        if (loja == null || cliente == null || vendedor == null) {
            System.out.println("Para criar um pedido, cadastre primeiro a loja, o cliente e o vendedor.");
            return;
        }

        try {
            System.out.println("===== CRIAÇÃO DE PEDIDO =====");

            System.out.print("Data de criação do pedido (dd/MM/yyyy): ");
            Date dataCriacao = formatadorData.parse(scanner.nextLine());

            System.out.print("Data de pagamento do pedido (dd/MM/yyyy): ");
            Date dataPagamento = formatadorData.parse(scanner.nextLine());

            System.out.print("Data de vencimento da reserva (dd/MM/yyyy): ");
            Date dataVencimentoReserva = formatadorData.parse(scanner.nextLine());

            System.out.print("Quantidade de itens no pedido: ");
            int quantidadeItens = lerInteiro();

            Item[] itens = new Item[quantidadeItens];

            for (int i = 0; i < quantidadeItens; i++) {
                System.out.println("===== ITEM " + (i + 1) + " =====");

                System.out.print("ID do item: ");
                int id = lerInteiro();

                System.out.print("Nome do item: ");
                String nome = scanner.nextLine();

                System.out.print("Tipo do item: ");
                String tipo = scanner.nextLine();

                System.out.print("Valor do item: R$ ");
                double valor = lerDouble();

                itens[i] = new Item(id, nome, tipo, valor);
            }

            ProcessaPedido processaPedido = new ProcessaPedido();

            Pedido pedido = processaPedido.processar(
                    contadorPedidos,
                    dataCriacao,
                    dataPagamento,
                    dataVencimentoReserva,
                    cliente,
                    vendedor,
                    loja,
                    itens
            );

            if (pedido != null) {
                contadorPedidos++;
                pedido.gerarDescricaoVenda();

                System.out.println("Itens do pedido:");

                for (int i = 0; i < itens.length; i++) {
                    itens[i].gerarDescricao();
                }
            }

        } catch (ParseException e) {
            System.out.println("Data inválida. Use o formato dd/MM/yyyy.");
        }
    }

    public static int lerInteiro() {
        int valor = Integer.parseInt(scanner.nextLine());
        return valor;
    }

    public static double lerDouble() {
        String texto = scanner.nextLine();
        texto = texto.replace(",", ".");
        double valor = Double.parseDouble(texto);
        return valor;
    }
}

class Endereco {
    String estado;
    String cidade;
    String bairro;
    String numero;
    String complemento;

    public Endereco(String estado, String cidade, String bairro, String numero, String complemento) {
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.numero = numero;
        this.complemento = complemento;
    }

    public void apresentarLogradouro() {
        System.out.println("Endereço: " + cidade + " - " + estado
                + ", Bairro " + bairro
                + ", Nº " + numero
                + ", Complemento: " + complemento);
    }
}

class Pessoa {
    String nome;
    int idade;
    Endereco endereco;

    public Pessoa(String nome, int idade, Endereco endereco) {
        this.nome = nome;
        this.idade = idade;
        this.endereco = endereco;
    }

    public void apresentarse() {
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
    }
}

class Funcionario extends Pessoa {
    Loja loja;
    double salarioBase;
    double[] salarioRecebido;

    public Funcionario(String nome, int idade, Loja loja, Endereco endereco,
                       double salarioBase, double[] salarioRecebido) {
        super(nome, idade, endereco);
        this.loja = loja;
        this.salarioBase = salarioBase;
        this.salarioRecebido = salarioRecebido;
    }

    @Override
    public void apresentarse() {
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Loja: " + loja.nomeFantasia);
    }

    public double calcularMedia() {
        double soma = 0;

        for (int i = 0; i < salarioRecebido.length; i++) {
            soma += salarioRecebido[i];
        }

        return soma / salarioRecebido.length;
    }

    public double calcularBonus() {
        return 0;
    }
}

class Vendedor extends Funcionario {

    public Vendedor(String nome, int idade, Loja loja, Endereco endereco,
                    double salarioBase, double[] salarioRecebido) {
        super(nome, idade, loja, endereco, salarioBase, salarioRecebido);
    }

    @Override
    public double calcularBonus() {
        return salarioBase * 0.2;
    }
}

class Gerente extends Funcionario {

    public Gerente(String nome, int idade, Loja loja, Endereco endereco,
                   double salarioBase, double[] salarioRecebido) {
        super(nome, idade, loja, endereco, salarioBase, salarioRecebido);
    }

    @Override
    public double calcularBonus() {
        return salarioBase * 0.35;
    }
}

class Cliente extends Pessoa {

    public Cliente(String nome, int idade, Endereco endereco) {
        super(nome, idade, endereco);
    }

    @Override
    public void apresentarse() {
        System.out.println("Nome do cliente: " + nome);
        System.out.println("Idade: " + idade);
    }
}

class Loja {
    String nomeFantasia;
    String razaoSocial;
    String cnpj;
    Endereco endereco;

    public Loja(String nomeFantasia, String razaoSocial, String cnpj, Endereco endereco) {
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.endereco = endereco;
    }

    public void apresentarse() {
        System.out.println("Nome fantasia: " + nomeFantasia);
        System.out.println("Razão social: " + razaoSocial);
        System.out.println("CNPJ: " + cnpj);
        endereco.apresentarLogradouro();
    }
}

class Item {
    int id;
    String nome;
    String tipo;
    double valor;

    public Item(int id, String nome, String tipo, double valor) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
    }

    public void gerarDescricao() {
        System.out.println("Item ID: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("Tipo: " + tipo);
        System.out.println("Valor: R$ " + valor);
        System.out.println("-----------------------------");
    }
}

class Pedido {
    int id;
    Date dataCriacao;
    Date dataPagamento;
    Date dataVencimentoReserva;
    Cliente cliente;
    Vendedor vendedor;
    Loja loja;
    Item[] itens;

    public Pedido(int id, Date dataCriacao, Date dataPagamento, Date dataVencimentoReserva,
                  Cliente cliente, Vendedor vendedor, Loja loja, Item[] itens) {
        this.id = id;
        this.dataCriacao = dataCriacao;
        this.dataPagamento = dataPagamento;
        this.dataVencimentoReserva = dataVencimentoReserva;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.loja = loja;
        this.itens = itens;
    }

    public double calcularValorTotal() {
        double total = 0;

        for (int i = 0; i < itens.length; i++) {
            total += itens[i].valor;
        }

        return total;
    }

    public void gerarDescricaoVenda() {
        System.out.println("===== DESCRIÇÃO DO PEDIDO =====");
        System.out.println("Pedido ID: " + id);
        System.out.println("Data de criação: " + dataCriacao);
        System.out.println("Cliente: " + cliente.nome);
        System.out.println("Vendedor: " + vendedor.nome);
        System.out.println("Loja: " + loja.nomeFantasia);
        System.out.println("Valor total do pedido: R$ " + calcularValorTotal());
    }
}

class ProcessaPedido {

    public Pedido processar(int id, Date dataCriacao, Date dataPagamento, Date dataVencimentoReserva,
                            Cliente cliente, Vendedor vendedor, Loja loja, Item[] itens) {

        if (confirmarPagamento(dataVencimentoReserva)) {
            System.out.println("Pagamento confirmado. Pedido criado com sucesso.");

            return new Pedido(
                    id,
                    dataCriacao,
                    dataPagamento,
                    dataVencimentoReserva,
                    cliente,
                    vendedor,
                    loja,
                    itens
            );
        } else {
            System.out.println("Não foi possível criar o pedido. A reserva está vencida.");
            return null;
        }
    }

    private boolean confirmarPagamento(Date dataVencimentoReserva) {
        Date dataAtual = new Date();

        return !dataAtual.after(dataVencimentoReserva);
    }
}