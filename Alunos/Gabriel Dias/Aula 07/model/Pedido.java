package myplant.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private int id;
    private LocalDate dataCriacao;
    private LocalDate dataPagamento;
    private LocalDate dataVencimentoReserva;
    private Cliente cliente;
    private Vendedor vendedor;
    private Loja loja;
    private List<Item> itens;

    public Pedido(int id, LocalDate dataCriacao, LocalDate dataPagamento,
                  LocalDate dataVencimentoReserva, Cliente cliente,
                  Vendedor vendedor, Loja loja) {
        this.id = id;
        this.dataCriacao = dataCriacao;
        this.dataPagamento = dataPagamento;
        this.dataVencimentoReserva = dataVencimentoReserva;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.loja = loja;
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(Item item) { itens.add(item); }

    public double calcularValorTotal() {
        return itens.stream().mapToDouble(Item::getValor).sum();
    }

    public void gerarDescricaoVenda() {
        System.out.printf("📋 Pedido #%d | Criado em: %s | Valor Total: R$ %.2f%n",
                id, dataCriacao.format(FORMATTER), calcularValorTotal());
    }

    // Getters
    public int getId()                              { return id; }
    public LocalDate getDataCriacao()               { return dataCriacao; }
    public LocalDate getDataPagamento()             { return dataPagamento; }
    public LocalDate getDataVencimentoReserva()     { return dataVencimentoReserva; }
    public Cliente getCliente()                     { return cliente; }
    public Vendedor getVendedor()                   { return vendedor; }
    public Loja getLoja()                           { return loja; }
    public List<Item> getItens()                    { return itens; }
}
