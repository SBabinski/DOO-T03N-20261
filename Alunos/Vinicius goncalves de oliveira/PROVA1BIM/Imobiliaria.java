import java.time.LocalDate;

abstract class Imobiliaria {
    String endereco;
    double valor;
    LocalDate aluguel;

    public Imobiliaria(String endereco, double valor, LocalDate aluguel) {
        this.endereco = endereco;
        this.valor = valor;
        this.aluguel = aluguel;
    }

    public void exibirImobiliaria() {
        System.out.println("Endereco: " + endereco
                + "Valor: " + valor
                + "Aluguel: " + aluguel);
    }
}

class apartamento extends Imobiliaria {
    int andar;

    public apartamento(String endereco, double valor, LocalDate aluguel, int andar) {
        super(endereco, valor, aluguel);
        this.andar = andar;
    }

    public void exibirImobiliaria() {
        System.out.println();
        System.out.println("Endereco: " + endereco
                + "Valor: " + valor
                + "Aluguel: " + aluguel
                + "Andar: " + andar);
    }

}

class casa extends Imobiliaria {
    boolean temQuintal;

    public casa(String endereco, double valor, LocalDate aluguel, boolean temQuintal) {
        super(endereco, valor, aluguel);
        this.temQuintal = temQuintal;
    }

    public void exibirImobiliaria() {
        System.out.println("Endereco: " + endereco
                + "Valor: " + valor
                + "Aluguel: " + aluguel
                + "Tem Quintal: " + temQuintal);
    }
}
