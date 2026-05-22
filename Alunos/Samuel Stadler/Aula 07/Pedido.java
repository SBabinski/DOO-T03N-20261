import java.util.Date;
public class Pedido {
    private int id;
    private Date dataCriacao;
    private Date dataPagamento;
    private Date dataVencimentoReserva;
    private Cliente cliente;
    private Vendedor vendedor;
    private String loja;
    private Item[] itens;
    public Pedido(int id, Date dataCriacao, Date dataPagamento, Date dataVencimentoReserva,
                  Cliente cliente, Vendedor vendedor, String loja, Item[] itens) {
        this.id = id;
        this.dataCriacao = dataCriacao;
        this.dataPagamento = dataPagamento;
        this.dataVencimentoReserva = dataVencimentoReserva;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.loja = loja;
        this.itens = itens;
    }
    public int getId() { return id; }
    public Date getDataCriacao() { return dataCriacao; }
    public Date getDataPagamento() { return dataPagamento; }
    public Date getDataVencimentoReserva() { return dataVencimentoReserva; }
    public Cliente getCliente() { return cliente; }
    public Vendedor getVendedor() { return vendedor; }
    public String getLoja() { return loja; }
    public Item[] getItens() { return itens; }
    public double calcularValorTotal() {
        double total = 0;
        for (Item item : itens) {
            total += item.getValor();
        }
        return total;
    }
    public void gerarDescricaoVenda() {
        System.out.println("Pedido #" + id);
        System.out.println("Data de Criacao: " + dataCriacao);
        System.out.println("Valor Total: R$ " + calcularValorTotal());
    }
}
