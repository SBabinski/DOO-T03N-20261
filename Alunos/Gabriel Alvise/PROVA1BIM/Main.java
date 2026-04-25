import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Imobiliaria imobiliaria = new Imobiliaria();

        Inquilino[] inquilinos = new Inquilino[10];
        Imovel[] imoveis = new Imovel[10];

        int qtdInquilinos = 0;
        int qtdImoveis = 0;
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\nSistema de Gerenciamento de Aluguéis");
            System.out.println("[1] Cadastrar inquilino");
            System.out.println("[2] Cadastrar imóvel");
            System.out.println("[3] Cadastrar contrato");
            System.out.println("[4] Encerrar contrato");
            System.out.println("[5] Listar contratos ativos");
            System.out.println("[6] Fazer demonstração");
            System.out.println("[0] Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    if (qtdInquilinos >= inquilinos.length) {
                        System.out.println("Limite de inquilinos atingido");
                        break;
                    }

                    System.out.print("Nome: ");
                    String nome = sc.nextLine();

                    System.out.print("CPF: ");
                    String cpf = sc.nextLine();

                    System.out.print("Telefone: ");
                    String telefone = sc.nextLine();

                    inquilinos[qtdInquilinos] = new Inquilino(nome, cpf, telefone);
                    qtdInquilinos++;

                    System.out.println("Inquilino cadastrado");
                    break;

                case 2:
                    if (qtdImoveis >= imoveis.length) {
                        System.out.println("Limite de imóveis atingido.");
                        break;
                    }

                    System.out.println("[1] Casa");
                    System.out.println("[2] Apartamento");
                    System.out.print("Tipo do imóvel: ");
                    int tipoImovel = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Endereço: ");
                    String endereco = sc.nextLine();

                    System.out.print("Valor do aluguel mensal: ");
                    double valorMensal = sc.nextDouble();

                    if (tipoImovel == 1) {
                        System.out.print("Tem quintal? [1] Sim [2] Não: ");
                        int opcaoQuintal = sc.nextInt();

                        boolean temQuintal = opcaoQuintal == 1;

                        imoveis[qtdImoveis] = new Casa(endereco, valorMensal, temQuintal);
                        qtdImoveis++;

                        System.out.println("Casa cadastrada");

                    } else if (tipoImovel == 2) {
                        System.out.print("Andar do apartamento: ");
                        int andar = sc.nextInt();

                        imoveis[qtdImoveis] = new Apartamento(endereco, valorMensal, andar);
                        qtdImoveis++;

                        System.out.println("Apartamento cadastrado");

                    } else {
                        System.out.println("Tipo de imóvel inválido.");
                    }

                    break;

                case 3:
                    if (qtdInquilinos == 0 || qtdImoveis == 0) {
                        System.out.println("Cadastre pelo menos um inquilino e um imóvel antes");
                        break;
                    }

                    System.out.println("\nInquilinos cadastrados:");
                    for (int i = 0; i < qtdInquilinos; i++) {
                        System.out.println("[" + i + "] " + inquilinos[i].getNome());
                    }

                    System.out.print("Escolha o inquilino: ");
                    int indiceInquilino = sc.nextInt();

                    System.out.println("\nImóveis cadastrados:");
                    for (int i = 0; i < qtdImoveis; i++) {
                        System.out.println("[" + i + "] " + imoveis[i].getEndereco());
                    }

                    System.out.print("Escolha o imóvel: ");
                    int indiceImovel = sc.nextInt();
                    sc.nextLine();

                    if (indiceInquilino < 0 || indiceInquilino >= qtdInquilinos || indiceImovel < 0 || indiceImovel >= qtdImoveis) {
                        System.out.println("Inquilino ou imóvel inválido");
                        break;
                    }

                    System.out.print("Data de início (AAAA-MM-DD): ");
                    LocalDate dataInicio = LocalDate.parse(sc.nextLine());

                    System.out.print("Data final (AAAA-MM-DD): ");
                    LocalDate dataFim = LocalDate.parse(sc.nextLine());

                    ContratoAluguel contrato = new ContratoAluguel(
                            inquilinos[indiceInquilino],
                            imoveis[indiceImovel],
                            dataInicio,
                            dataFim,
                            false
                    );

                    imobiliaria.adicionarContrato(contrato);
                    break;

                case 4:
                    boolean encontrouAtivos = imobiliaria.listarContratosAtivos();

                    if (encontrouAtivos) {
                        System.out.print("Digite o número do contrato que deseja encerrar: ");
                        int indiceContrato = sc.nextInt();
                        imobiliaria.encerrarContrato(indiceContrato);
                    }
                    else {
                        System.out.println("Sem contratos ativos");
                    }

                    break;

                case 5:
                    imobiliaria.listarContratosAtivos();
                    break;

                case 6:
                    fazerDemonstracao();
                    break;

                case 0:
                    System.out.println("Saindo do sistema");
                    break;

                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }

        sc.close();
    }

    public static void fazerDemonstracao() {
        System.out.println("\nDEMONSTRAÇÃO DO SISTEMA");

        Imobiliaria imobiliariaDemo = new Imobiliaria();

        Inquilino inquilino1 = new Inquilino("Gabriel", "111.111.111-11", "4599999-1111");
        Inquilino inquilino2 = new Inquilino("Maria", "222.222.222-22", "4599999-2222");

        Imovel apartamento = new Apartamento("Rua das Flores, 100", 1500.00, 3);
        Imovel casa = new Casa("Avenida Brasil, 500", 2200.00, true);

        ContratoAluguel contrato1 = new ContratoAluguel(
                inquilino1,
                apartamento,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 7, 1),
                true
        );

        ContratoAluguel contrato2 = new ContratoAluguel(
                inquilino2,
                casa,
                LocalDate.of(2024, 2, 10),
                LocalDate.of(2024, 8, 10),
                false
        );

        imobiliariaDemo.adicionarContrato(contrato1);
        imobiliariaDemo.adicionarContrato(contrato2);

        imobiliariaDemo.listarContratosAtivos();
    }
}