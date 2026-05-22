import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Imobiliaria imobiliaria = new Imobiliaria();

        int opcao;

        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Demonstração");
            System.out.println("2 - Listar contratos ativos");
            System.out.println("3 - Sair");
            opcao = sc.nextInt();

            switch(opcao) {

                case 1:

                    Inquilino i1 = new Inquilino("Carlos", "11111111111", "99999-9999");
                    Inquilino i2 = new Inquilino("Ana", "22222222222", "8888-8888");

                    Apartamento ap = new Apartamento("Rua A", 1200, 3);
                    Casa casa = new Casa("Rua B", 1800, true);

                    Contrato c1 = new Contrato(i1, ap, 12, "01/01/2024", "01/01/2025");
                    Contrato c2 = new Contrato(i2, casa, 10, "01/03/2024", "01/01/2025");

                    c1.encerrarContrato();

                    imobiliaria.adicionarContrato(c1);
                    imobiliaria.adicionarContrato(c2);

                    System.out.println("Demonstração criada.");
                    break;

                case 2:
                    imobiliaria.listarAtivos();
                    break;

                case 3:
                    System.out.println("Encerrando...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while(opcao != 3);

        sc.close();
    }
}
