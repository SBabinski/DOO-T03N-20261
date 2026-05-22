import java.time.LocalDate;

public class Venda {

    private String nomePlanta;
    private int quantidade;
    private double valorTotal;
    private double descontoAplicado;
    private LocalDate dataVenda;

    public Venda(String nomePlanta, int quantidade, double valorTotal,
                 double descontoAplicado, LocalDate dataVenda) {

        this.nomePlanta = nomePlanta;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.descontoAplicado = descontoAplicado;
        this.dataVenda = dataVenda;
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

    public LocalDate getDataVenda() {
        return dataVenda;
    }
}