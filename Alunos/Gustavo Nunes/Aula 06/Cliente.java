package Entities;

public class Cliente {
    private static int contId = 1;
    private int id;
    private String nome;
    private int idade;
    private String cidade;
    private String bairro;
    private String rua;
    
    public Cliente (String nome, int idade, String cidade,
                    String bairro, String rua){
        this.id = contId++; // Gera id
        this.nome = nome;
        this.idade = idade;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
    }
    
    // Retorna nome e idade do cliente
    public String apresentarse(){
        return String.format("\n" + id + "  |  " + nome + "  |  " +
                idade + " anos  |  " + cidade + "  |  " +
                bairro + "  |  " + rua);
    }
}
