import java.util.Scanner;
import java.time.LocalDate;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static boolean appRodando = true;

    public static void main(String[] args) throws Exception {
        while (appRodando) {
            exibirMenu();
            System.out.print("Ação: ");

            int acaoUsuario = scanner.nextInt();
            scanner.nextLine();

            gerenciarAcoes(acaoUsuario);
        }
        scanner.close();
    }

    public static void exibirMenu() {
        System.out.println("-----MENU-----");
        System.out.println("1 - Cadastrar Inquilino");
        System.out.println("2 - Cadastrar Imóvel");
        System.out.println("3 - Cadastrar Contrato");
        System.out.println("4 - Encerrar Contrato");
        System.out.println("5 - Listar todos os contratos");
        System.out.println("6 - Listar Contratos Ativos");
        System.out.println("7 - Demonstração");
        System.out.println("8 - Listar Inquilinos");
        System.out.println("9 - Listar Imoveis");
        System.out.println("10 - Sair");
        System.out.println("--------------");
    }

    public static void gerenciarAcoes(int acaoUsuario) {
        switch (acaoUsuario) {
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
                listarTodosContratos();
                break;
            case 6:
                listarContratosAtivos();
                break;
            case 7:
                demonstracao();
                break;
            case 8:
                exibirInquilinos();
                break;
            case 9:
                exibirImoveis();
                break;
            case 10:
                appRodando = false;
                break;
            default:
                System.out.println("Ação inválida.");
                break;
        }
    }

    public static void cadastrarInquilino() {
        System.out.println("-------CADASTRO-------");
        System.out.print("1. Nome: ");
        String nomeNovoInquilino = scanner.nextLine();

        System.out.print("2. CPF: ");
        String cpfNovoInquilino = scanner.nextLine();

        System.out.print("3. Telefone: ");
        String telefoneNovoInquilino = scanner.nextLine();

        Inquilino novoInquilino = new Inquilino(nomeNovoInquilino, cpfNovoInquilino, telefoneNovoInquilino);

        Database.cadastrarInquilino(novoInquilino);
    }

    public static void cadastrarImovel() {
        System.out.println("-------CADASTRO DE IMÓVEL-------");
        System.out.println("Escolha o tipo de imóvel:");
        System.out.println("1. Apartamento");
        System.out.println("2. Casa");
        System.out.print("Opção: ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();

        System.out.print("Valor do aluguel mensal: R$ ");
        double valorAluguel = scanner.nextDouble();
        scanner.nextLine();

        // cadastra o imóvel com base no seu tipo - casa ou apratamento
        if (tipo == 1) {
            System.out.print("Andar: ");
            int andar = scanner.nextInt();
            scanner.nextLine();

            Database.cadastrarImovel(new Apartamento(endereco, valorAluguel, andar));
        } else if (tipo == 2) {
            System.out.print("Possui quintal? (s/n): ");
            boolean temQuintal = scanner.nextLine().equalsIgnoreCase("s");

            Database.cadastrarImovel(new Casa(endereco, valorAluguel, temQuintal));
        } else {
            System.out.println("Tipo de imóvel inválido.");
            return;
        }

        System.out.println("Imóvel cadastrado com sucesso!");
        System.out.println("--------------------------");
    }

    public static void cadastrarContrato() {
        // não permite cadastrar mais que 10 contratos
        if (Database.pegarListaContratos().size() >= 10) {
            System.out.println("Limite de 10 contratos ativos atingido.");
            return;
        }

        var listaInquilinos = Database.pegarListaInquilinos();
        var listaImoveis = Database.pegarListaImoveis();

        // checa se existe algum inquilino ou imóvel cadastrado
        if (listaInquilinos.isEmpty() || listaImoveis.isEmpty()) {
            System.out.println("Erro: Cadastre pelo menos um inquilino e um imóvel antes.");
            return;
        }

        System.out.println("-------CADASTRO DE CONTRATO-------");

        System.out.println("Selecione o Inquilino:");
        for (int i = 0; i < listaInquilinos.size(); i++) {
            System.out.println(i + " - " + listaInquilinos.get(i).getNome());
        }
        System.out.print("ID: ");
        int idInquilino = scanner.nextInt();

        System.out.println("\n-----------------------");
        System.out.println("Selecione o Imóvel (apenas imóveis disponíveis):");
        boolean algumDisponivel = false;
        for (int i = 0; i < listaImoveis.size(); i++) {
            Imovel imovel = listaImoveis.get(i);
            boolean ocupado = false;

            // Verifica se este imóvel específico tem contrato ativo
            for (Contrato contrato : Database.pegarListaContratos()) {
                if (contrato.getImovel().equals(imovel) && !contrato.isEncerrado()) {
                    ocupado = true;
                    break;
                }
            }

            if (!ocupado) {
                System.out.print("ID: " + i + " - ");
                imovel.exibirInformacoes();
                algumDisponivel = true;
            }
        }

        if (!algumDisponivel) {
            System.out.println("Não há imóveis disponíveis no momento!");
            return;
        }

        System.out.print("Digite o ID do imóvel: ");
        int idImovel = scanner.nextInt();

        // VALIDAÇÃO: Proteção extra caso o usuário digite um ID de imóvel ocupado
        Imovel imovelSelecionado = listaImoveis.get(idImovel);
        for (Contrato contrato : Database.pegarListaContratos()) {
            if (contrato.getImovel().equals(imovelSelecionado) && !contrato.isEncerrado()) {
                System.out.println("Erro: O imóvel selecionado não está disponível!");
                return;
            }
        }


        System.out.println("Data de Início:");
        System.out.print("Dia: ");
        int dI = scanner.nextInt();
        System.out.print("Mês: ");
        int mI = scanner.nextInt();
        System.out.print("Ano: ");
        int aI = scanner.nextInt();
        LocalDate dataInicio = LocalDate.of(aI, mI, dI);

        System.out.println("Data Final:");
        System.out.print("Dia: ");
        int dF = scanner.nextInt();
        System.out.print("Mês: ");
        int mF = scanner.nextInt();
        System.out.print("Ano: ");
        int aF = scanner.nextInt();
        LocalDate dataFinal = LocalDate.of(aF, mF, dF);
        scanner.nextLine();
        
        // não permite que a data final seja menor que a data inicial
        if (dataFinal.isBefore(dataInicio)) {
            System.out.println("Erro: A data final não pode ser anterior à data inicial!");
            return;
        }

        Contrato novoContrato = new Contrato(listaInquilinos.get(idInquilino), listaImoveis.get(idImovel), dataInicio,
                dataFinal);

        if (Database.cadastrarContrato(novoContrato)) {
            System.out.println("Contrato cadastrado com sucesso!");
        }
    }

    public static void encerrarContrato() {
        var listaContratos = Database.pegarListaContratos();
        boolean temAtivo = false;

        System.out.println("\n------- ENCERRAMENTO DE CONTRATO -------");

        // listando apenas os contratos ativos
        for (var contrato : listaContratos) {
            if (!contrato.isEncerrado()) {
                int idContrato = listaContratos.indexOf(contrato);

                System.out.println(
                        "ID: " + idContrato + " - Inquilino: " + contrato.getInquilino().getNome() + " | Imóvel: "
                                + contrato.getImovel().getEndereco());
                temAtivo = true;
            }
        }

        if (!temAtivo) {
            System.out.println("Nenhum contrato ativo para encerrar.");
            return;
        }

        System.out.print("Digite o ID do contrato que deseja encerrar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (id >= 0 && id < listaContratos.size() && !listaContratos.get(id).isEncerrado()) {
            listaContratos.get(id).setEncerrado(true);
            System.out.println("Contrato encerrado com sucesso!");
        } else {
            System.out.println("ID inválido ou contrato já encerrado.");
        }
        System.out.println("----------------------------------------");
    }

    public static void listarContratosAtivos() {
        var listaContratos = Database.pegarListaContratos();
        boolean encontrouContratoAtivo = false;

        System.out.println("\n----- LISTAGEM DE CONTRATOS ATIVOS -----");
        for (Contrato contrato : listaContratos) {
            if (!contrato.isEncerrado()) {
                contrato.exibirDadosContrato();
                encontrouContratoAtivo = true;
            }
        }

        if (!encontrouContratoAtivo) {
            System.out.println("Nenhum contrato ativo encontrado.");
        }
        System.out.println("----------------------------------------");

    }

    public static void demonstracao() {
        System.out.println("\n======= INICIANDO DEMONSTRAÇÃO =======");

        // 1. Criar dois inquilinos
        Inquilino i1 = new Inquilino("João Silva", "123.456.789-00", "(48) 99999-1111");
        Inquilino i2 = new Inquilino("Maria Oliveira", "987.654.321-11", "(48) 98888-2222");
        Database.cadastrarInquilino(i1);
        Database.cadastrarInquilino(i2);
        System.out.println("Dois inquilinos cadastrados.");

        // 2. Criar um apartamento e uma casa
        Apartamento ap = new Apartamento("Rua das Flores, 100 - Centro", 1500.0, 5);
        Casa casa = new Casa("Av. Beira Mar, 500", 3500.0, true);
        Database.cadastrarImovel(ap);
        Database.cadastrarImovel(casa);
        System.out.println("Um apartamento e uma casa cadastrados.");

        // 3. Registrar dois contratos (um ativo e um encerrado)
        // Contrato 1: Apartamento (Encerrado)
        Contrato c1 = new Contrato(i1, ap, LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1));
        c1.setEncerrado(true);
        Database.cadastrarContrato(c1);

        // Contrato 2: Casa (Ativo)
        Contrato c2 = new Contrato(i2, casa, LocalDate.of(2024, 2, 1), LocalDate.of(2025, 2, 1));
        Database.cadastrarContrato(c2);
        System.out.println("Dois contratos registrados (1 ativo, 1 encerrado).");

        // 4. Listar contratos ativos
        System.out.println("\n--- LISTAGEM FINAL (CONTRATOS ATIVOS) ---");
        listarContratosAtivos();
        
        System.out.println("======= DEMONSTRAÇÃO CONCLUÍDA =======");
    }

    public static void exibirInquilinos() {
        System.out.println("\n-----INQUILINOS------");
        for (var inquilino : Database.pegarListaInquilinos()) {
            int idInquilino = Database.pegarListaInquilinos().indexOf(inquilino);

            System.out.println("ID: " + idInquilino + inquilino);
        }

        System.out.println("\n--------------------");
    }

    public static void exibirImoveis() {
        System.out.println("\n-----IMÓVEIS------");
        var listaDeImoveis = Database.pegarListaImoveis();
        for (var imovel : listaDeImoveis) {
            imovel.exibirInformacoes();
        }

        System.out.println("\n--------------------");
    }

    public static void listarTodosContratos() {
        var listaContratos = Database.pegarListaContratos();

        if (listaContratos.isEmpty()) {
            System.out.println("Nenhum contrato cadastrado.");
            return;
        }

        System.out.println("\n----- LISTAGEM DE TODOS OS CONTRATOS -----");
        for (Contrato contrato : listaContratos) {
            contrato.exibirDadosContrato();
        }
        System.out.println("------------------------------------------");
    }
}
