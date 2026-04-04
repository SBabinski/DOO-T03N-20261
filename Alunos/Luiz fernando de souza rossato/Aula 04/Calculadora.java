import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Calculadora {
    static ArrayList<Double> quantidades = new ArrayList<>();
    static ArrayList<Double> valores = new ArrayList<>();
    static ArrayList<Double> descontos = new ArrayList<>();
    static ArrayList<LocalDate> datas = new ArrayList<>();
    static LocalDate dataAtual;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static void main(String[] args)  {
        try (Scanner scanner = new Scanner(System.in)) {
            MostrarMenu(scanner);
        }
    }
    public static void MostrarMenu(Scanner scanner){
        int opcao;
        do{
            System.out.println("-----calculadora------");
            System.out.println("[1]- calcular preço final ");
            System.out.println("[2]- calcular troco");
            System.out.println("[3]- sair");
            System.out.println("[4]- registro de vendas");
            System.out.println("[5] - Pesquisar vendas por data");
            System.out.println("escolher uma das opcao:");

            opcao = scanner.nextInt();
        
            switch (opcao) {
                case 1:
                    CalcularVendas(scanner);
                    break;

                case 2:
                    CalcularTroco(scanner);
                    break;
                case 3:
                    System.out.println("encerrando o programa");
                    break;
                case 4:
                    registroVendas(scanner);
                    break;
                case 5:
                    pesquisarVendasPorData(scanner);
                    break;                        
                default:
                    System.out.println("opção invalida");
                    break;
            }  
        }while (opcao != 3);
    }

    private static void CalcularVendas(Scanner scanner) {
        System.out.println("coloque a quantidade da planta:");
        double quantidade = scanner.nextDouble();
        System.out.println("coloque o preço unitario da planta: ");
        double preco = scanner.nextDouble();
        double total = quantidade * preco;
        double desconto = 0;
        System.out.println("o preço final é: R$" + total);
        if (quantidade >= 10){
            desconto = total * 0.05;
            double valorFinal = total - desconto;
            System.out.println("o preço final com desconto é: R$" + valorFinal);
        }else{
            System.out.println("o preço final sem desconto é: R$" + total);
        }
          quantidades.add(quantidade);
            valores.add(total);
            descontos.add(desconto);
            dataAtual = LocalDate.now();
            datas.add(dataAtual);

    System.out.println("venda registrada!");
    System.out.println("data da venda: " + dataAtual.format(formatter));
    System.out.println("data da venda registrada com sucesso!");
    }

    private static void CalcularTroco(Scanner scanner) {
        System.out.println("digite o valor que o cliente deu: ");
        double valor = scanner.nextDouble();
        System.out.println("digite o valor total da compra");
        double valorTotal = scanner.nextDouble();

        double troco = valor - valorTotal;
        System.out.println("troco final é:  R$" + troco);

    }

    private static void registroVendas(Scanner scanner) {
        if (valores.isEmpty()) {
        System.out.println("nenhuma venda registrada.");
        return;
    }

    for (int i = 0; i < valores.size(); i++) {
        System.out.println("venda " + (i + 1));
        System.out.println("quantidade: " + quantidades.get(i));
        System.out.println("valor da venda: R$" + valores.get(i));
        System.out.println("desconto: R$" + descontos.get(i));
        System.out.println("data da venda:" + datas.get(i).format(formatter));
        
        
    }

    }

    private static void pesquisarVendasPorData(Scanner scanner) {
         System.out.println("Digite a data (dd/MM/yyyy): ");
    String texto = scanner.next();

    LocalDate dataBusca = LocalDate.parse(texto, formatter);

    for (int i = 0; i < datas.size(); i++) {
        if (datas.get(i).equals(dataBusca)) {


            System.out.println("Venda encontrada:");
            System.out.println("quantidade: " + quantidades.get(i));
            System.out.println("valor: R$" + valores.get(i));
            System.out.println("desconto: R$" + descontos.get(i));
            System.out.println("data: " + datas.get(i).format(formatter));
            System.out.println("-----------------");
        }
    }
        
    }
}
