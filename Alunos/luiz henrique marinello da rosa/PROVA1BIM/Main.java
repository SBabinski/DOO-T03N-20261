import java.util.Scanner;

//LEMBRAR DE DAR BEAUTFY NO FIM DO CODIGO PARA DEIXAR TUDO ORGANIZADO E BEM ESTRUTURADO
//LEMBRAR DE JOGAR NO MANUS PARA CORRIGIR A GRAMATICA DOS PRINTS E DA DEMONSTRAÇÃO
// conferir se todos os itens da lista de requisitos estão implementados e funcionando corretamente
//conferir se o manus nao mexeu em alguma coisa fora a gramatica que aparece no console


class Inquilino {

 
    private String nome;
    private String cpf;
    private String telefone;


    public Inquilino(String nome, String cpf, String telefone) {
        this.nome     = nome;
        this.cpf      = cpf;
        this.telefone = telefone;
    }


    public String getNome()     { return nome; }
    public String getCpf()      { return cpf; }
    public String getTelefone() { return telefone; }

    
    public void setNome(String novoNome)         { this.nome     = novoNome; }
    public void setCpf(String novoCpf)           { this.cpf      = novoCpf; }
    public void setTelefone(String novoTelefone) { this.telefone = novoTelefone; }

    
    public String toString() {
        return "Inquilino: " + nome + " | CPF: " + cpf + " | Tel: " + telefone;
    }
}


// Classe pai que Apartamento e Casa herdam

abstract class Imovel {

    private String endereco;
    private double aluguelMensal;

    public Imovel(String endereco, double aluguelMensal) {
        this.endereco      = endereco;
        this.aluguelMensal = aluguelMensal;
    }

    // ── Getters 
    public String getEndereco()      { return endereco; }
    public double getAluguelMensal() { return aluguelMensal; }

    // ── Setters 
    public void setEndereco(String novoEndereco)        { this.endereco      = novoEndereco; }
    public void setAluguelMensal(double novoAluguel)    { this.aluguelMensal = novoAluguel; }


    public abstract void exibirInformacoes();
}


class Apartamento extends Imovel {

    private int andar;

    public Apartamento(String endereco, double aluguelMensal, int andar) {
        super(endereco, aluguelMensal); // chama o construtor de Imovel
        this.andar = andar;
    }

    public int getAndar()              { return andar; }
    public void setAndar(int novoAndar){ this.andar = novoAndar; }


    public void exibirInformacoes() {
        System.out.println("[Apartamento]");
        System.out.println("  Endereço : " + getEndereco());
        System.out.println("  Andar    : " + andar);
        System.out.printf ("  Aluguel  : R$ %.2f%n", getAluguelMensal());
    }
}


class Casa extends Imovel {

    private boolean temQuintal;

    public Casa(String endereco, double aluguelMensal, boolean temQuintal) {
        super(endereco, aluguelMensal); // chama o construtor de Imovel
        this.temQuintal = temQuintal;
    }

    public boolean isTemQuintal()                  { return temQuintal; }
    public void setTemQuintal(boolean temQuintal)  { this.temQuintal = temQuintal; }

    // Implementação do método abstrato
    public void exibirInformacoes() {
        System.out.println("[Casa]");
        System.out.println("  Endereço : " + getEndereco());

        // Se temQuintal for true, mostra "Sim", senão "Não"
        if (temQuintal) {
            System.out.println("  Quintal  : Sim");
        } else {
            System.out.println("  Quintal  : Não");
        }

        System.out.printf("  Aluguel  : R$ %.2f%n", getAluguelMensal());
    }
}

// aq ele tem que ligar sempre um inquilino a um imóvel, e tem data de início e fim do contrato
class Contrato {

    private Inquilino inquilino;
    private Imovel    imovel;
    private String    dataInicio;
    private String    dataFim;
    private boolean   encerrado;

    public Contrato(Inquilino inquilino, Imovel imovel, String dataInicio, String dataFim, boolean encerrado) {
        this.inquilino  = inquilino;
        this.imovel     = imovel;
        this.dataInicio = dataInicio;
        this.dataFim    = dataFim;
        this.encerrado  = encerrado;
    }

    public boolean isEncerrado() {
        return encerrado;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }


    public void encerrar() {
        this.encerrado = true;
        System.out.println("Contrato de " + inquilino.getNome() + " encerrado com sucesso.");
    }


    private int calcularMeses() {

        String[] partesInicio = dataInicio.split("/");
        String[] partesFim    = dataFim.split("/");


        int mesInicio = Integer.parseInt(partesInicio[1]);
        int anoInicio = Integer.parseInt(partesInicio[2]);
        int mesFim    = Integer.parseInt(partesFim[1]);
        int anoFim    = Integer.parseInt(partesFim[2]);

        
        int totalMeses = (anoFim - anoInicio) * 12 + (mesFim - mesInicio);
        return totalMeses;
    }

    public void exibirDados() {
        System.out.println("========== CONTRATO ==========");
        System.out.println(inquilino);
        imovel.exibirInformacoes();
        System.out.println("  Início   : " + dataInicio + " | Fim: " + dataFim);

        if (encerrado) {
            System.out.println("  Situação : Encerrado");
        } else {
            System.out.println("  Situação : Ativo");
        }

        int    meses        = calcularMeses();
        double valorTotal   = meses * imovel.getAluguelMensal();
        System.out.printf("  Duração  : %d mês(es) | Total: R$ %.2f%n", meses, valorTotal);
        System.out.println("==============================");
    }
}


class Imobiliaria {

    // LEMBRAR DE DAR UM BEAUTFY AQUI PQ TA DIFICIL DE VER
    static final int LIMITE = 10;

    private Contrato[] contratos    = new Contrato[LIMITE];
    private int totalContratos      = 0;

    
    public boolean adicionarContrato(Contrato novoContrato) {
        if (totalContratos >= LIMITE) {
            System.out.println("Limite de contratos atingido.");
            return false;
        }
        contratos[totalContratos] = novoContrato;
        totalContratos++;
        System.out.println("Contrato registrado com sucesso!");
        return true;
    }

    
    public void listarContratosAtivos() {
        System.out.println("\n===== CONTRATOS ATIVOS =====");
        boolean encontrouAlgum = false;

        for (int i = 0; i < totalContratos; i++) {
            if (contratos[i].isEncerrado() == false) {
                contratos[i].exibirDados();
                encontrouAlgum = true;
            }
        }

        if (encontrouAlgum == false) {
            System.out.println("Nenhum contrato ativo no momento.");
        }

        System.out.println("============================\n");
    }

    public Contrato[] getContratos() {
        return contratos;
    }

    public int getTotalContratos() {
        return totalContratos;
    }
}


public class Main {

    // Scanner para ler oq o user fala
    static Scanner leitor = new Scanner(System.in);

    
    static Imobiliaria imobiliaria = new Imobiliaria();


    static Inquilino[] listaInquilinos = new Inquilino[20];
    static int totalInquilinos         = 0;

    static Imovel[] listaImoveis = new Imovel[20];
    static int totalImoveis      = 0;

    public static void main(String[] args) {
        int opcaoEscolhida;

        
        do {
            exibirMenu();
            opcaoEscolhida = lerInteiro();

            switch (opcaoEscolhida) {
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
                    demonstracao();
                    break;
                case 0:
                    System.out.println("Encerrando. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }

        } while (opcaoEscolhida != 0);
    }


    static void exibirMenu() {
        System.out.println("\n====== IMOBILIÁRIA ======");
        System.out.println("1 - Cadastrar Inquilino");
        System.out.println("2 - Cadastrar Imóvel");
        System.out.println("3 - Cadastrar Contrato");
        System.out.println("4 - Encerrar Contrato");
        System.out.println("5 - Listar Contratos Ativos");
        System.out.println("6 - Demonstração");
        System.out.println("0 - Sair");
        System.out.print("Escolha: ");
    }

  
    static void cadastrarInquilino() {
        
        if (totalInquilinos >= 20) {
            System.out.println("Limite de inquilinos atingido (máximo 20).");
            return;
        }

        System.out.print("Nome: ");
        String nome = leitor.nextLine();

        System.out.print("CPF: ");
        String cpf = leitor.nextLine();

        System.out.print("Telefone: ");
        String telefone = leitor.nextLine();

        listaInquilinos[totalInquilinos] = new Inquilino(nome, cpf, telefone);
        totalInquilinos++;

        System.out.println("Inquilino cadastrado!");
    }


    static void cadastrarImovel() {
  
        if (totalImoveis >= 20) {
            System.out.println("Limite de imóveis atingido (máximo 20).");
            return;
        }

        System.out.println("Tipo: 1-Apartamento  2-Casa");
        int tipo = lerInteiro();

        System.out.print("Endereço: ");
        String endereco = leitor.nextLine();

        System.out.print("Aluguel mensal (R$): ");
        double aluguel = lerDecimal();

        if (tipo == 1) {
            System.out.print("Andar: ");
            int andar = lerInteiro();
            listaImoveis[totalImoveis] = new Apartamento(endereco, aluguel, andar);
            totalImoveis++;

        } else if (tipo == 2) {
            System.out.print("Tem quintal? (1-Sim / 2-Não): ");
            boolean temQuintal = (lerInteiro() == 1); 
            listaImoveis[totalImoveis] = new Casa(endereco, aluguel, temQuintal);
            totalImoveis++;

        } else {
            System.out.println("Tipo inválido.");
            return; 

        System.out.println("Imóvel cadastrado!");
    }


    static void cadastrarContrato() {
        if (totalInquilinos == 0 || totalImoveis == 0) {
            System.out.println("Cadastre ao menos um inquilino e um imóvel antes.");
            return;
        }

      
        System.out.println("--- Inquilinos ---");
        for (int i = 0; i < totalInquilinos; i++) {
            System.out.println((i + 1) + " - " + listaInquilinos[i]);
        }
        System.out.print("Escolha o inquilino: ");
        int indiceInquilino = lerInteiro() - 1; // -1 porque o array começa em 0

        System.out.println("--- Imóveis ---");
        for (int i = 0; i < totalImoveis; i++) {
            System.out.print((i + 1) + " - ");
            listaImoveis[i].exibirInformacoes();
        }
        System.out.print("Escolha o imóvel: ");
        int indiceImovel = lerInteiro() - 1;

       
        if (indiceInquilino < 0 || indiceInquilino >= totalInquilinos ||
            indiceImovel    < 0 || indiceImovel    >= totalImoveis) {
            System.out.println("Seleção inválida.");
            return;
        }

        System.out.print("Data início (dd/MM/yyyy): ");
        String dataInicio = lerData();

        System.out.print("Data fim   (dd/MM/yyyy): ");
        String dataFim = lerData();

        System.out.print("Contrato já encerrado? (1-Sim / 2-Não): ");
        boolean jaEncerrado = (lerInteiro() == 1);

  
        Contrato novoContrato = new Contrato(
            listaInquilinos[indiceInquilino],
            listaImoveis[indiceImovel],
            dataInicio,
            dataFim,
            jaEncerrado
        );
        imobiliaria.adicionarContrato(novoContrato);
    }

    
    static void encerrarContrato() {
        int total = imobiliaria.getTotalContratos();

        if (total == 0) {
            System.out.println("Nenhum contrato registrado.");
            return;
        }

        Contrato[] lista = imobiliaria.getContratos();

      
        System.out.println("--- Contratos ---");
        for (int i = 0; i < total; i++) {
            String status;
            if (lista[i].isEncerrado()) {
                status = "Encerrado";
            } else {
                status = "Ativo";
            }
            System.out.println((i + 1) + " - " + lista[i].getInquilino().getNome() + " [" + status + "]");
        }

        System.out.print("Número do contrato a encerrar: ");
        int indice = lerInteiro() - 1;

        if (indice < 0 || indice >= total) {
            System.out.println("Número inválido.");
            return;
        }

        if (lista[indice].isEncerrado()) {
            System.out.println("Contrato já encerrado.");
            return;
        }

        lista[indice].encerrar();
    }

 
    static void demonstracao() {
        System.out.println("\n========== DEMONSTRAÇÃO ==========");


        Inquilino inquilino1 = new Inquilino("Ana Souza",  "111.111.111-11", "(11) 91111-1111");
        Inquilino inquilino2 = new Inquilino("Bruno Lima", "222.222.222-22", "(11) 92222-2222");

       
        Apartamento apartamento = new Apartamento("Av. Paulista, 100 - Apto 52", 1800.00, 5);
        Casa        casa        = new Casa("Rua das Flores, 200", 2200.00, true);

        System.out.println("\n-- Imóveis --");
        apartamento.exibirInformacoes();
        casa.exibirInformacoes();

       
        Contrato contrato1 = new Contrato(inquilino1, apartamento, "01/01/2024", "01/07/2024", true);
        Contrato contrato2 = new Contrato(inquilino2, casa,        "01/03/2024", "01/03/2025", false);

        imobiliaria.adicionarContrato(contrato1);
        imobiliaria.adicionarContrato(contrato2);

        System.out.println("\n-- Dados dos contratos --");
        contrato1.exibirDados();
        contrato2.exibirDados();


        imobiliaria.listarContratosAtivos();
    }

 
    static String lerData() {
        while (true) {
            String data = leitor.nextLine().trim();

            if (data.matches("\\d{2}/\\d{2}/\\d{4}")) {
                return data;
            }

            System.out.print("Formato inválido. Digite novamente (dd/MM/yyyy): ");
        }
    }


    static int lerInteiro() {
        try {
            return Integer.parseInt(leitor.nextLine().trim());
        } catch (NumberFormatException erro) {
   
            System.out.println("Valor inválido.");
            return 0;
        }
    }

    // Le número decimal (aceita vírgula ou ponto)
    static double lerDecimal() {
        try {
            String texto = leitor.nextLine().trim();
            texto = texto.replace(",", "."); // troca vírgula por ponto para o Java entender
            return Double.parseDouble(texto);
        } catch (NumberFormatException erro) {
            System.out.println("Valor inválido.");
            return 0.0;
        }
    }
}