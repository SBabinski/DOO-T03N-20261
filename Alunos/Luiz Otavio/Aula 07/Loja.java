import java.util.ArrayList;

public class Loja {
    String nomeFantasia;
    String razaoSocial;
    String cnpj;
    Endereco endereco;

    ArrayList<Cliente> clientes = new ArrayList<>();
    ArrayList<Vendedor> vendedores = new ArrayList<>();
    ArrayList<Gerente> gerentes = new ArrayList<>();

    public Loja(String nomeFantasia, String razaoSocial, String cnpj, Endereco endereco) {
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.endereco = endereco;
    }
}
