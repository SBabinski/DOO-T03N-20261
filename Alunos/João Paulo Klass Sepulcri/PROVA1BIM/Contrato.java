public class Contrato {
    Inquilino inquilino;
    Imovel imovel;
    String dataInicio;
    String dataFim;
    boolean encerrado;

    public Contrato(Inquilino inquilino, Imovel imovel, String dataInicio, String dataFim, boolean encerrado) {
        this.inquilino = inquilino;
        this.imovel = imovel;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.encerrado = encerrado;
    }

    public void exibirInformacoes() {
        System.out.println("Inquilino do contrato:");
        inquilino.exibirInformacoes();
        System.out.println("Imovel do contrato:");
        imovel.exibirInformacoes();
        System.out.println("Data de inicio: " + dataInicio);
        System.out.println("Data de fim: " + dataFim);
        System.out.println("Encerrado: " + (encerrado ? "Sim" : "Nao"));
        System.out.println("Valor total estimado: R$ " + calcularValorTotal());
    }

    public double calcularValorTotal() {
        int quantidadeMeses = 12;
        return imovel.valorAluguel * quantidadeMeses;
    }
}
