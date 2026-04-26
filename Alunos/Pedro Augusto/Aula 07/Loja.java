import java.util.ArrayList;

public class Loja {
    private String nomeFantasia;
    private String razaoSocial;
    private String cnpj;
    private Endereco endereco;
    private ArrayList<Vendedor> listaDeVendedores;
    private ArrayList<Cliente> listaDeClientes;
    private ArrayList<Gerente> listaDeGerentes;

    public Loja(String nomeFantasia, String razaoSocial, String cnpj, Endereco endereco) {
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.listaDeClientes = new ArrayList<>();
        this.listaDeVendedores = new ArrayList<>();
        this.listaDeGerentes = new ArrayList<>();
    }

    public void cadastrarVendedor(Vendedor vendedor) {
        listaDeVendedores.add(vendedor);
    }

    public void cadastrarCliente(Cliente cliente) {
        listaDeClientes.add(cliente);
    }

    public void cadastrarGerente(Gerente gerente) {
        listaDeGerentes.add(gerente);
    }

    public void apresentarSe() {
        System.out.println("---------LOJA-------");
        System.out.println("Nome Fantasia: " + nomeFantasia);
        System.out.println("CNPJ: " + cnpj);
        if (endereco != null) {
            endereco.apresentarLogradouro();
        }
    }

    // Getters e Setters
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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public ArrayList<Vendedor> getListaDeVendedores() {
        return listaDeVendedores;
    }

    public ArrayList<Cliente> getListaDeClientes() {
        return listaDeClientes;
    }

    public ArrayList<Gerente> getListaDeGerentes() {
        return listaDeGerentes;
    }

    @Override
    public String toString() {
        return nomeFantasia;
    }
}
