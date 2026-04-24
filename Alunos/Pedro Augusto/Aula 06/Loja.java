import java.util.ArrayList;

public class Loja {
    private String nomeFantasia;
    private String razaoSocial;
    private String cnpj;
    private String cidade;
    private String bairro;
    private String rua;
    private ArrayList<Vendedor> listaDeVendedores;
    private ArrayList<Cliente> listaDeClientes;

    public Loja(String nomeFantasia, String razaoSocial, String cnpj, String cidade, String bairro, String rua) {
        this.setNomeFantasia(nomeFantasia);
        this.setRazaoSocial(razaoSocial);
        this.setCnpj(cnpj);
        this.setCidade(cidade);
        this.setBairro(bairro);
        this.setRua(rua);
        this.listaDeClientes = new ArrayList<Cliente>();
        this.listaDeVendedores = new ArrayList<Vendedor>();
        // inicializa a loja com lista de vendedores e clientes vazias
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public ArrayList<Vendedor> getListaDeVendedores() {
        return listaDeVendedores;
    }

    public void cadastrarVendedor(Vendedor novoVendedor) {
        this.listaDeVendedores.add(novoVendedor);
    }

    public ArrayList<Cliente> getListaDeClientes() {
        return listaDeClientes;
    }

    public void cadastrarCliente(Cliente novoCliente) {
        this.listaDeClientes.add(novoCliente);
    }

    public void contarClientes() {
        System.out.println("Quantidade de clientes: " + this.listaDeClientes.size());
    }

    public void contarVendedores() {
        System.out.println("Quantidade de vendedores: " + this.listaDeVendedores.size());
    }

    public void apresentarSe() {
        String endereco = this.rua + this.bairro + this.cidade;

        System.out.println("Nome Fantasia: " + this.nomeFantasia);
        System.out.println("CNPJ: " + this.cnpj);
        System.out.println("Endereço: " + endereco);
    }

    @Override
    public String toString() {
        return this.nomeFantasia;
    }
}
