import java.util.Scanner;
import java.util.ArrayList;

public class Alugueis {
    public static void main(String[] args) {
        ArrayList <Inquilino> listaInquilinos = new ArrayList<>();
        ArrayList <Imovel> listaImovel = new ArrayList<>();
        ArrayList <Contrato> listaContrato = new ArrayList<>();


        Scanner scanner = new Scanner(System.in);
        int opcao = 1;
        do {
            System.out.println("======Imobilaria Kozikoski======");
            System.out.println("[1] - cadastro de inquilino");
            System.out.println("[2] - cadastro de imovel");
            System.out.println("[3] - cadastro de contrato");
            System.out.println("================================");
            opcao = scanner.nextInt();

            switch (opcao){
                case 1:
                    cadastroInquilino(listaInquilinos , scanner);break;
                case 2:
                    cadastroImovel(listaImovel , scanner);break;
                case 3:
                    cadastroContrato(listaInquilinos , listaImovel , listaContrato , scanner);break;
            }
        }while(opcao != 0);
    }

    public static void cadastroInquilino(ArrayList<Inquilino> lista, Scanner sc) {
        sc.nextLine();
        System.out.println("Digite o nome do novo Inquilino:");
        String nome = sc.nextLine();
        System.out.println("Digite o CPF para cadastro :");
        float cpf = sc.nextInt();
        System.out.println("Digite o numero de telefone para contato :");
        float telefone = sc.nextInt();
        Inquilino inquilino = new Inquilino(nome,cpf,telefone);
        lista.add(inquilino);
        inquilino.Apresentacao();
    }
    public static void cadastroImovel(ArrayList<Imovel> lista , Scanner sc){
        sc.nextLine();
        System.out.println("Digite o endereço do imovel");
        String endereco = sc.nextLine();
        System.out.println("possui quintal?");
        String quintal = sc.nextLine();
        System.out.println("se for (AP) -  qual andar que é : ");
        String andar = sc.nextLine();
        System.out.println("Qual o valor mensal :");
        int valorAluguel = sc.nextInt();
        Imovel imovel = new Imovel(endereco , quintal , andar , valorAluguel);
        lista.add(imovel);
        imovel.Apresentacao();
    }

    public static void cadastroContrato(ArrayList<Inquilino> listaInq, ArrayList<Imovel> listaImo, ArrayList<Contrato> listaCon, Scanner scanner) {
        scanner.nextLine();

        if (listaInq.isEmpty() || listaImo.isEmpty()) {
            System.out.println("Erro: Precisas de ter pelo menos um inquilino e um imóvel cadastrados!");
            return;
        }

        System.out.println("=== Selecione o Inquilino pelo ID ===");
        for (int i = 0; i < listaInq.size(); i++) {
            System.out.println("[" + i + "] " + listaInq.get(i).getNome());
        }
        int idInquilino = scanner.nextInt();
        Inquilino inqEscolhido = listaInq.get(idInquilino);

        System.out.println("=== Selecione o Imóvel pelo ID ===");
        for (int i = 0; i < listaImo.size(); i++) {
            System.out.println("[" + i + "] " + listaImo.get(i).getEndereco());
        }
        int idImovel = scanner.nextInt();
        Imovel imoEscolhido = listaImo.get(idImovel);

        scanner.nextLine();
        System.out.println("Data de Início:");
        String inicio = scanner.nextLine();
        System.out.println("Data Final:");
        String fim = scanner.nextLine();
        System.out.println("Quantidade de meses:");
        int meses = scanner.nextInt();


        Contrato contrato = new Contrato(inqEscolhido, imoEscolhido, inicio, fim, meses);

        listaCon.add(contrato);
        System.out.println("Contrato realizado com sucesso!");
        contrato.exibirDadosContrato();
    }


}