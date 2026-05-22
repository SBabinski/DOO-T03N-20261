public class Contrato {
    Inquilino inquilino;
    Imovel imovel;
    int meses;
    boolean encerrado;

    public Contrato(Inquilino inquilino, Imovel imovel, int meses) {
        this.inquilino = inquilino;
        this.imovel = imovel;
        this.meses = meses;
        this.encerrado = false;
    }

    public double calcularTotal() {
        return meses * imovel.valor;
    }

    public void encerrar() {
        encerrado = true;
    }

    public void mostrar() {
        System.out.println("=== CONTRATO ===");
        inquilino.mostrar();
        imovel.mostrar();
        System.out.println("Meses: " + meses);
        System.out.println("Encerrado: " + encerrado);
        System.out.println("Total: " + calcularTotal());
        System.out.println();
    }
}