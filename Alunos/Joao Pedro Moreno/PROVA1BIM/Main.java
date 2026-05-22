import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static Scanner scan = new Scanner(System.in);
    static ArrayList<Imovel> imovel = new ArrayList<>();
    static ArrayList<Inquilino> inquilino = new ArrayList<>();
    static ArrayList<Contrato> contrato = new ArrayList<>();

    public static void main(String[] args) {
        // Demonstração: dois inquilinos, um apartamento e uma casa
        Inquilino inq1 = new Inquilino("João Silva", "123.456.789-00", "(11) 98765-4321");
        Inquilino inq2 = new Inquilino("Maria Oliveira", "987.654.321-00", "(21) 91234-5678");
        inquilino.add(inq1);
        inquilino.add(inq2);

        Casa casa = new Casa("Rua das Flores, 123", 1500.00, true);
        Apartamento apto = new Apartamento("Avenida Central, 456", 1200.00, 5);
        imovel.add(casa);
        imovel.add(apto);

        // Dois contratos: um encerrado e um ativo
        Contrato c1 = new Contrato(inq1, casa, "01/01/2024", "31/12/2024", 12);
        c1.setEncerrado(true); // contrato encerrado
        Contrato c2 = new Contrato(inq2, apto, "01/03/2025", "28/02/2026", 12);
        contrato.add(c1);
        contrato.add(c2);

        // Lista os contratos ativos no console (parte da demonstração)
        System.out.println("=== DEMONSTRAÇÃO INICIAL ===");
        System.out.println("Contratos: João (encerrado) e Maria (ativo).");
        listarContratos();
        System.out.println("============================");

        verMenu();
    }

    // Parte visual do menu
    public static void verMenu() {
        int escolha;
        do {
            System.out.println("\n___________________________________");
            System.out.println("|--------------Menu---------------|");
            System.out.println("|1- Cadastrar Inquilino           |");
            System.out.println("|2- Cadastrar Imóvel              |");
            System.out.println("|3- Cadastrar Contrato            |");
            System.out.println("|4- Encerrar Contrato             |");
            System.out.println("|5- Lista de Contratos            |");
            System.out.println("|6- Sair                          |");
            System.out.print("|Escolha: ");
            escolha = scan.nextInt();
            scan.nextLine();
            validarEscolha(escolha);
        } while (escolha != 6);
    }

    // Validação da escolha do menu
    public static void validarEscolha(int escolha) {
        switch (escolha) {
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
                listarContratos();
                break;
            case 6:
                System.out.println("Obrigado, volte sempre!");
                break;
            default:
                System.out.println("Opção inválida, tente novamente.");
        }
    }

    // Método para cadastrar inquilino
    public static void cadastrarInquilino() {
        System.out.println("\n--- Cadastrar Inquilino ---");
        System.out.print("Nome: ");
        String nome = scan.nextLine();
        System.out.print("CPF: ");
        String cpf = scan.nextLine();
        System.out.print("Telefone: ");
        String telefone = scan.nextLine();

        Inquilino inquilino1 = new Inquilino(nome, cpf, telefone);
        inquilino.add(inquilino1);
        System.out.println("Inquilino cadastrado com sucesso!");
    }

    // Método para cadastrar imóvel
    public static void cadastrarImovel() {
        System.out.println("\n--- Cadastrar Imóvel ---");
        System.out.println("Tipo: 1 - Apartamento | 2 - Casa");
        System.out.print("Escolha: ");
        int tipo = scan.nextInt();
        scan.nextLine();

        System.out.print("Endereço: ");
        String endereco = scan.nextLine();
        System.out.print("Valor do Aluguel Mensal: R$ ");
        double valorAluguel = scan.nextDouble();
        scan.nextLine();

        if (tipo == 1) {
            System.out.print("Andar: ");
            int andar = scan.nextInt();
            scan.nextLine();
            imovel.add(new Apartamento(endereco, valorAluguel, andar));
        } else if (tipo == 2) {
            System.out.print("Tem quintal? (1 - Sim / 2 - Não): ");
            int opt = scan.nextInt();
            scan.nextLine();
            imovel.add(new Casa(endereco, valorAluguel, opt == 1));
        } else {
            System.out.println("Tipo inválido.");
            return;
        }
        System.out.println("Imóvel cadastrado com sucesso! (ID: " + imovel.size() + ")");
    }

    // Método para cadastrar contrato
    public static void cadastrarContrato() {
        System.out.println("\n--- Cadastrar Contrato ---");

        if (contrato.size() >= 10) {
            System.out.println("Limite de 10 contratos atingido.");
            return;
        }
        if (inquilino.isEmpty()) {
            System.out.println("Nenhum inquilino cadastrado. Cadastre um inquilino primeiro.");
            return;
        }
        if (imovel.isEmpty()) {
            System.out.println("Nenhum imóvel cadastrado. Cadastre um imóvel primeiro.");
            return;
        }

        // Listar inquilinos disponíveis
        System.out.println("Inquilinos disponíveis:");
        for (int i = 0; i < inquilino.size(); i++) {
            System.out.printf("  %d - %s (CPF: %s)%n", i + 1,
                    inquilino.get(i).getNome(), inquilino.get(i).getCpf());
        }
        System.out.print("Escolha o inquilino (número): ");
        int idxInq = scan.nextInt() - 1;
        scan.nextLine();
        if (idxInq < 0 || idxInq >= inquilino.size()) {
            System.out.println("Inquilino inválido.");
            return;
        }

        // Listar imóveis disponíveis
        System.out.println("Imóveis disponíveis:");
        for (int i = 0; i < imovel.size(); i++) {
            System.out.printf("  %d - ", i + 1);
            imovel.get(i).mostrarImovel();
        }
        System.out.print("Escolha o imóvel (número): ");
        int idxImo = scan.nextInt() - 1;
        scan.nextLine();
        if (idxImo < 0 || idxImo >= imovel.size()) {
            System.out.println("Imóvel inválido.");
            return;
        }

        System.out.print("Data de Início (dd/mm/aaaa): ");
        String dataInicio = scan.nextLine();
        System.out.print("Data de Fim (dd/mm/aaaa): ");
        String dataFim = scan.nextLine();
        System.out.print("Quantidade de meses: ");
        int meses = scan.nextInt();
        scan.nextLine();

        Contrato novoContrato = new Contrato(
                inquilino.get(idxInq),
                imovel.get(idxImo),
                dataInicio, dataFim, meses);
        contrato.add(novoContrato);
        System.out.println("Contrato cadastrado com sucesso!");
    }

    // Método para encerrar contrato
    public static void encerrarContrato() {
        System.out.println("\n--- Encerrar Contrato ---");

        if (contrato.isEmpty()) {
            System.out.println("Nenhum contrato cadastrado.");
            return;
        }

        for (int i = 0; i < contrato.size(); i++) {
            System.out.printf("Contrato #%d | ", i + 1);
            contrato.get(i).mostrarContrato();
        }

        System.out.print("Número do contrato a encerrar: ");
        int num = scan.nextInt();
        scan.nextLine();
        int idx = num - 1;

        if (idx < 0 || idx >= contrato.size()) {
            System.out.println("Contrato não encontrado.");
            return;
        }
        if (contrato.get(idx).isEncerrado()) {
            System.out.println("Este contrato já está encerrado.");
            return;
        }
        contrato.get(idx).setEncerrado(true);
        System.out.println("Contrato encerrado com sucesso!");
    }

    // Método para listar apenas contratos ativos
    public static void listarContratos() {
        System.out.println("\n--- Lista de Contratos Ativos ---");
        boolean encontrou = false;
        for (int i = 0; i < contrato.size(); i++) {
            if (!contrato.get(i).isEncerrado()) {
                System.out.printf("Contrato #%d | ", i + 1);
                contrato.get(i).mostrarContrato();
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum contrato ativo no momento.");
        }
    }
}
