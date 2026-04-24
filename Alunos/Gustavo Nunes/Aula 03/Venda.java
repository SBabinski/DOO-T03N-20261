package loja.model;

public class Venda {
    private static int contId = 1;
    private int id;
    private int quant;
    private double valVen;
    private double desconto;
    private double valFin;

    public Venda(int quant, double valVen, double desconto){
        this.id = contId++; // Gera id
        this.quant = quant;
        this.valVen = valVen;
        this.desconto = desconto;
        this.valFin = valVen - desconto;
    }

    @Override
    public String toString() {
        return String.format("  %-3d   %-10d   %-10.2f   %-10.2f   %-10.2f  ",
                id, quant, valVen, desconto, valFin);
    }
}
