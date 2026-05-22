import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static Imobiliaria imobiliaria = new Imobiliaria();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Cadastrar o Inquilino");
            System.out.println("2. Cadastrar o Imóvel");
            System.out.println("3. Cadastrar Contrato");
            System.out.println("4. Encerrar Contrato");
            System.out.println("5. Mostrar Contratos Ativos");
            System.out.println("6. Demonstração");
            System.out.println("0. Sair");
            System.out.print("Escolha uma das opçao: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    cadastrarInquilino();
                    break;
                case 2:
                    cadastrarImovel();
                    break;
                case 3:
                    cadastrarContrato();
                    break;
                case 4:
                    encerrarContrato();
                    break;
                case 5:
                    imobiliaria.listarContratosAtivos();
                    break;
                case 6:
                    demonstracao();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void cadastrarInquilino() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        Inquilino inquilino = new Inquilino(nome, cpf, telefone);
        imobiliaria.adicionarInquilino(inquilino);
        System.out.println("Inquilino cadastrado!");
    }

    private static void cadastrarImovel() {
        int tipo = 0;
        while (true) {
            try {
                System.out.print("Tipo (1 - Apartamento, 2 - Casa): ");
                tipo = scanner.nextInt();
                scanner.nextLine();
                if (tipo == 1 || tipo == 2) break;
                else System.out.println("Tipo inválido! Digite 1 ou 2.");
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida! Digite um número (1 ou 2).");
                scanner.nextLine(); // Consumir entrada inválida
            }
        }
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Valor Mensal: ");
        double valorMensal = scanner.nextDouble();
        scanner.nextLine();

        if (tipo == 1) {
            System.out.print("Andar: ");
            int andar = scanner.nextInt();
            scanner.nextLine();
            Apartamento apt = new Apartamento(endereco, valorMensal, andar);
            imobiliaria.adicionarImovel(apt);
            System.out.println("Apartamento cadastrado!");
        } else if (tipo == 2) {
            System.out.print("Tem Quintal? (true/false): ");
            boolean temQuintal = scanner.nextBoolean();
            scanner.nextLine();
            Casa casa = new Casa(endereco, valorMensal, temQuintal);
            imobiliaria.adicionarImovel(casa);
            System.out.println("Casa cadastrada!");
        }
    }

    private static void cadastrarContrato() {
        if (imobiliaria.getInquilinos().isEmpty() || imobiliaria.getImoveis().isEmpty()) {
            System.out.println("Cadastre inquilinos e imóveis primeiro!");
            return;
        }
        System.out.println("Inquilinos:");
        for (int i = 0; i < imobiliaria.getInquilinos().size(); i++) {
            System.out.println(i + ". " + imobiliaria.getInquilinos().get(i).getNome());
        }
        System.out.print("Escolha inquilino (índice): ");
        int idxInq = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Imóveis:");
        for (int i = 0; i < imobiliaria.getImoveis().size(); i++) {
            System.out.print(i + ". ");
            imobiliaria.getImoveis().get(i).exibirInformacoes();
        }
        System.out.print("Escolha imóvel (índice): ");
        int idxImov = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Data Início (AAAA-MM-DD): ");
        LocalDate inicio = LocalDate.parse(scanner.nextLine());
        System.out.print("Data Fim (AAAA-MM-DD): ");
        LocalDate fim = LocalDate.parse(scanner.nextLine());

        Contrato contrato = new Contrato(imobiliaria.getInquilinos().get(idxInq), imobiliaria.getImoveis().get(idxImov), inicio, fim);
        if (imobiliaria.adicionarContrato(contrato)) {
            System.out.println("Contrato cadastrado!");
        } else {
            System.out.println("Limite de contratos atingido!");
        }
    }

    private static void encerrarContrato() {
        System.out.println("Contratos ativos:");
        for (int i = 0; i < imobiliaria.getNumContratos(); i++) {
            if (imobiliaria.getContratos()[i].isAtivo()) {
                System.out.println(i + ". ");
                imobiliaria.getContratos()[i].exibirDados();
                System.out.println("---");
            }
        }
        System.out.print("Escolha contrato para encerrar (índice): ");
        int idx = scanner.nextInt();
        scanner.nextLine();
        imobiliaria.encerrarContrato(idx);
        System.out.println("Contrato encerrado");
    }

    private static void demonstracao() {
        
        Inquilino inq1 = new Inquilino("João Silva", "12345678901", "11987654321");
        Inquilino inq2 = new Inquilino("Maria Santos", "09876543210", "11876543210");
        imobiliaria.adicionarInquilino(inq1);
        imobiliaria.adicionarInquilino(inq2);

        
        Apartamento apt = new Apartamento("Rua A, 123", 1500.0, 5);
        Casa casa = new Casa("Rua B, 456", 2000.0, true);
        imobiliaria.adicionarImovel(apt);
        imobiliaria.adicionarImovel(casa);

        
        Contrato contrato1 = new Contrato(inq1, apt, LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1));
        Contrato contrato2 = new Contrato(inq2, casa, LocalDate.of(2023, 6, 1), LocalDate.of(2024, 6, 1));
        contrato2.encerrar(); // Encerrar um
        imobiliaria.adicionarContrato(contrato1);
        imobiliaria.adicionarContrato(contrato2);

        System.out.println("Contratos Ativos:");
        imobiliaria.listarContratosAtivos();
    }
}