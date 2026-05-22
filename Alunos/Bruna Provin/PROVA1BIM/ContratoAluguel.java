public class ContratoAluguel {
    private static int contadorId = 1;

    private int id;
    private Inquilino inquilino;
    private Imovel imovel;
    private String dataInicio;
    private String dataFim;
    private int quantidadeMeses;
    private boolean encerrado;

    public ContratoAluguel(Inquilino inquilino, Imovel imovel, String dataInicio, String dataFim, int quantidadeMeses) {
        this.id = contadorId++;
        this.inquilino = inquilino;
        this.imovel = imovel;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.quantidadeMeses = quantidadeMeses;
        this.encerrado = false;
    }

    public int getId() { return id; }
    public Inquilino getInquilino() { return inquilino; }
    public Imovel getImovel() { return imovel; }
    public String getDataInicio() { return dataInicio; }
    public String getDataFim() { return dataFim; }
    public boolean isEncerrado() { return encerrado; }

    public void encerrar() {
        this.encerrado = true;
    }

    public double calcularValorTotal() {
        return quantidadeMeses * imovel.getValorAluguelMensal();
    }

    public void exibirDados() {
        System.out.println("--- Contrato #" + id + " ---");
        System.out.println("Situacao: " + (encerrado ? "ENCERRADO" : "ATIVO"));
        System.out.println("Inicio: " + dataInicio + " | Fim: " + dataFim);
        System.out.println("Meses: " + quantidadeMeses);
        System.out.println("Valor total: R$ " + calcularValorTotal());
        System.out.println("-- Inquilino --");
        inquilino.exibirDados();
        System.out.println("-- Imovel --");
        imovel.exibirInformacoes();
    }
}