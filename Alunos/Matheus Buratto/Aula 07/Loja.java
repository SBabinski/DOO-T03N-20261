import java.util.ArrayList;

public class Loja {
    private String nomeFantasia;
    private String razaoSocial;
    private String cnpj;
    private Endereco endereco;
    private ArrayList<Vendedor> vendedores;
    private ArrayList<Gerente> gerentes;
    private ArrayList<Cliente> clientes;

    public Loja(String nomeFantasia, String razaoSocial, String cnpj, Endereco endereco) {
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.vendedores = new ArrayList<>();
        this.gerentes = new ArrayList<>();
        this.clientes = new ArrayList<>();
    }

    public void apresentarSe() {
        System.out.println("========== DADOS DA LOJA ==========");
        System.out.println("Nome Fantasia : " + nomeFantasia);
        System.out.println("Razão Social  : " + razaoSocial);
        System.out.println("CNPJ          : " + cnpj);
        endereco.apresentarLogradouro();
        System.out.println("Gerentes      : " + gerentes.size());
        System.out.println("Vendedores    : " + vendedores.size());
        System.out.println("Clientes      : " + clientes.size());
        System.out.println("====================================");
    }

    public void contarClientes() {
        System.out.printf("Total de clientes: %d%n", clientes.size());
    }

    public void contarVendedores() {
        System.out.printf("Total de vendedores: %d%n", vendedores.size());
    }

    public void contarGerentes() {
        System.out.printf("Total de gerentes: %d%n", gerentes.size());
    }

    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }
    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }
    public ArrayList<Vendedor> getVendedores() { return vendedores; }
    public ArrayList<Gerente> getGerentes() { return gerentes; }
    public ArrayList<Cliente> getClientes() { return clientes; }
}