public abstract class Imovel {
    String endereco;
    float valorMensalAluguel;

    public Imovel(String endereco, float valorMensalAluguel){
        this.endereco = endereco;
        this.valorMensalAluguel = valorMensalAluguel;
    }

    public abstract void exibirInformacoes();
}
