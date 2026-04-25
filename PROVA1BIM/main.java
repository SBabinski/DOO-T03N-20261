import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Imobiliaria imobiliaria = new Imobiliaria();

        // DEMONSTRAÇÃO
        Inquilino i1 = new Inquilino("João", "111", "9999");
        Inquilino i2 = new Inquilino("Maria", "222", "8888");

        Apartamento ap = new Apartamento("Rua A", 1200, 3);
        Casa casa = new Casa("Rua B", 1500, true);

        Contrato c1 = new Contrato(i1, ap, 12, "01/01/2024", "01/01/2025");
        Contrato c2 = new Contrato(i2, casa, 6, "01/02/2024", "01/08/2024");

        c1.encerrar(); // um encerrado

        imobiliaria.adicionarContrato(c1);
        imobiliaria.adicionarContrato(c2);

        imobiliaria.listarContratosAtivos();

        // MENU SIMPLES
        int opcao;
        do {
            System.out.println("\n1 - Listar contratos ativos");
            System.out.println("2 - Encerrar contrato");
            System.out.println("0 - Sair");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    imobiliaria.listarContratosAtivos();
                    break;
                case 2:
                    System.out.print("Índice do contrato: ");
                    int idx = sc.nextInt();
                    imobiliaria.encerrarContrato(idx);
                    break;
            }
        } while (opcao != 0);

        sc.close();
    }
}
//entrega final
