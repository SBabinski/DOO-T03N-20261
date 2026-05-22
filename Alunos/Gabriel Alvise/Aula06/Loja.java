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

    public Loja(String nomeFantasia, String razaoSocial, String cnpj, String cidade, String bairro, String rua, ArrayList<Vendedor> vendedores, ArrayList<Cliente> clientes) {
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.vendedores = vendedores;
        this.clientes = clientes;
    }

    public void contarClientes() {
        int qtdClientes = clientes.size();
        System.out.printf("Total de clientes: %d\n", qtdClientes);
    }

    public void contarVendedores() {
        int qtdVendedores = vendedores.size();
        System.out.printf("Total de vendedores: %d\n", qtdVendedores);
    }

    public void apresentarse() {
        String endereco = rua + " - " + bairro + " - " + cidade;
        System.out.printf("Nome Fantasia: %s | CNPJ: %s | Endereço: %s\n", nomeFantasia, cnpj, endereco);
    }

}
