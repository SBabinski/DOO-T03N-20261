import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    void mostrar() {
        System.out.println(nome + " - " + cpf + " - " + telefone);
    }
}

abstract class Imovel {
    String endereco;
    double valor;

    public Imovel(String endereco, double valor) {
        this.endereco = endereco;
        this.valor = valor;
    }

    abstract void mostrar();
}

class Apartamento extends Imovel {
    int andar;

    public Apartamento(String endereco, double valor, int andar) {
        super(endereco, valor);
        this.andar = andar;
    }

    void mostrar() {
        System.out.println("Apartamento: " + endereco + " - " + valor + " - Andar " + andar);
    }
}

class Casa extends Imovel {
    boolean quintal;

    public Casa(String endereco, double valor, boolean quintal) {
        super(endereco, valor);
        this.quintal = quintal;
    }

    void mostrar() {
        System.out.println("Casa: " + endereco + " - " + valor + " - Quintal " + quintal);
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

    void encerrar() {
        encerrado = true;
    }

    boolean isEncerrado() {
        return encerrado;
    }

    void mostrar() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        inquilino.mostrar();
        imovel.mostrar();

        System.out.println("Inicio: " + inicio.format(fmt));
        System.out.println("Fim: " + fim.format(fmt));
        System.out.println("Encerrado: " + encerrado);
        System.out.println("----------------");
    }
}

class Imobiliaria {
    Contrato[] contratos = new Contrato[10];
    int qtd = 0;

    void adicionar(Contrato c) {
        if (qtd < 10) {
            contratos[qtd] = c;
            qtd++;
        }
    }

    void listarAtivos() {
        for (int i = 0; i < qtd; i++) {
            if (!contratos[i].isEncerrado()) {
                contratos[i].mostrar();
            }
        }
    }

    Contrato get(int i) {
        if (i >= 0 && i < qtd) {
            return contratos[i];
        }
        return null;
    }
}

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Imobiliaria imo = new Imobiliaria();

        int op = 0;

        do {
            System.out.println("1 - Demo");
            System.out.println("2 - Ativos");
            System.out.println("3 - Encerrar");
            System.out.println("0 - Sair");

            op = sc.nextInt();

            switch (op) {

                case 1:
                    Inquilino i1 = new Inquilino("Pedro", "111", "999");
                    Inquilino i2 = new Inquilino("Ana", "222", "888");

                    Apartamento a = new Apartamento("Rua A", 1000, 5);
                    Casa c = new Casa("Rua B", 2000, true);

                    Contrato c1 = new Contrato(
                            i1,
                            a,
                            LocalDate.of(2026, 1, 1),
                            LocalDate.of(2026, 6, 1)
                    );

                    Contrato c2 = new Contrato(
                            i2,
                            c,
                            LocalDate.of(2026, 2, 1),
                            LocalDate.of(2026, 8, 1)
                    );

                    c1.encerrar();

                    imo.adicionar(c1);
                    imo.adicionar(c2);

                    System.out.println("Demo pronta");
                    break;

                case 2:
                    imo.listarAtivos();
                    break;

                case 3:
                    System.out.println("Indice:");
                    int id = sc.nextInt();

                    Contrato con = imo.get(id);

                    if (con != null) {
                        con.encerrar();
                        System.out.println("Encerrado");
                    } else {
                        System.out.println("Nao encontrado");
                    }
                    break;
            }

        } while (op != 0);

        sc.close();
    }
}
