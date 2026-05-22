import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {

        Scanner sc = new Scanner(System.in);
        Imobiliaria imobiliaria = new Imobiliaria();

        Inquilino[] inquilinos = new Inquilino[10];
        Imovel[] imoveis = new Imovel[10];

        int qtdInquilinos = 0;
        int qtdImoveis = 0;

        int opcao;

        do {
            System.out.println("\n1 - Cadastrar Imóvel");
            System.out.println("2 - Cadastrar Inquilino");
            System.out.println("3 - Cadastrar Contrato");
            System.out.println("4 - Encerrar Contrato");
            System.out.println("5 - Listar Contratos Ativos");
            System.out.println("6 - Listar Casas");
            System.out.println("7 - Listar Apartamentos");
            System.out.println("8 - Listar Inquilinos");
            System.out.println("9 - Listar Todos Contratos");
            System.out.println("10 - Demonstração");
            System.out.println("0 - Sair");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {

                case 1:

                    System.out.println("1 - Apartamento");
                    System.out.println("2 - Casa");
                    int tipo = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Endereço: ");
                    String endereco = sc.nextLine();

                    System.out.print("Valor do aluguel: ");
                    double valor = sc.nextDouble();

                    if (tipo == 1) {

                        System.out.print("Andar: ");
                        int andar = sc.nextInt();

                        imoveis[qtdImoveis] = new Apartamento(endereco, valor, andar);

                    } else {

                        System.out.print("Tem quintal (true/false): ");
                        boolean quintal = sc.nextBoolean();

                        imoveis[qtdImoveis] = new Casa(endereco, valor, quintal);
                    }

                    qtdImoveis++;
                    break;

                case 2:

                    System.out.print("Nome: ");
                    String nome = sc.nextLine();

                    System.out.print("CPF: ");
                    String cpf = sc.nextLine();

                    System.out.print("Telefone: ");
                    String tel = sc.nextLine();

                    inquilinos[qtdInquilinos] = new Inquilino(nome, cpf, tel);
                    qtdInquilinos++;

                    break;

                case 3:

                    System.out.println("Escolha o Inquilino:");
                    for (int i = 0; i < qtdInquilinos; i++) {
                        System.out.println(i + " - " + inquilinos[i].getNome());
                    }
                    int i = sc.nextInt();

                    System.out.println("Escolha o Imóvel:");
                    for (int j = 0; j < qtdImoveis; j++) {
                        System.out.println(j + " - " + imoveis[j].getEndereco());
                    }
                    int j = sc.nextInt();

                    System.out.print("Ano início: ");
                    int ai = sc.nextInt();
                    System.out.print("Mês início: ");
                    int mi = sc.nextInt();
                    System.out.print("Dia início: ");
                    int di = sc.nextInt();

                    System.out.print("Ano fim: ");
                    int af = sc.nextInt();
                    System.out.print("Mês fim: ");
                    int mf = sc.nextInt();
                    System.out.print("Dia fim: ");
                    int df = sc.nextInt();

                    Contrato c = new Contrato(
                            imoveis[j],
                            inquilinos[i],
                            LocalDate.of(ai, mi, di),
                            LocalDate.of(af, mf, df));

                    imobiliaria.adicionarContrato(c);

                    break;

                case 4:

                    imobiliaria.listarTodos();

                    System.out.print("numero do contrato(0-9): ");
                    int index = sc.nextInt();

                    imobiliaria.encerrarContrato(index);

                    break;

                case 5:
                    imobiliaria.listarContratosAtivos();
                    break;

                case 6:
                    System.out.println("CASAS:");
                    for (int k = 0; k < qtdImoveis; k++) {
                        if (imoveis[k] instanceof Casa) {
                            imoveis[k].exibirImovel();
                            System.out.println("----------------");
                        }
                    }
                    break;

                case 7:
                    System.out.println("APARTAMENTOS:");
                    for (int k = 0; k < qtdImoveis; k++) {
                        if (imoveis[k] instanceof Apartamento) {
                            imoveis[k].exibirImovel();
                            System.out.println("----------------");
                        }
                    }
                    break;

                case 8:
                    System.out.println("INQUILINOS:");
                    for (int k = 0; k < qtdInquilinos; k++) {
                        inquilinos[k].exibir();
                        System.out.println("----------------");
                    }
                    break;

                case 9:
                    imobiliaria.listarTodos();
                    break;

                case 10:
                    demonstracao(imobiliaria);
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida");
            }

        } while (opcao != 0);

        sc.close();
    }

    public static void demonstracao(Imobiliaria imobiliaria) {

        System.out.println("===== DEMONSTRAÇÃO =====");

        // Inquilinos
        Inquilino i1 = new Inquilino("João", "11111111111", "99999-1111");
        Inquilino i2 = new Inquilino("Maria", "22222222222", "99999-2222");

        // Imóveis
        Apartamento ap = new Apartamento("Rua A, 123", 1200, 3);
        Casa casa = new Casa("Rua B, 456", 1800, true);

        // Contratos
        Contrato c1 = new Contrato(
                ap,
                i1,
                LocalDate.of(2026, 5, 1),
                LocalDate.of(2026, 8, 1));

        Contrato c2 = new Contrato(
                casa,
                i2,
                LocalDate.of(2026, 6, 1),
                LocalDate.of(2026, 12, 1));

        // encerra apenas o primeiro
        c1.encerrarContrato();

        imobiliaria.adicionarContrato(c1);
        imobiliaria.adicionarContrato(c2);

        // MOSTRA TODOS (inclui encerrado)
        System.out.println("\nTODOS OS CONTRATOS:");
        imobiliaria.listarTodos();

        // MOSTRA APENAS ATIVOS
        System.out.println("\nCONTRATOS ATIVOS:");
        imobiliaria.listarContratosAtivos();
    }
}