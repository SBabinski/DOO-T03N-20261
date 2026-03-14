import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Loja {
    public static void main(String[] args){
        List<Calculadora> vendas = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        System.out.println("Bem vindo!");
        apresentaCafe();
        int escolha = -1;

        do{
            escolha = apresentaMenu(escolha, scan);
            switch (escolha) {
                case 1:
                    Calculadora novaVenda = new Calculadora();
                    calculaTotal(scan, novaVenda);
                    vendas.add(novaVenda);
                    break;
                case 2:
                    calculaTroco(scan);
                    break;   
                case 3:
                    registroVendas(scan, vendas);
                    break;
                case 4:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida, tente novamente.");
                    
                 
            }
        }while(escolha != 4);


    }

    public static void apresentaCafe(){
        System.out.println("Lembre-se que dar um café sobre a conversão da venda para 80%");
    }

    public static int apresentaMenu(int escolha, Scanner scan){
        System.out.println("1. Calcular venda");
        System.out.println("2. Calcular troco");
        System.out.println("3. Registro de vendas");
        System.out.println("4. Sair");
        
        escolha = scan.nextInt();
        scan.nextLine(); // Limpa o buffer do scanner
        return escolha;
    }

    public static void calculaTotal(Scanner scan, Calculadora calc){
        System.out.println("Digite a quantidade do produto:");
        int quantidade = scan.nextInt();
        double totalcomdesc;
        System.out.println("Digite o preço do produto:");
        double preco = scan.nextDouble();
        double total = preco * quantidade;
        if(quantidade > 10){
            totalcomdesc = total *0.95;
        }else{
            totalcomdesc = total;
        }
        calc.setQuantidade(quantidade);
        calc.setPreco(total);
        calc.setDescontoAplicado(totalcomdesc);
        System.out.printf("O preço total é: %.2f\n", totalcomdesc);
        return;


    }

    public static void calculaTroco(Scanner scan){
        System.out.println("Digite o valor pago:");
        double valorPago = scan.nextDouble();
        System.out.println("Digite o preço total da compra:");
        double precoTotal = scan.nextDouble();
        double troco = valorPago - precoTotal;
        System.out.println("O troco é: " + troco);
        return;
    }

    public static void registroVendas(Scanner scan, List<Calculadora> vendas){
        System.out.println("Registro de vendas:");
        for(Calculadora calc : vendas){
            System.out.printf("Quantidade: %d, Preço: %.2f,"+
             " Total com desconto: %.2f%n",
            calc.getQuantidade(), calc.getPreco(), calc.getDescontoAplicado());
        }
       
        return;
    }
}

    

