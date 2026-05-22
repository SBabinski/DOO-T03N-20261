import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Pedido {
    private int id;
    private Date dataCriacao;
    private Date dataPagamento;
    private Date dataVencimentoReserva;
    private LocalDate dataVenda;
    private Cliente cliente;
    private Vendedor vendedor;
    private Loja loja;
    private int quantidade;
    private double desconto;
    private ArrayList<Item> itens;

    public Pedido(int id, Date dataCriacao, Date dataPagamento, Date dataVencimentoReserva,
                  Cliente cliente, Vendedor vendedor, Loja loja,
                  int quantidade, double desconto, LocalDate dataVenda) {
        this.id = id;
        this.dataCriacao = dataCriacao;
        this.dataPagamento = dataPagamento;
        this.dataVencimentoReserva = dataVencimentoReserva;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.loja = loja;
        this.quantidade = quantidade;
        this.desconto = desconto;
        this.dataVenda = dataVenda;
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(Item item) {
        itens.add(item);
    }

    public double calcularValorTotal() {
        double total = 0;
        for (Item item : itens) total += item.getValor();
        return total - desconto;
    }

    public void gerarDescricaoVenda() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("========== PEDIDO #" + id + " ==========");
        System.out.println("   Data da venda  : " + dataVenda.format(fmt));
        System.out.println("   Criado em      : " + dataCriacao);
        System.out.println("   Cliente        : " + cliente.getNome());
        System.out.println("   Vendedor       : " + vendedor.getNome());
        System.out.println("   Quantidade     : " + quantidade + " unidade(s)");
        System.out.println("   Itens:");
        for (Item item : itens) item.gerarDescricao();
        if (desconto > 0) {
            System.out.printf("   Desconto       : -R$ %.2f%n", desconto);
        }
        System.out.printf("   Total          : R$ %.2f%n", calcularValorTotal());
        System.out.println("====================================");
    }

    public int getId() { return id; }
    public Date getDataCriacao() { return dataCriacao; }
    public Date getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(Date dataPagamento) { this.dataPagamento = dataPagamento; }
    public Date getDataVencimentoReserva() { return dataVencimentoReserva; }
    public LocalDate getDataVenda() { return dataVenda; }
    public Cliente getCliente() { return cliente; }
    public Vendedor getVendedor() { return vendedor; }
    public Loja getLoja() { return loja; }
    public int getQuantidade() { return quantidade; }
    public double getDesconto() { return desconto; }
    public ArrayList<Item> getItens() { return itens; }
}