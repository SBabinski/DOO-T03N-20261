package service;

import entities.Venda;
import java.util.ArrayList;

public class VendaService {
    private static final double TAXA_DESCONTO = 0.05;
    private static final int LIMITE_DESCONTO = 10;
    
    public double calculaPreco(int quant, double valUni) {
        return quant * valUni;
    }
    
    public double calculaDesconto(int quantidade, double valTot) {
        if (quantidade > LIMITE_DESCONTO) {
            return valTot * TAXA_DESCONTO;
        }
        return 0;
    }
    
    public double calculaTroco(double valPag, double valDev) {
        return valPag - valDev;
    }
    
    public Venda cadastraVenda(int quant, double valVen, double desconto) {
        return new Venda(quant, valVen, desconto);
    }
    
    public ArrayList<Venda> filtrarPorAno(ArrayList<Venda> vendas, int ano) {
        ArrayList<Venda> resultado = new ArrayList<>();
        for (Venda v : vendas) {
            if (v.getYear() == ano) resultado.add(v);
        }
        return resultado;
    }
    
    public ArrayList<Venda> filtrarPorMes(ArrayList<Venda> vendas, int ano, int mes) {
        ArrayList<Venda> resultado = new ArrayList<>();
        for (Venda v : vendas) {
            if (v.getYear() == ano && v.getMonth() == mes) resultado.add(v);
        }
        return resultado;
    }
    
    public ArrayList<Venda> filtrarPorDia(ArrayList<Venda> vendas, int ano, int mes, int dia) {
        ArrayList<Venda> resultado = new ArrayList<>();
        for (Venda v : vendas) {
            if (v.getYear() == ano && v.getMonth() == mes && v.getDay() == dia) resultado.add(v);
        }
        return resultado;
    }
}
