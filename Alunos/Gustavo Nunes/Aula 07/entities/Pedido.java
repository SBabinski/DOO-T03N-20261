package entities;

import java.util.ArrayList;
import java.util.Date;

public class Pedido {
    private static int geraID = 0;
    private int id;
    private Date dataCriacao;
    private Date dataPagamento;
    private Date dataVencimentoReserva;
    private Cliente cliente;
    private Vendedor vendedor;
    private Loja loja;
    private ArrayList<Item> items;
    
    public Pedido(Cliente cliente, Vendedor vendedor, Loja loja, ArrayList<Item> items) {
        this.id = ++geraID;
        this.dataCriacao = new Date();
        this.dataPagamento = null;
        // Reserva vence em 30 dias
        this.dataVencimentoReserva = new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000);
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.loja = loja;
        this.items = items;
    }
    
    public Date getDataPagamento() {
        return dataPagamento;
    }
    
    public Date getDataVencimentoReserva() {
        return dataVencimentoReserva;
    }
    
    // Usado apenas em testes para simular reserva vencida
    public void vencerReservaParaTeste() {
        this.dataVencimentoReserva = new Date(System.currentTimeMillis() - 1000);
    }
    
    // Retorna o valor total do pedido
    public double calcularValorTotal() {
        double total = 0.0;
        for (Item i : items) {
            total += i.getValor();
        }
        return total;
    }
    
    // Printa a data de criação do pedido e seu valor total
    public String gerarDescricaoVenda() {
        return String.format("%d | %s | %s | %s | R$ %.2f",
                id, dataCriacao, dataPagamento, dataVencimentoReserva, calcularValorTotal());
    }
}
