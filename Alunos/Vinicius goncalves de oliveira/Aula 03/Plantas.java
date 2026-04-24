import java.util.Scanner;

public class Plantas {
int quantidade;
double preco;
double total;
double desconto;

    public Plantas() {}

    public Plantas(int quantidade,  double preco, double total, double desconto) {
        this.quantidade = quantidade;
        this.preco = preco;
        this.total = total;
        this.desconto = desconto;

    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
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

    @Override
    public String toString() {
        return "Plantas{" +
                "quantidade=" + quantidade +
                ", preco=" + preco +
                ", total=" + total +
                ", desconto=" + desconto +
                '}';
    }
}
