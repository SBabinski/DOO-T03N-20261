public class Casa extends Imovel {

    private boolean quintal;

    public Casa(String endereco, double valorAluguel, boolean quintal) {
        super(endereco, valorAluguel);
        this.quintal = quintal;
    }

    @Override
    public void exibirDados() {
        System.out.println("CASA");
        System.out.println("Endereço: " + endereco);
        System.out.println("Valor: " + valorAluguel);
        System.out.println("Quintal: " + (quintal ? "Sim" : "Não"));
    }
}