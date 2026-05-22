public class Aula06 {
    public static void main(String[] args) {

        Loja loja = new Loja(
                "My Plant",
                "My Plant LTDA",
                "12.345.678/0001-99",
                "Cascavel",
                "Centro",
                "Rua das Flores"
        );

        Cliente cliente1 = new Cliente("Joao", 20, "Cascavel", "Centro", "Rua A");
        Cliente cliente2 = new Cliente("Maria", 25, "Cascavel", "Bairro Alto", "Rua B");

        Cliente[] clientes = {cliente1, cliente2};
        loja.clientes = clientes;

        double[] salariosVendedor1 = {2000.0, 2100.0, 2200.0};
        double[] salariosVendedor2 = {1800.0, 1850.0, 1900.0};

        Vendedor vendedor1 = new Vendedor(
                "Carlos",
                30,
                loja,
                "Cascavel",
                "Centro",
                "Rua X",
                2000.0,
                salariosVendedor1
        );

        Vendedor vendedor2 = new Vendedor(
                "Ana",
                28,
                loja,
                "Cascavel",
                "Cancelli",
                "Rua Y",
                1800.0,
                salariosVendedor2
        );

        Vendedor[] vendedores = {vendedor1, vendedor2};
        loja.vendedores = vendedores;

        System.out.println("===== APRESENTACAO DA LOJA =====");
        loja.apresentarse();

        System.out.println();
        System.out.println("Quantidade de clientes: " + loja.contarClientes());
        System.out.println("Quantidade de vendedores: " + loja.contarVendedores());

        System.out.println();
        System.out.println("===== APRESENTACAO DOS CLIENTES =====");
        cliente1.apresentarse();
        System.out.println();
        cliente2.apresentarse();

        System.out.println();
        System.out.println("===== APRESENTACAO DOS VENDEDORES =====");
        vendedor1.apresentarse();
        System.out.println("Media dos salarios: " + vendedor1.calcularMedia());
        System.out.println("Bonus: " + vendedor1.calcularBonus());

        System.out.println();

        vendedor2.apresentarse();
        System.out.println("Media dos salarios: " + vendedor2.calcularMedia());
        System.out.println("Bonus: " + vendedor2.calcularBonus());
    }
}

class Loja {
    String nomeFantasia;
    String razaoSocial;
    String cnpj;
    String cidade;
    String bairro;
    String rua;
    Vendedor[] vendedores;
    Cliente[] clientes;

    public Loja(String nomeFantasia, String razaoSocial, String cnpj, String cidade, String bairro, String rua) {
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
    }

    public int contarClientes() {
        if (clientes == null) {
            return 0;
        }
        return clientes.length;
    }

    public int contarVendedores() {
        if (vendedores == null) {
            return 0;
        }
        return vendedores.length;
    }

    public void apresentarse() {
        System.out.println("Nome fantasia: " + nomeFantasia);
        System.out.println("CNPJ: " + cnpj);
        System.out.println("Endereco: " + rua + ", " + bairro + ", " + cidade);
    }
}

class Vendedor {
    String nome;
    int idade;
    Loja loja;
    String cidade;
    String bairro;
    String rua;
    double salarioBase;
    double[] salarioRecebido;

    public Vendedor(String nome, int idade, Loja loja, String cidade, String bairro, String rua, double salarioBase, double[] salarioRecebido) {
        this.nome = nome;
        this.idade = idade;
        this.loja = loja;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.salarioBase = salarioBase;
        this.salarioRecebido = salarioRecebido;
    }

    public void apresentarse() {
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Loja: " + loja.nomeFantasia);
    }

    public double calcularMedia() {
        double soma = 0;

        for (int i = 0; i < salarioRecebido.length; i++) {
            soma = soma + salarioRecebido[i];
        }

        return soma / salarioRecebido.length;
    }

    public double calcularBonus() {
        return salarioBase * 0.2;
    }
}

class Cliente {
    String nome;
    int idade;
    String cidade;
    String bairro;
    String rua;

    public Cliente(String nome, int idade, String cidade, String bairro, String rua) {
        this.nome = nome;
        this.idade = idade;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
    }

    public void apresentarse() {
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
    }
}