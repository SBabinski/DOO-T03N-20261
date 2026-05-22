import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<Imobiliaria> imobiliarias = new ArrayList<Imobiliaria>();
    public static ArrayList<Inquilino> inquilinos = new ArrayList<Inquilino>();
    public static ArrayList<Vinculo> vinculos = new ArrayList<Vinculo>();
    public static Scanner scan = new Scanner(System.in);


    public static void main(String[] args) {
        exemplo();

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Adicionar Inquilino");
            System.out.println("2. Adicionar Imobiliaria");
            System.out.println("3. Exibir Inquilinos");
            System.out.println("4. Exibir Imobiliarias");
            System.out.println("5. Vincular Inquilino a Imobiliaria");
            System.out.println("6. Exibir Vinculos");
            System.out.println("7. Encerrar Contrato");
            System.out.println("8. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scan.nextInt();
            scan.nextLine();

            switch (opcao) {
                case 1:
                    CriarInquilino();
                    break;
                case 2:
                    CriarImobiliaria();
                    break;
                case 3:
                    ExibirInquilino();

                    break;
                case 4:
                    ExibirImobiliaria();
                    break;
                case 5:
                    CriarVinculo();
                    break;
                case 6:
                    exibirVinculo();
                    break;
                case 7:
                    encerrarContrato();
                    break;

                case 8:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }


    public static void CriarInquilino() {
        System.out.print("Digite o nome do Inquilino: ");
        String nome = scan.nextLine();

        System.out.print("Digite o cpf do Inquilino: ");
        String cpf = scan.nextLine();

        System.out.print("Digite o telefone do Inquilino: ");
        String telefone = scan.nextLine();

        Inquilino specInquilino = new Inquilino(nome, cpf, telefone);
        inquilinos.add(specInquilino);
    }

    public static void CriarImobiliaria() {
        if (imobiliarias.size() >= 10) {
            System.out.println("Limite de imobiliarias atingido!");
            return;
        } else {
            System.out.print("digite 1-casa / 2- apartamento");
            int opcao = scan.nextInt();

            System.out.print("Digite o endereco: ");
            String endereco = scan.nextLine();

            System.out.print("Digite o valor do aluguel: ");
            double valor = scan.nextDouble();

            System.out.print("Digite a data de aluguel (YYYY-MM-DD): ");
            String aluguelStr = scan.nextLine();
            LocalDate aluguel = LocalDate.parse(aluguelStr);


            if (opcao == 1) {
                System.out.print("Tem quintal?: ");
                boolean temQuintal = scan.nextBoolean();

                Imobiliaria imobiliaria = new casa(endereco, valor, aluguel, temQuintal);
                imobiliarias.add(imobiliaria);

            }


            if (opcao == 2) {
                System.out.print("Qual andar?: ");
                int andar = scan.nextInt();
                Imobiliaria imobiliaria = new apartamento(endereco, valor, aluguel, andar);
                imobiliarias.add(imobiliaria);
            }
        }
    }

    public static void CriarVinculo() {
        System.out.print("Qual inquilino deseja vincular? (Digite o ID): \n");
        ExibirInquilino();
        int idInquilino = scan.nextInt();

        System.out.print("Qual imobiliaria deseja vincular? (Digite o ID): \n");
        ExibirImobiliaria();
        int idImobiliaria = scan.nextInt();

        System.out.print("Digite a data de início do contrato (YYYY-MM-DD): ");
        String inicioContratoStr = scan.nextLine();
        LocalDate inicioContrato = LocalDate.parse(inicioContratoStr);
        scan.nextLine();


        System.out.print("Digite a data de fim do contrato (YYYY-MM-DD): ");
        String fimContratoStr = scan.nextLine();
        LocalDate fimContrato = LocalDate.parse(fimContratoStr);

        Vinculo vinculo = new Vinculo(inquilinos.get(idInquilino), imobiliarias.get(idImobiliaria), inicioContrato, fimContrato, false, 0);
        vinculos.add(vinculo);
    }

    public static void exibirVinculo() {
        for (int i = 0; i < vinculos.size(); i++) {
            Vinculo vinculo = vinculos.get(i);
            if (!vinculo.SituacaoContrato) {
                vinculo.exibirVinculo();
                System.out.println("-------------------------");
            }
        }
    }

    public static void ExibirInquilino() {
        for (int i = 0; i < inquilinos.size(); i++) {
            Inquilino inquilino = inquilinos.get(i);
            inquilino.exibirInquilino();
            System.out.println("-------------------------");
        }
    }

    public static void ExibirImobiliaria() {

        for (int i = 0; i < imobiliarias.size(); i++) {
            Imobiliaria imobiliaria = imobiliarias.get(i);
            imobiliaria.exibirImobiliaria();
            System.out.println("-------------------------");
        }
    }

    public static void encerrarContrato() {
        System.out.print("Qual contrato deseja encerrar? (Digite o ID): \n");
        exibirVinculo();
        int idContrato = scan.nextInt();

        Vinculo vinculo = vinculos.get(idContrato);
        vinculo.SituacaoContrato = true;
    }


    public static void exemplo() {
        Inquilino inquilino1 = new Inquilino("João Silva", "123.456.789-00", "joao@telefone.com");
        Inquilino inquilino2 = new Inquilino("Maria Santos", "987.654.321-00", "maria@telefone.com");


        inquilinos.add(inquilino1);
        inquilinos.add(inquilino2);

        Imobiliaria apartamento1 = new apartamento("Rua das Flores, 100", 1500.00, LocalDate.of(2026, 1, 1), 5);

        Imobiliaria casa1 = new casa("Avenida Central, 50", 2000.00, LocalDate.of(2026, 2, 1), true);

        imobiliarias.add(apartamento1);
        imobiliarias.add(casa1);


        Vinculo contrato1 = new Vinculo(inquilino1, apartamento1, LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), false, 0);
        contrato1.CalcularAluguel();

        Vinculo contrato2 = new Vinculo(inquilino2, casa1, LocalDate.of(2026, 1, 15), LocalDate.of(2026, 12, 31), true, 0);
        contrato2.CalcularAluguel();

        vinculos.add(contrato1);
        vinculos.add(contrato2);

    }
}