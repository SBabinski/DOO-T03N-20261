import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Cliente {
    
    private String nome;
    private int idade;
    private String cidade;
    private String bairro;
    private String rua;

     public Cliente(String nome, int idade, String cidade, String bairro, String rua) {
        this.nome = nome;
        this.idade = idade;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
    }

    public void mostrarCliente() {
       System.out.println("O Nome do cliente é " + nome + " e tem " + idade + " anos.");
    }
}
