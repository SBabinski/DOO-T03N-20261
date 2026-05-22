import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Registrador reg = new Registrador();
        int opcao;

        do {
            System.out.println("\n1. Cadastrar inquilino/Imóvel");
            System.out.println("2. Encerrar contrato");
            System.out.println("3. Listar contratos ativos" );
            System.out.println("4. Executar DEMO");
            System.out.println("0. Sair");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("\n--- CADASTRO DE INQUILINO ---");
                    sc.nextLine();
                    System.out.print("Nome do Inquilino: ");
                    String nome = sc.nextLine();
                    System.out.print("CPF: ");
                    String cpf = sc.nextLine();
                    System.out.print("Telefone: ");
                    String tel = sc.nextLine();
                    Inquilino novoInq = new Inquilino(nome, cpf, tel);

                    System.out.println("\n--- CADASTRO DE IMÓVEL ---");
                    System.out.println("Qual o tipo do imóvel? (1- Apartamento | 2- Casa)");
                    int tipo = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Endereço: ");
                    String endereco = sc.nextLine();
                    System.out.print("Valor do Aluguel Mensal: ");
                    float valor = sc.nextFloat();

                    Imovel novoImovel = null;

                    if (tipo == 1) {
                        System.out.print("Número do Andar: ");
                        int andar = sc.nextInt();
                        novoImovel = new Apartamento(endereco, valor, andar);
                    } else {
                        System.out.print("Possui quintal? (true/false): ");
                        boolean temQuintal = sc.nextBoolean();
                        novoImovel = new Casa(endereco, valor, temQuintal);
                    }

                    System.out.println("\n--- REGISTRO DE CONTRATO ---");
                    System.out.print("Quantos meses de duração terá o contrato? ");
                    int mesesDuracao = sc.nextInt();

                    LocalDate dataInicio = LocalDate.now();
                    LocalDate dataFinal = dataInicio.plusMonths(mesesDuracao);

                    Contrato novoContrato = new Contrato(novoInq, novoImovel, dataInicio, dataFinal);

                    reg.registrarContrato(novoContrato);
                    break;
                case 2:
                    System.out.println("Digite o ID do Contrato: ");
                    reg.encerrarContratoNoSistema(sc.nextInt());
                    break;
                case 3:
                    reg.listarContratosAtivos();
                    break;
                case 4:
                    executarDemo(reg);
                    break;
            }
        } while (opcao != 0);
    }

    public static void executarDemo(Registrador reg){
        Inquilino inq1 = new Inquilino("João Gnoatto", "111.111.111-11", "45 11111-1111");
        Inquilino inq2 = new Inquilino("Gabriel Arataque", "222.222.222-22", "45 22222-2222");

        Apartamento ap1 = new Apartamento("Avenida da FAG, 3322, Cascavel-PR", 1200.0f, 3);
        Casa casa = new Casa("Rua Jardins, 443, Santa Tereza do Oeste-PR", 2500.0f, true);

        Contrato c1 = new Contrato(inq1, ap1, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31));
        Contrato c2 = new Contrato(inq2, casa, LocalDate.of(2025, 1, 1), LocalDate.of(2025,5, 1));

        c1.encerrarContrato();

        reg.registrarContrato(c1);
        reg.registrarContrato(c2);

        System.out.println("DEMO finalizada | Listando contratos ativos:");
        reg.listarContratosAtivos();
    }


}