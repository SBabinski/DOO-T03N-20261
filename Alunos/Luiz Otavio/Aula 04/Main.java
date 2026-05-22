import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {
    static ArrayList<Venda> vendas = new ArrayList<>();
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        MenuInicial(scan);

    }
    public static void MenuInicial(Scanner scan){
        int escolha;
        do {
            System.out.printf("========MENU==========\n");
            System.out.printf("[1] - Calcular preco total\n");
            System.out.printf("[2] - Calcular troco\n");
            System.out.printf("[3] - Registros de vendas\n");
            System.out.printf("[4] - Buscar vendas por dia\n");
            System.out.printf("[5] - Buscar vendas por mes\n");
            System.out.printf("[6] - Sair\n");
            escolha = scan.nextInt();
            if (escolha < 1 || escolha > 6){
                System.out.println("Escolha inválida!");
            }
            switch (escolha){
                case 1 : CalcularTotal(scan);
                    break;
                case 2 : CalcularTroco(scan);
                    break;
                case 3 : MostrarVendas(vendas);
                    break;
                case 4:
                    BuscarPorDia(scan);
                    break;
                case 5:
                    BuscarPorMes(scan);
                    break;

            }

        } while (escolha != 6);
        System.out.println("Obrigado(a), volte sempre!");


    }
    public static void CalcularTotal(Scanner scan){
        System.out.println("Digite a data da venda (dd/MM/yyyy):");
        String dataTexto = scan.next();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(dataTexto, formatter);

        System.out.println("Qual a quantidade?");
        int quantidade = scan.nextInt();

        System.out.println("Qual o valor unitario?");
        double valor = scan.nextDouble();

        double precoTotal = quantidade * valor;
        double desconto = 0;

        if(quantidade >= 10){
            desconto = precoTotal * 0.05;
            precoTotal = precoTotal - desconto;
        }

        System.out.println("Valor total: R$ " + precoTotal);

        Venda v = new Venda(quantidade, precoTotal, desconto, data);
        vendas.add(v);
    }

    public static void CalcularTroco(Scanner scan){
        System.out.println("Qual o valor recebido pelo cliente?");
        double pago= scan.nextDouble();
        if (pago <= 0){
            System.out.println("Valor pago invalido");
            return;
        }
        System.out.println("Qual foi o valor da compra?");
        double compra = scan.nextDouble();
        if (pago < compra){
            System.out.println("Valor pago insuficiente");
            return;
        }
        System.out.println("Troco:R$" +(pago - compra));
    }
    public static void MostrarVendas(ArrayList<Venda> vendas){

        if(vendas.size() == 0){
            System.out.println("Nenhuma venda registrada.");
            return;
        }

        for(int i = 0; i < vendas.size(); i++){
            Venda v = vendas.get(i);

            System.out.println("Venda " + (i+1));
            System.out.println("Quantidade: " + v.quantidade);
            System.out.println("Valor da venda: R$ " + v.valorVenda);
            System.out.println("Desconto: R$ " + v.desconto);
            System.out.println("----------------------");
        }
    }
    public static void BuscarPorDia(Scanner scan){
        System.out.println("Digite a data (dd/MM/yyyy):");
        String dataTexto = scan.next();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataBusca = LocalDate.parse(dataTexto, formatter);

        int total = 0;

        for(Venda v : vendas){
            if(v.data.equals(dataBusca)){
                total++;
            }
        }

        System.out.println("Total de vendas no dia: " + total);
    }
    public static void BuscarPorMes(Scanner scan){
        System.out.println("Digite o mês (1 a 12):");
        int mes = scan.nextInt();

        int total = 0;

        for(Venda v : vendas){
            if(v.data.getMonthValue() == mes){
                total++;
            }
        }

        System.out.println("Total de vendas no mes: " + total);
    }

}