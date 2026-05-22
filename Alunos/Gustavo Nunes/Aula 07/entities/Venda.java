package entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Venda extends Object{
    private static int contId = 1;
    private int id;
    private int quant;
    private double valVen;
    private double desconto;
    private double valFin;
    private LocalDate dataVenda;
    
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    // Builder
    public Venda(int quant, double valVen, double desconto) {
        this.id = contId++; // Gera id
        this.quant = quant;
        this.valVen = valVen;
        this.desconto = desconto;
        this.valFin = valVen - desconto;
        this.dataVenda = LocalDate.now();
    }
    
    // Getters
    public int getYear() {
        return dataVenda.getYear();
    }
    
    public int getMonth() {
        return dataVenda.getMonthValue();
    }
    
    public int getDay() {
        return dataVenda.getDayOfMonth();
    }
    
    // To String
    @Override
    public String toString() {
        return String.format("  %-3d   %-10s   %-10d   %-10.2f   %-10.2f   %-10.2f  ",
                id, fmt.format(dataVenda), quant, valVen, desconto, valFin);
    }
}
