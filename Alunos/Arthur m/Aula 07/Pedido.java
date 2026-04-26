import java.util.Date;

public class Pedido {

    int id;
    Date dataCriacao;
    Date dataPagamento;
    Date dataVencimentoReserva;

    Cliente cliente;
    Vendedor vendedor;
    Loja loja;

    Item[] itens = new Item[10];
    int totalItens = 0;

    public Pedido(int id, Cliente cliente, Vendedor vendedor, Loja loja) {
        this.id = id;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.loja = loja;
        this.dataCriacao = new Date();
    }

    public void adicionarItem(Item item) {
        itens[totalItens++] = item;
    }

    public double calcularValorTotal() {
        double total = 0;
        for (int i = 0; i < totalItens; i++) {
            total += itens[i].valor;
        }
        return total;
    }

    public void gerarDescricaoVenda() {
        System.out.println("Pedido criado em: " + dataCriacao);
        System.out.println("Total: R$ " + calcularValorTotal());
    }
}