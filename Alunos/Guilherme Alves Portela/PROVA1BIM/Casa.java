public class Casa extends Imovel{
    public boolean quintal;

    public Casa(String endereco, double valorAluguel, boolean quintal) {
        super(endereco, valorAluguel);
        this.quintal = quintal;
    }

    //Getters

    public boolean isQuintal() {
        return quintal;
    }

    //Setters

    public void setQuintal(boolean quintal) {
        this.quintal = quintal;
    }
    
    // Métodos da classe
    
    @Override
    public void exibirInfos() { 
        String infos = String.format("Endereço: %s | Quintal: %s | Valor Aluguel: %.2f",
        this.endereco, (this.quintal ? "SIM" : "NÃO") , this.valorAluguel);

        System.out.println(infos);
    }

 
}
