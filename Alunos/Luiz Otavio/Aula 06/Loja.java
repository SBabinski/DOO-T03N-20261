import java.util.ArrayList;

public class Loja {

    String nomeFantasia;
    String razaoSocial;
    String cnpj;
    String cidade;
    String bairro;
    String rua;

    ArrayList<Cliente> clientes = new ArrayList<>();
    ArrayList<Vendedor> vendedores = new ArrayList<>();

    public Loja(String nomeFantasia, String razaoSocial, String cnpj,
                String cidade, String bairro, String rua) {

        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
    }
}