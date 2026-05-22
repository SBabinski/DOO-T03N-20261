import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Contrato {
    private Inquilino inquilino;
    private Imovel imovel;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean ativo;

    public Contrato(Inquilino inquilino, Imovel imovel, LocalDate dataInicio, LocalDate dataFim) {
        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("Data de início deve ser anterior à data de fim.");
        }
        this.inquilino = inquilino;
        this.imovel = imovel;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.ativo = true; 
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

    public boolean isAtivo() {
        return ativo;
    }

    public void encerrar() {
        this.ativo = false;
    }

    public long getQuantidadeMeses() {
        return ChronoUnit.MONTHS.between(dataInicio, dataFim);
    }

    public double getValorTotal() {
        return getQuantidadeMeses() * imovel.getValorMensal();
    }

    public void exibirDados() {
       System.out.println("Inquilino: " + inquilino.toString());
       System.out.println("Imóvel:");
       imovel.exibirInformacoes();
       System.out.println("Data Início: " + dataInicio);
       System.out.println("Data Fim: " + dataFim);
       System.out.println("Situação: " + (ativo ? "Ativo" : "Encerrado"));
       System.out.println("Valor Total: R$ " + getValorTotal());
    }
}