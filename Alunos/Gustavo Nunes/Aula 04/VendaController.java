package controller;

import Entities.Venda;
import io.VendaIO;
import service.VendaService;

import java.util.ArrayList;

public class VendaController {
    
    // onde serão armazenadas as vendas
    private ArrayList<Venda> vendas = new ArrayList<>();
    private VendaIO io;
    private VendaService service;
    
    public VendaController(VendaIO io, VendaService service) {
        this.io = io;
        this.service = service;
    }
    
    // Fluxo do programa
    public void iniciar() {
        
        int op;
        
        do {
            op = io.mostraMenuInicio();
            processaOpInicio(op);
        } while (op != 4);
    }
    
    // direciona usuário para opção escolhida
    private void processaOpInicio(int op) {
        
        switch (op) {
            
            case 1:
                cadastraVenda();
                break;
            
            case 2:
                mostraOpListagem();
                break;
            
            case 3:
                mostraOpCalculo();
                break;
            
            case 4:
                io.exibeSaida();
                break;
            
            default:
                io.exibeOpInvalida();
                break;
        }
    }
    
    // menu de listagem
    private void mostraOpListagem() {
        int op;
        
        do {
            op = io.mostraMenuListagem();
            processaOpListagem(op);
            
        } while (op != 5);
    }
    
    // direciona usuário para opção escolhida
    private void processaOpListagem(int op) {
        
        switch (op) {
            
            case 1:
                listaTodas();
                break;
            
            case 2:
                listaAno();
                break;
            
            case 3:
                listaMes();
                break;
            
            case 4:
                listaDia();
                break;
            
            case 5:
                break;
            
            default:
                io.exibeOpInvalida();
                break;
        }
    }
    
    // Menu de cálculo
    private void mostraOpCalculo() {
        int op;
        
        do {
            op = io.mostraMenuCalculo();
            processaOpCalculo(op);
            
        } while (op != 4);
    }
    
    // direciona usuário para opção escolhida
    private void processaOpCalculo(int op) {
        
        switch (op) {
            
            case 1:
                calculaPreco();
                break;
            
            case 2:
                calculaTroco();
                break;
            
            case 3:
                calculaDesconto();
                break;
            
            case 4:
                break;
            
            default:
                io.exibeOpInvalida();
                break;
        }
    }
    
    // Lista todas as compras realizadas
    private void listaTodas() {
        io.listaCompras(vendas);
    }
    
    // Lista compras por ano
    private void listaAno() {
        ArrayList<Venda> vendas = new ArrayList<>();
        
        int ano = io.pedeAno();
        
        for (Venda m : this.vendas) {
            if (m.getYear() == ano) {
                vendas.add(m);
            }
        }
        
        io.listaCompras(vendas);
        
    }
    
    // Lista compras por mês
    private void listaMes() {
        ArrayList<Venda> vendas = new ArrayList<>();
        
        int ano = io.pedeAno();
        int mes = io.pedeMes();
        
        for (Venda m : this.vendas) {
            if ((m.getYear() == ano) && (m.getMonth() == mes)) {
                vendas.add(m);
            }
        }
        
        io.listaCompras(vendas);
    }
    
    // Lista compras por dia
    private void listaDia() {
        ArrayList<Venda> vendas = new ArrayList<>();
        
        int ano = io.pedeAno();
        int mes = io.pedeMes();
        int dia = io.pedeDia();
        
        for (Venda m : this.vendas) {
            if ((m.getYear() == ano) && (m.getMonth() == mes) && (m.getDay() == dia)) {
                vendas.add(m);
            }
        }
        
        io.listaCompras(vendas);
    }
    
    
    // Cadastra nova compra
    private void cadastraVenda() {
        int quant = io.pedeQundidade();
        double valUni = io.pedeValor();
        
        double valVenda = service.calculaPreco(quant, valUni);
        double desconto = service.calculaDesconto(quant, valVenda);
        vendas.add(service.cadastraVenda(quant, valVenda, desconto));
        
        io.exibeSucessoCadastro();
    }
    
    // Calculo do Preço
    private void calculaPreco() {
        
        int quant = io.pedeQundidade();
        double valUni = io.pedeValor();
        
        double preco = service.calculaPreco(quant, valUni);
        
        io.mostraResultadoVenda(preco);
        
    }
    
    // Calculo do troco
    private void calculaTroco() {
        
        double valPag = io.pedeValorPago();
        double valDev = io.pedeValorDevido();
        
        double troco = service.calculaTroco(valPag, valDev);
        
        io.mostraResultadoTroco(troco);
        
    }
    
    // calcula desconto
    private void calculaDesconto() {
        
        int quant = io.pedeQundidade();
        double valTot = io.pedeValorTotal();
        
        double desconto = service.calculaDesconto(quant, valTot);
        
        io.mostraResultadoDesconto(desconto);
    }
    
}
