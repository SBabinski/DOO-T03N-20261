package fag;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Inquilino> inquilinos = new ArrayList<>();
        ArrayList<Imovel> imoveis = new ArrayList<>();
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
                    fazerDemonstracao(inquilinos, imoveis, imobiliaria);
                    break;
                case 0:
                    System.out.println("Encerrando o sistema.");
                    break;
                default:
                    System.out.println("Opcao invalida.");
                    break;
            }
        } while (opcao != 0);

        scanner.close();
    }

    public static void exibirMenu() {
        System.out.println();
        System.out.println("==== MENU ====");
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
        System.out.print("Digite o tipo do imovel: ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Endereco: ");
        String endereco = scanner.nextLine();
        System.out.print("Valor do aluguel mensal: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        if (tipo == 1) {
            System.out.print("Andar: ");
            int andar = scanner.nextInt();
            scanner.nextLine();
            imoveis.add(new Apartamento(endereco, valor, andar));
            System.out.println("Apartamento cadastrado com sucesso.");
        } else if (tipo == 2) {
            boolean possuiQuintal = lerOpcaoSimNao(scanner, "A casa possui quintal?");
            imoveis.add(new Casa(endereco, valor, possuiQuintal));
            System.out.println("Casa cadastrada com sucesso.");
        } else {
            System.out.println("Tipo de imovel invalido.");
        }
    }

    public static void cadastrarContrato(Scanner scanner, ArrayList<Inquilino> inquilinos, ArrayList<Imovel> imoveis,
            Imobiliaria imobiliaria) {
        if (inquilinos.isEmpty()) {
            System.out.println("Cadastre pelo menos um inquilino antes.");
            return;
        }

        if (imoveis.isEmpty()) {
            System.out.println("Cadastre pelo menos um imovel antes.");
            return;
        }

        System.out.println("Inquilinos cadastrados:");
        for (int i = 0; i < inquilinos.size(); i++) {
            System.out.println(i + " - " + inquilinos.get(i).getNome());
        }

        System.out.print("Digite o numero do inquilino: ");
        int indiceInquilino = scanner.nextInt();
        scanner.nextLine();

        if (indiceInquilino < 0 || indiceInquilino >= inquilinos.size()) {
            System.out.println("Numero de inquilino invalido.");
            return;
        }

        System.out.println("Imoveis cadastrados:");
        for (int i = 0; i < imoveis.size(); i++) {
            System.out.println(i + " - " + imoveis.get(i).getEndereco());
        }

        System.out.print("Digite o numero do imovel: ");
        int indiceImovel = scanner.nextInt();
        scanner.nextLine();

        if (indiceImovel < 0 || indiceImovel >= imoveis.size()) {
            System.out.println("Numero de imovel invalido.");
            return;
        }

        System.out.print("Data de inicio (dd/MM/yyyy): ");
        LocalDate dataInicio = LocalDate.parse(scanner.nextLine(), FORMATO_DATA);
        System.out.print("Data final (dd/MM/yyyy): ");
        LocalDate dataFim = LocalDate.parse(scanner.nextLine(), FORMATO_DATA);
        boolean encerrado = lerOpcaoSimNao(scanner, "O contrato esta encerrado?");

        ContratoAluguel contrato = new ContratoAluguel(
                inquilinos.get(indiceInquilino),
                imoveis.get(indiceImovel),
                dataInicio,
                dataFim,
                encerrado);

        if (imobiliaria.adicionarContrato(contrato)) {
            System.out.println("Contrato cadastrado com sucesso.");
        } else {
            System.out.println("Nao foi possivel cadastrar o contrato. Limite de 10 contratos atingido.");
        }
    }

    public static void encerrarContrato(Scanner scanner, Imobiliaria imobiliaria) {
        imobiliaria.listarTodosContratos();

        if (imobiliaria.getQuantidadeContratos() == 0) {
            return;
        }

        System.out.print("Digite o numero do contrato que deseja encerrar: ");
        int indice = scanner.nextInt();
        scanner.nextLine();

        if (imobiliaria.encerrarContrato(indice)) {
            System.out.println("Contrato encerrado com sucesso.");
        } else {
            System.out.println("Numero de contrato invalido.");
        }
    }

    public static void fazerDemonstracao(ArrayList<Inquilino> inquilinos, ArrayList<Imovel> imoveis, Imobiliaria imobiliaria) {
        System.out.println("==== DEMONSTRACAO ====");

        Inquilino inquilino1 = new Inquilino("Ana Souza", "111.111.111-11", "(11) 99999-1111");
        Inquilino inquilino2 = new Inquilino("Carlos Lima", "222.222.222-22", "(11) 98888-2222");

        Imovel apartamento = new Apartamento("Rua das Flores, 100", 1500.0, 5);
        Imovel casa = new Casa("Avenida Central, 200", 2200.0, true);

        ContratoAluguel contrato1 = new ContratoAluguel(
                inquilino1,
                apartamento,
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 7, 1),
                true);

        ContratoAluguel contrato2 = new ContratoAluguel(
                inquilino2,
                casa,
                LocalDate.of(2026, 3, 1),
                LocalDate.of(2026, 12, 1),
                false);

        inquilinos.add(inquilino1);
        inquilinos.add(inquilino2);

        imoveis.add(apartamento);
        imoveis.add(casa);

        imobiliaria.adicionarContrato(contrato1);
        imobiliaria.adicionarContrato(contrato2);

        System.out.println();
        System.out.println("Inquilinos criados:");
        inquilino1.exibirInformacoes();
        System.out.println("-------------------------");
        inquilino2.exibirInformacoes();
        System.out.println("-------------------------");

        System.out.println("Imoveis criados:");
        apartamento.exibirInformacoes();
        System.out.println("-------------------------");
        casa.exibirInformacoes();
        System.out.println("-------------------------");

        System.out.println("Contratos ativos:");
        imobiliaria.listarContratosAtivos();
    }

    public static boolean lerOpcaoSimNao(Scanner scanner, String mensagem) {
        int opcao;

        do {
            System.out.println(mensagem);
            System.out.println("1 - Sim");
            System.out.println("2 - Nao");
            System.out.print("Digite uma opcao: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao != 1 && opcao != 2) {
                System.out.println("Opcao invalida.");
            }
        } while (opcao != 1 && opcao != 2);

        return opcao == 1;
    }
}
