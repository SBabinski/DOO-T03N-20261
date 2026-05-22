import java.util.Scanner;
import java.time.LocalDate;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static Inquilino[] inquilinos = new Inquilino[50];
    static int totalInquilinos = 0;
    static Imovel[] imoveis = new Imovel[50];
    static int totalImoveis = 0;
    static Imobiliaria imobiliaria = new Imobiliaria();

    public static void main(String[] args) {
        int opcao = -1;
        while (opcao != 0) {

            System.out.println();
            System.out.println("==========================================");
            System.out.println("   Sistema de Gerenciamento de Alugueis  ");
            System.out.println("==========================================");
            System.out.println("1. Cadastrar Inquilino");
            System.out.println("2. Cadastrar Imovel");
            System.out.println("3. Cadastrar Contrato");
            System.out.println("4. Encerrar Contrato");
            System.out.println("5. Listar Contratos Ativos");
            System.out.println("0. Sair");
            System.out.println("==========================================");
            System.out.print("Escolha uma opcao: ");

            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.nextLine();
                System.out.println("Opcao invalida. Digite um numero.");
                opcao = -1;
            }

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
                    break;

                default:
                    System.out.println("Opcao invalida. Tente novamente.");
            }
        }

        System.out.println("Encerrando o sistema. Ate logo!");
        scanner.close();
    }

    static void cadastrarInquilino() {
        System.out.println("--- Cadastro de Inquilino ---");
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
        System.out.println("--- Cadastro de Imovel ---");
        System.out.println("Tipo: 1 - Apartamento | 2 - Casa");
        System.out.print("Tipo: ");

        if (scanner.hasNextInt()) {
            int tipo = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Endereco: ");
            String endereco = scanner.nextLine();

            System.out.print("Valor do aluguel mensal (R$): ");

            if (scanner.hasNextDouble()) {
                double valor = scanner.nextDouble();
                scanner.nextLine();

                if (tipo == 1) {
                    System.out.print("Andar: ");
                    if (scanner.hasNextInt()) {
                        int andar = scanner.nextInt();
                        scanner.nextLine();
                        imoveis[totalImoveis] = new Apartamento(endereco, valor, andar);
                        totalImoveis++;
                        System.out.println("Apartamento cadastrado com sucesso!");
                    } else {
                        scanner.nextLine();
                        System.out.println("Andar invalido.");
                    }

                } else if (tipo == 2) {
                    System.out.print("Tem quintal? (s/n): ");
                    String resp = scanner.nextLine();
                    boolean temQuintal = resp.equalsIgnoreCase("s");
                    imoveis[totalImoveis] = new Casa(endereco, valor, temQuintal);
                    totalImoveis++;
                    System.out.println("Casa cadastrada com sucesso!");

                } else {
                    System.out.println("Tipo invalido.");
                }

            } else {
                scanner.nextLine();
                System.out.println("Valor invalido.");
            }

        } else {
            scanner.nextLine();
            System.out.println("Tipo invalido.");
        }
    }

    static void cadastrarContrato() {
        if (totalInquilinos == 0) {
            System.out.println("Nenhum inquilino cadastrado. Cadastre um inquilino primeiro.");
            return;
        }

        if (totalImoveis == 0) {
            System.out.println("Nenhum imovel cadastrado. Cadastre um imovel primeiro.");
            return;
        }

        System.out.println("--- Cadastro de Contrato ---");
        System.out.println("Inquilinos cadastrados:");
        for (int i = 0; i < totalInquilinos; i++) {
            System.out.println("[" + i + "] " + inquilinos[i]);
        }

        System.out.print("Indice do inquilino: ");

        if (scanner.hasNextInt()) {
            int idxInquilino = scanner.nextInt();
            scanner.nextLine();

            if (idxInquilino < 0 || idxInquilino >= totalInquilinos) {
                System.out.println("Indice invalido.");
                return;
            }

            System.out.println("Imoveis cadastrados:");
            for (int i = 0; i < totalImoveis; i++) {
                System.out.print("[" + i + "] ");
                imoveis[i].exibirInformacoes();
            }

            System.out.print("Indice do imovel: ");

            if (scanner.hasNextInt()) {
                int idxImovel = scanner.nextInt();
                scanner.nextLine();

                if (idxImovel < 0 || idxImovel >= totalImoveis) {
                    System.out.println("Indice invalido.");
                    return;
                }

                System.out.print("Data de inicio (aaaa-mm-dd): ");
                String dataInicioStr = scanner.nextLine();
                System.out.print("Data de fim (aaaa-mm-dd): ");
                String dataFimStr = scanner.nextLine();

                LocalDate dataInicio = LocalDate.parse(dataInicioStr);
                LocalDate dataFim = LocalDate.parse(dataFimStr);

                Contrato contrato = new Contrato(
                        inquilinos[idxInquilino],
                        imoveis[idxImovel],
                        dataInicio,
                        dataFim
                );
                imobiliaria.adicionarContrato(contrato);

            } else {
                scanner.nextLine();
                System.out.println("Indice invalido.");
            }

        } else {
            scanner.nextLine();
            System.out.println("Indice invalido.");
        }
    }

    static void encerrarContrato() {
        System.out.println("--- Encerrar Contrato ---");
        imobiliaria.listarTodosContratos();

        if (imobiliaria.getTotalContratos() == 0) {
            System.out.println("Nenhum contrato cadastrado.");
            return;
        }

        System.out.print("Indice do contrato a encerrar: ");

        if (scanner.hasNextInt()) {
            int indice = scanner.nextInt();
            scanner.nextLine();
            imobiliaria.encerrarContrato(indice);
        } else {
            scanner.nextLine();
            System.out.println("Indice invalido.");
        }
    }
}