import java.time.LocalDate;

public class Vendas {

    private int quantidade;
    private double preco;
    private double desconto;
    private LocalDate data;
    private int qdvendida;

    public Vendas(int quantidade, double preco, double desconto, LocalDate data, int qdvendida) {

        this.quantidade = quantidade;
        this.preco = preco;
        this.desconto = desconto;
        this.data = data;
        this.qdvendida = qdvendida;
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

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public LocalDate getdata() {
        return data;
    }

    public void setdata(LocalDate data) {
        this.data = data;
    }

    public int getqdvendida() {
        return qdvendida;
    }

    public void setqdvendida(int qdvendida) {
        this.qdvendida = qdvendida;
    }
}