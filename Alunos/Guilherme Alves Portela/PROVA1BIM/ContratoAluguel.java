import java.time.LocalDate;

public class ContratoAluguel {
    public Inquilino inquilino;
    public Imovel imovel;
    public LocalDate inicioContrato;
    public LocalDate fimContrato;
    public boolean encerrado;
    public ContratoAluguel(Inquilino inquilino, Imovel imovel, LocalDate inicioContrato) {
        this.inquilino = inquilino;
        this.imovel = imovel;
        this.inicioContrato = inicioContrato;
        this.fimContrato = null;
        this.encerrado = false;
    }

    //Getters

    public Inquilino getInquilino() {
        return inquilino;
    }
    public Imovel getImovel() {
        return imovel;
    }
    public LocalDate getInicioContrato() {
        return inicioContrato;
    }
    public LocalDate getFimContrato() {
        return fimContrato;
    }
    public boolean isEncerrado() {
        return encerrado;
    }

    //Setters

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }
    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }
    public void setInicioContrato(LocalDate inicioContrato) {
        this.inicioContrato = inicioContrato;
    }
    public void setFimContrato(LocalDate fimContrato) {
        this.fimContrato = fimContrato;
    }
    public void setEncerrado(boolean encerrado) {
        this.encerrado = encerrado;
    }
    
    //Métodos da classe

    public void encerrarContrato(LocalDate fimContrato){
        this.encerrado = true;
        this.fimContrato = fimContrato;
    }

    public void exibirInfos() {
    String infos = String.format("Inquilino: %s | Imovel: %s | Inicio Contrato: %s | Fim Contrato: %s | Encerrado: %s",
        this.inquilino.getNome(), this.imovel.getEndereco(), this.inicioContrato, this.fimContrato,(this.encerrado ? "SIM" : "NÃO"));

        System.out.println(infos);
    }
}
