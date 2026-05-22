public class Contrato {
    private Inquilino inquilino;
    private Imovel imovel;
    private int meses;
    private boolean ativo;

    public Contrato(Inquilino i, Imovel im, int meses) {
        if(meses <= 0) {
            throw new IllegalArgumentException("Meses inválidos!");
        }
        this.inquilino = i;
        this.imovel = im;
        this.meses = meses;
        this.ativo = true;
    }

    public void encerrar() {
        ativo = false;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public double calcularTotal() {
        return meses * imovel.getValorAluguel();
    }

    public void exibir() {
        System.out.println(inquilino);
        System.out.println(imovel.exibirDados());
        System.out.println("Total: R$" + calcularTotal() + 
                           " | " + (ativo ? "Ativo" : "Encerrado"));
        System.out.println("---------------------------");
    }
}