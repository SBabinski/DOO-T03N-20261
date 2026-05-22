package service;

import entities.*;
import entities.enums.TipoENUM;
import entities.enums.PagamentoENUM;
import java.util.ArrayList;
import java.util.Date;

public class PedidoService {
    
    public Pedido processar(Cliente cliente, Vendedor vendedor, Loja loja, ArrayList<Item> itens) {
        return new Pedido(cliente, vendedor, loja, itens);
    }
    
    private PagamentoENUM confirmaPagamento(Pedido pedido) {
        Date hoje = new Date();
        if (hoje.after(pedido.getDataVencimentoReserva())) {
            return PagamentoENUM.VENCIDO;
        }
        return PagamentoENUM.PAGO;
    }
    
    public PagamentoENUM processarEConfirmar(Cliente cliente, Vendedor vendedor,
                                             Loja loja, ArrayList<Item> itens) {
        Pedido pedido = processar(cliente, vendedor, loja, itens);
        return confirmaPagamento(pedido);
    }
    
    public Pedido criaPedidoFake(ArrayList<Loja> lojas) {
        if (lojas.isEmpty()) return null;
        Loja loja = lojas.get(0);
        
        ArrayList<Vendedor> vendedores = loja.getVendedores().retornaVendedores();
        if (vendedores.isEmpty()) return null;
        Vendedor vendedor = vendedores.get(0);
        
        ArrayList<Cliente> clientes = loja.getClientes().retornaClientes();
        if (clientes.isEmpty()) return null;
        Cliente cliente = clientes.get(0);
        
        ArrayList<Item> itens = new ArrayList<>();
        itens.add(new Item("Violeta Africana", TipoENUM.FLOR, 20.00));
        itens.add(new Item("Aloe Vera", TipoENUM.MUDA, 30.00));
        itens.add(new Item("Semente de Lavanda", TipoENUM.SEMENTE, 10.00));
        
        return processar(cliente, vendedor, loja, itens);
    }
    
    public void testarConfirmaPagamento() {
        System.out.println("\n===== TESTE: confirmaPagamento =====");
        
        Loja loja = new Loja("Loja Teste", "Teste LTDA", "000000000", "Cascavel", "Centro", "Rua Teste");
        Cliente cliente = new Cliente("Cliente Teste", 30, "PR", "Cascavel", "Centro", "Rua Teste", 1, "Apto 1");
        Vendedor vendedor = new Vendedor("Vendedor Teste", 25, loja, "Cascavel", "Centro", "Rua Teste", 2000);
        vendedor.setSalario(2000);
        vendedor.setSalario(2100);
        vendedor.setSalario(2200);
        
        ArrayList<Item> itens = new ArrayList<>();
        
        Pedido pedidoValido = processar(cliente, vendedor, loja, itens);
        System.out.printf("\nPedido com reserva válida  -> Status: %s", confirmaPagamento(pedidoValido));
        
        Pedido pedidoVencido = new Pedido(cliente, vendedor, loja, itens);
        pedidoVencido.vencerReservaParaTeste();
        System.out.printf("\nPedido com reserva vencida -> Status: %s", confirmaPagamento(pedidoVencido));
        
        System.out.println("\n===== FIM DO TESTE =====");
    }
}