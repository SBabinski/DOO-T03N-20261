import java.util.ArrayList;
import java.time.LocalDate;

public class Pedido {

    private static int contadorId = 1;

    private int id;
    private LocalDate dataCriacao;
    private LocalDate dataPagamento; // null = ainda não pago
    private LocalDate dataVencimentoReserva;

    private Cliente cliente;
    private Vendedor vendedor;
    private Loja loja;

    ArrayList<Item> itens = new ArrayList<>();

    // Construtor completo — usado por jProcessaPedido
    public Pedido(LocalDate dataCriacao, LocalDate dataVencimentoReserva,
            Cliente cliente, Vendedor vendedor, Loja loja) {
        this.id = contadorId++;
        this.dataCriacao = dataCriacao;
        this.dataVencimentoReserva = dataVencimentoReserva;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.loja = loja;
    }

    // Getters das datas
    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public LocalDate getDataVencimentoReserva() {
        return dataVencimentoReserva;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    // Setter de pagamento(chama confirmarPagamento())
    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public boolean isPago() {
        return dataPagamento != null;
    }

    public void adicionarItem(Item item) {
        itens.add(item);
    }

    public double calcularPrecoTotal() {
        double total = 0;
        int quant = 0;
        for (Item item : itens) {
            total += item.getQuant() * item.getValor();
            quant += item.getQuant();
        }
        if (quant >= 10) {
            double desconto = total * 0.05;
            total -= desconto;
            System.out.println("  Desconto de 5% aplicado por quantidade!");
        }
        return total;
    }

    // Método para exibir detalhes do pedido
    @Override
    public String toString() {
        String texto = "\n--------------------------------------------------";
        texto += "\nPedido #" + id;
        texto += "\nCriado em: " + Date.formatar(dataCriacao);
        texto += "\nVencimento: " + Date.formatar(dataVencimentoReserva);
        texto += "\nPagamento: " + (isPago() ? Date.formatar(dataPagamento) : "Pendente");
        texto += "\nCliente: " + cliente.getNome();
        texto += "\nVendedor: " + vendedor.getNome();
        texto += "\nLoja: " + loja.getNome();
        texto += "\nItens:";

        for (Item item : itens) {
            texto += "\n" + item.exibirDescricao();
        }

        texto += "\nTotal: R$ " + calcularPrecoTotal();
        texto += "\n--------------------------------------------------";

        return texto;
    }
}
