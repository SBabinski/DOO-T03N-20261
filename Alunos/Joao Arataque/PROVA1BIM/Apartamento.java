public class Apartamento extends Imovel{
    int andar;

    public Apartamento(String endereco, float valorMensalAluguel, int andar){
        super(endereco, valorMensalAluguel);
        this.andar = andar;
    }

    @Override
    public void exibirInformacoes(){
        System.out.println("Tipo: Apartamento");
        System.out.println("Endereço: " + endereco + " | Andar: " + andar);
        System.out.println("Valor do aluguel mensal: R$" + valorMensalAluguel + ".");
    }

}
