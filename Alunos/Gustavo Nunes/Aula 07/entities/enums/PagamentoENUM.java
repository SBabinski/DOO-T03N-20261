package entities.enums;

public enum PagamentoENUM {
    PAGO("PAGO"),
    PENDENTE("PENDENTE"),
    VENCIDO("VENCIDO");
    
    private String pagamento;
    
    PagamentoENUM (String pagamento) {
        this.pagamento = pagamento;
    }
}
