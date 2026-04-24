package service;

import Entities.Venda;

public class Service {
    private static final double TAXA_DESCONTO = 0.05;
    private static final int LIMITE_DESCONTO = 10;
    
    // Retorna preço
    public double calculaPreco(int quant, double valUni) {
        
        return quant * valUni;
        
    }
    
    // Retorna valor do desconto se aplicavel
    public double calculaDesconto(int quantidade, double valTot) {
        
        if (quantidade > LIMITE_DESCONTO) {
            return valTot * TAXA_DESCONTO;
        }
        return 0;
    }
    
    // Retorna troco
    public double calculaTroco(double valPag, double valDev) {
        
        return valPag - valDev;
        
    }
    
    // Retorna cadastro da venda
    public Venda cadastraVenda(int quant, double valVen, double desconto) {
        
        return new Venda(quant, valVen, desconto);
    }
    
}
