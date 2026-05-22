package entities;

public class Apartamento extends Imovel{
    
    private int andar;
    
    public Apartamento(String endereco, double aluguel, int andar) {
        this.endereco = endereco;
        this.aluguel = aluguel;
        this.andar = andar;
    }
    
    public int getAndar() {
        return andar;
    }
    
    public void setAndar(int andar) {
        this.andar = andar;
    }
    
    @Override
    public String toString() {
        return String.format(
                "Apartamento | %s | Andar: %d",
                super.toString(),
                andar
        );
    }
}
