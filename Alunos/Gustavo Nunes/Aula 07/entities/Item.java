package entities;

import entities.enums.TipoENUM;

public class Item {
    private static int geraID = 0;
    private int id;
    private String nome;
    private TipoENUM tipo;
    private double valor;
    
    public Item(String nome, TipoENUM tipo, double valor) {
        this.id = ++geraID;
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
    }
    
    public double getValor() {
        return valor;
    }
    
    // Printa o id, nome, tipo e valor do item
    public String gerarDescricao() {
        return String.format("%d | %s | %s | %.2f",
                id, nome, tipo, valor);
    }
}
