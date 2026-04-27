import java.util.Date;
import java.util.List;

public class Pedido {
    int id;
    Date dataCriacao, dataPagamento, dataVencimentoReserva;
    Cliente cliente;
    Vendedor vendedor;
    Loja loja;
    List<Item> itens;

    public double calcularValorTotal() {
        double total = 0;
        for (Item item : itens) total += item.valor;
        return total;
    }

    public void gerarDescricaoVenda() {
        System.out.println("Pedido Criado em: " + dataCriacao + " | Valor Total: R$ " + calcularValorTotal());
    }
}
