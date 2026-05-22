public class Contrato {
    private Inquilino inquilino;
    private Imovel imovel;
    String dataInicio;
    String dataFim;
    int quantidadeMesses;
    boolean encerrado;

    public Contrato(Inquilino inquilino , Imovel imovel , String dataFim , String dataInicio , int quantidadeMesses , boolean encerrado) {
        this.inquilino = inquilino;
        this.imovel = imovel;
        this.dataFim = dataFim;
        this.dataInicio = dataInicio;
        this.quantidadeMesses = quantidadeMesses;
        this.encerrado = false;
    }

    public Contrato(Inquilino inqEscolhido, Imovel imoEscolhido, String inicio, String fim, int meses) {
    }

    public double  calculoTotalaluguel(){
        return this.quantidadeMesses * this.imovel.getvalorAluguel();
    }
    public void exibirDadosContrato() {
        System.out.println("Inquilino: " + inquilino.getNome());
        System.out.println("Imóvel: " + imovel.getEndereco());
        System.out.println("Período: " + dataInicio + " até " + dataFim);
        System.out.println("Situação: " + (encerrado ? "Encerrado" : "Ativo"));
        System.out.println("Valor Total do Contrato: R$ " + calculoTotalaluguel());
    }
}
