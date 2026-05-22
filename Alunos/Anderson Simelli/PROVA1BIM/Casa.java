package Imobiliaria;

public class Casa extends Imovel {

    boolean quintal;

    public Casa(String endereco, double valor, boolean quintal) {
        super(endereco, valor);
        this.quintal = quintal;
    }

    public void mostrar() {
    	System.out.println("Casa | Endereço: " + endereco +
    	        " | Quintal: " + (quintal ? "Sim" : "Não") +
    	        " | Valor: R$ " + valor);
    }
}