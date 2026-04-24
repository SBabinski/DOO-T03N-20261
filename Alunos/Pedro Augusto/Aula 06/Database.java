import java.util.ArrayList;

public class Database {
    public static ArrayList<Venda> registroDeVendas = new ArrayList<Venda>();

    public static ArrayList<Venda> pegarVendasPorMes(int mes) {
        ArrayList<Venda> vendasFiltradas = new ArrayList<Venda>();

        // checa se existe alguma venda
        if (registroDeVendas.isEmpty()) {
            System.out.println("Registro de vendas vazio");
            return vendasFiltradas;
        }

        for (Venda venda : registroDeVendas) {
            if (venda.getDataVenda().getMonthValue() == mes) {
                vendasFiltradas.add(venda);
            }
        }

        return vendasFiltradas;
    }

    public static ArrayList<Venda> pegarVendasPorDiaMes(int dia, int mes) {
        ArrayList<Venda> vendasFiltradas = new ArrayList<Venda>();

        if (registroDeVendas.isEmpty()) {
            System.out.println("Nenhuma venda realizada");
            return vendasFiltradas;
        }

        // filtra as vendas
        for (Venda venda : registroDeVendas) {
            if (venda.getDataVenda().getDayOfMonth() == dia && venda.getDataVenda().getMonthValue() == mes) {
                vendasFiltradas.add(venda);
            }
        }

        return vendasFiltradas;
    }

    public static void registrarVenda(Venda venda) {
        registroDeVendas.add(venda);
    }

    public static ArrayList<Venda> pegarTotalVendas() {
        return registroDeVendas;
    }
}