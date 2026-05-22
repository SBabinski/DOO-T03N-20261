package entities;

public abstract class Imovel {
    protected String endereco;
    protected double aluguel;
    
    public String getEndereco() {
        return endereco;
    }
    
    public double getAluguel() {
        return aluguel;
    }
    
    public void setAluguel(double aluguel) {
        this.aluguel = aluguel;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    @Override
    public String toString() {
        return String.format(
                "Endereço: %s | Aluguel: R$ %.2f",
                endereco,
                aluguel
        );
    }
}
