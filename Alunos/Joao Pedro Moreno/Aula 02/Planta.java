import java.util.Scanner;

public class Planta {

    static Scanner scan = new Scanner(System.in);

    String nome;
    int quant;
    double valor;
    double total;

    public Planta() {
    }

    public String LeituraNome(){
        System.out.println("\nInforme a espécie da planta:");
        nome = scan.nextLine();
        return nome;      
    }

    public int LeituraQuant() {

        System.out.println("Informe a quantidade da planta:");
        quant = scan.nextInt();
        scan.nextLine();
        return quant;
    }

    public double LeituraValor() {

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

     public String toString() {
        return "|Planta: " + nome + "  Valor: " + total;
    }
}
