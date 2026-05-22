import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Inquilinos> inquilinos = new ArrayList<>();
    static ArrayList<Imoveis> imoveis = new ArrayList<>();
    static Imobiliaria imobiliaria = new Imobiliaria();

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void mostrarMenu() {
        int escolha = 0;
        do {
            System.out.println("\n========== Menu ==========");
            System.out.println("1. Cadastrar um inquilino");
            System.out.println("2. Cadastrar um imóvel");
            System.out.println("3. Cadastrar um contrato de aluguel");
            System.out.println("4. Encerrar um contrato de aluguel");
            System.out.println("5. Listar contratos de aluguel ativos");
            System.out.println("6. Demonstração");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");
 
              if (sc.hasNextInt()) {
                escolha = sc.nextInt();
                sc.nextLine(); 
             } else {
                System.out.println("Entrada inválida! Digite um número.");
                sc.nextLine();
                escolha = -1; 
                continue;
            }
 
            switch (escolha) {
                case 1 -> cadastrarInquilino();
                case 2 -> cadastrarImovel();
                case 3 -> cadastrarContratoAluguel();
                case 4 -> encerrarContratoAluguel();
                case 5 -> imobiliaria.listarContratosAtivos();
                case 6 -> demonstracao();
                case 7 -> System.out.println("Saindo do menu. Até logo!");
                default -> System.out.println("Opção inválida. Escolha entre 1 e 7.");
            }
        } while (escolha != 7);
    }

    private static void cadastrarInquilino() {
        System.out.println("\n--- Cadastro de Inquilino ---");
        System.out.print("Nome: ");
        String nome = sc.nextLine().trim();
        System.out.print("CPF: ");
        String cpf = sc.nextLine().trim();
        System.out.print("Telefone: ");
        String telefone = sc.nextLine().trim();

        if (nome.isEmpty() || cpf.isEmpty()) {
            System.out.println("Nome e CPF são obrigatórios. Cadastro cancelado.");
            return;
        }

        Inquilinos inquilino = new Inquilinos(nome, cpf, telefone);
        inquilinos.add(inquilino);
        System.out.println("Inquilino \"" + nome + "\" cadastrado com sucesso! (Total: " + inquilinos.size() + ")");
    }

    private static void cadastrarImovel() {
        System.out.println("\n--- Cadastro de Imóvel ---");
        System.out.print("Endereço: ");
        String endereco = sc.nextLine().trim();

        double valorAluguel = 0;
        while (true) {
            System.out.print("Valor do aluguel mensal (R$): ");
            if (sc.hasNextDouble()) {
                valorAluguel = sc.nextDouble();
                sc.nextLine();
                if (valorAluguel > 0) {
                    break;
                } else {
                    System.out.println("Valor inválido. Informe um número positivo.");
                }
            } else {
                System.out.println("Entrada inválida. Informe um número para o valor do aluguel.");
                sc.nextLine(); 
            }
        }

        System.out.print("Tipo de imóvel — (1) Casa  (2) Apartamento: ");
        String tipo = sc.nextLine().trim();

        if (tipo.equals("1")) {
            System.out.print("Possui quintal? (sim/não): ");
            boolean quintal = sc.nextLine().trim().equalsIgnoreCase("sim");
            imoveis.add(new Casa(endereco, valorAluguel, quintal));
            System.out.println("Casa cadastrada com sucesso! (Total de imóveis: " + imoveis.size() + ")");
        } else if (tipo.equals("2")) {
            int andar = 0;
            while (true) {
                System.out.print("Número do andar: ");
                if (sc.hasNextInt()) {
                    andar = sc.nextInt();
                    sc.nextLine();
                    if (andar >= 0) {
                        break;
                    } else {
                        System.out.println("Número do andar não pode ser negativo.");
                    }
                } else {
                    System.out.println("Entrada inválida. Informe um número inteiro para o andar.");
                    sc.nextLine(); 
                }
            }
            imoveis.add(new Apartamento(endereco, valorAluguel, andar));
            System.out.println("Apartamento cadastrado com sucesso! (Total de imóveis: " + imoveis.size() + ")");
        } else {
            System.out.println("Tipo inválido. Cadastro cancelado.");
        }
    }

    private static void cadastrarContratoAluguel() {
        System.out.println("\n--- Cadastro de Contrato de Aluguel ---");

        if (inquilinos.isEmpty()) {
            System.out.println("Nenhum inquilino cadastrado. Cadastre um inquilino primeiro.");
            return;
        }
        if (imoveis.isEmpty()) {
            System.out.println("Nenhum imóvel cadastrado. Cadastre um imóvel primeiro.");
            return;
        }

        System.out.println("Inquilinos disponíveis:");
        for (int i = 0; i < inquilinos.size(); i++) {
            System.out.println("[" + i + "] " + inquilinos.get(i).getNome() + " — CPF: " + inquilinos.get(i).getCpf());
        }
        int idxInq = lerIndice("Escolha o inquilino pelo número: ", inquilinos.size());
        if (idxInq < 0) return;

        System.out.println("\nImóveis disponíveis:");
        for (int i = 0; i < imoveis.size(); i++) {
            Imoveis im = imoveis.get(i);
            String tipo = (im instanceof Casa) ? "Casa" : "Apartamento";
            System.out.printf("[%d] %s — %s — R$ %.2f/mês%n", i, tipo, im.getEndereco(), im.getValorAluguel());
        }
        int idxIm = lerIndice("Escolha o imóvel pelo número: ", imoveis.size());
        if (idxIm < 0) return;

        LocalDate dataInicio = lerData("Data de início (dd/MM/yyyy): ");
        if (dataInicio == null) return;
        LocalDate dataFim = lerData("Data de término (dd/MM/yyyy): ");
        if (dataFim == null) return;

        if (!dataFim.isAfter(dataInicio)) {
            System.out.println("A data de término deve ser posterior à data de início.");
            return;
        }

        Contrato contrato = new Contrato(inquilinos.get(idxInq), imoveis.get(idxIm), dataInicio, dataFim);
        imobiliaria.adicionarContrato(contrato);
    }

    private static void encerrarContratoAluguel() {
        System.out.println("\n--- Encerrar Contrato de Aluguel ---");
        if (imobiliaria.getTotalContratos() == 0) {
            System.out.println("Nenhum contrato cadastrado.");
            return;
        }
        imobiliaria.listarTodosContratos();
        int idx = lerIndice("Informe o número do contrato a encerrar: ", imobiliaria.getTotalContratos());
        if (idx < 0) return;
        imobiliaria.encerrarContrato(idx);
    }

    private static void demonstracao() {
        System.out.println("\n========== DEMONSTRAÇÃO ==========");

        Inquilinos inquilino1 = new Inquilinos("Ana Silva", "111.222.333-44", "(45) 99999-1111");
        Inquilinos inquilino2 = new Inquilinos("Samuel Babinski", "555.666.777-88", "(45) 98888-2222");

        Apartamento apartamento = new Apartamento("Av. Brasil, 500 - Apto 301", 1500.00, 3);
        Casa casa = new Casa("Rua dos Javeiros, 120", 2000.00, true);

      
        System.out.println("\n--- Imóveis criados ---");
        apartamento.exibirInformacoes();
        System.out.println();
        casa.exibirInformacoes();

    
        Contrato contrato1 = new Contrato(
                inquilino1, apartamento,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31)
        );

        Contrato contrato2 = new Contrato(
                inquilino2, casa,
                LocalDate.of(2025, 3, 1),
                LocalDate.of(2026, 2, 28)
        );


        Imobiliaria imobildemo = new Imobiliaria();
        imobildemo.adicionarContrato(contrato1);
        imobildemo.adicionarContrato(contrato2);

        System.out.println("\n--- Encerrando contrato de Ana Silva ---");
        imobildemo.encerrarContrato(0);

        System.out.println("\n--- Dados completos dos contratos ---");
        contrato1.exibirDados();
        System.out.println();
        contrato2.exibirDados();

        System.out.println("\n--- Listando apenas contratos ATIVOS ---");
        imobildemo.listarContratosAtivos();
    }

     private static int lerIndice(String mensagem, int tamanho) {
        while (true) {
            System.out.print(mensagem);
          if (sc.hasNextInt()) {
                int indice = sc.nextInt();
                sc.nextLine(); 
                if (indice >= 0 && indice < tamanho) {
                    return indice;
                } else {
                    System.out.println("Número inválido. Informe um número entre 0 e " + (tamanho - 1) + ".");
                }
            } else {
                System.out.println("Entrada inválida. Informe um número inteiro.");
                sc.nextLine(); 
            }
        }
    }

    private static LocalDate lerData(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            if (sc.hasNextLine()) {
                String input = sc.nextLine().trim();
                try {
                    return LocalDate.parse(input, FORMATO_DATA);
                } catch (DateTimeParseException e) {
                    System.out.println("Data inválida. Use o formato dd/MM/yyyy.");
                }
            } else {
                System.out.println("Entrada inválida. Tente novamente.");
                sc.nextLine(); 
            }
        }
    }
}