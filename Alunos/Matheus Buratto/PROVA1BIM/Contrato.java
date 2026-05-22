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

    public boolean isEncerrado() { return encerrado; }
    public void encerrar() { this.encerrado = true; }

    public Inquilino getInquilino() { return inquilino; }
    public Imovel getImovel() { return imovel; }

    private int calcularMeses() {
        String[] inicio = dataInicio.split("/");
        String[] fim = dataFim.split("/");
        int mesInicio = Integer.parseInt(inicio[1]);
        int anoInicio = Integer.parseInt(inicio[2]);
        int mesFim = Integer.parseInt(fim[1]);
        int anoFim = Integer.parseInt(fim[2]);
        return (anoFim - anoInicio) * 12 + (mesFim - mesInicio);
    }

    public double calcularValorTotal() {
        return calcularMeses() * imovel.getValorAluguelMensal();
    }

    public void exibirDados() {
        System.out.println("===== Contrato =====");
        System.out.println(inquilino);
        imovel.exibirInformacoes();
        System.out.println("Início: " + dataInicio + " | Fim: " + dataFim);
        System.out.println("Situação: " + (encerrado ? "Encerrado" : "Ativo"));
        System.out.println("Meses: " + calcularMeses() + " | Total: R$ " + calcularValorTotal());
    }
}