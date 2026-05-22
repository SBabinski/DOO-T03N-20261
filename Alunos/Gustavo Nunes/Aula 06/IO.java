package io;

import Entities.Cliente;
import Entities.Loja;
import Entities.Venda;
import Entities.Vendedor;

import java.util.ArrayList;
import java.util.Scanner;

public class IO {
    private final String LINHA_MENU = "\n==========================" +
            "================================================\n";
    private final String LINHA_TABELA = "\n--------------------------" +
            "------------------------------------------------";
    private Scanner scanner = new Scanner(System.in);
    
    // Menu inicial
    public int mostraMenuInicio() {
        System.out.println(LINHA_MENU +
                "[1] - Vendas\n" +
                "[2] - Lojas\n" +
                "[3] - Sair" +
                LINHA_MENU +
                "Entre com a opção : ");
        
        return scanner.nextInt();
    }
    
    // Menu de vendas
    public int mostraMenuVendas() {
        System.out.println(LINHA_MENU +
                "[1] - Cadastrar nova Venda\n" +
                "[2] - Listar vendas cadastradas\n" +
                "[3] - Apenas calcular\n" +
                "[4] - voltar" +
                LINHA_MENU +
                "Entre com a opção : ");
        
        return scanner.nextInt();
    }
    
    // Menu de lojas
    public int mostraMenuLojas() {
        System.out.println(LINHA_MENU +
                "[1] - Listar lojas\n" +
                "[2] - Listar vendedores\n" +
                "[3] - Dados dos vendedores\n" +
                "[4] - Listar clientes\n" +
                "[4] - voltar" +
                LINHA_MENU +
                "Entre com a opção : ");
        
        return scanner.nextInt();
    }
    
    // Menu de listagem
    public int mostraMenuListagem() {
        System.out.println(LINHA_MENU +
                "[1] - Todas as vendas\n" +
                "[2] - Por ano\n" +
                "[3] - Por mes\n" +
                "[4] - por dia\n" +
                "[5] - Voltar" +
                LINHA_MENU +
                "Entre com a opção : ");
        
        return scanner.nextInt();
    }
    
    // Menu de calculo
    public int mostraMenuCalculo() {
        System.out.println(LINHA_MENU +
                "[1] - Cálcular venda\n" +
                "[2] - Cálcular troco\n" +
                "[3] - Cálcular desconto\n" +
                "[4] - Voltar" +
                LINHA_MENU +
                "Entre com a opção : ");
        
        return scanner.nextInt();
    }
    
    // Pede quantidade
    public int pedeQundidade() {
        System.out.println("\nQuantidade: ");
        return scanner.nextInt();
    }
    
    // Pede valor
    public double pedeValor() {
        System.out.println("\nValor unitário: ");
        return scanner.nextDouble();
    }
    
    // sucesso na venda
    public void exibeSucessoCadastro() {
        System.out.println("venda cadastrada com sucesso!");
    }
    
    // Lista compras realizadas
    public void listaCompras(ArrayList<Venda> vendas) {
        
        if (vendas.isEmpty()) {
            System.out.println("\nNenhuma compra encontrada.");
            return;
        }
        
        System.out.printf(
                LINHA_TABELA +
                        "\n  %-3s   %-10s   %-10s   %-10s   %-10s   %-10s   " +
                        LINHA_TABELA,
                "ID", "DATA", "QUANTIDADE", "V.TOTAL", "DESCONTO", "V.PAGO");
        
        for (Venda m : vendas) {
            System.out.printf("\n%s", m);
        }
        
        System.out.printf(LINHA_TABELA);
        
    }
    
    // Ano da listagem
    public int pedeAno() {
        System.out.println("\nAno: ");
        return scanner.nextInt();
    }
    
    // Mês da listagem
    public int pedeMes() {
        System.out.println("\nMês: ");
        return scanner.nextInt();
    }
    
    // Dia da listagem
    public int pedeDia() {
        System.out.println("\nDia: ");
        return scanner.nextInt();
    }
    
    // Resultado de venda
    public void mostraResultadoVenda(double preco) {
        System.out.printf("\nPreço = R$%.2f", preco);
    }
    
    // Valor pago
    public double pedeValorPago() {
        System.out.println("\nValor pago: ");
        return scanner.nextDouble();
    }
    
    // Valor devido
    public double pedeValorDevido() {
        System.out.println("\nValor devido: ");
        return scanner.nextDouble();
    }
    
    // Valor total
    public double pedeValorTotal() {
        System.out.println("\nValor total: ");
        return scanner.nextDouble();
    }
    
    // Resultado de troco
    public void mostraResultadoTroco(double preco) {
        System.out.printf("\nTroco = R$%.2f", preco);
    }
    
    // Resultado de desconto
    public void mostraResultadoDesconto(double preco) {
        System.out.printf("\nDesconto = R$%.2f", preco);
    }
    
    
    // Exibição de opção invalida
    public void exibeOpInvalida() {
        System.out.println("\nOpção inválida! Tente novamente!");
    }
    
    // Exibição de saída do sistema
    public void exibeSaida() {
        System.out.println("\nSAINDO...");
        scanner.close();
    }
    
    // ID da loja
    public int pedeIdLoja() {
        System.out.println("\nID da loja: ");
        return scanner.nextInt();
    }
    
    // Lista Lojas
    public void listaLojas(ArrayList<Loja> lojas) {
        if (lojas.isEmpty()) {
            System.out.println("\nNenhuma Loja encontrada.");
            return;
        }
        
        System.out.printf(
                LINHA_TABELA +
                        "\n  ID  |  NOME FANTASIA  |  CNPJ  |  CIDADE  |" +
                        "  BAIRRO  |  RUA  |  CLIENTES  | VENDEDORES" +
                        LINHA_TABELA);
        
        for (Loja m : lojas) {
            System.out.printf("\n%s", m.apresentarse());
        }
        
        System.out.printf(LINHA_TABELA);
    }
    
    // Lista funcionarios
    public void listaVendedores(Loja loja) {
        
        if (loja == null) {
            System.out.println("\nLoja não encontrada.");
            return;
        }
        
        ArrayList<Vendedor> vendedores = loja.getVendedores().retornaVendedores();
        
        if (vendedores == null || vendedores.isEmpty()) {
            System.out.println("\nNenhum vendedor cadastrado nesta loja.");
            return;
        }
        
        System.out.printf(
                LINHA_TABELA +
                        "\n  ID  |  NOME  |  IDADE  |  LOJA" +
                        LINHA_TABELA);
        
        for (Vendedor v : vendedores) {
            System.out.printf("\n%s", v.apresentarse());
        }
        
        System.out.printf(LINHA_TABELA);
    }
    
    // Lista Clientes
    public void listaClientes(Loja loja) {
        if (loja == null) {
            System.out.println("\nLoja não encontrada.");
            return;
        }
        
        ArrayList<Cliente> clientes = loja.getClientes().retornaClientes();
        
        if (clientes == null || clientes.isEmpty()) {
            System.out.println("\nNenhum cliente cadastrado nesta loja.");
            return;
        }
        
        System.out.printf(
                LINHA_TABELA +
                        "\n  ID  |  NOME  |  IDADE  |  CIDADE  |  BAIRRO  |  RUA" +
                        LINHA_TABELA);
        
        for (Cliente m : clientes) {
            System.out.printf("\n%s", m.apresentarse());
        }
        
        System.out.printf(LINHA_TABELA);
    }
    
   
}
