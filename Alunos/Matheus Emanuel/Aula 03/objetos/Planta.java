public class Planta {

    private String especie;
    private int qtd;
    private double valor;   


public Planta(String especie, int qtd, double valor) {
        this.especie = especie;
        this.qtd = qtd;
        this.valor = valor;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void mostrarResumo(){
        System.out.println("Espécie: " + especie + " | Quantidade: " + qtd + " | Valor: " + valor);
        
    }
}