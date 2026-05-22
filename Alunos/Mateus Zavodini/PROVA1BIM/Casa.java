public class Casa extends Imovel {
    boolean quintal;

    public Casa(String endereco, double valor, boolean quintal) {
        super(endereco, valor);
        this.quintal = quintal;
    }

    public void mostrar() {
        super.mostrar();
        System.out.println("Tem quintal: " + quintal);
    }
}