public class Venda {

    private String nomePlanta;
    private int quantidade;
    private double valorTotal;
    private double descontoAplicado;

    public Venda(String nomePlanta, int quantidade, double valorTotal, double descontoAplicado) {
        this.nomePlanta = nomePlanta;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.descontoAplicado = descontoAplicado;
    }

    public String getNomePlanta() {
        return nomePlanta;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public double getDescontoAplicado() {
        return descontoAplicado;
    }
}