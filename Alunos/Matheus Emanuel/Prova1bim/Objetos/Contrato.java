import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Contrato {
    private Inquilinos inquilino;
    private Imoveis imovel;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean encerrado;

    private static final DateTimeFormatter DATA_FORMATADA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Contrato(Inquilinos inquilino, Imoveis imovel, LocalDate dataInicio, LocalDate dataFim) {
        this.inquilino = inquilino;
        this.imovel = imovel;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.encerrado = false;
    }

    public long calcularQuantidadeMeses() {
        int anos = dataFim.getYear() - dataInicio.getYear();
        int meses = dataFim.getMonthValue() - dataInicio.getMonthValue();
        long total = anos * 12 + meses;
        return total > 0 ? total : 1;
    }

    public double calcularValorTotal() {
        return calcularQuantidadeMeses() * imovel.getValorAluguel();
    }

    public void exibirDados() {
        System.out.println("=== Dados do Contrato ===");
        System.out.println("--- Inquilino ---");
        inquilino.exibirInformacoes();
        System.out.println("--- Imóvel ---");
        imovel.exibirInformacoes();
        System.out.println("Data de início: " + dataInicio.format(DATA_FORMATADA));
        System.out.println("Data de término: " + dataFim.format(DATA_FORMATADA));
        System.out.println("Situação: " + (encerrado ? "Encerrado" : "Ativo"));
        System.out.printf("Quantidade de meses: %d%n", calcularQuantidadeMeses());
        System.out.printf("Valor total a pagar: R$ %.2f%n", calcularValorTotal());
    }

    public Inquilinos getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilinos inquilino) {
        this.inquilino = inquilino;
    }

    public Imoveis getImovel() {
        return imovel;
    }

    public void setImovel(Imoveis imovel) {
        this.imovel = imovel;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public boolean isEncerrado() {
        return encerrado;
    }

    public void encerrar() {
        this.encerrado = true;
        System.out.println("Contrato encerrado com sucesso.");
    }
}