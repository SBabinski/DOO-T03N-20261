import java.util.ArrayList;

public class Loja {
    // nome fantasia, razão social, cnpj, cidade, bairro, rua, array de vendedores, array de clientes
    String nome ;
    String social;
    String CNPJ;
    String cidade;
    String bairro;
    String rua;
    ArrayList<Vendedor> vendedors;
    ArrayList<Cliente> clientes;
    Endereco endereco;

    public Loja (String nome , String social , String CNPJ , String cidade , String bairro , String rua , Endereco endereco){
        this.nome = nome;
        this.bairro = bairro;
        this.rua = rua;
        this.social = social ;
        this.CNPJ = CNPJ;
        this.cidade = cidade;
        this.vendedors = new ArrayList<>();
        this.clientes = new ArrayList<>() ;
        this.endereco = endereco;
    }

    public void contarClientes(){
        System.out.println("Quantidade de clientes : " + clientes.size());
    }

    public void contarVendedores(){
        System.out.println("Quantidade de vendedores : " + vendedors.size());
    }

    public void apresentacaoLoja(){
        System.out.println("Loja : " + nome);
        System.out.println("CNPJ : " + CNPJ);
        endereco.apresentaEndereco();
    }
}
