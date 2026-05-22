package entities;

public class Casa extends Imovel{
    
    private boolean quintal;
    
    public Casa(String endereco, double aluguel, boolean quintal) {
        this.endereco = endereco;
        this.aluguel = aluguel;
        this.quintal = quintal;
    }
    
    public boolean isQuintal() {
        return quintal;
    }
    
    public void setQuintal(boolean quintal) {
        this.quintal = quintal;
    }
    
    @Override
    public String toString() {
        return String.format(
                "Casa | %s | Quintal: %s",
                super.toString(),
                (quintal ? "POSSUI" : "NÃO POSSUI")
        );
    }
}
