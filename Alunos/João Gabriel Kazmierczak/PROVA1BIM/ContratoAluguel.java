package fag;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ContratoAluguel {
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
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

    public boolean isEncerrado() {
        return encerrado;
    }

    public void encerrarContrato() {
        encerrado = true;
    }

    public long calcularQuantidadeMeses() {
        int anos = dataFim.getYear() - dataInicio.getYear();
        int meses = dataFim.getMonthValue() - dataInicio.getMonthValue();
        int totalMeses = anos * 12 + meses;

        if (totalMeses <= 0) {
            return 1;
        }

        return totalMeses;
    }

    public double calcularValorTotal() {
        return calcularQuantidadeMeses() * imovel.getValorAluguelMensal();
    }

    public void exibirDadosContrato() {
        System.out.println("Inquilino:");
        inquilino.exibirInformacoes();
        System.out.println("Imovel:");
        imovel.exibirInformacoes();
        System.out.println("Data de inicio: " + dataInicio.format(FORMATO_DATA));
        System.out.println("Data final: " + dataFim.format(FORMATO_DATA));
        System.out.println("Situacao: " + (encerrado ? "Encerrado" : "Ativo"));
        System.out.println("Quantidade de meses: " + calcularQuantidadeMeses());
        System.out.println("Valor total a pagar: R$ " + calcularValorTotal());
    }
}
