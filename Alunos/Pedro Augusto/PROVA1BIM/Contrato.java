import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Contrato {
    private Inquilino inquilino;
    private Imovel imovel;
    private LocalDate dataInicio;
    private LocalDate dataFinal;
    private boolean encerrado;

    public Contrato(Inquilino inquilino, Imovel imovel, LocalDate dataInicio, LocalDate dataFinal) {
        this.inquilino = inquilino;
        this.imovel = imovel;
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.encerrado = false;
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

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public boolean isEncerrado() {
        return encerrado;
    }

    public void setEncerrado(boolean encerrado) {
        this.encerrado = encerrado;
    }

    public long calcularQuantidadeMeses() {
        return ChronoUnit.MONTHS.between(dataInicio, dataFinal);
    }

    public double calcularValorTotal() {
        return calcularQuantidadeMeses() * imovel.getValorAluguel();
    }

    public void exibirDadosContrato() {
        System.out.println("----- DADOS DO CONTRATO -----");
        System.out.println("Inquilino: " + inquilino.getNome());
        System.out.println("Imóvel: " + imovel.getEndereco());
        System.out.println("Data de Início: " + dataInicio);
        System.out.println("Data de Fim: " + dataFinal);
        System.out.println("Situação: " + (encerrado ? "Encerrado" : "Ativo"));
        System.out.println("Quantidade de Meses: " + calcularQuantidadeMeses());
        System.out.println("Valor Total a ser Pago: R$ " + calcularValorTotal());
        System.out.println("-----------------------------");
    }
}
