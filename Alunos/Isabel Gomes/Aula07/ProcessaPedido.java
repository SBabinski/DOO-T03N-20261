package fag.objetos;

import java.time.LocalDate;

public class ProcessaPedido {
	
	private boolean confirmarPagamento(Pedido pedido) {

        LocalDate hoje = LocalDate.now();

        if (hoje.isAfter(pedido.getDataVencimentoReserva())) {
            return false;
        }

        return true;
    }
	
	public Vendas processar(Pedido pedido, double valorPago) {

	    if (!confirmarPagamento(pedido)) {
	        System.out.println("\nPedido vencido!");
	        return null;
	    }

	    Vendas venda = new Vendas();

	    double total = pedido.calcularValorTotal();

	    // desconto
	    if (pedido.getItens().size() > 10) {
	        System.out.println("\nDesconto de 5% aplicado!");
	        total = total - (total * 0.05);
	        venda.alterarDesconto();
	    }

	    venda.setPrecoTotal(total);
	    venda.setQuantidade(pedido.getItens().size());
	    venda.setData(pedido.getDataCriacao().format(Pedido.FORMATO));
	    // troco
	    if (valorPago < total) {
	        System.out.println("\nPagamento insuficiente!");
	        return null;
	    }

	    double troco = valorPago - total;

	    System.out.printf("\nTroco: R$ %.2f\n", troco);

	    pedido.gerarDescricaoVenda();

	    return venda;
	}

	    

}
