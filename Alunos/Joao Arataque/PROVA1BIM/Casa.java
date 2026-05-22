public class Casa extends Imovel{
    boolean quintal;

    public Casa(String endereco, float valorMensalAluguel, boolean quintal){
        super(endereco, valorMensalAluguel);
        this.quintal = quintal;
    }


    @Override
    public void exibirInformacoes(){
        System.out.println("Tipo: Casa");
        System.out.println("Endereço: " + endereco + " Quintal: " + (quintal ? "Sim":"Não"));
        System.out.println("Valor do aluguel mensal: R$" + valorMensalAluguel + ".");
    }
}
