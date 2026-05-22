import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Contrato {
    private Inquilino inquilino;
    private Imovel imovel;
    private LocalDate dataInicio;
    private LocalDate dataFinal;
    private boolean encerrado;

    public Contrato(Inquilino inquilino, Imovel imovel, LocalDate dataInicio, LocalDate dataFinal){
        this.inquilino = inquilino;
        this.imovel = imovel;
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.encerrado = false;
    }

    public void encerrarContrato(){
        this.encerrado = true;
    }

    public boolean isEncerrado(){
        return encerrado;
    }

    public double calcularValorTotal(){
        long meses = ChronoUnit.MONTHS.between(dataInicio, dataFinal);
        if (meses <= 0) meses = 1;
        return meses * imovel.valorMensalAluguel;
    }

public void exibirDados(){
    System.out.println("--- Dados do contrato ---");
    System.out.println("Inquilino: " + inquilino);
    imovel.exibirInformacoes();
    System.out.println("Data do início do contrato: " + dataInicio);
    System.out.println("Data do fim do contrato: " + dataFinal);
    System.out.println("Valor total a pagar: R$" + calcularValorTotal());
    System.out.println("Situação: " + (encerrado ? "Encerrado":"Ativo"));
}
}
