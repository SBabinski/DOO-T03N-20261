package obj;

public class Produto {

    private String nomeProduto;
    private double precoUnitario;

    public Produto(String nomeProduto, double precoUnitario) {
        this.nomeProduto = nomeProduto;
        this.precoUnitario = precoUnitario;
    }

    public String getNomeProduto() {

        return nomeProduto;
    }

    public double getPrecoUnitario() {

        return precoUnitario;
    }
}

