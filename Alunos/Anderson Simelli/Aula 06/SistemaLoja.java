package MyPlantLoja;

public class SistemaLoja {

    public static void main(String[] args) {

        Loja loja = new Loja("My Plant", "My Plant LTDA", "123456789",
                "Corbélia", "Centro", "Rua A");

        Vendedor vendedor1 = new Vendedor("João", 30, loja,
                "Corbélia", "Centro", "Rua B", 2000);

        Cliente cliente1 = new Cliente("Maria", 25,
                "Corbélia", "Centro", "Rua C");

        loja.vendedores = new Vendedor[]{vendedor1};
        loja.clientes = new Cliente[]{cliente1};

        vendedor1.apresentarse();
        System.out.println("Média salarial: " + vendedor1.calcularMedia());
        System.out.println("Bônus: " + vendedor1.calcularBonus());

        cliente1.apresentarse();

        loja.apresentarse();
        loja.contarClientes();
        loja.contarVendedores();
    }
}

// ================= VENDEDOR =================

class Vendedor {

    String nome;
    int idade;
    Loja loja;
    String cidade, bairro, rua;
    double salarioBase;
    double[] salarioRecebido = {2000, 2100, 2200};

    public Vendedor(String nome, int idade, Loja loja,
                    String cidade, String bairro, String rua,
                    double salarioBase) {

        this.nome = nome;
        this.idade = idade;
        this.loja = loja;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.salarioBase = salarioBase;
    }

    public void apresentarse() {
        System.out.println("Nome: " + nome +
                " | Idade: " + idade +
                " | Loja: " + loja.nomeFantasia);
    }

    public double calcularMedia() {

        double soma = 0;

        for (double salario : salarioRecebido) {
            soma += salario;
        }

        return soma / salarioRecebido.length;
    }

    public double calcularBonus() {
        return salarioBase * 0.2;
    }
}

// ================= CLIENTE =================

class Cliente {

    String nome;
    int idade;
    String cidade, bairro, rua;

    public Cliente(String nome, int idade,
                   String cidade, String bairro, String rua) {

        this.nome = nome;
        this.idade = idade;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
    }

    public void apresentarse() {
        System.out.println("Nome: " + nome + " | Idade: " + idade);
    }
}

// ================= LOJA =================

class Loja {

    String nomeFantasia;
    String razaoSocial;
    String cnpj;
    String cidade, bairro, rua;

    Vendedor[] vendedores;
    Cliente[] clientes;

    public Loja(String nomeFantasia, String razaoSocial, String cnpj,
                String cidade, String bairro, String rua) {

        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
    }

    public void contarClientes() {
        System.out.println("Quantidade de clientes: " + clientes.length);
    }

    public void contarVendedores() {
        System.out.println("Quantidade de vendedores: " + vendedores.length);
    }

    public void apresentarse() {
        System.out.println("Loja: " + nomeFantasia +
                " | CNPJ: " + cnpj +
                " | Endereço: " + cidade + ", " + bairro + ", " + rua);
    }
}