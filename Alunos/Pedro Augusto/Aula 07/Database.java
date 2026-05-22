import java.util.ArrayList;

public class Database {
    private static ArrayList<Venda> vendas = new ArrayList<>();
    private static ArrayList<Pedido> pedidos = new ArrayList<>();

    public static void registrarVenda(Venda venda) {
        vendas.add(venda);
    }

    public static ArrayList<Venda> pegarTotalVendas() {
        return vendas;
    }

    public static void registrarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public static ArrayList<Pedido> pegarTodosPedidos() {
        return pedidos;
    }

    public static ArrayList<Venda> pegarVendasPorMes(int mes) {
        ArrayList<Venda> filtradas = new ArrayList<>();
        for (Venda v : vendas) {
            if (v.getDataVenda().getMonthValue() == mes) {
                filtradas.add(v);
            }
        }
        return filtradas;
    }

    public static ArrayList<Venda> pegarVendasPorDiaMes(int dia, int mes) {
        ArrayList<Venda> filtradas = new ArrayList<>();
        for (Venda v : vendas) {
            if (v.getDataVenda().getDayOfMonth() == dia && v.getDataVenda().getMonthValue() == mes) {
                filtradas.add(v);
            }
        }
        return filtradas;
    }
}
