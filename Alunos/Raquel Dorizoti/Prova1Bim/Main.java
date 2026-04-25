import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

abstract class Imovel {
    protected String endereco;
    protected double valorAluguelMensal;
    protected boolean disponivel;

    public Imovel(String endereco, double valorAluguelMensal) {
        this.endereco = endereco;
        this.valorAluguelMensal = valorAluguelMensal;
        this.disponivel = true;
    }

    public double getValorAluguelMensal() {
        return valorAluguelMensal;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void alugar() {
        disponivel = false;
    }

    public void liberar() {
        disponivel = true;
    }

    public abstract void exibirInformacoes();
}

class Apartamento extends Imovel {
    private int andar;

    public Apartamento(String endereco, double valorAluguelMensal, int andar) {
        super(endereco, valorAluguelMensal);
        this.andar = andar;
    }

    public void exibirInformacoes() {
        System.out.println("Tipo: Apartamento");
        System.out.println("Endereço: " + endereco);
        System.out.println("Valor mensal: R$ " + valorAluguelMensal);
        System.out.println("Andar: " + andar);
        System.out.println("Disponível: " + (disponivel ? "Sim" : "Não"));
    }
}

class Casa extends Imovel {
    private boolean temQuintal;

    public Casa(String endereco, double valorAluguelMensal, boolean temQuintal) {
        super(endereco, valorAluguelMensal);
        this.temQuintal = temQuintal;
    }

    public void exibirInformacoes() {
        System.out.println("Tipo: Casa");
        System.out.println("Endereço: " + endereco);
        System.out.println("Valor mensal: R$ " + valorAluguelMensal);
        System.out.println("Tem quintal: " + (temQuintal ? "Sim" : "Não"));
        System.out.println("Disponível: " + (disponivel ? "Sim" : "Não"));
    }
}

class Inquilino {
    private String nome;
    private String cpf;
    private String telefone;

    public Inquilino(String nome, String cpf, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public void exibirInformacoes() {
        System.out.println("Nome: " + nome);
        System.out.println("CPF: " + cpf);
        System.out.println("Telefone: " + telefone);
    }
}

class ContratoAluguel {
    private Inquilino inquilino;
    private Imovel imovel;
    private LocalDate dataInicio;
    private LocalDate dataFinal;
    private boolean encerrado;

    public ContratoAluguel(Inquilino inquilino, Imovel imovel, LocalDate dataInicio, LocalDate dataFinal) {
        this.inquilino = inquilino;
        this.imovel = imovel;
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.encerrado = false;

        this.imovel.alugar();
    }

    public boolean isEncerrado() {
        return encerrado;
    }

    public void encerrarContrato() {
        if (!encerrado) {
            encerrado = true;
            imovel.liberar();
        }
    }

    public long calcularQuantidadeMeses() {
        long meses = ChronoUnit.MONTHS.between(dataInicio, dataFinal);

        if (meses <= 0) {
            meses = 1;
        }

        return meses;
    }

    public double calcularValorTotal() {
        return calcularQuantidadeMeses() * imovel.getValorAluguelMensal();
    }

    public void exibirDados() {
        System.out.println("\n===== CONTRATO DE ALUGUEL =====");

        System.out.println("\nInquilino:");
        inquilino.exibirInformacoes();

        System.out.println("\nImóvel:");
        imovel.exibirInformacoes();

        System.out.println("\nData de início: " + dataInicio);
        System.out.println("Data final: " + dataFinal);
        System.out.println("Quantidade de meses: " + calcularQuantidadeMeses());
        System.out.println("Valor total: R$ " + calcularValorTotal());
        System.out.println("Situação: " + (encerrado ? "Encerrado" : "Ativo"));
    }
}

class Imobiliaria {
    private ContratoAluguel[] contratos = new ContratoAluguel[10];
    private int quantidadeContratos = 0;

    public void adicionarContrato(ContratoAluguel contrato) {
        if (quantidadeContratos < 10) {
            contratos[quantidadeContratos] = contrato;
            quantidadeContratos++;
            System.out.println("Contrato cadastrado com sucesso!");
        } else {
            System.out.println("Limite de 10 contratos atingido.");
        }
    }

    public void listarTodosContratos() {
        if (quantidadeContratos == 0) {
            System.out.println("Nenhum contrato cadastrado.");
            return;
        }

        System.out.println("\n===== TODOS OS CONTRATOS =====");

        for (int i = 0; i < quantidadeContratos; i++) {
            System.out.println("\nCódigo do contrato: " + i);
            contratos[i].exibirDados();
        }
    }

    public void listarContratosAtivos() {
        boolean encontrou = false;

        System.out.println("\n===== CONTRATOS ATIVOS =====");

        for (int i = 0; i < quantidadeContratos; i++) {
            if (!contratos[i].isEncerrado()) {
                System.out.println("\nCódigo do contrato: " + i);
                contratos[i].exibirDados();
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum contrato ativo encontrado.");
        }
    }

    public void encerrarContrato(int codigo) {
        if (codigo >= 0 && codigo < quantidadeContratos) {
            if (contratos[codigo].isEncerrado()) {
                System.out.println("Esse contrato já está encerrado.");
            } else {
                contratos[codigo].encerrarContrato();
                System.out.println("Contrato encerrado com sucesso!");
            }
        } else {
            System.out.println("Código inválido.");
        }
    }
}

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Imobiliaria imobiliaria = new Imobiliaria();

    static Inquilino[] inquilinos = new Inquilino[10];
    static Imovel[] imoveis = new Imovel[10];

    static int qtdInquilinos = 0;
    static int qtdImoveis = 0;

    public static void main(String[] args) {
        int opcao;

        do {
            System.out.println("\n===== MENU IMOBILIÁRIA =====");
            System.out.println("1 - Cadastrar inquilino");
            System.out.println("2 - Cadastrar imóvel");
            System.out.println("3 - Cadastrar contrato");
            System.out.println("4 - Encerrar contrato");
            System.out.println("5 - Listar contratos ativos");
            System.out.println("6 - Listar todos os contratos");
            System.out.println("7 - Demonstração");
            System.out.println("0 - Sair");

            opcao = lerInteiro("Escolha: ");

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
                    imobiliaria.listarTodosContratos();
                    break;
                case 7:
                    demonstracao();
                    break;
                case 0:
                    System.out.println("Sistema encerrado.");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    static String lerTexto(String mensagem) {
        String texto;

        do {
            System.out.print(mensagem);
            texto = scanner.nextLine().trim();

            if (texto.isEmpty()) {
                System.out.println("Esse campo não pode ficar em branco.");
            }

        } while (texto.isEmpty());

        return texto;
    }

    static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);

            if (scanner.hasNextInt()) {
                int numero = scanner.nextInt();
                scanner.nextLine();

                if (numero < 0) {
                    System.out.println("Digite um número positivo.");
                } else {
                    return numero;
                }
            } else {
                System.out.println("Digite um número válido.");
                scanner.nextLine();
            }
        }
    }

    static double lerDouble(String mensagem) {
        while (true) {
            System.out.print(mensagem);

            if (scanner.hasNextDouble()) {
                double numero = scanner.nextDouble();
                scanner.nextLine();

                if (numero <= 0) {
                    System.out.println("Digite um valor maior que zero.");
                } else {
                    return numero;
                }
            } else {
                System.out.println("Digite um valor válido.");
                scanner.nextLine();
            }
        }
    }

    static LocalDate lerData(String mensagem) {
        while (true) {
            try {
                String texto = lerTexto(mensagem + " (AAAA-MM-DD): ");
                return LocalDate.parse(texto);
            } catch (Exception e) {
                System.out.println("Data inválida. Exemplo correto: 2026-04-24");
            }
        }
    }

    static boolean lerBooleano(String mensagem) {
        while (true) {
            String resposta = lerTexto(mensagem + " (S/N): ");

            if (resposta.equalsIgnoreCase("S")) {
                return true;
            } else if (resposta.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println("Digite apenas S para sim ou N para não.");
            }
        }
    }

    static void cadastrarInquilino() {
        if (qtdInquilinos >= 10) {
            System.out.println("Limite de inquilinos atingido.");
            return;
        }

        System.out.println("\n===== CADASTRO DE INQUILINO =====");

        String nome = lerTexto("Nome: ");
        String cpf = lerTexto("CPF: ");
        String telefone = lerTexto("Telefone: ");

        inquilinos[qtdInquilinos] = new Inquilino(nome, cpf, telefone);
        qtdInquilinos++;

        System.out.println("Inquilino cadastrado com sucesso!");
    }

    static void cadastrarImovel() {
        if (qtdImoveis >= 10) {
            System.out.println("Limite de imóveis atingido.");
            return;
        }

        System.out.println("\n===== CADASTRO DE IMÓVEL =====");
        System.out.println("1 - Apartamento");
        System.out.println("2 - Casa");

        int tipo = lerInteiro("Escolha o tipo: ");

        if (tipo != 1 && tipo != 2) {
            System.out.println("Tipo inválido.");
            return;
        }

        String endereco = lerTexto("Endereço: ");
        double valorMensal = lerDouble("Valor do aluguel mensal: ");

        if (tipo == 1) {
            int andar = lerInteiro("Andar: ");

            imoveis[qtdImoveis] = new Apartamento(endereco, valorMensal, andar);
            qtdImoveis++;

            System.out.println("Apartamento cadastrado com sucesso!");
        } else {
            boolean temQuintal = lerBooleano("A casa tem quintal?");

            imoveis[qtdImoveis] = new Casa(endereco, valorMensal, temQuintal);
            qtdImoveis++;

            System.out.println("Casa cadastrada com sucesso!");
        }
    }

    static void cadastrarContrato() {
        if (qtdInquilinos == 0) {
            System.out.println("Cadastre pelo menos um inquilino antes.");
            return;
        }

        if (qtdImoveis == 0) {
            System.out.println("Cadastre pelo menos um imóvel antes.");
            return;
        }

        boolean existeDisponivel = false;

        for (int i = 0; i < qtdImoveis; i++) {
            if (imoveis[i].isDisponivel()) {
                existeDisponivel = true;
            }
        }

        if (!existeDisponivel) {
            System.out.println("Não há imóveis disponíveis para aluguel.");
            return;
        }

        System.out.println("\n===== INQUILINOS CADASTRADOS =====");

        for (int i = 0; i < qtdInquilinos; i++) {
            System.out.println("\nCódigo do inquilino: " + i);
            inquilinos[i].exibirInformacoes();
        }

        int codigoInquilino = lerInteiro("\nEscolha o código do inquilino: ");

        if (codigoInquilino < 0 || codigoInquilino >= qtdInquilinos) {
            System.out.println("Inquilino inválido.");
            return;
        }

        System.out.println("\n===== IMÓVEIS DISPONÍVEIS =====");

        for (int i = 0; i < qtdImoveis; i++) {
            if (imoveis[i].isDisponivel()) {
                System.out.println("\nCódigo do imóvel: " + i);
                imoveis[i].exibirInformacoes();
            }
        }

        int codigoImovel = lerInteiro("\nEscolha o código do imóvel: ");

        if (codigoImovel < 0 || codigoImovel >= qtdImoveis) {
            System.out.println("Imóvel inválido.");
            return;
        }

        if (!imoveis[codigoImovel].isDisponivel()) {
            System.out.println("Esse imóvel já está alugado e não pode ser escolhido.");
            return;
        }

        LocalDate inicio = lerData("Data de início");
        LocalDate fim = lerData("Data final");

        if (fim.isBefore(inicio)) {
            System.out.println("A data final não pode ser antes da data de início.");
            return;
        }

        ContratoAluguel contrato = new ContratoAluguel(
                inquilinos[codigoInquilino],
                imoveis[codigoImovel],
                inicio,
                fim
        );

        imobiliaria.adicionarContrato(contrato);
    }

    static void encerrarContrato() {
        imobiliaria.listarTodosContratos();

        int codigo = lerInteiro("\nDigite o código do contrato para encerrar: ");
        imobiliaria.encerrarContrato(codigo);
    }

    static void demonstracao() {
        System.out.println("\n===== DEMONSTRAÇÃO =====");

        Inquilino inquilino1 = new Inquilino("Raquel", "109.876.543-21", "(45) 7070-7070");
        Inquilino inquilino2 = new Inquilino("Samuel", "123.456.789-10", "(45) 0800-7070");

        Apartamento apartamento = new Apartamento("Rua das Flores, 100", 1500.00, 5);
        Casa casa = new Casa("Rua das Palmeiras, 200", 2200.00, true);

        ContratoAluguel contrato1 = new ContratoAluguel(
                inquilino1,
                apartamento,
                LocalDate.of(2026, 4, 1),
                LocalDate.of(2026, 10, 1)
        );

        ContratoAluguel contrato2 = new ContratoAluguel(
                inquilino2,
                casa,
                LocalDate.of(2026, 5, 1),
                LocalDate.of(2026, 11, 1)
        );

        contrato1.encerrarContrato();

        imobiliaria.adicionarContrato(contrato1);
        imobiliaria.adicionarContrato(contrato2);

        System.out.println("\nA demonstração criou dois contratos:");
        System.out.println("Um contrato encerrado e um contrato ativo.");

        imobiliaria.listarContratosAtivos();
    }
}