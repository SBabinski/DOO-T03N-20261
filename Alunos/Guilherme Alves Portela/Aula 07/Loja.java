import java.util.ArrayList;

public class Loja {

    private String nomeFantasia;
    private String razaoSocial;
    private String cnpj;
    private String cidade;
    private String bairro;
    private String rua;
    private ArrayList<Vendedor> listaDeVendedores;
    private Gerente gerente;
    private ArrayList<Cliente> listaDeClientes;

    public Loja(String nomeFantasia, String razaoSocial, String cnpj, String cidade, String bairro, String rua){
        setNomeFantasia(nomeFantasia);
        setRazaoSocial(razaoSocial);
        setCnpj(cnpj);
        setCidade(cidade);
        setBairro(bairro);
        setRua(rua);
        //Listas de Clientes e Vendedores são inicialmente vazias, Gerente é null
        this.listaDeClientes = new ArrayList<Cliente>();
        this.listaDeVendedores = new ArrayList<Vendedor>();
        this.gerente = null;
    }

    //Getters
    
    public String getNomeFantasia(){return nomeFantasia;}
    
    public String getRazaoSocial(){return razaoSocial;}
    
    public String getCnpj(){return cnpj;}
    
    public String getCidade(){return cidade;}
    
    public String getBairro(){return bairro;}
    
    public String getRua(){return rua;}
    
    public ArrayList<Vendedor> getListaDeVendedores(){return listaDeVendedores;}

    public Gerente getGerente(){return gerente;}
    
    public ArrayList<Cliente> getListaDeClientes(){return listaDeClientes;}
    
    //Setters
    
    public void setNomeFantasia(String nomeFantasia){this.nomeFantasia = nomeFantasia;}
    
    public void setRazaoSocial(String razaoSocial){this.razaoSocial = razaoSocial;}
    
    public void setCnpj(String cnpj){this.cnpj = cnpj;}
    
    public void setCidade(String cidade){this.cidade = cidade;}
    
    public void setBairro(String bairro){this.bairro = bairro;}
    
    public void setRua(String rua){this.rua = rua;}
    
    public void cadastrarVendedor(Vendedor novoVendedor){this.listaDeVendedores.add(novoVendedor);}

    public void setGerente(Gerente novoGerente){this.gerente = novoGerente;}

    public void cadastrarCliente(Cliente novoCliente){this.listaDeClientes.add(novoCliente);}
    
    //Métodos para classe Loja
    
    public void contarClientes(){
        System.out.println("Quantidade de clientes: " + this.listaDeClientes.size());
    }

    public void contarVendedores(){
        System.out.println("Quantidade de vendedores: " + this.listaDeVendedores.size());
    }

    public void temGerente(){
        if (this.gerente != null) {
            System.out.println("Gerente: " + this.gerente.getNome());
        } else {
            System.out.println("Gerente: Nenhum");
        }
    }

    public int totalFuncionarios(){
        int total = this.listaDeVendedores.size();
        if (this.gerente != null) {
            total += 1;
        }
        return total;
    }

    public void apresentarSe(){
        String endereco = String.format("%s, %s, %s",this.cidade, this.bairro, this.rua);

        System.out.println("Nome Fantasia: " + this.nomeFantasia);
        System.out.println("CNPJ: " + this.cnpj);
        System.out.println("Endereço: " + endereco);
    }
}
