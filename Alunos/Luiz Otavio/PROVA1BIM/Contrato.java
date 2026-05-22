import java.time.LocalDate;

public class Contrato {

    private Inquilino inquilino;
    private Imovel imovel;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean encerrado;

    public Contrato(Inquilino inquilino, Imovel imovel, LocalDate dataInicio, LocalDate dataFim) {
        this.inquilino = inquilino;
        this.imovel = imovel;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
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

    public LocalDate getDataFim() {
        return dataFim;
    }

    public boolean isEncerrado() {
        return encerrado;
    }

    public void encerrar() {
        this.encerrado = true;
    }

    public int calcularQuantidadeMeses() {
        int anos = dataFim.getYear() - dataInicio.getYear();
        int meses = dataFim.getMonthValue() - dataInicio.getMonthValue();
        int total = anos * 12 + meses;
        if (total <= 0) {
            total = 1;
        }
        return total;
    }

    public double calcularValorTotal() {
        return calcularQuantidadeMeses() * imovel.getValorAluguelMensal();
    }

    public void exibirDados() {
        System.out.println("------------------------------------------");
        System.out.println("Dados do Contrato:");
        System.out.println(inquilino.toString());
        imovel.exibirInformacoes();
        System.out.println("Data de Inicio: " + dataInicio);
        System.out.println("Data de Fim: " + dataFim);
        System.out.println("Duracao: " + calcularQuantidadeMeses() + " mes(es)");
        System.out.printf("Valor Total: R$ %.2f%n", calcularValorTotal());
        System.out.println("Situacao: " + (encerrado ? "Encerrado" : "Ativo"));
        System.out.println("------------------------------------------");
    }
}