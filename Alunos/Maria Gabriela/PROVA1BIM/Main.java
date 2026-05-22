import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Imobiliaria imob = new Imobiliaria();

        Inquilino[] inquilinos = new Inquilino[10];
        Imovel[] imoveis = new Imovel[10];
        int qtdInq = 0, qtdIm = 0;

        int op;

        do {
            System.out.println("\n1 - Cadastrar Inquilino");
            System.out.println("2 - Cadastrar Imóvel");
            System.out.println("3 - Cadastrar Contrato");
            System.out.println("4 - Encerrar Contrato");
            System.out.println("5 - Listar Contratos Ativos");
            System.out.println("6 - Demonstração");
            System.out.println("0 - Sair");
            op = sc.nextInt();
            sc.nextLine();

            switch(op) {

                case 1:
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("CPF: ");
                    String cpf = sc.nextLine();
                    System.out.print("Telefone: ");
                    String tel = sc.nextLine();

                    inquilinos[qtdInq++] = new Inquilino(nome, cpf, tel);
                    break;

                case 2:
                    System.out.println("1-Apartamento 2-Casa");
                    int tipo = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Endereço: ");
                    String end = sc.nextLine();
                    System.out.print("Valor: ");
                    double val = sc.nextDouble();

                    if(tipo == 1) {
                        System.out.print("Andar: ");
                        int andar = sc.nextInt();
                        imoveis[qtdIm++] = new Apartamento(end, val, andar);
                    } else {
                        System.out.print("Tem quintal (true/false): ");
                        boolean q = sc.nextBoolean();
                        imoveis[qtdIm++] = new Casa(end, val, q);
                    }
                    break;

                case 3:
                    System.out.print("Índice do inquilino: ");
                    int i = sc.nextInt();
                    System.out.print("Índice do imóvel: ");
                    int j = sc.nextInt();
                    System.out.print("Meses: ");
                    int m = sc.nextInt();

                    imob.adicionarContrato(new Contrato(inquilinos[i], imoveis[j], m));
                    break;

                case 4:
                    System.out.print("Índice do contrato: ");
                    int c = sc.nextInt();
                    Contrato cont = imob.getContrato(c);
                    if(cont != null) cont.encerrar();
                    break;

                case 5:
                    imob.listarAtivos();
                    break;

                case 6:
                    Inquilino a = new Inquilino("João", "111", "9999");
                    Inquilino b = new Inquilino("Maria", "222", "8888");

                    Imovel ap = new Apartamento("Rua A", 1000, 2);
                    Imovel cs = new Casa("Rua B", 1500, true);

                    Contrato c1 = new Contrato(a, ap, 10);
                    Contrato c2 = new Contrato(b, cs, 5);

                    c1.encerrar(); 

                    imob.adicionarContrato(c1);
                    imob.adicionarContrato(c2);

                    imob.listarAtivos();
                    break;
            }
        } while(op != 0);
    }
}