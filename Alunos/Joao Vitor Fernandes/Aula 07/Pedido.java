import java.util.Date;

public class Pedido {
    private int id;
    private Date dataCriacao;
    private Date dataPagamento;
    private Date dataVencimentoReserva;
    private Cliente cliente;
    private Vendedor vendedor;
    private Gerente gerente;
    private String loja;
    private Item[] itens;

    public Pedido(int id, Date dataCriacao, Date dataVencimentoReserva, 
                  Cliente cliente, Vendedor vendedor, Gerente gerente, String loja) {
        this.id = id;
        this.dataCriacao = dataCriacao;
        this.dataVencimentoReserva = dataVencimentoReserva;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.gerente = gerente;
        this.loja = loja;
        this.itens = new Item[0];
        this.dataPagamento = null;
    }

    public void adicionarItem(Item item) {
        Item[] novosItens = new Item[itens.length + 1];
        System.arraycopy(itens, 0, novosItens, 0, itens.length);
        novosItens[itens.length] = item;
        itens = novosItens;
    }

    public double calcularValorTotal() {
        double total = 0;
        for (Item item : itens) {
            total += item.getValor();
        }
        return total;
    }

    public void gerarDescricaoVenda() {
        System.out.println("\n📋 ==== DESCRIÇÃO DA VENDA ====");
        System.out.println("ID do Pedido: " + id);
        System.out.println("Data de Criação: " + dataCriacao);
        System.out.println("Valor Total: R$ " + String.format("%.2f", calcularValorTotal()));
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Vendedor: " + vendedor.getNome());
        System.out.println("Gerente: " + gerente.getNome());
        System.out.println("Loja: " + loja);
        System.out.println("Itens do Pedido:");
        for (Item item : itens) {
            item.gerarDescricao();
        }
        System.out.println("===============================\n");
    }

    // Getters
    public int getId() {
        return id;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Date getDataVencimentoReserva() {
        return dataVencimentoReserva;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public Gerente getGerente() {
        return gerente;
    }

    public String getLoja() {
        return loja;
    }

    public Item[] getItens() {
        return itens;
    }
}
