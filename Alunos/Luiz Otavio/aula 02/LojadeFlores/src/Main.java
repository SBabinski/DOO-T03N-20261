import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
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
            System.out.printf("[3] - Sair\n");
            escolha = scan.nextInt();
            if (escolha !=3 && escolha != 2 && escolha != 1){
                System.out.println("Faça uma escolha valida:");
            }
            switch (escolha){
                case 1 : CalcularTotal(scan);
                    break;
                case 2 : CalcularTroco(scan);
                break;
            }

        } while (escolha != 3);
         System.out.println("Obrigado(a), volte sempre!");


    }
    public static void CalcularTotal(Scanner scan){
        System.out.println("Qual a quantidade?");
        int quantidade = scan.nextInt();
        if (quantidade <= 0){
            System.out.println("Quantidade invalida");
            return;
        }
        System.out.println("Qual o valor unitario?");
        double valor = scan.nextDouble();
        if (valor <= 0){
            System.out.println("Valor invalido");
            return;
        }
        double precoTotal = quantidade * valor;
        System.out.println("O valor total é: R$" + precoTotal);
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
}