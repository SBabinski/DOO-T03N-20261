import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("ddMMyyyy");
    public static void main(String[] args) {
        Imobiliaria imobiliaria = new Imobiliaria();
        Scanner scanner = new Scanner(System.in);
        boolean executar = true;

        while (executar) {
            System.out.println("\n=== Sistema de Gerenciamento de Alugueis ===");
            System.out.println("1. Cadastrar inquilino");
            System.out.println("2. Cadastrar imovel");
            System.out.println("3. Cadastrar contrato");
            System.out.println("4. Encerrar contrato");
            System.out.println("5. Listar contratos ativos");
            System.out.println("6. Demonstracao");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opcao: ");

            int opcao = lerInteiro(scanner);
            switch (opcao) {
                case 1:
                    cadastrarInquilino(imobiliaria, scanner);
                    break;
                case 2:
                    cadastrarImovel(imobiliaria, scanner);
                    break;
                case 3:
                    cadastrarContrato(imobiliaria, scanner);
                    break;
                case 4:
                    encerrarContrato(imobiliaria, scanner);
                    break;
                case 5:
                    imobiliaria.listarContratosAtivos();
                    break;
                case 6:
                    demonstracao(imobiliaria);
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    executar = false;
                    break;
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
                    break;
            }
        }

        scanner.close();
    }

    private static int lerInteiro(Scanner scanner) {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return -1;
        }
    }

    private static double lerDouble(Scanner scanner) {
        try {
            String texto = scanner.nextLine().trim().replace(',', '.');
            return Double.parseDouble(texto);
        } catch (Exception e) {
            return -1;
        }
    }

    private static void cadastrarInquilino(Imobiliaria imobiliaria, Scanner scanner) {
        scanner.nextLine();
        System.out.print("Nome do inquilino: ");
        String nome = scanner.nextLine();
        System.out.print("CPF do inquilino: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone do inquilino: ");
        String telefone = scanner.nextLine();
        Inquilino inquilino = new Inquilino(nome, cpf, telefone);
        imobiliaria.adicionarInquilino(inquilino);
        System.out.println("Inquilino cadastrado com sucesso.");
    }

    private static void cadastrarImovel(Imobiliaria imobiliaria, Scanner scanner) {
        scanner.nextLine();
        System.out.println("Tipo de imovel: ");
        System.out.println("1. Apartamento");
        System.out.println("2. Casa");
        System.out.print("Escolha uma opcao: ");
        int tipo = lerInteiro(scanner);
        scanner.nextLine();
        System.out.print("Endereco: ");
        String endereco = scanner.nextLine();
        System.out.print("Valor do aluguel mensal: ");
        double valorAluguel = lerDouble(scanner);

        if (valorAluguel <= 0) {
            System.out.println("Valor do aluguel invalido. Cadastro cancelado.");
            return;
        }

        Imovel imovel;
        if (tipo == 1) {
            System.out.print("Andar do apartamento: ");
            int andar = lerInteiro(scanner);
            scanner.nextLine();
            imovel = new Apartamento(endereco, valorAluguel, andar);
        } else if (tipo == 2) {
            System.out.print("Possui quintal? (S/N): ");
            String resposta = scanner.nextLine().trim().toUpperCase();
            boolean quintal = resposta.startsWith("S");
            imovel = new Casa(endereco, valorAluguel, quintal);
        } else {
            System.out.println("Tipo de imovel invalido. Cadastro cancelado.");
            return;
        }

        imobiliaria.adicionarImovel(imovel);
        System.out.println("Imovel cadastrado com sucesso.");
    }

    private static void cadastrarContrato(Imobiliaria imobiliaria, Scanner scanner) {
        if (imobiliaria.getInquilinos().isEmpty()) {
            System.out.println("Nao ha inquilinos cadastrados. Cadastre um inquilino antes.");
            return;
        }
        if (imobiliaria.getImoveis().isEmpty()) {
            System.out.println("Nao ha imoveis cadastrados. Cadastre um imovel antes.");
            return;
        }

        System.out.println("Inquilinos disponiveis:");
        List<Inquilino> inquilinos = imobiliaria.getInquilinos();
        for (int i = 0; i < inquilinos.size(); i++) {
            System.out.println((i + 1) + ". " + inquilinos.get(i));
        }
        System.out.print("Escolha o numero do inquilino: ");
        int indiceInquilino = lerInteiro(scanner) - 1;
        scanner.nextLine();

        if (indiceInquilino < 0 || indiceInquilino >= inquilinos.size()) {
            System.out.println("Inquilino invalido.");
            return;
        }

        System.out.println("Imoveis disponiveis:");
        List<Imovel> imoveis = imobiliaria.getImoveis();
        for (int i = 0; i < imoveis.size(); i++) {
            System.out.println((i + 1) + ". " + imoveis.get(i).exibirInformacoes());
        }
        System.out.print("Escolha o numero do imovel: ");
        int indiceImovel = lerInteiro(scanner) - 1;
        scanner.nextLine();

        if (indiceImovel < 0 || indiceImovel >= imoveis.size()) {
            System.out.println("Imovel invalido.");
            return;
        }

        System.out.print("Data de inicio (DDMMYYYY): ");
        String dataInicioTexto = scanner.nextLine();
        System.out.print("Data final (DDMMYYYY): ");
        String dataFinalTexto = scanner.nextLine();

        LocalDate dataInicio;
        LocalDate dataFinal;
        try {
            dataInicio = LocalDate.parse(dataInicioTexto, FORMATO_DATA);
            dataFinal = LocalDate.parse(dataFinalTexto, FORMATO_DATA);
        } catch (Exception e) {
            System.out.println("Data invalida. Cadastro de contrato cancelado.");
            return;
        }

        if (dataFinal.isBefore(dataInicio) || dataFinal.equals(dataInicio)) {
            System.out.println("A data final deve ser posterior a data de inicio.");
            return;
        }

        Contrato contrato = new Contrato(inquilinos.get(indiceInquilino), imoveis.get(indiceImovel), dataInicio, dataFinal);
        boolean adicionado = imobiliaria.adicionarContrato(contrato);
        if (adicionado) {
            System.out.println("Contrato cadastrado com sucesso.");
        } else {
            System.out.println("Nao ha espaco para mais contratos. Maximo de 10 contratos atingido.");
        }
    }

    private static void encerrarContrato(Imobiliaria imobiliaria, Scanner scanner) {
        if (imobiliaria.getContratos().isEmpty()) {
            System.out.println("Nao ha contratos cadastrados.");
            return;
        }

        System.out.println("Contratos cadastrados:");
        List<Contrato> contratos = imobiliaria.getContratos();
        for (int i = 0; i < contratos.size(); i++) {
            Contrato contrato = contratos.get(i);
            System.out.printf("%d. %s - %s\n", i + 1, contrato.getInquilino().getNome(), contrato.getSituacaoTexto());
        }
        System.out.print("Escolha o numero do contrato a encerrar: ");
        int indiceContrato = lerInteiro(scanner) - 1;
        scanner.nextLine();

        if (indiceContrato < 0 || indiceContrato >= contratos.size()) {
            System.out.println("Contrato invalido.");
            return;
        }

        Contrato contrato = contratos.get(indiceContrato);
        if (contrato.isEncerrado()) {
            System.out.println("Este contrato ja esta encerrado.");
            return;
        }

        contrato.encerrar();
        System.out.println("Contrato encerrado com sucesso.");
    }

    private static void demonstracao(Imobiliaria imobiliaria) {
        Inquilino inquilino1 = new Inquilino("Lucas Silva", "123.456.789-00", "(11) 99999-0000");
        Inquilino inquilino2 = new Inquilino("Maria Oliveira", "987.654.321-00", "(21) 98888-1111");
        imobiliaria.adicionarInquilino(inquilino1);
        imobiliaria.adicionarInquilino(inquilino2);

        Imovel apartamento = new Apartamento("Av. Paulista, 1000 - apto 101", 2500.00, 10);
        Imovel casa = new Casa("Rua das Flores, 123", 3200.00, true);
        imobiliaria.adicionarImovel(apartamento);
        imobiliaria.adicionarImovel(casa);

        Contrato contrato1 = new Contrato(inquilino1, apartamento, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 6, 1));
        contrato1.encerrar();
        Contrato contrato2 = new Contrato(inquilino2, casa, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 12, 1));

        imobiliaria.adicionarContrato(contrato1);
        imobiliaria.adicionarContrato(contrato2);

        System.out.println("Demonstracao criada com sucesso.");
        System.out.println("Lista de contratos ativos:");
        imobiliaria.listarContratosAtivos();
    }
}

class Inquilino {
    private final String nome;
    private final String cpf;
    private final String telefone;

    public Inquilino(String nome, String cpf, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    @Override
    public String toString() {
        return String.format("%s (CPF: %s, Tel: %s)", nome, cpf, telefone);
    }
}

abstract class Imovel {
    private final String endereco;
    private final double valorAluguel;

    public Imovel(String endereco, double valorAluguel) {
        this.endereco = endereco;
        this.valorAluguel = valorAluguel;
    }

    public String getEndereco() {
        return endereco;
    }

    public double getValorAluguel() {
        return valorAluguel;
    }

    public abstract String exibirInformacoes();
}

class Apartamento extends Imovel {
    private final int andar;

    public Apartamento(String endereco, double valorAluguel, int andar) {
        super(endereco, valorAluguel);
        this.andar = andar;
    }

    @Override
    public String exibirInformacoes() {
        return String.format("Apartamento - Endereco: %s, Valor aluguel: R$ %.2f, Andar: %d", getEndereco(), getValorAluguel(), andar);
    }
}

class Casa extends Imovel {
    private final boolean quintal;

    public Casa(String endereco, double valorAluguel, boolean quintal) {
        super(endereco, valorAluguel);
        this.quintal = quintal;
    }

    @Override
    public String exibirInformacoes() {
        return String.format("Casa - Endereco: %s, Valor aluguel: R$ %.2f, Quintal: %s", getEndereco(), getValorAluguel(), quintal ? "Sim" : "Nao");
    }
}

class Contrato {
    private final Inquilino inquilino;
    private final Imovel imovel;
    private final LocalDate dataInicio;
    private final LocalDate dataFinal;
    private boolean encerrado;

    public Contrato(Inquilino inquilino, Imovel imovel, LocalDate dataInicio, LocalDate dataFinal) {
        this.inquilino = inquilino;
        this.imovel = imovel;
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.encerrado = false;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public boolean isEncerrado() {
        return encerrado;
    }

    public void encerrar() {
        this.encerrado = true;
    }

    public long calcularQuantidadeMeses() {
        long dias = ChronoUnit.DAYS.between(dataInicio, dataFinal);
        long meses = dias / 30;
        if (dias % 30 != 0) {
            meses++;
        }
        return Math.max(meses, 1);
    }

    public double calcularValorTotal() {
        return calcularQuantidadeMeses() * imovel.getValorAluguel();
    }

    public String getSituacaoTexto() {
        return encerrado ? "Encerrado" : "Ativo";
    }

    @Override
    public String toString() {
        return String.format(
            "Contrato: %s | Imovel: %s | Inicio: %s | Fim: %s | Situacao: %s | Valor total: R$ %.2f",
            inquilino.getNome(),
            imovel.exibirInformacoes(),
            dataInicio,
            dataFinal,
            getSituacaoTexto(),
            calcularValorTotal()
        );
    }
}

class Imobiliaria {
    private final List<Inquilino> inquilinos = new ArrayList<>();
    private final List<Imovel> imoveis = new ArrayList<>();
    private final List<Contrato> contratos = new ArrayList<>();
    private static final int MAX_CONTRATOS = 10;

    public void adicionarInquilino(Inquilino inquilino) {
        inquilinos.add(inquilino);
    }

    public void adicionarImovel(Imovel imovel) {
        imoveis.add(imovel);
    }

    public boolean adicionarContrato(Contrato contrato) {
        if (contratos.size() >= MAX_CONTRATOS) {
            return false;
        }
        contratos.add(contrato);
        return true;
    }

    public List<Inquilino> getInquilinos() {
        return inquilinos;
    }

    public List<Imovel> getImoveis() {
        return imoveis;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void listarContratosAtivos() {
        boolean encontrouAtivo = false;
        for (Contrato contrato : contratos) {
            if (!contrato.isEncerrado()) {
                System.out.println(contrato);
                encontrouAtivo = true;
            }
        }
        if (!encontrouAtivo) {
            System.out.println("Nenhum contrato ativo encontrado.");
        }
    }
}
