package Objetos;

import java.util.Date;

public class ProcessaPedido {

    public Pedido processar(int id, Date dataVencimentoReserva,
                            Cliente cliente, Vendedor vendedor, Loja loja,
                            Item... itens) {
        Pedido pedido = new Pedido(id, new Date(), dataVencimentoReserva, cliente, vendedor, loja);
        for (Item item : itens) {
            pedido.adicionarItem(item);
        }
        confirmarPagamento(pedido);
        return pedido;
    }

    private void confirmarPagamento(Pedido pedido) {
        Date agora = new Date();
        if (agora.after(pedido.getDataVencimentoReserva())) {
            System.out.println("⚠️  Pedido #" + pedido.getId()
                    + ": reserva vencida em " + pedido.getDataVencimentoReserva()
                    + ". Pagamento não confirmado.");
        } else {
            pedido.setDataPagamento(agora);
            System.out.println("✅ Pedido #" + pedido.getId()
                    + ": pagamento confirmado em " + agora);
        }
    }

    public static void testar(Loja loja, Cliente cliente, Vendedor vendedor) {
        ProcessaPedido processador = new ProcessaPedido();

        System.out.println("\n=== Teste ProcessaPedido ===");

        Date reservaValida = new Date(System.currentTimeMillis() + 86_400_000L);
        Item item1 = new Item(1, "Samambaia", "Planta", 45.00);
        Item item2 = new Item(2, "Vaso Cerâmica", "Acessório", 30.00);
        Pedido pedido1 = processador.processar(1, reservaValida, cliente, vendedor, loja, item1, item2);
        pedido1.gerarDescricaoVenda();

        System.out.println();

        Date reservaVencida = new Date(System.currentTimeMillis() - 86_400_000L);
        Item item3 = new Item(3, "Orquídea", "Planta", 80.00);
        Pedido pedido2 = processador.processar(2, reservaVencida, cliente, vendedor, loja, item3);
        pedido2.gerarDescricaoVenda();
    }
}
