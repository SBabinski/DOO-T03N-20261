public class Contrato {

private Inquilino inquilino;
    private Imovel imovel;
    private String dataInicio;
    private String dataFim;
    private boolean encerrado;
    private int quantidadeMeses;

    // Construtor completo
    public Contrato(Inquilino inquilino, Imovel imovel, String dataInicio, String dataFim, int quantidadeMeses) {
        this.inquilino = inquilino;
        this.imovel = imovel;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.quantidadeMeses = quantidadeMeses;
        this.encerrado = false; // novo contrato sempre começa ativo
    }

    // Getters e setters
    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public boolean isEncerrado() {
        return encerrado;
    }

    public void setEncerrado(boolean encerrado) {
        this.encerrado = encerrado;
    }

    public int getQuantidadeMeses() {
        return quantidadeMeses;
    }

    public void setQuantidadeMeses(int quantidadeMeses) {
        this.quantidadeMeses = quantidadeMeses;
    }

    // Calcula o valor total do contrato
    public double calcularValorTotal() {
        return quantidadeMeses * imovel.getValorAluguel();
    }

    // Exibe todas as informações do contrato
    public void mostrarContrato() {
        System.out.println("-------------------------------");
        inquilino.mostrarInquilino();
        imovel.mostrarImovel();
        System.out.printf("Período: %s até %s | Meses: %d%n", dataInicio, dataFim, quantidadeMeses);
        System.out.printf("Valor Total: R$ %.2f | Situação: %s%n",
                calcularValorTotal(), encerrado ? "Encerrado" : "Ativo");
        System.out.println("-------------------------------");
    }
}
