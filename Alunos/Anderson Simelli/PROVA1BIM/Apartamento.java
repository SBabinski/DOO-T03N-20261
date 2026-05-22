package Imobiliaria;

public class Apartamento extends Imovel {

    int andar;

    public Apartamento(String endereco, double valor, int andar) {
        super(endereco, valor);
        this.andar = andar;
    }

    public void mostrar() {
        System.out.println("Apartamento | Endereço: " + endereco +
                " | Andar: " + andar + " | Valor: R$ " + valor);
    }
}