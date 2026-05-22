public class Casa extends Imovel {
    private boolean quintal;

    public Casa(String endereco, double valor, boolean quintal) {
        super(endereco, valor);
        this.quintal = quintal;
    }

    @Override
    public String exibirDados() {
        return "Casa: " + endereco + " | R$" + valorAluguel +
               " | Quintal: " + (quintal ? "Sim" : "Não");
    }
}
