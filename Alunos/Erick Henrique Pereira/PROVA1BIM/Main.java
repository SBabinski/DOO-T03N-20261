import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    
    public static Scanner scan = new Scanner(System.in);
    public static List<Inquilino> inquilinos = new ArrayList<>();
    public static List<Imovel> imoveis = new ArrayList<>();
    public static List<ContratoAluguel> contratos = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Bem vindo ao gerenciador de Alugueis");
        int escolha = 0;
        do{
            System.out.println("");
            System.out.println("Selecione a opção desejada");
            mostrarMenu();
            escolha = scan.nextInt();
            scan.nextLine();

            switch (escolha){
                case 0 -> System.out.println("Programa finalizado!");
                case 1 -> cadastrarInquilino();
                case 2 -> cadastrarImovel();
                case 3 -> cadastrarContrato();
                case 4 -> encerrarContrato();
                case 5 -> listarContratosAtivos();
            }

        }while (escolha != 0);
    }


    public static void listarContratosAtivos(){
        System.out.println("Contratos Ativos: ");
        for(ContratoAluguel contrato : contratos){
            if(contrato.isStatus() == true){
                System.out.println("Inquilino: " + contrato.getInquilino().getNome() + ", Imóvel: " + contrato.getImovel().mostrarInformacoes() + 
                ", Data de Entrada: " + contrato.getDataEntrada() + ", Data de Fim: " + contrato.getDataFimContrato() + ", Valor total" + contrato.valorPagar());
            }else{
                System.out.println("Não há contratos a listar");
            }
        }
    }


    public static void encerrarContrato(){
        if(contratos.size() == 0){
            System.out.println("Não há contratos para encerrar!");
            return;
        }
        listarContrato();
        System.out.println("Selecione o contrato que deseja encerrar: ");
        int escolhaContrato = scan.nextInt();
        ContratoAluguel contrato = contratos.get(escolhaContrato - 1);
        contrato.setStatus(false);
        System.out.println("Contrato encerrado com sucesso!");
    }

    public static void listarContrato(){
        System.out.println("Contratos Ativos: ");
        for(ContratoAluguel contrato : contratos){
            if(contrato.isStatus() == true){
                System.out.println("Inquilino: " + contrato.getInquilino().getNome() + ", Imóvel: " + contrato.getImovel().mostrarInformacoes() + 
                ", Data de Entrada: " + contrato.getDataEntrada() + ", Data de Fim: " + contrato.getDataFimContrato() + contrato.valorPagar());
            }
        }
    }


    public static void cadastrarContrato(){
        if(inquilinos.size() >= 1 && imoveis.size() >= 1){
            if(contratos.size() <=10){
                System.out.println("Selecione o inquilino para o contrato: ");
                listarInquilinos();
                int escolhaInquilino = scan.nextInt();
                Inquilino inquilino = inquilinos.get(escolhaInquilino - 1);
                
                System.out.println("Selecione o imóvel para o contrato: ");
                listarImoveis();
                int escolhaImovel = scan.nextInt();
                scan.nextLine();
                Imovel imovel = imoveis.get(escolhaImovel - 1);

                System.out.println("Digite a data  de entrada do contrato (yyyy-MM-dd):");
                String dataEntradaStr = scan.nextLine();
                LocalDate dataEntrada = LocalDate.parse(dataEntradaStr);

                System.out.println("Digite a data de fim do contrato (yyyy-MM-dd):");
                String dataFimStr = scan.nextLine();
                LocalDate dataFim = LocalDate.parse(dataFimStr);

                boolean status = true;
                ContratoAluguel contrato = new ContratoAluguel(inquilino, imovel, dataEntrada, dataFim, status);
                contratos.add(contrato);

                System.out.println("Contrato cadastrado com sucesso!");
            }else{
                System.out.println("Quantidade de contratos está no máximo");
            }
        }else{
            System.out.println("É necessário cadastrar pelo menos um inquilino e um imóvel para criar um contrato!");
        }
    }

    public static void listarInquilinos(){
        for(int i = 0; i < inquilinos.size(); i++){
            System.out.println((i+1) + " - " + inquilinos.get(i).getNome());
        }
    }

    
    public static void listarImoveis(){
        for(int i = 0; i < imoveis.size(); i++){
            System.out.println((i+1) + " - " + imoveis.get(i).mostrarInformacoes());
        }
    }


    public static void cadastrarImovel(){
        System.out.println("Digite o tipo de imóvel (1 - Casa | 2 - Apartamento)");
        int tipoImovel = scan.nextInt();
        scan.nextLine();

        System.out.println("Digite o Estado onde está localizado o imóvel: ");
        String estado = scan.nextLine();

        System.out.println("Digite a Cidade onde está localizado o imóvel: ");
        String cidade = scan.nextLine();

        System.out.println("Digite o Bairro onde está localizado o imóvel: ");
        String bairro = scan.nextLine();

        System.out.println("Digite a Rua onde está localizado o imóvel: ");
        String rua = scan.nextLine();

        System.out.println("Digite o Número onde está localizado o imóvel: ");
        int numero = scan.nextInt();

        System.out.println("Digite o valor do aluguel do imóvel: ");
        double valorAluguel = scan.nextDouble();

        Endereco endereco = new Endereco(cidade, bairro, rua, numero);

        if(tipoImovel == 1){
            System.out.println("O imóvel possui quintal? (true/false)");
            boolean quintal = scan.nextBoolean();
            Casa casa = new Casa(endereco, valorAluguel, quintal);
            imoveis.add(casa);
            System.out.println("Casa cadastrada com sucesso!");
        }else if(tipoImovel == 2){
            System.out.println("Digite o andar do apartamento: ");
            int andar = scan.nextInt();
            Apartamento apartamento = new Apartamento(andar, endereco, valorAluguel);
            imoveis.add(apartamento);
            System.out.println("Apartamento cadastrado com sucesso!");
        }else{
            System.out.println("Tipo de imóvel inválido!");
        }
    }


    public static void cadastrarInquilino(){
        System.out.println("Digite o nome do inquilino: ");
        String nome = scan.nextLine();

        System.out.println("Digite o CPF do inquilino: ");
        String cpf = scan.nextLine();

        System.out.println("Digite o número do inquilino: ");
        String numero = scan.nextLine();

        Inquilino inquilino = new Inquilino(nome, cpf, numero);
        inquilinos.add(inquilino);

        System.out.println("Inquilino cadastrado com sucesso!");
    }


    public static void mostrarMenu(){
        System.out.println("1 - Cadastrar Inquilino");
        System.out.println("2 - Cadastrar Imóvel");
        System.out.println("3 - Cadastrar Contrato");
        System.out.println("4 - Encerrar Contrato");
        System.out.println("5 - Listar Contratos Ativos");
        System.out.println("0 - Finalizar Programa");
    }
}
