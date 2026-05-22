public class Casa extends Imovel {

    private boolean quintal;

    // Construtor vazio
    public Casa() {
    }

    // Construtor completo
    public Casa(String endereco, double valorAluguel, boolean quintal) {
        super(endereco, valorAluguel);
        this.quintal = quintal;
    }

    // Getter e setter para o atributo quintal
    public boolean getQuintal() {
        return quintal;
    }

    public void setQuintal(boolean quintal) {
        this.quintal = quintal;
    }

    // Exibe informações específicas do carro
    @Override
    public void mostrarImovel() {
        System.out.printf("Casa | Endereço: %s | Valor do Aluguel: R$ %.2f | Quintal: %b%n",
                getEndereco(), getValorAluguel(), getQuintal());
    }
}
