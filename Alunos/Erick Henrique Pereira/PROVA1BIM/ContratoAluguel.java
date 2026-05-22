import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ContratoAluguel {
    private Inquilino inquilino;
    private Imovel imovel;
    private LocalDate dataEntrada;
    private LocalDate dataFimContrato;
    private boolean status;

    public ContratoAluguel(Inquilino inquilino, Imovel imovel, LocalDate dataEntrada, LocalDate dadaFimContrato,
            boolean status) {
        this.inquilino = inquilino;
        this.imovel = imovel;
        this.dataEntrada = dataEntrada;
        this.dataFimContrato = dadaFimContrato;
        this.status = status;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDate getDataFimContrato() {
        return dataFimContrato;
    }

    public void setDataFimContrato(LocalDate dataFimContrato) {
        this.dataFimContrato = dataFimContrato;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double valorPagar(){
        long mesesPagar = ChronoUnit.MONTHS.between(this.dataEntrada, this.dataFimContrato);
        return imovel.getValorAluguel() * mesesPagar;
    }
}


