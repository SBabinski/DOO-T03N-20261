import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Scanner;

//================ INQUILINO ==================
class Inquilino {

    String nome;
    String cpf;
    String telefone;

    public Inquilino(String nome, String cpf, String telefone){
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public void exibir(){
        System.out.println("Nome: " + nome);
        System.out.println("CPF: " + cpf);
        System.out.println("Telefone: " + telefone);
    }
}

//================ HERANÇA IMÓVEL ==================
abstract class Imovel {

    String endereco;
    double aluguelMensal;

    public Imovel(String endereco,double aluguelMensal){
        this.endereco = endereco;
        this.aluguelMensal = aluguelMensal;
    }

    public double getAluguelMensal(){
        return aluguelMensal;
    }

    public abstract void exibirInformacoes();
}

//================ APARTAMENTO ==================
class Apartamento extends Imovel {

    int andar;

    public Apartamento(String endereco,double aluguelMensal,int andar){
        super(endereco,aluguelMensal);
        this.andar = andar;
    }

    @Override
    public void exibirInformacoes(){
        System.out.println("Tipo: Apartamento");
        System.out.println("Endereço: " + endereco);
        System.out.println("Aluguel: R$ " + aluguelMensal);
        System.out.println("Andar: " + andar);
    }
}

//================ CASA ==================
class Casa extends Imovel {

    boolean quintal;

    public Casa(String endereco,double aluguelMensal,boolean quintal){
        super(endereco,aluguelMensal);
        this.quintal = quintal;
    }

    @Override
    public void exibirInformacoes(){
        System.out.println("Tipo: Casa");
        System.out.println("Endereço: " + endereco);
        System.out.println("Aluguel: R$ " + aluguelMensal);
        System.out.println("Possui quintal? " + (quintal ? "Sim":"Não"));
    }
}

//================ CONTRATO ==================
class ContratoAluguel {

    Inquilino inquilino;
    Imovel imovel;

    LocalDate dataInicio;
    LocalDate dataFim;

    boolean encerrado;

    public ContratoAluguel(
            Inquilino inquilino,
            Imovel imovel,
            LocalDate inicio,
            LocalDate fim,
            boolean encerrado){

        this.inquilino = inquilino;
        this.imovel = imovel;
        this.dataInicio = inicio;
        this.dataFim = fim;
        this.encerrado = encerrado;
    }

    public double calcularValorTotal(){

        int meses = Period.between(dataInicio,dataFim).getMonths();

        if(meses <= 0){
            meses = 1;
        }

        return meses * imovel.getAluguelMensal();
    }

    public void encerrarContrato(){
        encerrado = true;
    }

    public boolean ativo(){
        return !encerrado;
    }

    public void exibirContrato(){

        System.out.println("\n===== CONTRATO =====");

        inquilino.exibir();

        imovel.exibirInformacoes();

        System.out.println("Início: " + dataInicio);
        System.out.println("Fim: " + dataFim);

        System.out.println("Situação: " +
                (encerrado ? "Encerrado":"Ativo"));

        System.out.println("Valor Total: R$ " +
                calcularValorTotal());
    }
}

//================ IMOBILIÁRIA ==================
class Imobiliaria {

    ContratoAluguel[] contratos = new ContratoAluguel[10];

    int total = 0;

    public void adicionarContrato(ContratoAluguel contrato){

        if(total < 10){
            contratos[total] = contrato;
            total++;
            System.out.println("Contrato cadastrado.");
        }
        else{
            System.out.println("Limite de contratos atingido.");
        }
    }

    public void listarContratosAtivos(){

        System.out.println("\n===== CONTRATOS ATIVOS =====");

        for(int i=0;i<total;i++){

            if(contratos[i].ativo()){
                contratos[i].exibirContrato();
            }

        }
    }

    public void encerrarContrato(int indice){

        if(indice >=0 && indice < total){
            contratos[indice].encerrarContrato();
            System.out.println("Contrato encerrado.");
        }
    }

    public void listarTodos(){

        for(int i=0;i<total;i++){
            System.out.println("Contrato índice " + i);
            contratos[i].exibirContrato();
        }
    }
}

//================ MAIN ==================
public class SistemaImobiliaria {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Inquilino> inquilinos = new ArrayList<>();

    static ArrayList<Imovel> imoveis = new ArrayList<>();

    static Imobiliaria imobiliaria = new Imobiliaria();


//========= CADASTROS ==========
    static void cadastrarInquilino(){

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        System.out.print("Telefone: ");
        String tel = sc.nextLine();

        inquilinos.add(
                new Inquilino(nome,cpf,tel)
        );

        System.out.println("Inquilino cadastrado.");
    }


    static void cadastrarImovel(){

        System.out.println("1-Apartamento");
        System.out.println("2-Casa");

        int tipo = sc.nextInt();
        sc.nextLine();

        System.out.print("Endereço: ");
        String end = sc.nextLine();

        System.out.print("Aluguel: ");
        double aluguel = sc.nextDouble();

        if(tipo==1){

            System.out.print("Andar: ");
            int andar = sc.nextInt();

            imoveis.add(
                    new Apartamento(end,aluguel,andar)
            );
        }

        if(tipo==2){

            System.out.print("Possui quintal? 1-Sim 0-Não: ");
            int q = sc.nextInt();

            imoveis.add(
                    new Casa(end,aluguel,q==1)
            );
        }

        sc.nextLine();

        System.out.println("Imóvel cadastrado.");
    }


//========= CONTRATO ==========
    static void cadastrarContrato(){

        if(inquilinos.isEmpty() || imoveis.isEmpty()){
            System.out.println("Cadastre inquilinos e imóveis primeiro.");
            return;
        }

        System.out.println("\nEscolha inquilino:");

        for(int i=0;i<inquilinos.size();i++){
            System.out.println(i + " - " +
                    inquilinos.get(i).nome);
        }

        int i = sc.nextInt();


        System.out.println("Escolha imóvel:");

        for(int j=0;j<imoveis.size();j++){
            System.out.println(j + " - " +
                    imoveis.get(j).endereco);
        }

        int m = sc.nextInt();

        sc.nextLine();

        System.out.print("Data início (AAAA-MM-DD): ");
        LocalDate inicio=
                LocalDate.parse(sc.nextLine());

        System.out.print("Data fim (AAAA-MM-DD): ");
        LocalDate fim=
                LocalDate.parse(sc.nextLine());


        imobiliaria.adicionarContrato(
                new ContratoAluguel(
                        inquilinos.get(i),
                        imoveis.get(m),
                        inicio,
                        fim,
                        false
                )
        );

    }


//========= DEMONSTRAÇÃO ==========
    static void demonstracao(){

        Inquilino a=
                new Inquilino(
                        "Carlos",
                        "11111111111",
                        "9999-1111");

        Inquilino b=
                new Inquilino(
                        "Marina",
                        "22222222222",
                        "9999-2222");

        Apartamento ap=
                new Apartamento(
                        "Rua Central 100",
                        1200,
                        5);

        Casa casa=
                new Casa(
                        "Rua Verde 90",
                        1800,
                        true);

        ContratoAluguel c1=
                new ContratoAluguel(
                        a,
                        ap,
                        LocalDate.of(2025,1,1),
                        LocalDate.of(2025,12,1),
                        true);

        ContratoAluguel c2=
                new ContratoAluguel(
                        b,
                        casa,
                        LocalDate.of(2026,1,1),
                        LocalDate.of(2026,12,1),
                        false);


        imobiliaria.adicionarContrato(c1);
        imobiliaria.adicionarContrato(c2);

        imobiliaria.listarContratosAtivos();
    }


//========= MAIN ==========
    public static void main(String[] args) {

        int op;

        do{

            System.out.println("\n===== MENU =====");
            System.out.println("1 Cadastrar Inquilino");
            System.out.println("2 Cadastrar Imóvel");
            System.out.println("3 Cadastrar Contrato");
            System.out.println("4 Encerrar Contrato");
            System.out.println("5 Listar Contratos Ativos");
            System.out.println("6 Demonstração");
            System.out.println("7 Sair");

            op = sc.nextInt();
            sc.nextLine();

            switch(op){

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
                    imobiliaria.listarTodos();

                    System.out.print(
                            "Índice contrato encerrar: ");

                    int i=sc.nextInt();

                    imobiliaria.encerrarContrato(i);
                    break;

                case 5:
                    imobiliaria.listarContratosAtivos();
                    break;

                case 6:
                    demonstracao();
                    break;
            }

        }while(op!=7);

    }

}