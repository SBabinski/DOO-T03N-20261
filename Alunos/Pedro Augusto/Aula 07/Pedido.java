import java.util.Date;
import java.util.ArrayList;

public class Pedido {
    private int id;
    private Date dataCriacao;
    private Date dataPagamento;
    private Date dataVencimentoReserva;
    private Cliente cliente;
    private Vendedor vendedor;
    private Loja loja;
    private ArrayList<Item> itens;

    public Pedido(int id, Cliente cliente, Vendedor vendedor, Loja loja, ArrayList<Item> itens) {
        this.id = id;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.loja = loja;
        this.itens = itens;
        this.dataCriacao = new Date();
        // Reserva vence em 3 dias por padrão
        long tresDiasEmMs = 3L * 24 * 60 * 60 * 1000;
        this.dataVencimentoReserva = new Date(this.dataCriacao.getTime() + tresDiasEmMs);
    }

    public float calcularValorTotal() {
        float total = 0f;
        for (Item item : itens) {
            total += item.getValor();
        }
        return total;
    }

    public void gerarDescricaoVenda() {
        System.out.println(
                "Pedido ID: " + id + " | Criado em: " + dataCriacao + " | Valor Total: R$ " + calcularValorTotal());
    }

    // Getters e Setters
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

    public void setDataVencimentoReserva(Date dataVencimentoReserva) {
        this.dataVencimentoReserva = dataVencimentoReserva;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public Loja getLoja() {
        return loja;
    }

    public ArrayList<Item> getItens() {
        return itens;
    }
}
