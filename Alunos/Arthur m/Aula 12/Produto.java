public class Produto {

    String nome;
    double preco;

    Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    String getNome() {
        return nome;
    }

    double getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return nome + " - R$" + preco;
    }
}