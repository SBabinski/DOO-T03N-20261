package objetos;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Planta {

    private String especie;
    private int qtd;
    private double valor;
    private LocalDate dataVenda;

    private static final DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Planta(String especie, int qtd, double valor) {
        this.especie = especie;
        this.qtd = qtd;
        this.valor = valor;
        this.dataVenda = LocalDate.now();
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

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getDataFormatada() {
        return dataVenda.format(dataFormatada);
    }

    public void mostrarResumo() {
        System.out.println("Espécie: " + especie
                + " | Quantidade: " + qtd
                + " | Valor: " + valor
                + " | Data: " + getDataFormatada());
    }
}
