public class Apartamento extends Imovel {

    private int andar;

    // Construtor vazio
    public Apartamento() {
    }

    // Construtor completo
    public Apartamento(String endereco, double valorAluguel, int andar) {
        super(endereco, valorAluguel);
        this.andar = andar;
    }

    // Getter e setter para o atributo andar
    public int getAndar() {
        return andar;
    }

    public void setAndar(int andar) {
        this.andar = andar;
    }

    // Exibe informações específicas do apartamento
    @Override
    public void mostrarImovel() {
        System.out.printf("Apartamento | Endereço: %s | Valor do Aluguel: R$ %.2f | Andar: %d%n",
                getEndereco(), getValorAluguel(), getAndar());
    }
}
