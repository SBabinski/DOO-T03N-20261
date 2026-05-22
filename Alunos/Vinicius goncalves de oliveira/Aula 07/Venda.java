import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Venda {
    private int quantidade;
    private double preco;
    private double total;
    private double desconto;
    private LocalDate dataVenda;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Venda(int quantidade, double preco, double total, double desconto, LocalDate dataVenda) {
        this.quantidade = quantidade;
        this.preco = preco;
        this.total = total;
        this.desconto = desconto;
        this.dataVenda = dataVenda;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public double getTotal() {
        return total;
    }

    public double getDesconto() {
        return desconto;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "quantidade=" + quantidade +
                ", preco=" + preco +
                ", total=" + total +
                ", desconto=" + desconto +
                ", dataVenda=" + (dataVenda != null ? dataVenda.format(FORMATTER) : "sem data") +
                '}';
    }
}
