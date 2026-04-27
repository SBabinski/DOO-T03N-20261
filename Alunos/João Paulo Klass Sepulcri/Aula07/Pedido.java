import java.text.SimpleDateFormat;
import java.util.Date;

public class Pedido {
    int id;
    Date dataCriacao;
    Date dataPagamento;
    Date dataVencimentoReserva;
    Cliente cliente;
    Vendedor vendedor;
    Loja loja;
    Item[] itens;

    public Pedido(int id, Date dataCriacao, Date dataPagamento, Date dataVencimentoReserva, Cliente cliente, Vendedor vendedor, Loja loja, Item[] itens) {
        this.id = id;
        this.dataCriacao = dataCriacao;
        this.dataPagamento = dataPagamento;
        this.dataVencimentoReserva = dataVencimentoReserva;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.loja = loja;
        this.itens = itens;
    }

    public double calcularValorTotal() {
        double total = 0;

        if (itens == null) {
            return total;
        }

        for (int i = 0; i < itens.length; i++) {
            total = total + itens[i].valor;
        }

        return total;
    }

    public String gerarDescricaoVenda() {
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String descricao = "Pedido: " + id + "\n";
        descricao = descricao + "Data criacao: " + formatador.format(dataCriacao) + "\n";
        descricao = descricao + "Data pagamento: " + formatador.format(dataPagamento) + "\n";
        descricao = descricao + "Vencimento reserva: " + formatador.format(dataVencimentoReserva) + "\n";
        descricao = descricao + "Cliente: " + cliente.nome + "\n";
        descricao = descricao + "Vendedor: " + vendedor.nome + "\n";
        descricao = descricao + "Loja: " + loja.nomeFantasia + "\n";
        descricao = descricao + "Itens:\n";

        if (itens != null) {
            for (int i = 0; i < itens.length; i++) {
                descricao = descricao + "- " + itens[i].gerarDescricao() + "\n";
            }
        }

        descricao = descricao + "Valor total: R$ " + calcularValorTotal();
        return descricao;
    }
}
