import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Planta {

    static Scanner scan = new Scanner(System.in);

    private String nome;
    private int quant;
    private double valor;
    private double total;
    private LocalDate dataVenda;

    public Planta() {
        dataVenda = LocalDate.now(); // salva a data da venda automaticamente
    }

    public String leituraNome(){
        System.out.println("\nInforme a espécie da planta:");
        nome = scan.nextLine();
        return nome;
    }

    public int leituraQuant() {

        System.out.println("Informe a quantidade da planta:");
        quant = scan.nextInt();
        scan.nextLine();
        return quant;
    }

    public double leituraValor() {

        System.out.println("Informe o valor da planta:");
        valor = scan.nextDouble();
        scan.nextLine();
        return valor;
    }

    public double calcularPrecoTotal() {

        total = quant * valor;

        if (quant >= 10) {
            double desconto = total * 0.05;
            total = total - desconto;
            System.out.println("Desconto de 5% foi aplicado!");
        }

        return total;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public String toString() {

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return "| Data: " + dataVenda.format(formato) +
               " / Planta: " + nome +
               " / Quantidade: " + quant +
               " / Total: " + total;
    }
}