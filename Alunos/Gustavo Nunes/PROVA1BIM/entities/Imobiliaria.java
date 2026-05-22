package entities;

import java.util.ArrayList;

public class Imobiliaria {
    
    private ArrayList<Contrato> contratos;
    
    public Imobiliaria() {
        this.contratos = new ArrayList<>();
    }
    
    public void adicionaContrato(Contrato novoContrato) {
        
        if (contratos.size() >= 10) {
            System.out.println("Limite de contratos atingido.");
            return;
        }
        
        contratos.add(novoContrato);
    }
    
    public ArrayList<Contrato> getContratos() {
        return contratos;
    }
    
    @Override
    public String toString() {
        
        if (contratos.isEmpty()) {
            return "Nenhum contrato cadastrado.";
        }
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("Contratos ativos:\n");
        
        for (Contrato c : contratos) {
            sb.append(c).append("\n");
        }
        
        return sb.toString();
    }
}
