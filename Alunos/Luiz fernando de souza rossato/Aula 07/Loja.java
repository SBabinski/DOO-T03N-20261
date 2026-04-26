import java.util.ArrayList;

public class Loja {
    String nomeFantasia;
    String razaoSocial;
    String cnpj;
    Endereco endereco;

    ArrayList<Vendedor> vendedores = new ArrayList<>();
    ArrayList<Gerente> gerentes = new ArrayList<>();
    ArrayList<Cliente> clientes = new ArrayList<>();

    public Loja(String nomeFantasia, String razaoSocial,
                String cnpj, Endereco endereco) {

        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.endereco = endereco;

        // Vendedores
        vendedores.add(new Vendedor(
                "João",
                30,
                new Endereco("PR", "Cascavel", "Jardim Itália", "120", "Casa"),
                this,
                1800.00
        ));

        vendedores.add(new Vendedor(
                "Ana",
                28,
                new Endereco("PR", "Cascavel", "Centro", "85", "Apto 202"),
                this,
                1800.00
        ));

        // Gerente
        gerentes.add(new Gerente(
                "Carlos",
                40,
                new Endereco("PR", "Cascavel", "Centro", "300", "Sala 1"),
                this,
                3500.00
        ));

        // Clientes
        clientes.add(new Cliente(
                "Maria",
                25,
                new Endereco("PR", "Cascavel", "São Cristóvão", "450", "Casa")
        ));

        clientes.add(new Cliente(
                "Pedro",
                40,
                new Endereco("PR", "Cascavel", "Coqueiral", "210", "Fundos")
        ));
    }

    public void contarClientes() {
        System.out.println("Quantidade de clientes: " + clientes.size());
    }

    public void contarVendedores() {
        System.out.println("Quantidade de vendedores: " + vendedores.size());
    }

    public void contarGerentes() {
        System.out.println("Quantidade de gerentes: " + gerentes.size());
    }

    public void apresentarSe() {
        System.out.println("Nome Fantasia: " + nomeFantasia);
        System.out.println("Razão Social: " + razaoSocial);
        System.out.println("CNPJ: " + cnpj);
        endereco.apresentarLogradouro();
    }

    public void mostrarDetalhes() {
        System.out.println("\n========== LOJA ==========");
        apresentarSe();

        System.out.println("\n======= GERENTES =======");
        for (Gerente g : gerentes) {
            g.apresentarSe();
            System.out.println("Salário Base: R$ " + g.salarioBase);
            System.out.println("Média Salarial: R$ " + g.calcularMedia());
            System.out.println("Bônus: R$ " + g.calcularBonus());
            System.out.println("-------------------------");
        }

        System.out.println("\n====== VENDEDORES ======");
        for (Vendedor v : vendedores) {
            v.apresentarSe();
            System.out.println("Salário Base: R$ " + v.salarioBase);
            System.out.println("Média Salarial: R$ " + v.calcularMedia());
            System.out.println("Bônus: R$ " + v.calcularBonus());
            System.out.println("-------------------------");
        }

        System.out.println("\n======= CLIENTES =======");
        for (Cliente c : clientes) {
            c.apresentarSe();
            System.out.println("-------------------------");
        }
    }
}