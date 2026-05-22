import java.util.ArrayList;

public class Pedidos {
    ArrayList<Item> item;
    Loja loja;
    Vendedor vendedor;
    Cliente cliente;
    String dataVencimentoReserva;
    String datapagamento;
    String dataCriacao;
    int ID;

    public Pedidos(  ArrayList<Item>item , Loja loja , Vendedor vendedor , Cliente cliente , String dataVencimentoReserva , String datapagamento , String dataCriacao , int ID){
        this.item = item;
        this.loja = loja;
        this.vendedor = vendedor;
        this.cliente = cliente;
        this.datapagamento = datapagamento;
        this.dataVencimentoReserva = dataVencimentoReserva;
        this.dataCriacao = dataCriacao;
        this.ID = ID;
    }
    public double calculoValorTotal() {
        double total = 0;
        // Percorremos a lista de itens deste pedido e somamos o valor de cada um
        for (Item i : this.item) {
            total += i.valor;
        }
        return total;
    }
}
