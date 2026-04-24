import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Imobiliaria imobiliaria = new Imobiliaria();
    static Inquilino[] inquilinos = new Inquilino[10];
    static int totalInquilinos = 0;
    static Imovel[] imoveis = new Imovel[10];
    static int totalImoveis = 0;
    public static void main(String[] args) {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n=== SISTEMA DE ALUGUEIS ===");
            System.out.println("1 - Cadastrar Inquilino");
            System.out.println("2 - Cadastrar Imovel");
            System.out.println("3 - Cadastrar Contrato");
            System.out.println("4 - Encerrar Contrato");
            System.out.println("5 - Listar Contratos Ativos");
            System.out.println("6 - Demonstracao");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");
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
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        }
    }
    static void cadastrarInquilino() {
        if (totalInquilinos >= 10) {
            System.out.println("Limite de inquilinos atingido!");
            return;
        }
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        inquilinos[totalInquilinos] = new Inquilino(nome, cpf, telefone);
        totalInquilinos++;
        System.out.println("Inquilino cadastrado com sucesso!");
    }
    static void cadastrarImovel() {
        if (totalImoveis >= 10) {
            System.out.println("Limite de imoveis atingido!");
            return;
        }
        System.out.println("Tipo: 1 - Apartamento / 2 - Casa");
        System.out.print("Escolha: ");
        int tipo = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Endereco: ");
        String endereco = scanner.nextLine();
        System.out.print("Valor do Aluguel Mensal: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();
        if (tipo == 1) {
            System.out.print("Andar: ");
            int andar = scanner.nextInt();
            scanner.nextLine();
            imoveis[totalImoveis] = new Apartamento(endereco, valor, andar);
        } else {
            System.out.print("Tem quintal? (1 - Sim / 2 - Nao): ");
            int q = scanner.nextInt();
            scanner.nextLine();
            imoveis[totalImoveis] = new Casa(endereco, valor, q == 1);
        }
        totalImoveis++;
        System.out.println("Imovel cadastrado com sucesso!");
    }
    static void cadastrarContrato() {
        if (totalInquilinos == 0 || totalImoveis == 0) {
            System.out.println("Cadastre ao menos um inquilino e um imovel antes.");
            return;
        }
        System.out.println("=== Inquilinos cadastrados ===");
        for (int i = 0; i < totalInquilinos; i++) {
            System.out.println(i + " - " + inquilinos[i].getNome());
        }
        System.out.print("Escolha o numero do inquilino: ");
        int idxInq = scanner.nextInt();
        scanner.nextLine();
        System.out.println("=== Imoveis cadastrados ===");
        for (int i = 0; i < totalImoveis; i++) {
            System.out.println(i + " - " + imoveis[i].getEndereco());
        }
        System.out.print("Escolha o numero do imovel: ");
        int idxImo = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Data de inicio (dd/MM/yyyy): ");
        String dataInicio = scanner.nextLine();
        System.out.print("Data de fim (dd/MM/yyyy): ");
        String dataFim = scanner.nextLine();
        Contrato contrato = new Contrato(inquilinos[idxInq], imoveis[idxImo], dataInicio, dataFim);
        imobiliaria.adicionarContrato(contrato);
    }
    static void encerrarContrato() {
        int total = imobiliaria.getTotalContratos();
        if (total == 0) {
            System.out.println("Nenhum contrato cadastrado.");
            return;
        }
        Contrato[] contratos = imobiliaria.getContratos();
        System.out.println("=== Contratos ===");
        for (int i = 0; i < total; i++) {
            String status = contratos[i].isEncerrado() ? "Encerrado" : "Ativo";
            System.out.println(i + " - Contrato " + i + " [" + status + "]");
        }
        System.out.print("Numero do contrato a encerrar: ");
        int idx = scanner.nextInt();
        scanner.nextLine();
        if (idx < 0 || idx >= total) {
            System.out.println("Numero invalido.");
            return;
        }
        contratos[idx].encerrar();
        System.out.println("Contrato encerrado com sucesso!");
    }

    static void demonstracao() {
        System.out.println("\n=== DEMONSTRACAO ===");
        Inquilino inq1 = new Inquilino("Carlos Silva", "123.456.789-00", "(44) 99999-1111");
        Inquilino inq2 = new Inquilino("Ana Souza", "987.654.321-00", "(44) 98888-2222");
        Apartamento ap = new Apartamento("Rua das Flores, 100 - Apto 302", 1500.00, 3);
        Casa casa = new Casa("Rua dos Pinheiros, 45", 2000.00, true);
        Contrato c1 = new Contrato(inq1, ap, "01/01/2024", "01/07/2024");
        Contrato c2 = new Contrato(inq2, casa, "01/03/2024", "01/03/2025");
        imobiliaria.adicionarContrato(c1);
        imobiliaria.adicionarContrato(c2);
        System.out.println("\n--- Listando contratos ativos apos a demonstracao ---");
        imobiliaria.listarContratosAtivos();
    }
}
