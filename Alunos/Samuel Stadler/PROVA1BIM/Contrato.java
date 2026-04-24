public class Contrato {
    private Inquilino inquilino;
    private Imovel imovel;
    private String dataInicio;
    private String dataFim;
    private boolean encerrado;
    public Contrato(Inquilino inquilino, Imovel imovel, String dataInicio, String dataFim) {
        this.inquilino = inquilino;
        this.imovel = imovel;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.encerrado = false;
    }
    public boolean isEncerrado() {
        return encerrado;
    }
    public void encerrar() {
        this.encerrado = true;
    }
    private int calcularMeses() {
        String[] partesInicio = dataInicio.split("/");
        String[] partesFim = dataFim.split("/");
        int mesInicio = Integer.parseInt(partesInicio[1]);
        int anoInicio = Integer.parseInt(partesInicio[2]);
        int mesFim = Integer.parseInt(partesFim[1]);
        int anoFim = Integer.parseInt(partesFim[2]);
        return (anoFim - anoInicio) * 12 + (mesFim - mesInicio);
    }
    public double calcularValorTotal() {
        int meses = calcularMeses();
        return meses * imovel.getValorAluguelMensal();
    }
    public void exibirInfo() {
        System.out.println("=== Dados do Contrato ===");
        System.out.println("Data de Inicio: " + dataInicio);
        System.out.println("Data de Fim: " + dataFim);
        System.out.println("Situacao: " + (encerrado ? "Encerrado" : "Ativo"));
        System.out.println("Valor Total: R$ " + calcularValorTotal());
        System.out.println("--- Inquilino ---");
        inquilino.exibirInfo();
        System.out.println("--- Imovel ---");
        imovel.exibirInfo();
    }
}
