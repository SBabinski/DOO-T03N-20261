import java.util.Date;

public class Pedido {
    int id;
    Date dataCriacao = new Date();
    Date dataPagamento;
    Date dataVencimentoReserva;
    Item[] itens;

    public double calcularValorTotal() {
        double total = 0;
        for (Item item : itens) total += item.valor;
        return total;
    }

    public void gerarDescricaoVenda() {
        System.out.println("Pedido: " + id + " | Criado em: " + dataCriacao + " | Total: R$ " + calcularValorTotal());
    }
}
