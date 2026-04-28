import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Item planta = new Item();
        planta.id = 1;
        planta.nome = "Samambaia";
        planta.tipo = "Planta";
        planta.valor = 45.0;

        Pedido p = new Pedido();
        p.id = 101;
        p.itens = new Item[]{planta};
        p.dataVencimentoReserva = new Date(System.currentTimeMillis() + 86400000);

        ProcessaPedido proc = new ProcessaPedido();
        proc.processar(p);
        p.gerarDescricaoVenda();

        Gerente g = new Gerente("Pedro CTO", 25, "My Plant Matriz", 5000.0);
        g.apresentarse();
        System.out.println("Media: " + g.calcularMedia());
        System.out.println("Bonus: " + g.calcularBonus());
    }
}
