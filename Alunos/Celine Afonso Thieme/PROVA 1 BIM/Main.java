import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static Imobiliaria imob = new Imobiliaria();

    public static void main(String[] args) {

        int op;

        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1 Inquilino");
            System.out.println("2 Imóvel Casa");
            System.out.println("3 Imóvel Apartamento");
            System.out.println("4 Criar Contrato");
            System.out.println("5 Listar Ativos");
            System.out.println("6 Encerrar Contrato");
            System.out.println("0 Sair");

            op = sc.nextInt();

            switch (op) {

                case 1:
                    cadastrarInquilino();
                    break;

                case 2:
                    cadastrarCasa();
                    break;

                case 3:
                    cadastrarApartamento();
                    break;

                case 4:
                    criarContrato();
                    break;

                case 5:
                    imob.listarAtivos();
                    break;

                case 6:
                    encerrar();
                    break;
            }

        } while (op != 0);
    }

    static void cadastrarInquilino() {
        sc.nextLine();
        System.out.print("Nome: ");
        String n = sc.nextLine();
        System.out.print("CPF: ");
        String c = sc.nextLine();
        System.out.print("Tel: ");
        String t = sc.nextLine();

        imob.addInquilino(new Inquilino(n, c, t));
    }

    static void cadastrarCasa() {
        sc.nextLine();
        System.out.print("Endereço: ");
        String e = sc.nextLine();
        System.out.print("Valor: ");
        double v = sc.nextDouble();
        System.out.print("Quintal (true/false): ");
        boolean q = sc.nextBoolean();

        imob.addImovel(new Casa(e, v, q));
    }

    static void cadastrarApartamento() {
        sc.nextLine();
        System.out.print("Endereço: ");
        String e = sc.nextLine();
        System.out.print("Valor: ");
        double v = sc.nextDouble();
        System.out.print("Andar: ");
        int a = sc.nextInt();

        imob.addImovel(new Apartamento(e, v, a));
    }

    static void criarContrato() {

        System.out.println("Escolha inquilino:");
        imob.listarInquilinos();
        int i = sc.nextInt();

        System.out.println("Escolha imóvel:");
        imob.listarImoveis();
        int j = sc.nextInt();

        Contrato c = new Contrato(
                imob.getInquilino(i),
                imob.getImovel(j),
                LocalDate.now(),
                LocalDate.now().plusMonths(12)
        );

        imob.addContrato(c);
    }

    static void encerrar() {
        System.out.println("Qual contrato?");
        int i = sc.nextInt();
        imob.getContrato(i).encerrar();
    }
}