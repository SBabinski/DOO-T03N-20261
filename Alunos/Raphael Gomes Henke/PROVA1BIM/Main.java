import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Inquilino {
    String nome;
    String cpf;
    String telefone;

    public Inquilino(String nome, String cpf, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public void exibir() {
        System.out.println("Nome: " + nome);
        System.out.println("CPF: " + cpf);
        System.out.println("Telefone: " + telefone);
    }
}

abstract class Imovel {
    String endereco;
    double valorMensal;

    public Imovel(String endereco, double valorMensal) {
        this.endereco = endereco;
        this.valorMensal = valorMensal;
    }

    public abstract void exibir();
}

class Apartamento extends Imovel {
    int andar;

    public Apartamento(String endereco, double valorMensal, int andar) {
        super(endereco, valorMensal);
        this.andar = andar;
    }

    public void exibir() {
        System.out.println("Apartamento");
        System.out.println("Endereço: " + endereco);
        System.out.println("Valor mensal: " + valorMensal);
        System.out.println("Andar: " + andar);
    }
}

class Casa extends Imovel {
    boolean temQuintal;

    public Casa(String endereco, double valorMensal, boolean temQuintal) {
        super(endereco, valorMensal);
        this.temQuintal = temQuintal;
    }

    public void exibir() {
        System.out.println("Casa");
        System.out.println("Endereço: " + endereco);
        System.out.println("Valor mensal: " + valorMensal);
        System.out.println("Tem quintal: " + (temQuintal ? "Sim" : "Não"));
    }
}

class Contrato {
    Inquilino inquilino;
    Imovel imovel;
    LocalDate inicio;
    LocalDate fim;
    boolean encerrado;

    public Contrato(Inquilino inquilino, Imovel imovel, LocalDate inicio, LocalDate fim) {
        this.inquilino = inquilino;
        this.imovel = imovel;
        this.inicio = inicio;
        this.fim = fim;
        this.encerrado = false;
    }

    public long calcularMeses() {
        return ChronoUnit.MONTHS.between(inicio, fim);
    }

    public double valorTotal() {
        return calcularMeses() * imovel.valorMensal;
    }

    public void encerrar() {
        encerrado = true;
    }

    public void exibir() {
        inquilino.exibir();
        imovel.exibir();
        System.out.println("inicio: " + inicio);
        System.out.println("Fim: " + fim);
        System.out.println("Encerrado: " + encerrado);
        System.out.println("Valor total: " + valorTotal());
        System.out.println("------------------------");
    }
}

class Imobiliaria {
    List<Contrato> contratos = new ArrayList<>();
    int LIMITE = 10;

    public void adicionarContrato(Contrato c) {
        if (contratos.size() < LIMITE) {
            contratos.add(c);
        } else {
            System.out.println("Limite de contratos atingido");
        }
    }

    public void listarAtivos() {
        for (Contrato c : contratos) {
            if (!c.encerrado) {
                c.exibir();
            }
        }
    }
}

public class Main {
    static Scanner sc = new Scanner(System.in);
    static List<Inquilino> inquilinos = new ArrayList<>();
    static List<Imovel> imoveis = new ArrayList<>();
    static Imobiliaria imobiliaria = new Imobiliaria();

    public static void main(String[] args) {
        int op;
        do {
            System.out.println("1 - Cadastrar Inquilino");
            System.out.println("2 - Cadastrar imovel");
            System.out.println("3 - Cadastrar Contrato");
            System.out.println("4 - Encerrar Contrato");
            System.out.println("5 - Listar Contratos Ativos");
            System.out.println("0 - Sair");
            op = sc.nextInt();

            switch (op) {
                case 1 -> cadastrarInquilino();
                case 2 -> cadastrarImovel();
                case 3 -> cadastrarContrato();
                case 4 -> encerrarContrato();
                case 5 -> imobiliaria.listarAtivos();
            }
        } while (op != 0);
    }

    static void cadastrarInquilino() {
        sc.nextLine();
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        System.out.print("Telefone: ");
        String tel = sc.nextLine();
        inquilinos.add(new Inquilino(nome, cpf, tel));
    }

    static void cadastrarImovel() {
        System.out.println("1 - Apartamento | 2 - Casa");
        int tipo = sc.nextInt();
        sc.nextLine();

        System.out.print("Endereço: ");
        String end = sc.nextLine();
        System.out.print("Valor mensal: ");
        double val = sc.nextDouble();

        if (tipo == 1) {
            System.out.print("Andar: ");
            int andar = sc.nextInt();
            imoveis.add(new Apartamento(end, val, andar));
        } else {
            System.out.print("Tem quintal (true/false): ");
            boolean q = sc.nextBoolean();
            imoveis.add(new Casa(end, val, q));
        }
    }

    static void cadastrarContrato() {
        if (inquilinos.isEmpty() || imoveis.isEmpty()) {
            System.out.println("Cadastre inquilinos e imóveis primeiro");
            return;
        }

        System.out.println("Escolha inquilino:");
        for (int i = 0; i < inquilinos.size(); i++) {
            System.out.println(i + " - " + inquilinos.get(i).nome);
        }
        int i = sc.nextInt();

        System.out.println("Escolha imóvel:");
        for (int j = 0; j < imoveis.size(); j++) {
            System.out.println(j + " - " + imoveis.get(j).endereco);
        }
        int j = sc.nextInt();

        System.out.print("Ano inicio: ");
        int ai = sc.nextInt();
        System.out.print("mes inicio: ");
        int mi = sc.nextInt();

        System.out.print("Ano fim: ");
        int af = sc.nextInt();
        System.out.print("mes fim: ");
        int mf = sc.nextInt();

        Contrato c = new Contrato(
                inquilinos.get(i),
                imoveis.get(j),
                LocalDate.of(ai, mi, 1),
                LocalDate.of(af, mf, 1)
        );

        imobiliaria.adicionarContrato(c);
    }

    static void encerrarContrato() {
        for (int i = 0; i < imobiliaria.contratos.size(); i++) {
            System.out.println(i + " - Contrato de " + imobiliaria.contratos.get(i).inquilino.nome);
        }
        int i = sc.nextInt();
        imobiliaria.contratos.get(i).encerrar();
    }
}