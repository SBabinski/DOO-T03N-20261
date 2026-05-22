package myplant.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RegistroVenda {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private LocalDate data;
    private int quantidade;
    private double valorVenda;
    private double desconto;

    public RegistroVenda(LocalDate data, int quantidade, double valorVenda, double desconto) {
        this.data = data;
        this.quantidade = quantidade;
        this.valorVenda = valorVenda;
        this.desconto = desconto;
    }

    public void exibir() {
        System.out.printf("📅 Data: %s | Qtd: %d | Valor: R$ %.2f | Desconto: R$ %.2f%n",
                data.format(FORMATTER), quantidade, valorVenda, desconto);
    }

    // Getters
    public LocalDate getData()     { return data; }
    public int getQuantidade()     { return quantidade; }
    public double getValorVenda()  { return valorVenda; }
    public double getDesconto()    { return desconto; }
}
