import java.util.ArrayList;

public class Loja {
    private String nomeLoja;
    private String razaoSocial;
    private String cnpj;
    private String cidade;
    private String bairro;
    private String rua;
    private ArrayList<Vendedor> vendedores;
    private ArrayList<Cliente> clientes;

    public Loja(String nomeLoja, String razaoSocial, String cnpj, String cidade, String bairro, String rua) {
        this.nomeLoja = nomeLoja;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.vendedores = new ArrayList<>();
        this.clientes = new ArrayList<>();
    }

    public void adicionarVendedor(Vendedor vendedor) {

        vendedores.add(vendedor);
    }

    public void adicionarCliente(Cliente cliente) {

        clientes.add(cliente);
    }

    public int contarClientes() {

        return clientes.size();
    }

    public int contarVendedores() {

        return vendedores.size();
    }

    public void apresentarse() {
        System.out.println("Loja: " + nomeLoja);
        System.out.println("CNPJ: " + cnpj);
        System.out.println("Endereco: " + rua + ", " + bairro + ", " + cidade);
    }

    public String getNomeLoja() {

        return nomeLoja;
    }

    public String getRazaoSocial() {

        return razaoSocial;
    }

    public String getCnpj() {

        return cnpj;
    }

    public String getCidade() {

        return cidade;
    }

    public String getBairro() {

        return bairro;
    }

    public String getRua() {

        return rua;
    }
}
