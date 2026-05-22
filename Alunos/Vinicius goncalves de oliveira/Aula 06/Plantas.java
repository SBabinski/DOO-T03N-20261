import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Plantas {
    int quantidade;
    double preco;
    double total;
    double desconto;
    LocalDate dataVenda;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Plantas() {}

    public Plantas(int quantidade, double preco, double total, double desconto, LocalDate dataVenda) {
        this.quantidade = quantidade;
        this.preco = preco;
        this.total = total;
        this.desconto = desconto;
        this.dataVenda = dataVenda; // NOVO
    }

    public int getQuantidade() {

        return quantidade;
    }
    public void setQuantidade(int quantidade) {

        this.quantidade = quantidade;
    }
    public double getPreco() {

        return preco;
    }
    public void setPreco(double preco) {

        this.preco = preco;
    }
    public double getTotal() {

        return total;
    }
    public void setTotal(double total) {

        this.total = total;
    }
    public double getDesconto() {

        return desconto;
    }
    public void setDesconto(double desconto) {

        this.desconto = desconto;
    }
    public LocalDate getDataVenda() {

        return dataVenda;
    }
    public void setDataVenda(LocalDate dataVenda) {

        this.dataVenda = dataVenda;
    }

    @Override
    public String toString() {
        return "Plantas{" +
                "quantidade=" + quantidade +
                ", preco=" + preco +
                ", total=" + total +
                ", desconto=" + desconto +
                ", dataVenda=" + (dataVenda != null ? dataVenda.format(FORMATTER) : "sem data") +
                '}';
    }
}