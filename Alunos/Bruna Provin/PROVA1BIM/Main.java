import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static Imobiliaria imobiliaria = new Imobiliaria();

    static Inquilino[] inquilinos = new Inquilino[20];
    static int totalInquilinos = 0;

    static Imovel[] imoveis = new Imovel[20];
    static int totalImoveis = 0;

    public static void main(String[] args) {
        realizarDemonstracao();

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- MENU ---");
            System.out.println("1 - Cadastrar Inquilino");
            System.out.println("2 - Cadastrar Imovel");
            System.out.println("3 - Cadastrar Contrato");
            System.out.println("4 - Encerrar Contrato");
            System.out.println("5 - Listar Contratos Ativos");
            System.out.println("0 - Sair");
            System.out.print("Opcao: ");
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
                case 0:
                    System.out.println("Encerrando o sistema. Ate logo!");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        }
    }

     static void realizarDemonstracao() {
        System.out.println("\n--- DEMONSTRACAO DO SISTEMA ---");

        Inquilino inq1 = new Inquilino(" Bruna Provin", "121.551.049-79", "(42) 98404-7330");
        Inquilino inq2 = new Inquilino("Celine Thieme", "555.666.777-88", "(11) 91234-5678");
        Inquilino inq3 = new Inquilino(" Maria Comiran", "521.576.049-69", "(42) 96704-7000");
        inquilinos[totalInquilinos++] = inq1;
        inquilinos[totalInquilinos++] = inq2;
        inquilinos[totalInquilinos++] = inq3;

        Apartamento apt = new Apartamento("Rua dos ipês, 40", 1300.00, 3);
        Casa casa = new Casa("Av. Principal, 250", 2500.00, true);
        Casa casa2 = new Casa("Av. das flores, 20", 1700.00, false);
        imoveis[totalImoveis++] = apt;
        imoveis[totalImoveis++] = casa;
        imoveis[totalImoveis++] = casa2;

        ContratoAluguel contrato1 = new ContratoAluguel(inq1, apt, "01/01/2024", "30/06/2024", 6);
        contrato1.encerrar();
        imobiliaria.adicionarContrato(contrato1);

        ContratoAluguel contrato2 = new ContratoAluguel(inq2, casa, "01/07/2026", "30/06/2026", 12);
        imobiliaria.adicionarContrato(contrato2);

        ContratoAluguel contrato3 = new ContratoAluguel(inq3, casa2, "01/01/2024", "30/06/2024", 6);
        imobiliaria.adicionarContrato(contrato3);

        System.out.println("Demonstracao concluida.");
        System.out.println("\nContratos ativos apos a demonstracao:");
        imobiliaria.listarContratosAtivos();
    }

    static void cadastrarInquilino() {
        System.out.println("\n--- Cadastrar Inquilino ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        inquilinos[totalInquilinos] = new Inquilino(nome, cpf, telefone);
        System.out.println("Inquilino cadastrado com sucesso!");
        inquilinos[totalInquilinos].exibirDados();
        totalInquilinos++;
    }

    static void cadastrarImovel() {
        System.out.println("\n--- Cadastrar Imovel ---");
        System.out.println("1 - Apartamento");
        System.out.println("2 - Casa");
        System.out.print("Tipo: ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Endereco: ");
        String endereco = scanner.nextLine();
        System.out.print("Valor mensal (R$): ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        if (tipo == 1) {
            System.out.print("Andar: ");
            int andar = scanner.nextInt();
            scanner.nextLine();
            imoveis[totalImoveis] = new Apartamento(endereco, valor, andar);
        } else if (tipo == 2) {
            System.out.print("Tem quintal? (1-Sim / 2-Nao): ");
            int resp = scanner.nextInt();
            scanner.nextLine();
            imoveis[totalImoveis] = new Casa(endereco, valor, resp == 1);
        } else {
            System.out.println("Tipo invalido.");
            return;
        }

        System.out.println("Imovel cadastrado com sucesso!");
        imoveis[totalImoveis].exibirInformacoes();
        totalImoveis++;
    }

    static void cadastrarContrato() {
        System.out.println("\n--- Cadastrar Contrato ---");

        if (totalInquilinos == 0) {
            System.out.println("Nenhum inquilino cadastrado.");
            return;
        }
        if (totalImoveis == 0) {
            System.out.println("Nenhum imovel cadastrado.");
            return;
        }

        System.out.println("Inquilinos:");
        for (int i = 0; i < totalInquilinos; i++) {
            System.out.print("[" + i + "] ");
            inquilinos[i].exibirDados();
        }
        System.out.print("Numero do inquilino: ");
        int idxInq = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Imoveis:");
        for (int i = 0; i < totalImoveis; i++) {
            System.out.println("[" + i + "]");
            imoveis[i].exibirInformacoes();
        }
        System.out.print("Numero do imovel: ");
        int idxImo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Data de inicio (DD/MM/AAAA): ");
        String inicio = scanner.nextLine();
        System.out.print("Data de fim (DD/MM/AAAA): ");
        String fim = scanner.nextLine();
        System.out.print("Quantidade de meses: ");
        int meses = scanner.nextInt();
        scanner.nextLine();

        ContratoAluguel contrato = new ContratoAluguel(inquilinos[idxInq], imoveis[idxImo], inicio, fim, meses);
        imobiliaria.adicionarContrato(contrato);
        contrato.exibirDados();
    }

    static void encerrarContrato() {
        System.out.println("\n--- Encerrar Contrato ---");

        boolean haAtivo = false;
        System.out.println("Contratos ativos:");
        for (int i = 0; i < imobiliaria.getTotalContratos(); i++) {
            ContratoAluguel c = imobiliaria.getContratos()[i];
            if (!c.isEncerrado()) {
                System.out.println("[" + c.getId() + "] " + c.getInquilino().getNome()
                        + " | " + c.getDataInicio() + " ate " + c.getDataFim());
                haAtivo = true;
            }
        }

        if (!haAtivo) {
            System.out.println("Nenhum contrato ativo para encerrar.");
            return;
        }

        System.out.print("ID do contrato a encerrar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        ContratoAluguel contrato = imobiliaria.buscarContratoPorId(id);
        if (contrato == null) {
            System.out.println("Contrato nao encontrado.");
        } else if (contrato.isEncerrado()) {
            System.out.println("Este contrato ja esta encerrado.");
        } else {
            contrato.encerrar();
            System.out.println("Contrato #" + id + " encerrado com sucesso!");
            contrato.exibirDados();
        }
    }
}