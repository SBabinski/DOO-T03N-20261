import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Endereco enderecoLoja = new Endereco("Paraná", "Cascavel", "Centro", 100, "Loja principal");

        Loja loja = new Loja();
        loja.nomeFantasia = "My Plant";
        loja.razaoSocial = "My Plant LTDA";
        loja.cnpj = "12.345.678/0001-90";
        loja.endereco = enderecoLoja;

        int opcao;

        do {
            System.out.println("\n=== MENU MY PLANT ===");
            System.out.println("1 - Demonstração de vendedor");
            System.out.println("2 - Demonstração de cliente");
            System.out.println("3 - Demonstração de gerente");
            System.out.println("4 - Mostrar loja");
            System.out.println("5 - Criar pedido com dados fakes");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    Vendedor vendedor = criarVendedorFake(loja);
                    vendedor.apresentarse();
                    System.out.println("Média salarial: " + vendedor.calcularMedia());
                    System.out.println("Bônus: " + vendedor.calcularBonus());
                    break;

                case 2:
                    Cliente cliente = criarClienteFake();
                    cliente.apresentarse();
                    break;

                case 3:
                    Gerente gerente = criarGerenteFake(loja);
                    gerente.apresentarse();
                    System.out.println("Média salarial: " + gerente.calcularMedia());
                    System.out.println("Bônus: " + gerente.calcularBonus());
                    break;

                case 4:
                    loja.apresentarse();
                    break;

                case 5:
                    Cliente clientePedido = criarClienteFake();
                    Vendedor vendedorPedido = criarVendedorFake(loja);

                    Item item1 = new Item(1, "Rosa do Deserto", "Planta", 80.00);
                    Item item2 = new Item(2, "Orquídea", "Planta", 120.00);
                    Item item3 = new Item(3, "Vaso Decorativo", "Acessório", 45.00);

                    Item[] itens = {item1, item2, item3};

                    ProcessaPedido processaPedido = new ProcessaPedido();
                    Pedido pedido = processaPedido.processar(
                            1,
                            clientePedido,
                            vendedorPedido,
                            loja,
                            itens
                    );

                    pedido.gerarDescricaoVenda();

                    System.out.println("\nItens do pedido:");
                    for (Item item : itens) {
                        item.gerarDescricao();
                    }
                    break;

                case 0:
                    System.out.println("Sistema encerrado.");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        sc.close();
    }

    public static Vendedor criarVendedorFake(Loja loja) {
        Endereco endereco = new Endereco("Paraná", "Cascavel", "Centro", 50, "Casa");
        double[] salarios = {2000.00, 2100.00, 2200.00};
        return new Vendedor("Carlos", 28, loja, endereco, 2000.00, salarios);
    }

    public static Cliente criarClienteFake() {
        Endereco endereco = new Endereco("Paraná", "Cascavel", "Cancelli", 75, "Apartamento");
        return new Cliente("Ana", 22, endereco);
    }

    public static Gerente criarGerenteFake(Loja loja) {
        Endereco endereco = new Endereco("Paraná", "Cascavel", "Centro", 200, "Casa");
        double[] salarios = {5000.00, 5200.00, 5400.00};
        return new Gerente("Mariana", 35, loja, endereco, 5000.00, salarios);
    }
}

class Endereco {
    String estado;
    String cidade;
    String bairro;
    int numero;
    String complemento;

    public Endereco(String estado, String cidade, String bairro, int numero, String complemento) {
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.numero = numero;
        this.complemento = complemento;
    }

    public void apresentarLogradouro() {
        System.out.println("Endereço: " + cidade + " - " + estado + ", bairro " + bairro +
                ", número " + numero + ", complemento: " + complemento);
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
        endereco.apresentarLogradouro();
    }
}

class Funcionario extends Pessoa {
    Loja loja;
    double salarioBase;
    double[] salarioRecebido;

    public Funcionario(String nome, int idade, Loja loja, Endereco endereco, double salarioBase, double[] salarioRecebido) {
        super(nome, idade, endereco);
        this.loja = loja;
        this.salarioBase = salarioBase;
        this.salarioRecebido = salarioRecebido;
    }

    public double calcularMedia() {
        double soma = 0;

        for (double salario : salarioRecebido) {
            soma += salario;
        }

        return soma / salarioRecebido.length;
    }

    public double calcularBonus() {
        return 0;
    }
}

class Vendedor extends Funcionario {
    public Vendedor(String nome, int idade, Loja loja, Endereco endereco, double salarioBase, double[] salarioRecebido) {
        super(nome, idade, loja, endereco, salarioBase, salarioRecebido);
    }

    @Override
    public void apresentarse() {
        System.out.println("Vendedor: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Loja: " + loja.nomeFantasia);
        endereco.apresentarLogradouro();
    }

    @Override
    public double calcularBonus() {
        return salarioBase * 0.2;
    }
}

class Gerente extends Funcionario {
    public Gerente(String nome, int idade, Loja loja, Endereco endereco, double salarioBase, double[] salarioRecebido) {
        super(nome, idade, loja, endereco, salarioBase, salarioRecebido);
    }

    @Override
    public void apresentarse() {
        System.out.println("Gerente: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Loja: " + loja.nomeFantasia);
        endereco.apresentarLogradouro();
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
        System.out.println("Cliente: " + nome);
        System.out.println("Idade: " + idade);
        endereco.apresentarLogradouro();
    }
}

class Loja {
    String nomeFantasia;
    String razaoSocial;
    String cnpj;
    Endereco endereco;

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
        System.out.println("Item ID: " + id + " | Nome: " + nome + " | Tipo: " + tipo + " | Valor: R$ " + valor);
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

        for (Item item : itens) {
            total += item.valor;
        }

        return total;
    }

    public void gerarDescricaoVenda() {
        System.out.println("\n=== DESCRIÇÃO DO PEDIDO ===");
        System.out.println("Data de criação: " + dataCriacao);
        System.out.println("Valor total: R$ " + calcularValorTotal());
    }
}

class ProcessaPedido {
    public Pedido processar(int id, Cliente cliente, Vendedor vendedor, Loja loja, Item[] itens) {
        Date dataCriacao = new Date();
        Date dataPagamento = new Date();

        Date dataVencimentoReserva = new Date(dataCriacao.getTime() + 86400000);

        Pedido pedido = new Pedido(
                id,
                dataCriacao,
                dataPagamento,
                dataVencimentoReserva,
                cliente,
                vendedor,
                loja,
                itens
        );

        if (confirmarPagamento(pedido)) {
            System.out.println("Pagamento confirmado. Pedido criado com sucesso.");
        } else {
            System.out.println("Reserva vencida. Pagamento não confirmado.");
        }

        testeConfirmarPagamento(pedido);

        return pedido;
    }

    private boolean confirmarPagamento(Pedido pedido) {
        Date dataAtual = new Date();
        return !dataAtual.after(pedido.dataVencimentoReserva);
    }

    public void testeConfirmarPagamento(Pedido pedido) {
        System.out.println("\n=== TESTE DE PAGAMENTO ===");

        if (confirmarPagamento(pedido)) {
            System.out.println("Teste aprovado: reserva ainda está válida.");
        } else {
            System.out.println("Teste reprovado: reserva está vencida.");
        }
    }
}