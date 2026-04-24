import java.time.LocalDate;

public class Calculadora {
    private int quantidade;
    private double preco;
    private double descontoAplicado;
    private LocalDate saleDate;

    public Calculadora() {
    }

    public Calculadora(int quantidade, double preco, double descontoAplicado) {
        this.quantidade = quantidade;
        this.preco = preco;
        this.descontoAplicado = descontoAplicado;
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

    public double getDescontoAplicado() {
        return descontoAplicado;
    }

    public void setDescontoAplicado(double descontoAplicado) {
        this.descontoAplicado = descontoAplicado;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }
}
