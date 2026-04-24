import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Contrato {

    private Inquilino inquilino;
    private Imovel imovel;
    private LocalDate inicio;
    private LocalDate fim;
    private boolean encerrado;

    public Contrato(Inquilino inquilino, Imovel imovel, LocalDate inicio, LocalDate fim) {
        this.inquilino = inquilino;
        this.imovel = imovel;
        this.inicio = inicio;
        this.fim = fim;
        this.encerrado = false;
    }

    public void encerrar() {
        this.encerrado = true;
    }

    public boolean isEncerrado() {
        return encerrado;
    }

    public double calcularTotal() {
        long meses = ChronoUnit.MONTHS.between(inicio, fim);
        return meses * imovel.getValorAluguel();
    }

    public void exibirDados() {
        System.out.println("\nCONTRATO");
        System.out.println("Inquilino: " + inquilino.getNome());
        imovel.exibirDados();
        System.out.println("Total: " + calcularTotal());
        System.out.println("Encerrado: " + encerrado);
    }
}