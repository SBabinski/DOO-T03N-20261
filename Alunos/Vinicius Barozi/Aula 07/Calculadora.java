import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

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
        System.out.println(
                "Endereço: " + bairro + ", Nº " + numero + " - " +
                cidade + "/" + estado + " | " + complemento
        );
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
        System.out.println("Nome: " + nome + " | Idade: " + idade);
        endereco.apresentarLogradouro();
    }
}

class Cliente extends Pessoa {

    public Cliente(String nome, int idade, Endereco endereco) {
        super(nome, idade, endereco);
    }

    @Override
    public void apresentarse() {
        System.out.println("CLIENTE");
        super.apresentarse();
    }
}

class Vendedor extends Pessoa {
    String loja;
    double salarioBase;
    double[] salarioRecebido;

    public Vendedor(String nome, int idade, String loja, Endereco endereco, double salarioBase) {
        super(nome, idade, endereco);
        this.loja = loja;
        this.salarioBase = salarioBase;
        this.salarioRecebido = new double[]{
                salarioBase,
                salarioBase * 1.10,
                salarioBase * 1.05
        };
    }

    public double calcularMedia() {
        double soma = 0;
        for (double s : salarioRecebido) {
            soma += s;
        }
        return soma / salarioRecebido.length;
    }

    public double calcularBonus() {
        return salarioBase * 0.20;
    }

    @Override
    public void apresentarse() {
        System.out.println("VENDEDOR");
        System.out.println("Nome: " + nome + " | Idade: " + idade + " | Loja: " + loja);
    }
}

class Gerente extends Pessoa {
    String loja;
    double salarioBase;
    double[] salarioRecebido;

    public Gerente(String nome, int idade, String loja, Endereco endereco, double salarioBase) {
        super(nome, idade, endereco);
        this.loja = loja;
        this.salarioBase = salarioBase;
        this.salarioRecebido = new double[]{
                salarioBase,
                salarioBase * 1.20,
                salarioBase * 1.15
        };
    }

    public double calcularMedia() {
        double soma = 0;
        for (double s : salarioRecebido) {
            soma += s;
        }
        return soma / salarioRecebido.length;
    }

    public double calcularBonus() {
        return salarioBase * 0.35;
    }

    @Override
    public void apresentarse() {
        System.out.println("GERENTE");
        System.out.println("Nome: " + nome + " | Idade: " + idade + " | Loja: " + loja);
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
        System.out.println(
                "ID: " + id +
                " | Nome: " + nome +
                " | Tipo: " + tipo +
                " | Valor: R$ " + valor
        );
    }
}

class Loja {
    String nomeFantasia;
    String cnpj;
    Endereco endereco;

    List<Vendedor> vendedores = new ArrayList<>();
    List<Cliente> clientes = new ArrayList<>();
    List<Gerente> gerentes = new ArrayList<>();

    public Loja(String nomeFantasia, String cnpj, Endereco endereco) {
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
        this.endereco = endereco;
    }

    public void apresentarse() {
        System.out.println("Loja: " + nomeFantasia);
        System.out.println("CNPJ: " + cnpj);
        endereco.apresentarLogradouro();
        System.out.println("Clientes: " + clientes.size());
        System.out.println("Vendedores: " + vendedores.size());
        System.out.println("Gerentes: " + gerentes.size());
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

    List<Item> itens = new ArrayList<>();

    public Pedido(
            int id,
            Cliente cliente,
            Vendedor vendedor,
            Loja loja,
            Date dataCriacao,
            Date dataVencimentoReserva
    ) {
        this.id = id;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.loja = loja;
        this.dataCriacao = dataCriacao;
        this.dataVencimentoReserva = dataVencimentoReserva;
    }

    public double calcularValorTotal() {
        double total = 0;
        for (Item item : itens) {
            total += item.valor;
        }
        return total;
    }

    public void gerarDescricaoVenda() {
        System.out.println(
                "Pedido criado em: " + dataCriacao +
                " | Valor total: R$ " + calcularValorTotal()
        );
    }
}

class ProcessaPedido {

    public Pedido processar(
            int id,
            Cliente cliente,
            Vendedor vendedor,
            Loja loja,
            List<Item> itens
    ) {
        Date hoje = new Date();
        Date vencimento = new Date(System.currentTimeMillis() + (2 * 24 * 60 * 60 * 1000));

        Pedido pedido = new Pedido(id, cliente, vendedor, loja, hoje, vencimento);
        pedido.itens.addAll(itens);

        if (confirmarPagamento(pedido)) {
            pedido.dataPagamento = new Date();
            System.out.println("Pagamento confirmado!");
        } else {
            System.out.println("Reserva vencida.");
        }

        return pedido;
    }

    private boolean confirmarPagamento(Pedido pedido) {
        Date atual = new Date();
        return !atual.after(pedido.dataVencimentoReserva);
    }
}

public class Calculadora {

    static Scanner sc = new Scanner(System.in);

    static Endereco enderecoLoja = new Endereco(
            "PR",
            "Ubiratã",
            "Centro",
            1750,
            "Av. Nilza de Oliveira"
    );

    static Loja loja = new Loja(
            "Gabi Floricultura",
            "12.345.678/0001-99",
            enderecoLoja
    );

    static void inicializarDados() {
        Cliente c1 = new Cliente(
                "Maria Oliveira",
                45,
                new Endereco("PR", "Ubiratã", "Centro", 10, "Casa")
        );

        Vendedor v1 = new Vendedor(
                "Carlos Lima",
                35,
                loja.nomeFantasia,
                new Endereco("PR", "Ubiratã", "Mutirão", 59, "Casa"),
                2200
        );

        Gerente g1 = new Gerente(
                "Fernanda Souza",
                40,
                loja.nomeFantasia,
                new Endereco("PR", "Ubiratã", "Centro", 101, "Apto"),
                5000
        );

        loja.clientes.add(c1);
        loja.vendedores.add(v1);
        loja.gerentes.add(g1);
    }

    static void criarPedidoFake() {
        if (loja.clientes.isEmpty() || loja.vendedores.isEmpty()) {
            System.out.println("Necessário cliente e vendedor cadastrados.");
            return;
        }

        List<Item> itens = new ArrayList<>();
        itens.add(new Item(1, "Samambaia", "Planta", 50));
        itens.add(new Item(2, "Vaso Decorativo", "Acessório", 80));

        ProcessaPedido processa = new ProcessaPedido();

        Pedido pedido = processa.processar(
                1,
                loja.clientes.get(0),
                loja.vendedores.get(0),
                loja,
                itens
        );

        pedido.gerarDescricaoVenda();
    }

    public static void main(String[] args) {
        inicializarDados();

        int op;

        do {
            System.out.println("\n===== SISTEMA FLORICULTURA =====");
            System.out.println("[1] Informações da Loja");
            System.out.println("[2] Listar Clientes");
            System.out.println("[3] Listar Vendedores");
            System.out.println("[4] Listar Gerentes");
            System.out.println("[5] Criar Pedido (fake)");
            System.out.println("[6] Sair");
            System.out.print("Escolha: ");

            op = sc.nextInt();

            switch (op) {
                case 1:
                    loja.apresentarse();
                    break;

                case 2:
                    for (Cliente c : loja.clientes) {
                        c.apresentarse();
                    }
                    break;

                case 3:
                    for (Vendedor v : loja.vendedores) {
                        v.apresentarse();
                        System.out.println("Média: " + v.calcularMedia());
                        System.out.println("Bônus: " + v.calcularBonus());
                    }
                    break;

                case 4:
                    for (Gerente g : loja.gerentes) {
                        g.apresentarse();
                        System.out.println("Média: " + g.calcularMedia());
                        System.out.println("Bônus: " + g.calcularBonus());
                    }
                    break;

                case 5:
                    criarPedidoFake();
                    break;

                case 6:
                    System.out.println("Encerrando...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (op != 6);
    }
}