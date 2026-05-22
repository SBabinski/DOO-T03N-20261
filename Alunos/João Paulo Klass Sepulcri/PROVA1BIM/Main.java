import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Inquilino> inquilinos = new ArrayList<Inquilino>();
        ArrayList<Imovel> imoveis = new ArrayList<Imovel>();
        Imobiliaria imobiliaria = new Imobiliaria();
        int opcao;

        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarInquilino(scanner, inquilinos);
                    break;
                case 2:
                    cadastrarImovel(scanner, imoveis);
                    break;
                case 3:
                    cadastrarContrato(scanner, inquilinos, imoveis, imobiliaria);
                    break;
                case 4:
                    encerrarContrato(scanner, imobiliaria);
                    break;
                case 5:
                    imobiliaria.listarContratosAtivos();
                    break;
                case 6:
                    executarDemonstracao();
                    break;
                case 0:
                    System.out.println("Encerrando o programa.");
                    break;
                default:
                    System.out.println("Opcao invalida.");
                    break;
            }

            System.out.println();
        } while (opcao != 0);

        scanner.close();
    }

    public static void exibirMenu() {
        System.out.println("===== MENU =====");
        System.out.println("1 - Cadastrar inquilino");
        System.out.println("2 - Cadastrar imovel");
        System.out.println("3 - Cadastrar contrato");
        System.out.println("4 - Encerrar contrato");
        System.out.println("5 - Listar contratos ativos");
        System.out.println("6 - Demonstracao");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    public static void cadastrarInquilino(Scanner scanner, ArrayList<Inquilino> inquilinos) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        Inquilino inquilino = new Inquilino(nome, cpf, telefone);
        inquilinos.add(inquilino);
        System.out.println("Inquilino cadastrado com sucesso.");
    }

    public static void cadastrarImovel(Scanner scanner, ArrayList<Imovel> imoveis) {
        System.out.println("1 - Apartamento");
        System.out.println("2 - Casa");
        System.out.print("Escolha o tipo do imovel: ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Endereco: ");
        String endereco = scanner.nextLine();
        System.out.print("Valor do aluguel: ");
        double valorAluguel = scanner.nextDouble();

        if (tipo == 1) {
            System.out.print("Andar: ");
            int andar = scanner.nextInt();
            Apartamento apartamento = new Apartamento(endereco, valorAluguel, andar);
            imoveis.add(apartamento);
            System.out.println("Apartamento cadastrado com sucesso.");
        } else if (tipo == 2) {
            System.out.print("Possui quintal? (true/false): ");
            boolean possuiQuintal = scanner.nextBoolean();
            Casa casa = new Casa(endereco, valorAluguel, possuiQuintal);
            imoveis.add(casa);
            System.out.println("Casa cadastrada com sucesso.");
        } else {
            System.out.println("Tipo de imovel invalido.");
        }

        scanner.nextLine();
    }

    public static void cadastrarContrato(Scanner scanner, ArrayList<Inquilino> inquilinos, ArrayList<Imovel> imoveis,
                                         Imobiliaria imobiliaria) {
        if (inquilinos.size() == 0 || imoveis.size() == 0) {
            System.out.println("Cadastre pelo menos um inquilino e um imovel antes de criar contrato.");
            return;
        }

        System.out.println("Lista de inquilinos:");
        for (int i = 0; i < inquilinos.size(); i++) {
            System.out.println(i + " - " + inquilinos.get(i).nome);
        }
        System.out.print("Escolha o indice do inquilino: ");
        int indiceInquilino = scanner.nextInt();

        System.out.println("Lista de imoveis:");
        for (int i = 0; i < imoveis.size(); i++) {
            System.out.println(i + " - " + imoveis.get(i).endereco);
        }
        System.out.print("Escolha o indice do imovel: ");
        int indiceImovel = scanner.nextInt();
        scanner.nextLine();

        if (indiceInquilino < 0 || indiceInquilino >= inquilinos.size() ||
                indiceImovel < 0 || indiceImovel >= imoveis.size()) {
            System.out.println("Indice invalido.");
            return;
        }

        System.out.print("Data de inicio: ");
        String dataInicio = scanner.nextLine();
        System.out.print("Data de fim: ");
        String dataFim = scanner.nextLine();

        Contrato contrato = new Contrato(inquilinos.get(indiceInquilino), imoveis.get(indiceImovel), dataInicio, dataFim,
                false);
        imobiliaria.adicionarContrato(contrato);
    }

    public static void encerrarContrato(Scanner scanner, Imobiliaria imobiliaria) {
        if (imobiliaria.contratos.size() == 0) {
            System.out.println("Nao ha contratos cadastrados.");
            return;
        }

        System.out.println("Lista de contratos:");
        for (int i = 0; i < imobiliaria.contratos.size(); i++) {
            Contrato contrato = imobiliaria.contratos.get(i);
            System.out.println(i + " - " + contrato.inquilino.nome + " / " + contrato.imovel.endereco
                    + " / Encerrado: " + (contrato.encerrado ? "Sim" : "Nao"));
        }

        System.out.print("Escolha o indice do contrato para encerrar: ");
        int indiceContrato = scanner.nextInt();
        scanner.nextLine();

        if (indiceContrato < 0 || indiceContrato >= imobiliaria.contratos.size()) {
            System.out.println("Indice invalido.");
            return;
        }

        imobiliaria.contratos.get(indiceContrato).encerrado = true;
        System.out.println("Contrato encerrado com sucesso.");
    }

    public static void executarDemonstracao() {
        Imobiliaria imobiliaria = new Imobiliaria();

        Inquilino inquilino1 = new Inquilino("Ana Souza", "111.111.111-11", "(11) 99999-1111");
        Inquilino inquilino2 = new Inquilino("Carlos Lima", "222.222.222-22", "(11) 98888-2222");

        Apartamento apartamento = new Apartamento("Rua das Flores, 100", 1800.0, 5);
        Casa casa = new Casa("Avenida Central, 250", 2500.0, true);

        Contrato contrato1 = new Contrato(inquilino1, apartamento, "01/01/2026", "31/12/2026", false);
        Contrato contrato2 = new Contrato(inquilino2, casa, "01/02/2026", "31/01/2027", true);

        imobiliaria.adicionarContrato(contrato1);
        imobiliaria.adicionarContrato(contrato2);

        System.out.println("===== DEMONSTRACAO =====");
        System.out.println("Foram criados 2 inquilinos, 1 apartamento, 1 casa e 2 contratos.");
        System.out.println("Um contrato esta ativo e o outro esta encerrado.");
        System.out.println();
        System.out.println("Contratos ativos:");
        imobiliaria.listarContratosAtivos();
    }
}
