import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Vinculo {
    Inquilino inquilino;
    Imobiliaria imobiliaria;
    LocalDate InicioContrato;
    LocalDate FimContrato;
    boolean SituacaoContrato;
    double totalAluguel;

    public Vinculo(Inquilino inquilino, Imobiliaria imobiliaria, LocalDate inicioContrato, LocalDate fimContrato, boolean situacaoContrato, double totalAluguel) {
        this.inquilino = inquilino;
        this.imobiliaria = imobiliaria;
        InicioContrato = inicioContrato;
        FimContrato = fimContrato;
        SituacaoContrato = situacaoContrato;
        this.totalAluguel = totalAluguel;
    }

    public void CalcularAluguel(){
        long diasContrato = ChronoUnit.MONTHS.between(InicioContrato, FimContrato);
        totalAluguel = diasContrato * imobiliaria.valor;
    }


    public void exibirVinculo() {
        System.out.println("Inquilino: " + inquilino.nome
                + ", Imobiliaria: " + imobiliaria.endereco
                + ", Inicio do Contrato: " + InicioContrato
                + ", Fim do Contrato: " + FimContrato
                + ", Situação do Contrato: " + SituacaoContrato
                + ", Total do Aluguel: " + totalAluguel);
    }
}


