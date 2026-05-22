public non-sealed class Casa extends Imovel{

    private boolean quintal;

    public Casa(Endereco endereco, double valorAluguel, boolean quintal) {
        super(endereco, valorAluguel);
        this.quintal = quintal;
    }

    public Casa(){}

    public boolean isQuintal() {
        return quintal;
    }

    public void setQuintal(boolean quintal) {
        this.quintal = quintal;
    }

    public String mostrarInformacoes(){
        String informacoes = "";
        if(this.isQuintal() == true){
            informacoes = "Casa endereço: " + this.getEndereco().apresentarLogradouro() + ", tem quintal: " + this.isQuintal() + 
        ", valor do aluguel " + this.getValorAluguel();
        }else{
            informacoes = "Casa endereço: " + this.getEndereco().apresentarLogradouro() + ", não tem quintal: " + this.isQuintal() + 
        ", valor do aluguel " + this.getValorAluguel();
        }
        return informacoes;
    }

    
}
