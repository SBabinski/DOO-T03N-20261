public class Vendas {

private int quantidade;
private double preco;
private double desconto;

public Vendas(int quantidade, double preco, double desconto) {

    this.quantidade = quantidade;
    this.preco = preco;
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

public double getDesconto() {
    return desconto;
}

public void setDesconto(double desconto) {
    this.desconto = desconto;
}





}