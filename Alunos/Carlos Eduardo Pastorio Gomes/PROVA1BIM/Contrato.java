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

    public void encerrarContrato() {
        encerrado = true;
    }

    public boolean isEncerrado() {
        return encerrado;
    }

    public void exibirContrato() {
        System.out.println("=== CONTRATO ===");
        inquilino.exibirDados();
        imovel.exibirInformacoes();
        System.out.println("Data início: " + dataInicio);
        System.out.println("Data final: " + dataFim);
        System.out.println("Meses: " + meses);
        System.out.println("Total: R$ " + (meses * imovel.getAluguelMensal()));
        System.out.println("Status: " + (encerrado ? "Encerrado" : "Ativo"));
        System.out.println("---------------------");
    }
}