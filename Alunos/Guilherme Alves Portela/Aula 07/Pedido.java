import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Pedido {
    private int id;
    private Date dataCriacao;
    private Date dataPagamento;
    private Date dataVencimentoReserva;
    private Cliente cliente;
    private Vendedor vendedor;
    private Loja loja;
    private ArrayList<Item> itens;

    public Pedido(int id, Date dataCriacao, Date dataVencimentoReserva, Cliente cliente, Vendedor vendedor, Loja loja) {
        this.id = id;
        this.dataCriacao = dataCriacao;
        this.dataPagamento = null;
        this.dataVencimentoReserva = dataVencimentoReserva;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.loja = loja;
        this.itens = new ArrayList<Item>();
    }

    // Getters
    public int getId() {
        return this.id;
    }

    public Date getDataCriacao() {
        return this.dataCriacao;
    }

    public Date getDataPagamento() {
        return this.dataPagamento;
    }

    public Date getDataVencimentoReserva() {
        return this.dataVencimentoReserva;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public Vendedor getVendedor() {
        return this.vendedor;
    }

    public Loja getLoja() {
        return this.loja;
    }

    public ArrayList<Item> getItens() {
        return this.itens;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public void setDataVencimentoReserva(Date dataVencimentoReserva) {
        this.dataVencimentoReserva = dataVencimentoReserva;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }

    // Método para adicionar item ao pedido
    public void adicionarItem(Item item) {
        this.itens.add(item);
    }

    // Método para calcular o valor total do pedido
    public double calcularValorTotal() {
        double total = 0.0;
        for (Item item : this.itens) {
            total += item.getValor();
        }
        return total;
    }

    // Método para gerar descrição da venda
    public void gerarDescricaoVenda() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("===== Descrição da Venda =====");
        System.out.println("ID do Pedido: " + this.id);
        System.out.println("Data de Criação: " + sdf.format(this.dataCriacao));
        System.out.println("Valor Total: R$ " + String.format("%.2f", this.calcularValorTotal()));
        System.out.println("Cliente: " + this.cliente.getNome());
        System.out.println("Vendedor: " + this.vendedor.getNome());
        System.out.println("=============================");
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("Pedido ID: %d | Data: %s | Cliente: %s | Valor Total: R$ %.2f",
                this.id, sdf.format(this.dataCriacao), this.cliente.getNome(), this.calcularValorTotal());
    }
}
