import java.util.Date;
import java.util.Calendar;
public class TesteProcessaPedido {
    public static void main(String[] args) {
        Endereco endCliente = new Endereco("PR", "Cascavel", "Centro", "Rua XV de Novembro", "100", "Apto 2");
        Endereco endVendedor = new Endereco("PR", "Cascavel", "Vila Real", "Rua das Palmeiras", "50", "");
        Cliente cliente = new Cliente("Joao Silva", 30, endCliente, "111.222.333-44", "(44) 99111-2222");
        Vendedor vendedor = new Vendedor("Maria Oliveira", 28, endVendedor, "MAT-001", "My Plant Cascavel");
        Item[] itens = {
            new Item(1, "Samambaia", "Planta", 45.00),
            new Item(2, "Vaso Ceramica", "Acessorio", 30.00)
        };
        Date dataCriacao = new Date();
        Date dataPagamento = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 5);
        Date dataVencimentoValida = cal.getTime();
        System.out.println("=== Teste 1: Reserva valida ===");
        ProcessaPedido pp = new ProcessaPedido();
        pp.processar(1, dataCriacao, dataPagamento, dataVencimentoValida, cliente, vendedor, "My Plant Cascavel", itens);
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -2);
        Date dataVencimentoVencida = cal.getTime();
        System.out.println("\n=== Teste 2: Reserva vencida ===");
        pp.processar(2, dataCriacao, dataPagamento, dataVencimentoVencida, cliente, vendedor, "My Plant Cascavel", itens);
    }
}
