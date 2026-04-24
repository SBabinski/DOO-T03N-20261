package controller;

import Entities.Loja;
import Entities.Venda;
import Entities.Vendedor;
import io.IO;
import repository.LojaRepository;
import repository.VendaRepository;
import service.Service;
import uteis.Uteis;

import java.util.ArrayList;

public class Controller {
    
    // Atributos
    private VendaRepository vendas;
    private LojaRepository lojas;
    private IO io;
    private Service service;
    
    // Construtor
    public Controller(IO io, Service service, VendaRepository vendas, LojaRepository lojas) {
        this.io = io;
        this.service = service;
        this.lojas = lojas;
        this.vendas = vendas;
    }
    
    // Ponto de partida
    public void iniciar() {
        
        int op;
        
        // povoa as listas de objetos pedidas pelo professor
        Uteis.populaDados(lojas);
        
        do {
            op = io.mostraMenuInicio();
            processaOpInicio(op);
        } while (op != 3);
    }
    
    // OPÇÕES PRIMARIAS
    
    // Processa a opção do menu inicial
    private void processaOpInicio(int op) {
        
        switch (op) {
            case 1:
                op = io.mostraMenuVendas();
                processaOpVenda(op);
                break;
            
            case 2:
                op = io.mostraMenuLojas();
                processaOpLoja(op);
                break;
            
            case 3:
                io.exibeSaida();
                break;
            
            default:
                io.exibeOpInvalida();
                break;
        }
    }
    
    // OPÇÕES SECUNDARIAS
    
    // Processa as opções de venda
    private void processaOpVenda(int op) {
        
        do {
            switch (op) {
                
                case 1:
                    cadastraVenda();
                    break;
                
                case 2:
                    op = io.mostraMenuListagem();
                    processaOpListagem(op);
                    break;
                
                case 3:
                    op = io.mostraMenuCalculo();
                    processaOpCalculo(op);
                    break;
                
                case 4:
                    break;
                
                default:
                    io.exibeOpInvalida();
                    break;
            }
            
            if (op != 4) {
                op = io.mostraMenuVendas();
            }
            
        } while (op != 4);
    }
    
    // Processa as opções de loja
    private void processaOpLoja(int op) {
        
        do {
            switch (op) {
                
                case 1:
                    listaLojas();
                    break;
                
                case 2:
                    listafuncionarios();
                    break;
                
                case 3:
                    calcularDadosVendedor();
                    break;
                
                case 4:
                    listaClientes();
                    break;
                    
                case 5:
                    break;
                
                default:
                    io.exibeOpInvalida();
                    break;
            }
            
            if (op != 5) {
                op = io.mostraMenuLojas();
            }
            
        } while (op != 5);
    }
    
    // OPÇÕES TERCIARIAS
    
    // Processa as opções de listagem
    private void processaOpListagem(int op) {
        
        do {
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
            
            if (op != 5) {
                op = io.mostraMenuListagem();
            }
            
        } while (op != 5);
    }
    
    // Processa as opções de calculo
    private void processaOpCalculo(int op) {
        
        do {
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
            
            if (op != 4) {
                op = io.mostraMenuCalculo();
            }
            
        } while (op != 4);
    }


// EXECUÇÃO DAS OPÇÕES
    
    // Cadastra nova compra
    private void cadastraVenda() {
        int quant = io.pedeQundidade();
        double valUni = io.pedeValor();
        
        double valVenda = service.calculaPreco(quant, valUni);
        double desconto = service.calculaDesconto(quant, valVenda);
        vendas.adicionaVenda(service.cadastraVenda(quant, valVenda, desconto));
        
        io.exibeSucessoCadastro();
    }
    
    // Lista todas as compras realizadas
    private void listaTodas() {
        io.listaCompras(vendas.retornaVendas());
    }
    
    // Lista compras por ano
    private void listaAno() {
        ArrayList<Venda> vendas = new ArrayList<>();
        
        int ano = io.pedeAno();
        
        for (Venda m : this.vendas.retornaVendas()) {
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
        
        for (Venda m : this.vendas.retornaVendas()) {
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
        
        for (Venda m : this.vendas.retornaVendas()) {
            if ((m.getYear() == ano) && (m.getMonth() == mes) && (m.getDay() == dia)) {
                vendas.add(m);
            }
        }
        
        io.listaCompras(vendas);
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
    
    // calcula do desconto
    private void calculaDesconto() {
        
        int quant = io.pedeQundidade();
        double valTot = io.pedeValorTotal();
        
        double desconto = service.calculaDesconto(quant, valTot);
        
        io.mostraResultadoDesconto(desconto);
    }
    
    // Lista lojas
    private void listaLojas() {
        ArrayList<Loja> lojas = this.lojas.retornaLojas();
        io.listaLojas(lojas);
    }
    
    // Lista funcionarios
    private void listafuncionarios() {
        ArrayList<Loja> lojas = this.lojas.retornaLojas();
        io.listaLojas(lojas);
        
        int id = io.pedeIdLoja();
        
        Loja loja = null;
        
        for (Loja m : lojas) {
            if (id == m.getID()){
                loja = m;
                break;
            }
        }
        io.listaVendedores(loja);
    }
    
    // Lista clientes
    private void listaClientes() {
        ArrayList<Loja> lojas = this.lojas.retornaLojas();
        io.listaLojas(lojas);
        
        int id = io.pedeIdLoja();
        
        Loja loja = null;
        
        for (Loja m : lojas) {
            if (id == m.getID()){
                loja = m;
                break;
            }
        }
        
        io.listaClientes(loja);
    }
    
    private void calcularDadosVendedor() {
        
        ArrayList<Loja> lojas = this.lojas.retornaLojas();
        io.listaLojas(lojas);
        
        int idLoja = io.pedeIdLoja();
        
        Loja loja = null;
        
        for (Loja m : lojas) {
            if (m.getID() == idLoja) {
                loja = m;
                break;
            }
        }
        
        if (loja == null) {
            System.out.println("\nLoja não encontrada.");
            return;
        }
        
        ArrayList<Vendedor> vendedores = loja.getVendedores().retornaVendedores();
        
        if (vendedores.isEmpty()) {
            System.out.println("\nNenhum vendedor cadastrado.");
            return;
        }
        
        // Lsta vendedores
        for (Vendedor v : vendedores) {
            
            double media = v.calcularMedia();
            double bonus = v.calcularBonus();
            
            System.out.printf(
                    "\nVendedor: %s\nMédia Salarial: R$ %.2f\nBônus: R$ %.2f",
                    v.apresentarse(),
                    media,
                    bonus
            );
        }
    }
    
    
}
