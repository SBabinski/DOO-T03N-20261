public class Cliente {

    // Atributos da classe

    private String nome;
    private int idade;
    private String cidade;
    private String bairro;
    private String rua;

    public Cliente(String nome, int idade, String cidade, String bairro, String rua){
        setNome(nome);
        setIdade(idade);
        setCidade(cidade);
        setBairro(bairro);
        setRua(rua);
    }
    
    //Getters

    public String getNome(){return nome;}
    
    public int getIdade(){return idade;}
    
    public String getBairro(){return bairro;}
    
    public String getCidade(){return cidade;}
    
    public String getRua(){return rua;}
    
    //Setters
   
    public void setNome(String nome){this.nome = nome;}
    
    public void setIdade(int idade){this.idade = idade;}
     
    public void setCidade(String cidade){this.cidade = cidade;}
    
    public void setBairro(String bairro){this.bairro = bairro;}
   
    public void setRua(String rua){this.rua = rua;}
    
    // Métodos para classe cliente

    public void apresentarSe(){
        System.out.println(String.format("Nome: %s | Idade: %d ",this.nome, this.idade));
    
    }
}