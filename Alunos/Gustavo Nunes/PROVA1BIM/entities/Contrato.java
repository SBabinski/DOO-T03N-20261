package entities;

import java.time.LocalDate;

public class Contrato {
    
    private Inquilino inquilino;
    private Imovel imovel;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean situacao;
    private double valorTotal;
    
    public Contrato(Inquilino inquilino, Imovel imovel, int meses) {
        this.inquilino = inquilino;
        this.imovel = imovel;
        this.dataInicio = LocalDate.now();
        this.dataFim = this.dataInicio.plusMonths(meses);
        this.situacao = false;
        this.valorTotal = this.imovel.getAluguel() * meses;
    }
    
    public Inquilino getInquilino() {
        return inquilino;
    }
    
    public Imovel getImovel() {
        return imovel;
    }
    
    public LocalDate getDataInicio() {
        return dataInicio;
    }
    
    public LocalDate getDataFim() {
        return dataFim;
    }
    
    public boolean isSituacao() {
        return situacao;
    }
    
    public double getValorTotal() {
        return valorTotal;
    }
    
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }
    
    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }
    
    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }
    
    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }
    
    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }
    
    @Override
    public String toString() {
        return String.format(
                "=========== CONTRATO ===========\n" +
                        "Inquilino: %s\n" +
                        "Imóvel: %s\n" +
                        "Início: %s\n" +
                        "Fim: %s\n" +
                        "Situação: %s\n" +
                        "Valor Total: R$ %.2f\n" +
                        "================================",
                inquilino,
                imovel,
                dataInicio,
                dataFim,
                (situacao ? "ENCERRADO" : "ATIVO"),
                valorTotal
        );
    }
}
