import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Contrato {

    private Imovel imovel;
    private Inquilino inquilino;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean encerrou;

    public Contrato(Imovel imovel, Inquilino inquilino, LocalDate dataInicio, LocalDate dataFim) {
        this.imovel = imovel;
        this.inquilino = inquilino;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.encerrou = false;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public boolean isEncerrou() {
        return encerrou;
    }

    public void encerrarContrato() {
        this.encerrou = true;
    }

    public void exibirContrato() {
        System.out.println("----- CONTRATO -----");
        imovel.exibirImovel();
        System.out.println("Inquilino: " + inquilino.getNome());
        System.out.println("Data de Início: " + dataInicio);
        System.out.println("Data de Fim: " + dataFim);
        System.out.println("Encerrado: " + (encerrou ? "Sim" : "Não"));
        System.out.println("Valor Total: " + calcularTotal());
    }

    public double calcularTotal() {
        long meses = ChronoUnit.MONTHS.between(dataInicio, dataFim);
        return meses * imovel.getValorAluguel();
    }
}