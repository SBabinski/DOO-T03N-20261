import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static Imobiliaria imobiliaria = new Imobiliaria();

    static Inquilino[] inquilinos = new Inquilino[20];
    static int totalInquilinos = 0;
    static Imovel[] imoveis = new Imovel[20];
    static int totalImoveis = 0;

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n======= Imobiliária =======");
            System.out.println("1. Cadastrar inquilino");
            System.out.println("2. Cadastrar imóvel");
            System.out.println("3. Cadastrar contrato");
            System.out.println("4. Encerrar contrato");
            System.out.println("5. Listar contratos ativos");
            System.out.println("6. Demonstração");
            System.out.println("0. Sair");
            System.out.print("Opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> cadastrarInquilino();
                case 2 -> cadastrarImovel();
                case 3 -> cadastrarContrato();
                case 4 -> encerrarContrato();
                case 5 -> imobiliaria.listarContratosAtivos();
                case 6 -> demonstracao();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    static void cadastrarInquilino() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        System.out.print("Telefone: ");
        String tel = sc.nextLine();
        inquilinos[totalInquilinos++] = new Inquilino(nome, cpf, tel);
        System.out.println("Inquilino cadastrado.");
    }

    static void cadastrarImovel() {
        System.out.println("Tipo: 1 - Apartamento | 2 - Casa");
        int tipo = sc.nextInt();
        sc.nextLine();
        System.out.print("Endereço: ");
        String end = sc.nextLine();
        System.out.print("Valor do aluguel mensal: ");
        double valor = sc.nextDouble();
        sc.nextLine();

        if (tipo == 1) {
            System.out.print("Andar: ");
            int andar = sc.nextInt();
            sc.nextLine();
            imoveis[totalImoveis++] = new Apartamento(end, valor, andar);
        } else {
            System.out.print("Tem quintal? (s/n): ");
            String q = sc.nextLine();
            imoveis[totalImoveis++] = new Casa(end, valor, q.equalsIgnoreCase("s"));
        }
        System.out.println("Imóvel cadastrado.");
    }

    static void cadastrarContrato() {
        if (totalInquilinos == 0 || totalImoveis == 0) {
            System.out.println("Cadastre ao menos um inquilino e um imóvel antes.");
            return;
        }

        System.out.println("Selecione o inquilino:");
        for (int i = 0; i < totalInquilinos; i++) {
            System.out.println(i + " - " + inquilinos[i].getNome());
        }
        int idxInq = sc.nextInt();
        sc.nextLine();

        System.out.println("Selecione o imóvel:");
        for (int i = 0; i < totalImoveis; i++) {
            System.out.print(i + " - ");
            imoveis[i].exibirInformacoes();
        }
        int idxImo = sc.nextInt();
        sc.nextLine();

        System.out.print("Data de início (DD/MM/AAAA): ");
        String inicio = sc.nextLine();
        System.out.print("Data de fim (DD/MM/AAAA): ");
        String fim = sc.nextLine();

        Contrato c = new Contrato(inquilinos[idxInq], imoveis[idxImo], inicio, fim);
        imobiliaria.adicionarContrato(c);
    }

    static void encerrarContrato() {
        Contrato[] contratos = imobiliaria.getContratos();
        int total = imobiliaria.getTotalContratos();
        if (total == 0) {
            System.out.println("Nenhum contrato cadastrado.");
            return;
        }
        for (int i = 0; i < total; i++) {
            System.out.print(i + " - ");
            contratos[i].exibirDados();
        }
        System.out.print("Índice do contrato a encerrar: ");
        int idx = sc.nextInt();
        sc.nextLine();
        imobiliaria.encerrarContrato(idx);
    }

    static void demonstracao() {
        System.out.println("\n===== DEMONSTRAÇÃO =====");

        Inquilino i1 = new Inquilino("João Tavares", "087.457.879-77", "4599875-4625");
        Inquilino i2 = new Inquilino("Matheus Buratto", "098.182.999-65", "4599153-0694");

        // apartamento e casa
        Apartamento ap = new Apartamento("Rua Raposo Tavares, 510, Apto 12", 2000.0, 4);
        Casa ca = new Casa("Rua Pato Branco, 267", 3000.0, true);

        // João no apartamento - Encerrado
        Contrato c1 = new Contrato(i1, ap, "06/08/2024", "07/12/2024");
        c1.encerrar();

        // Matheus na casa - Ativo
        Contrato c2 = new Contrato(i2, ca, "08/10/2024", "08/10/2025");

        // Demonstração separada
        Imobiliaria demo = new Imobiliaria();
        demo.adicionarContrato(c1);
        demo.adicionarContrato(c2);

        System.out.println("\n--- Todos os contratos cadastrados ---");
        c1.exibirDados();
        c2.exibirDados();

        System.out.println("\n--- Contratos ativos ---");
        demo.listarContratosAtivos();
    }
}