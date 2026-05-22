import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    
    
    public static ArrayList<Inquilino> inquilinosList = new ArrayList<Inquilino>();
    public static ArrayList<Imovel> imoveisList = new ArrayList<Imovel>();
    public static ArrayList<ContratoAluguel> contratosList = new ArrayList<ContratoAluguel>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        menuInicial(sc);

        sc.close();
    }

    //Menu inicial

    public static void menuInicial(Scanner sc) {

        int opc = 0;
        while (opc != 7) {
            System.out.println("*** MENU INICIAL : GESTÃO DE IMOVEIS ***");
            System.out.println("[1] - Cadastrar Inquilino");
            System.out.println("[2] - Cadastrar Imovel");
            System.out.println("[3] - Cadastrar Contrato");
            System.out.println("[4] - Encerrar Contrato");
            System.out.println("[5] - Listar Contratos Ativos");
            System.out.println("[6] - Demonstração");
            System.out.println("[7] - Sair.");
            System.out.print("Opção: ");
            
            opc = sc.nextInt();
            sc.nextLine();

            switch (opc) {
                case 1 -> cadastrarInquilino(sc);
                case 2 -> cadastrarImovel(sc);
                case 3 -> cadastrarContrato(sc);
                case 4 -> encerrarContrato(sc);
                case 5 -> exibirContratosAtivos();
                case 6 -> demonstracao();
                case 7 -> System.out.println("Encerrando...");
            
                default -> System.out.println("Opção Inválida. Apenas as opções acima");
            }
        }
    
    }
    
    // Métodos de cadastros

        // Cadastrar Inquilino
    public static void cadastrarInquilino(Scanner sc) {

        System.out.print("Insira o nome do novo inquilino: ");
        String nome = sc.nextLine();

        System.out.print("Insira o CPF do Inquilino: ");
        String cpf = sc.nextLine();
        
        System.out.print("Insira o telefone do Inquilino:");
        String telefone = sc.nextLine();
        sc.nextLine();  

        Inquilino inquilino = new Inquilino(nome, cpf, telefone);
        inquilinosList.add(inquilino);
    }

        //Cadastrar Imovel
    public static void cadastrarImovel(Scanner sc) {
        int opc = 0;




        while (opc != 1 && opc != 2) {
            System.out.println("Qual o tipo do imovel?");
            System.out.println("1 - Casa");
            System.out.println("2 - Apartamento");
            System.out.println("3 - Cancelar cadastro");
            System.out.print("Opção:");

            opc = sc.nextInt();
            sc.nextLine();
            
            switch (opc) {
                case 1:
                    System.out.print("Informe o bairro do imovel: ");
                    String bairro = sc.nextLine();
                    
                    System.out.print("Informe a rua do imovel: ");
                    String rua = sc.nextLine();
                    
                    System.out.print("Informe número do imovel: ");
                    int numero = sc.nextInt();
                    sc.nextLine();

                    String endereco = String.format("%s, %s, %d",bairro, rua, numero);

                    System.out.print("Informe o valor do aluguel: ");
                    double valorAluguel = sc.nextDouble();
                    
                    int opcQuintal = 5;
                    while (opcQuintal != 0 && opcQuintal != 1) {
                        System.out.println("Tem Quintal: ");
                        System.out.println("0 - NÃO");
                        System.out.println("1 - SIM");
                        System.out.print("Opção:");
                        
                        opcQuintal = sc.nextInt();
                        sc.nextLine();
                        
                        if (opcQuintal != 0 || opcQuintal != 1) {
                            System.out.println("Opção Inválida. Apenas as opções acima");
                        }
                        
                    }

                    boolean temQuintal = (opcQuintal == 1 ? true : false);

                    Casa casa = new Casa(endereco, valorAluguel, temQuintal);

                    imoveisList.add(casa);
                    break;
                case 2:
                    System.out.print("Informe o bairro do imovel: ");
                    bairro = sc.nextLine();
                    
                    System.out.print("Informe a rua do imovel: ");
                    rua = sc.nextLine();
                    
                    System.out.print("Informe número do imovel: ");
                    numero = sc.nextInt();
                    sc.nextLine();
                    
                    System.out.print("Informe número do andar: ");
                    int andar = sc.nextInt();
                    sc.nextLine();
                    
                    endereco = String.format("%s, %s, %d",bairro, rua, numero);
                    
                    System.out.print("Informe o valor do aluguel: ");
                    valorAluguel = sc.nextDouble();

                    Apartamento apartamento = new Apartamento(endereco, valorAluguel, andar);

                    imoveisList.add(apartamento);

                    break;
                case 3: return;
                default: System.out.println("Opção inválida!");

            }
        }
    }

        //Cadastrar Contrato
    public static void cadastrarContrato(Scanner sc) {
        exibirInquilinos();
        System.out.print("Insira o valor do indice do inquilino a fazer contrato: ");
        int indice = sc.nextInt();
        sc.nextLine();

        Inquilino inquilino = inquilinosList.get(indice - 1);

        exibirImoveis();
        System.out.print("Insira o valor do indice do imovel do contrato: ");
        indice = sc.nextInt();
        sc.nextLine();
        
        Imovel imovel = imoveisList.get(indice - 1);
        
        System.out.print("Insira o dia de inicio de contrato: ");
        int dia = sc.nextInt();
        sc.nextLine();
        
        System.out.print("Insira o mês de inicio de contrato: ");
        int mes = sc.nextInt();
        sc.nextLine();
        
        System.out.print("Insira o ano de inicio de contrato: ");
        int ano = sc.nextInt();
        sc.nextLine();

        
        if(obterContratosAtivos().size() < 10){
            contratosList.add(new ContratoAluguel(inquilino, imovel, LocalDate.of(ano, mes, dia)));
        }else{
            System.out.println("Contratos máximos alcançados. Limite de 10");
        }
        

    }

    
    //Encerrar Contrato
    public static void encerrarContrato(Scanner sc) {
        exibirContratosAtivos();

        System.out.print("Insira o valor do indice do inquilino a fazer contrato: ");
        int indice = sc.nextInt()- 1;
        sc.nextLine();
        
        if (contratosList.get(indice).isEncerrado()){
            System.out.println("Contrato já encerrado!");
            return;
        }   
        
        contratosList.get(indice).encerrarContrato(LocalDate.now());
        
        System.out.println("Contrato encerrado com sucesso!");

    }

    //Métodos de exibição

    public static void exibirInquilinos() {
        System.out.println("*** Inquilinos ***");
        for(var inquilino : inquilinosList){
            System.out.printf("%d - ", inquilinosList.indexOf(inquilino) + 1);
            inquilino.exibirInfos();
        }
    }

    public static void exibirImoveis() {
        System.out.println("*** Imoveis ***");
        for(var imovel : imoveisList){
            System.out.printf("%d - ", imoveisList.indexOf(imovel) + 1);
            imovel.exibirInfos();
        }
    }

    public static ArrayList<ContratoAluguel> obterContratosAtivos() {
        System.out.println("*** Contratos ***");
        ArrayList<ContratoAluguel> contratosAtivos = new ArrayList<ContratoAluguel>();
        for(var contrato : contratosList){

            if (!contrato.isEncerrado()) {
                contratosAtivos.add(contrato);
            }
        }
        return contratosAtivos;
    }
    public static void exibirContratosAtivos() {
        System.out.println("*** Contratos Ativos***");
        for(var contrato : contratosList) {
            if(!contrato.isEncerrado()) {
                System.out.printf("%d - ", contratosList.indexOf(contrato) + 1);
                contrato.exibirInfos();
            }
        }
    }

    public static void demonstracao() {
        inquilinosList.add(new Inquilino("Joao", "10101020010", "45900909999"));
        inquilinosList.add(new Inquilino("Maira", "18888888888", "65900909090"));

        imoveisList.add(new Casa("Coqueiral, rua 27, 143", 2000, false));
        imoveisList.add(new Apartamento("Coqueiral, rua 27, 143", 2500, 24));

        contratosList.add(new ContratoAluguel(inquilinosList.get(0), imoveisList.get(0), LocalDate.of(20, 04, 2010)));
        contratosList.add(new ContratoAluguel(inquilinosList.get(1), imoveisList.get(1), LocalDate.of(10, 06, 2020)));

        contratosList.get(0).encerrarContrato(LocalDate.now());

    }
}
