package controller;

import entities.*;
import io.IO;
import repository.LojaRepository;
import repository.PedidoRepository;
import repository.VendaRepository;
import service.LojaService;
import service.PedidoService;
import service.VendaService;
import uteis.Uteis;

import java.util.ArrayList;

public class Controller {
    
    private VendaRepository vendas;
    private LojaRepository lojas;
    private PedidoRepository pedidos;
    private IO io;
    private VendaService vendaService;
    private PedidoService pedidoService;
    private LojaService lojaService;
    
    public Controller(IO io, VendaService vendaService, PedidoService pedidoService,
                      LojaService lojaService, VendaRepository vendas,
                      LojaRepository lojas, PedidoRepository pedidos) {
        this.io = io;
        this.vendaService = vendaService;
        this.pedidoService = pedidoService;
        this.lojaService = lojaService;
        this.lojas = lojas;
        this.vendas = vendas;
        this.pedidos = pedidos;
    }
    
    public void iniciar() {
        int op;
        Uteis.populaDados(lojas, pedidos);
        do {
            op = io.mostraMenuInicio();
            processaOpInicio(op);
        } while (op != 4);
    }
    
    // OPÇÕES PRIMARIAS
    
    private void processaOpInicio(int op) {
        switch (op) {
            case 1: processaOpVenda(io.mostraMenuVendas()); break;
            case 2: processaOpLoja(io.mostraMenuLojas()); break;
            case 3: processaOpPedido(io.mostraMenuPedidos()); break;
            case 4: io.exibeSaida(); break;
            default: io.exibeOpInvalida(); break;
        }
    }
    
    // OPÇÕES SECUNDARIAS
    
    private void processaOpVenda(int op) {
        do {
            switch (op) {
                case 1: cadastraVenda(); break;
                case 2: processaOpListagem(io.mostraMenuListagem()); break;
                case 3: processaOpCalculo(io.mostraMenuCalculo()); break;
                case 4: break;
                default: io.exibeOpInvalida(); break;
            }
            if (op != 4) op = io.mostraMenuVendas();
        } while (op != 4);
    }
    
    private void processaOpLoja(int op) {
        do {
            switch (op) {
                case 1: listaLojas(); break;
                case 2: listafuncionarios(); break;
                case 3: calcularDadosVendedor(); break;
                case 4: listaClientes(); break;
                case 5: break;
                default: io.exibeOpInvalida(); break;
            }
            if (op != 5) op = io.mostraMenuLojas();
        } while (op != 5);
    }
    
    private void processaOpPedido(int op) {
        do {
            switch (op) {
                case 1: criaPedidoFake(); break;
                case 2: listaPedidos(); break;
                case 3: testarConfirmacaoPagamento(); break;
                case 4: break;
                default: io.exibeOpInvalida(); break;
            }
            if (op != 4) op = io.mostraMenuPedidos();
        } while (op != 4);
    }
    
    // OPÇÕES TERCIARIAS
    
    private void processaOpListagem(int op) {
        do {
            switch (op) {
                case 1: listaTodas(); break;
                case 2: listaAno(); break;
                case 3: listaMes(); break;
                case 4: listaDia(); break;
                case 5: break;
                default: io.exibeOpInvalida(); break;
            }
            if (op != 5) op = io.mostraMenuListagem();
        } while (op != 5);
    }
    
    private void processaOpCalculo(int op) {
        do {
            switch (op) {
                case 1: calculaPreco(); break;
                case 2: calculaTroco(); break;
                case 3: calculaDesconto(); break;
                case 4: break;
                default: io.exibeOpInvalida(); break;
            }
            if (op != 4) op = io.mostraMenuCalculo();
        } while (op != 4);
    }
    
    // EXECUÇÃO DAS OPÇÕES
    
    private void cadastraVenda() {
        int quant = io.pedeQundidade();
        double valUni = io.pedeValor();
        double valVenda = vendaService.calculaPreco(quant, valUni);
        double desconto = vendaService.calculaDesconto(quant, valVenda);
        vendas.adicionaVenda(vendaService.cadastraVenda(quant, valVenda, desconto));
        io.exibeSucessoCadastro();
    }
    
    private void listaTodas() {
        io.listaCompras(vendas.retornaVendas());
    }
    
    private void listaAno() {
        int ano = io.pedeAno();
        io.listaCompras(vendaService.filtrarPorAno(vendas.retornaVendas(), ano));
    }
    
    private void listaMes() {
        int ano = io.pedeAno();
        int mes = io.pedeMes();
        io.listaCompras(vendaService.filtrarPorMes(vendas.retornaVendas(), ano, mes));
    }
    
    private void listaDia() {
        int ano = io.pedeAno();
        int mes = io.pedeMes();
        int dia = io.pedeDia();
        io.listaCompras(vendaService.filtrarPorDia(vendas.retornaVendas(), ano, mes, dia));
    }
    
    private void calculaPreco() {
        int quant = io.pedeQundidade();
        double valUni = io.pedeValor();
        io.mostraResultadoVenda(vendaService.calculaPreco(quant, valUni));
    }
    
    private void calculaTroco() {
        double valPag = io.pedeValorPago();
        double valDev = io.pedeValorDevido();
        io.mostraResultadoTroco(vendaService.calculaTroco(valPag, valDev));
    }
    
    private void calculaDesconto() {
        int quant = io.pedeQundidade();
        double valTot = io.pedeValorTotal();
        io.mostraResultadoDesconto(vendaService.calculaDesconto(quant, valTot));
    }
    
    private void listaLojas() {
        io.listaLojas(lojas.retornaLojas());
    }
    
    private void listafuncionarios() {
        ArrayList<Loja> lista = lojas.retornaLojas();
        io.listaLojas(lista);
        int id = io.pedeIdLoja();
        Loja loja = lojaService.buscarPorId(lista, id);
        io.listaVendedores(loja);
    }
    
    private void listaClientes() {
        ArrayList<Loja> lista = lojas.retornaLojas();
        io.listaLojas(lista);
        int id = io.pedeIdLoja();
        Loja loja = lojaService.buscarPorId(lista, id);
        io.listaClientes(loja);
    }
    
    private void calcularDadosVendedor() {
        ArrayList<Loja> lista = lojas.retornaLojas();
        io.listaLojas(lista);
        int id = io.pedeIdLoja();
        Loja loja = lojaService.buscarPorId(lista, id);
        io.exibeDadosVendedores(loja);
    }
    
    private void criaPedidoFake() {
        Pedido pedido = pedidoService.criaPedidoFake(lojas.retornaLojas());
        if (pedido == null) {
            io.exibeErro("Não foi possível criar pedido fake: dados insuficientes.");
            return;
        }
        pedidos.adicionaPedido(pedido);
        io.exibeSucessoPedido(pedido.gerarDescricaoVenda());
    }
    
    private void listaPedidos() {
        io.listaPedidos(pedidos.retornaPedidos());
    }
    
    private void testarConfirmacaoPagamento() {
        pedidoService.testarConfirmaPagamento();
    }
}