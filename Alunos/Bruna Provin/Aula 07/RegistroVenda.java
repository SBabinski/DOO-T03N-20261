import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RegistroVenda {

    // Armazena pedidos por data
    private List<Pedido> vendas = new ArrayList<>();
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void salvarVenda(Pedido pedido) {
        vendas.add(pedido);
        System.out.println("Venda registrada: Pedido #" + pedido.id);
    }

    public void buscarVendasPorMesEDia(int mes, int dia) {
        LocalDate dataAlvo = LocalDate.now().withMonth(mes).withDayOfMonth(dia);
        System.out.println("Vendas em " + dataAlvo.format(FORMATTER) + ":");

        int total = 0;
        for (Pedido p : vendas) {
            LocalDate dataPedido = p.dataCriacao.toInstant()
                    .atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            if (dataPedido.equals(dataAlvo)) {
                p.gerarDescricaoVenda();
                total++;
            }
        }
        System.out.println("Total de vendas nesse dia: " + total);
    }
}