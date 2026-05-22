public class Apartamento extends Imovel {
    public int andar;

    public Apartamento(String endereco, double valorAluguel, int andar) {
        super(endereco, valorAluguel);
        this.andar = andar;
    }

    //Getters

    public int getAndar() {
        return andar;
    }

    //Setters

    public void setAndar(int andar) {
        this.andar = andar;
    }


    // Métodos da classe

    @Override
    public void exibirInfos() {
        String infos = String.format("Endereço: %s | Andar Apto: %d | Valor Aluguel: %.2f", this.endereco, this.andar , this.valorAluguel);

        System.out.println(infos);
    }

}
