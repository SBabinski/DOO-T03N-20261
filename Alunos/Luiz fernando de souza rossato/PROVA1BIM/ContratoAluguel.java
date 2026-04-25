
import java.time.LocalDate;


public class ContratoAluguel {
    private Inquilino inquilino;
    private Imovel imovel;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean encerrado;

    public ContratoAluguel(Inquilino inquilino, Imovel imovel, LocalDate dataInicio, LocalDate dataFim) {
        this.inquilino = inquilino;
        this.imovel = imovel;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.encerrado = false;
    }

    public ContratoAluguel(Inquilino inquilino, Imovel imovel, LocalDate dataInicio, LocalDate dataFim, boolean encerrado) {
        this.inquilino = inquilino;
        this.imovel = imovel;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.encerrado = encerrado;
    }

     public double calcularValorTotal() {
        int meses = dataFim.getMonthValue() - dataInicio.getMonthValue()
                    + (dataFim.getYear() - dataInicio.getYear()) * 12;
        return meses * imovel.getValorAluguelmensal();
    }

    public boolean isEncerrado() {
        return encerrado;
    }

    public void encerrarContrato() {
        this.encerrado = true;
    }

    public void exibirContrato() {
        inquilino.toString();
        imovel.exibirdados();
        System.out.println("Data de término: " + dataFim);
        System.out.println("Situação: " + (encerrado ? "Encerrado" : "Ativo"));
        System.out.printf("Valor total: R$ %.2f%n", calcularValorTotal());
    }
}
