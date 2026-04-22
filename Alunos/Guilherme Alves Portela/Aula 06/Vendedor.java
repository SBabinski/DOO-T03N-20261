import java.util.ArrayList;

public class Vendedor {
    
    // Atributos da classe
    private String nome;
    private int idade;
    private Loja loja;
    private String bairro;
    private String cidade;
    private String rua;
    private double salarioBase;
    private ArrayList<Double> salariosRecebidos;

    public Vendedor(String nome, int idade, Loja loja, String bairro, String cidade, String rua, double salarioBase){
        this.setNome(nome);
        this.setIdade(idade);
        this.setLoja(loja);
        this.setBairro(bairro);
        this.setCidade(cidade);
        this.setRua(rua);
        this.setSalarioBase(salarioBase);
        this.salariosRecebidos = new ArrayList<Double>();
    }

    //Getters

    public String getNome(){return this.nome;}
    
    public int getIdade(){return this.idade;}
    
    public String getCidade(){return this.cidade;}
    
    public String getBairro(){return this.bairro;}
    
    public String getRua(){return rua;}
    
    public Loja getLoja(){return this.loja;}
    
    public double getSalarioBase(){return salarioBase;}
    
    public ArrayList<Double> getSalariosRecebidos(){return salariosRecebidos;}

    //Setters
    
    public void setNome(String nome){this.nome = nome;}
    
    public void setIdade(int idade){this.idade = idade;}
    
    public void setCidade(String cidade){this.cidade = cidade;}
    
    public void setBairro(String novoBairro){this.bairro = novoBairro;}
    
    public void setRua(String rua){this.rua = rua;}
    
    public void setLoja(Loja novaLoja){this.loja = novaLoja;}
    
    public void setSalarioBase(double salarioBase){this.salarioBase = salarioBase;}

    //Setter de salario recebido
    public void pagarSalario(double salarioRecebido){this.salariosRecebidos.add(salarioRecebido);}

    //Métodos para classe Vendedor

    public double calcularMedia(){
        if (salariosRecebidos == null || salariosRecebidos.isEmpty()){
            return 0.0f;
        }
        
        double somaSalarios = 0.0f;
        for (var salario : salariosRecebidos){
            somaSalarios += salario;
        }
        
        double mediaSalarial = somaSalarios / salariosRecebidos.size();
        
        return mediaSalarial;
    }
    
    public double calcularBonus(){
        return this.salarioBase * 0.2f;
    }
    
    public void apresentarSe(){
        System.out.println(String.format("Nome: %s | Idade: %d | Loja: %s",this.nome, this.idade, (this.loja != null ? this.loja.getNomeFantasia() : "Nenhuma")));
    }
}
