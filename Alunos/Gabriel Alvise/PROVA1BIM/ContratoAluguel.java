import java.time.LocalDate;
import java.time.Period;

public class ContratoAluguel {
    private Inquilino inquilino;
    private Imovel imovel;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean encerrado;

    public ContratoAluguel(Inquilino inquilino, Imovel imovel, LocalDate dataInicio, LocalDate dataFim, boolean encerrado) {
        this.inquilino = inquilino;
        this.imovel = imovel;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.encerrado = encerrado;
    }

    public double calcularValorTotal() {
        Period periodo = Period.between(dataInicio, dataFim);

        int meses = periodo.getYears() * 12 + periodo.getMonths();

        if (periodo.getDays() > 0) {
            meses++;
        }

        if (meses <= 0) {
            meses = 1;
        }

        return meses * imovel.getValorAluguelMensal();
    }

    public void exibirContrato() {
        System.out.println("DADOS DO CONTRATO");

        System.out.println("\nInquilino:");
        inquilino.exibirInformacoes();

        System.out.println("\nImóvel:");
        imovel.exibirInformacoes();

        System.out.println("\nData início: " + dataInicio);
        System.out.println("Data final: " + dataFim);
        System.out.println("Situação: " + (encerrado ? "Encerrado" : "Ativo"));
        System.out.println("Valor total: R$ " + calcularValorTotal());
    }

    public void encerrarContrato() {
        encerrado = true;
    }

    public boolean isEncerrado() {
        return encerrado;
    }
}