public class Contrato {
    private Inquilino inquilino;
    private Imovel imovel;
    private int meses;
    private String dataInicio;
    private String dataFim;
    private boolean encerrado;

    public Contrato(Inquilino inquilino, Imovel imovel, int meses, String dataInicio, String dataFim) {
        this.inquilino = inquilino;
        this.imovel = imovel;
        this.meses = meses;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.encerrado = false;
    }

    public void encerrar() {
        encerrado = true;
    }

    public boolean isEncerrado() {
        return encerrado;
    } 

    public double calcularTotal() {
        return meses * imovel.getValorMensal();
    }

    public void exibirContrato() {
        System.out.println("\n--- CONTRATO ---");
        inquilino.exibirInformacoes();
        imovel.exibirInformacoes();
        System.out.println("Data início: " + dataInicio);
        System.out.println("Data fim: " + dataFim);
        System.out.println("Meses: " + meses);
        System.out.println("Total: " + calcularTotal());
        System.out.println("Situação: " + (encerrado ? "Encerrado" : "Ativo"));
    }
}
