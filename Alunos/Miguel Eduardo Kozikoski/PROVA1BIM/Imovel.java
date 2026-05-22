public class Imovel {
    String endereco;
    String quintal;
    String andar;
    int valorAluguel;

    public Imovel(String andar ,String quintal , String endereco , int valorAluguel){
        this.endereco = endereco;
        this.valorAluguel = valorAluguel;
        this.quintal = quintal ;
        this.andar = andar;
    }

    public void Apresentacao(){
        System.out.println("o imovel no endereço " + endereco + " " + "com aluguel de :" + valorAluguel);
    }
    public String getEndereco(){
        return this.endereco;
    }
    public int getvalorAluguel(){
        return this.valorAluguel;
    }

}
