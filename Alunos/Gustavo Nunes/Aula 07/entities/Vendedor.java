package entities;

import java.util.ArrayList;

public class Vendedor extends Funcionario {
    
    public Vendedor(String nome, int idade, Loja loja, String cidade,
                    String bairro, String rua, double salarioBase) {
        this.id = contId++;
        this.nome = nome;
        this.idade = idade;
        this.loja = loja;
        this.endereco = new Endereco("N/I", cidade, bairro, rua, 0, "N/I");
        this.salarioBase = salarioBase;
        this.salarioRecebido = new ArrayList<>();
    }
    
    // Retorna o valor do bônus
    @Override
    public double calcularBonus() {
        return salarioBase * 0.2;
    }
}
