import java.util.Scanner; 

public class calculadora {
 
    public static void main(String[] args) {
         Scanner sc = new Scanner(System.in);
int opcao = 0;
        
        while (opcao != 3) {
           
            System.out.println("1 - calcular valor total\n");
             
            System.out.println("2 - calcular troco\n");
            
            System.out.println("3-sair");
              System.out.println("Escolha uma opção:\n");
         opcao = sc.nextInt();
if(opcao == 1) {
            calculaValorTotal();
        }
   
        if(opcao == 2) {
            calculaTroco();
        }
    }
    System.out.println("\nPrograma encerrado.\n");

    }

    public static void calculaValorTotal() {
        int valorProduto;
        int quantidade;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Digite o valor do produto:");
        valorProduto = sc.nextInt();
        System.out.println("Digite a quantidade:");
        quantidade = sc.nextInt();
        int valorTotal = valorProduto * quantidade;
        System.out.println("Valor total: " + valorTotal);
    }


public static void calculaTroco() {
     int valorpago;
     int valorproduto;
        Scanner sc = new Scanner(System.in);


        System.out.println("Digite o valor pago:");
        valorpago = sc.nextInt();
        System.out.println("Digite o valor do produto:");
        valorproduto = sc.nextInt(); 

    System.out.println("Troco:" + (valorpago-valorproduto));
    }
}