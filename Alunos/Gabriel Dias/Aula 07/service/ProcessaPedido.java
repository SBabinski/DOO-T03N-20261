package myplant.service;

import myplant.model.*;

import java.time.LocalDate;

/**
 * Serviço responsável por processar pedidos.
 * Encapsula a lógica de criação e confirmação de pagamento.
 */
public class ProcessaPedido {

    private static int contadorId = 1;

    /**
     * Processa e cria um pedido com os dados fornecidos.
     */
    public Pedido processar(LocalDate dataCriacao, LocalDate dataPagamento,
                            LocalDate dataVencimentoReserva,
                            Cliente cliente, Vendedor vendedor,
                            Loja loja, Item... itens) {

        Pedido pedido = new Pedido(contadorId++, dataCriacao, dataPagamento,
                dataVencimentoReserva, cliente, vendedor, loja);

        for (Item item : itens) {
            pedido.adicionarItem(item);
        }

        if (confirmarPagamento(pedido)) {
            System.out.println("✅ Pagamento confirmado! Pedido processado com sucesso.");
            pedido.gerarDescricaoVenda();
        } else {
            System.out.println("❌ Pagamento NÃO confirmado: reserva vencida em " +
                    dataVencimentoReserva + ".");
        }

        return pedido;
    }

    /**
     * Confirma o pagamento verificando se a reserva não está vencida.
     * Retorna true se a data atual NÃO é superior à dataVencimentoReserva.
     */
    private boolean confirmarPagamento(Pedido pedido) {
        LocalDate hoje = LocalDate.now();
        return !hoje.isAfter(pedido.getDataVencimentoReserva());
    }

    // -------------------------------------------------------
    // Teste do método confirmarPagamento
    // -------------------------------------------------------
    public static void testarConfirmarPagamento() {
        System.out.println("\n===== TESTE: confirmarPagamento =====");

        ProcessaPedido pp = new ProcessaPedido();

        // Reserva válida (vence amanhã)
        Pedido pedidoValido = new Pedido(99, LocalDate.now(), null,
                LocalDate.now().plusDays(1), null, null, null);
        boolean resultadoValido = pp.confirmarPagamento(pedidoValido);
        System.out.println("Reserva válida (vence amanhã): " +
                (resultadoValido ? "✅ PASSOU" : "❌ FALHOU"));

        // Reserva vencida (venceu ontem)
        Pedido pedidoVencido = new Pedido(100, LocalDate.now(), null,
                LocalDate.now().minusDays(1), null, null, null);
        boolean resultadoVencido = pp.confirmarPagamento(pedidoVencido);
        System.out.println("Reserva vencida (venceu ontem): " +
                (!resultadoVencido ? "✅ PASSOU" : "❌ FALHOU"));

        System.out.println("=====================================\n");
    }
}
