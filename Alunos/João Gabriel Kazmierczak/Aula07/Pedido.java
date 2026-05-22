package fag;

import java.util.ArrayList;
import java.util.Date;

public class Pedido {
    private int id;
    private Date dataCriacao;
    private Date dataPagamento;
    private Date dataVencimentoReserva;
    private ArrayList<Item> itens = new ArrayList<>();

    public Pedido(int id, Date dataCriacao, Date dataVencimentoReserva) {
        this.id = id;
        this.dataCriacao = dataCriacao;
        this.dataVencimentoReserva = dataVencimentoReserva;
    }

    public void adicionarItem(Item item) {
        itens.add(item);
    }

    public double calcularValorTotal() {
        double total = 0;
        for (Item i : itens) total += i.getValor();
        return total;
    }

    public void gerarDescricaoVenda() {
        System.out.println("Data: " + dataCriacao);
        System.out.println("Total: R$ " + calcularValorTotal());
    }

    public Date getDataVencimentoReserva() {
        return dataVencimentoReserva;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}