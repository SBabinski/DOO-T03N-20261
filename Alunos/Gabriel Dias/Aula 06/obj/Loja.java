package obj;

public class Loja {

    private String nomeFantasia;
    private String razaoSocial;
    private String cnpj;
    private String cidade;
    private String bairro;
    private String rua;
    private Vendedor[] vendedores;
    private Cliente[] clientes;

    public Loja(String nomeFantasia, String razaoSocial, String cnpj,
                String cidade, String bairro, String rua,
                Vendedor[] vendedores, Cliente[] clientes) {
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.vendedores = vendedores;
        this.clientes = clientes;
    }

    public void apresentarse() {
        System.out.println("=== Loja ===");
        System.out.println("Nome Fantasia : " + nomeFantasia);
        System.out.println("CNPJ         : " + cnpj);
        System.out.println("Endereço     : " + rua + ", " + bairro + " - " + cidade);
    }

    public int contarClientes() {
        return clientes.length;
    }

    public int contarVendedores() {
        return vendedores.length;
    }

    public Vendedor[] getVendedores() { return vendedores; }
    public Cliente[] getClientes() { return clientes; }
    public String getNomeFantasia() { return nomeFantasia; }
    public String getRazaoSocial() { return razaoSocial; }
    public String getCnpj() { return cnpj; }
    public String getCidade() { return cidade; }
    public String getBairro() { return bairro; }
    public String getRua() { return rua; }

    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public void setRua(String rua) { this.rua = rua; }
    public void setVendedores(Vendedor[] vendedores) { this.vendedores = vendedores; }
    public void setClientes(Cliente[] clientes) { this.clientes = clientes; }
}