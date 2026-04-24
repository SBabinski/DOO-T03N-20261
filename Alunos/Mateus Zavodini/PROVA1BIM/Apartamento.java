public class Apartamento extends Imovel {
    int andar;

    public Apartamento(String endereco, double valor, int andar) {
        super(endereco, valor);
        this.andar = andar;
    }

    public void mostrar() {
        super.mostrar();
        System.out.println("Andar: " + andar);
    }
}