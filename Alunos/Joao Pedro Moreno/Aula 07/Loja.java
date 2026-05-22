import java.util.ArrayList;

public class Loja {

    private String nome;
    private String razaoSocial;
    private String cnpj;
    private Endereco endereco;

    private ArrayList<Vendedor> vendedores = new ArrayList<>();
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private ArrayList<Gerente> gerentes = new ArrayList<>();

    // Construtor completo
    public Loja(String nome, String razaoSocial, String cnpj, Endereco endereco) {
        this.nome = nome;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.endereco = endereco;
        popularDados();
    }

    // Popula listas com dados
    private void popularDados() {
        clientes.add(new Cliente("João", 30, new Endereco("São Paulo", "Centro", "Rua A")));
        clientes.add(new Cliente("Maria", 25, new Endereco("Rio de Janeiro", "Copacabana", "Rua B")));
        clientes.add(new Cliente("José", 33, new Endereco("Curitiba", "Bairro C", "Rua C")));
        clientes.add(new Cliente("Matheus", 40, new Endereco("Florianópolis", "Ilha Grande", "Rua D")));
        clientes.add(new Cliente("Julia", 18, new Endereco("Toledo", "Panorama", "Rua E")));

        vendedores.add(new Vendedor("Carlos", 28, "Loja A", new double[] { 2500, 2600, 2550 }, 2500,
                new Endereco("São Paulo", "Centro", "Rua F")));
        vendedores.add(new Vendedor("Ana", 35, "Loja B", new double[] { 2500, 2600, 2550 }, 2500,
                new Endereco("Rio de Janeiro", "Copacabana", "Rua G")));
        vendedores.add(new Vendedor("Pedro", 30, "Loja C", new double[] { 2500, 2600, 2550 }, 2500,
                new Endereco("Curitiba", "Bairro C", "Rua H")));
        vendedores.add(new Vendedor("Lucas", 27, "Loja D", new double[] { 2500, 2600, 2550 }, 2500,
                new Endereco("Florianópolis", "Ilha Grande", "Rua I")));
        vendedores.add(new Vendedor("Mariana", 32, "Loja E", new double[] { 2500, 2600, 2550 }, 2500,
                new Endereco("Toledo", "Panorama", "Rua J")));

        gerentes.add(new Gerente("Fernanda", 40, "Loja A", new double[] { 4000, 4100, 4050 }, 4000,
                new Endereco("São Paulo", "Centro", "Rua K")));
        gerentes.add(new Gerente("Ricardo", 45, "Loja B", new double[] { 4000, 4100, 4050 }, 4000,
                new Endereco("Rio de Janeiro", "Copacabana", "Rua L")));
        gerentes.add(new Gerente("Sofia", 38, "Loja C", new double[] { 4000, 4100, 4050 }, 4000,
                new Endereco("Curitiba", "Bairro C", "Rua M")));
        gerentes.add(new Gerente("Gustavo", 42, "Loja D", new double[] { 4000, 4100, 4050 }, 4000,
                new Endereco("Florianópolis", "Ilha Grande", "Rua N")));
        gerentes.add(new Gerente("Isabela", 37, "Loja E", new double[] { 4000, 4100, 4050 }, 4000,
                new Endereco("Toledo", "Panorama", "Rua O")));
    }

    // Getters e contadores
    public String getNome() {
        return nome;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public ArrayList<Vendedor> getVendedores() {
        return vendedores;
    }

    public ArrayList<Gerente> getGerentes() {
        return gerentes;
    }

    public int contarClientes() {
        return clientes.size();
    }

    public int contarVendedores() {
        return vendedores.size();
    }

    public int contarGerentes() {
        return gerentes.size();
    }

    // Método para exibir detalhes da loja
    public void mostrarLoja() {
        System.out.println("Nome: " + nome);
        System.out.println("Razão Social: " + razaoSocial);
        System.out.println("CNPJ: " + cnpj);
        System.out.println("Endereço: " + endereco);
    }
}
