import java.util.ArrayList;

public class Loja {
    private String nomeFantasia;
    private String razaoSocial;
    private String cnpj;
    private String cidade;
    private String bairro;
    private String rua;

    private ArrayList<Vendedor> vendedores;
    private ArrayList<Cliente> clientes;
    private ArrayList<Gerente> gerentes;

    public Loja(String nomeFantasia, String razaoSocial, String cnpj, String cidade, String bairro, String rua) {
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;

        vendedores = new ArrayList<>();
        clientes = new ArrayList<>();
        gerentes = new ArrayList<>();   
    }

    public void adicionarVendedor(Vendedor v) {
        vendedores.add(v);
    }

    public void adicionarGerente(Gerente g) {
        gerentes.add(g);
    }

    public void adicionarCliente(Cliente c) {
        clientes.add(c);
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

    public void apresentarSe() {
        System.out.println("Nome Fantasia: " + nomeFantasia);
        System.out.println("Razão Social: " + razaoSocial);
        System.out.println("CNPJ: " + cnpj);
        System.out.println("Endereço: " + rua + ", " + bairro + ", " + cidade);
    }
}