import java.util.Date;

public class Pedido {

    private int id;
    private Date dataCriacao;
    private Date dataPagamento;
    private Date dataVencimentoReserva;
    private Cliente cliente;
    private Vendedor vendedor;
    private Loja loja;

    private Item[] itens;

    public Pedido(int id, Date dataCriacao, Date dataVencimentoReserva,
            Cliente cliente, Vendedor vendedor, Loja loja, Item[] itens) {

        this.id = id;
        this.dataCriacao = dataCriacao;
        this.dataVencimentoReserva = dataVencimentoReserva;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.loja = loja;
        this.itens = itens;
    }

    public double calcularValorTotal() {

        double total = 0;

        for (Item i : itens) {
            total += i.getValor();
        }

        return total;
    }

    public void gerarDescricaoVenda() {

        System.out.println("Data criação: " + dataCriacao);
        System.out.println("Valor total: " + calcularValorTotal());
    }

    public Date getDataVencimentoReserva() {
        return dataVencimentoReserva;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}