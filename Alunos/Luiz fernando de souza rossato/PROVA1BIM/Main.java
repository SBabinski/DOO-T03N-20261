import java.util.Scanner;
import java.time.LocalDate;

public class Main {
    private static  Scanner scanner = new Scanner(System.in);
    private static  Imobiliaria imobiliaria = new Imobiliaria();

    public static void main(String[] args) {
        int opcao;

        do {
            exibirMenu();
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
                    listarContratosAtivos();
                    break;
                case 6:
                    demonstracao();
                    break;
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
        scanner.close();
    }

    public static void exibirMenu() {
        System.out.println("\n===== SISTEMA IMOBILIÁRIA =====");
        System.out.println("1 - Cadastrar Inquilino");
        System.out.println("2 - Cadastrar Imóvel");
        System.out.println("3 - Cadastrar Contrato");
        System.out.println("4 - Encerrar Contrato");
        System.out.println("5 - Listar Contratos Ativos");
        System.out.println("6 - Demonstração");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    public static void cadastrarInquilino() {
        System.out.println("\n--- Cadastro de Inquilino ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        Inquilino inquilino = new Inquilino(nome, cpf, telefone);
        System.out.println("Inquilino cadastrado com sucesso!");
    }

    public static void cadastrarImovel() {
        System.out.println("\n--- Cadastro de Imóvel ---");
        System.out.println("1 - Apartamento");
        System.out.println("2 - Casa");
        System.out.print("Escolha o tipo: ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Valor do aluguel: R$ ");
        double valor = scanner.nextDouble();

        switch (tipo) {
            case 1:
                System.out.print("Andar: ");
                int andar = scanner.nextInt();
                Apartamento apto = new Apartamento(endereco, valor, andar);
                System.out.println("Apartamento cadastrado com sucesso!");
                break;
            case 2:
                System.out.print("Possui quintal? (true/false): ");
                boolean quintal = scanner.nextBoolean();
                Casa casa = new Casa(endereco, valor, quintal);
                System.out.println("Casa cadastrada com sucesso!");
                break;
            default:
                System.out.println("Tipo inválido!");
        }
        scanner.nextLine();
    }

    public static void cadastrarContrato() {
        System.out.println("--- Cadastro de Contrato ---");
        System.out.println("Para cadastrar um contrato completo, utilize a opção Demonstração.");
        System.out.println("Ela cria automaticamente inquilinos, imóveis e contratos de exemplo.");
    }

    public static void encerrarContrato() {
        System.out.print("Informe o índice do contrato a encerrar: ");
        int indice = scanner.nextInt();
        scanner.nextLine();

        ContratoAluguel contrato = imobiliaria.getContrato(indice);

        if (contrato != null) {
            contrato.encerrarContrato();
            System.out.println("Contrato encerrado com sucesso!");
        } else {
            System.out.println("Contrato não encontrado.");
        }
    }

    public static void listarContratosAtivos() {
        System.out.println("\n--- Contratos Ativos ---");
        imobiliaria.listarContratosAtivos();
    }

    public static void demonstracao() {
        System.out.println("\n--- Demonstração do Sistema ---");
        System.out.println("Criando dados de exemplo...");

        Inquilino i1 = new Inquilino("João Silva", "111.111.111-11", "(18) 99999-1111");
        Inquilino i2 = new Inquilino("Maria Souza", "222.222.222-22", "(18) 99999-2222");

        Apartamento apto = new Apartamento("Rua A, 100", 1500.0, 5);
        Casa casa = new Casa("Rua B, 200", 2500.0, true);

        ContratoAluguel c1 = new ContratoAluguel(
                i1,
                apto,
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 12, 31),
                true
        );

        ContratoAluguel c2 = new ContratoAluguel(
                i2,
                casa,
                LocalDate.of(2026, 3, 1),
                LocalDate.of(2027, 2, 28),
                false
        );
        imobiliaria.adicionarContrato(c1);
        imobiliaria.adicionarContrato(c2);

        System.out.println("\nContratos ativos:");
        imobiliaria.listarContratosAtivos();
    }
}