import java.util.Scanner;
public class Principal {
    static Scanner scanner = new Scanner(System.in);
    static final String NOME_LOJA = "Loja de Plantas da Dona Gabrielinha";

    public static void main(String[] args) {
        menuPrincipal();
        scanner.close();
    }
    public static void cabecalho() {
        System.out.println("\n==========================================");
        System.out.println(" " + NOME_LOJA);
        System.out.println("           MENU PRINCIPAL");
        System.out.println("==========================================");
    }
    public static void menuPrincipal() {
        int op;
        do {
            cabecalho();
            System.out.println("Escolha uma opcao:");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Sair");
            System.out.print("Opcao: ");
            op = scanner.nextInt();
            switch (op) {
                case 1:
                    calculaTotal();
                    break;
                case 2:
                    calculaTroco();
                    break;
                case 3:
                    System.out.println("\nSistema finalizado.");
                    break;
                default:
                    System.out.println("\nOpcao invalida.");
            }
        } while (op != 3);
    }
    public static void calculaTotal() {
        int quantidade;
        double preco;
        while (true) {
            System.out.print("\nDigite a quantidade do produto: ");
            quantidade = scanner.nextInt();
            if (quantidade > 0) {
                break;
            }
            System.out.println("Quantidade invalida.");
        }
        while (true) {
            System.out.print("Digite o preco do produto: ");
            preco = scanner.nextDouble();
            if (preco > 0) {
                break;
            }
            System.out.println("Preco invalido.");
        }
        double total = quantidade * preco;
        System.out.printf("\nTotal da compra: R$ %.2f\n", total);
    }
    public static void calculaTroco() {
        double dinheiro;
        double compra;
        while (true) {
            System.out.print("\nDigite o valor entregue ao caixa: ");
            dinheiro = scanner.nextDouble();
            if (dinheiro > 0) {
                break;
            }
            System.out.println("Valor invalido.");
        }
        while (true) {
            System.out.print("Digite o valor da compra: ");
            compra = scanner.nextDouble();
            if (compra > 0) {
                break;
            }
            System.out.println("Valor invalido.");
        }
        double troco = dinheiro - compra;
        if (troco < 0) {
            System.out.printf("Faltou pagar: R$ %.2f\n", Math.abs(troco));
        } else {
            System.out.printf("Troco a devolver: R$ %.2f\n", troco);
        }
    }
}