import java.util.*;
import java.text.SimpleDateFormat;

class Endereco {
    String estado, cidade, bairro, complemento;
    int numero;

    void apresentarLogradouro() {
        System.out.println(cidade + ", " + bairro + ", " + numero + " - " + complemento + " - " + estado);
    }
}

class Pessoa {
    String nome;
    int idade;
    Endereco endereco;
}

class Vendedor extends Pessoa {
    String loja;
    double salarioBase;
    double[] salarios = {1500, 1600, 1700};

    void apresentarSe() {
        System.out.println(nome + " - " + idade + " - " + loja);
    }

    double calcularMedia() {
        double s = 0;
        for (double v : salarios) s += v;
        return s / salarios.length;
    }

    double calcularBonus() {
        return salarioBase * 0.2;
    }
}

class Gerente extends Pessoa {
    String loja;
    double salarioBase;
    double[] salarios = {3000, 3200, 3500};

    void apresentarSe() {
        System.out.println(nome + " - " + idade + " - " + loja);
    }

    double calcularMedia() {
        double s = 0;
        for (double v : salarios) s += v;
        return s / salarios.length;
    }

    double calcularBonus() {
        return salarioBase * 0.35;
    }
}

class Cliente extends Pessoa {
    void apresentarSe() {
        System.out.println(nome + " - " + idade);
    }
}

class Item {
    int id;
    String nome, tipo;
    double valor;

    void gerarDescricao() {
        System.out.println(id + " " + nome + " " + tipo + " R$" + valor);
    }
}

class Loja {
    String nome;
    String cnpj;
    Endereco endereco;

    List<Cliente> clientes = new ArrayList<>();
    List<Vendedor> vendedores = new ArrayList<>();
    List<Gerente> gerentes = new ArrayList<>();

    void apresentarSe() {
        System.out.println(nome + " - " + cnpj);
        endereco.apresentarLogradouro();
    }
}

// ✅ PEDIDO SIMPLIFICADO COM DATA STRING
class Pedido {
    int id;
    String data;
    Cliente cliente;
    Vendedor vendedor;
    List<Item> itens = new ArrayList<>();

    double total() {
        double t = 0;
        for (Item i : itens) t += i.valor;
        return t;
    }

    void mostrar() {
        System.out.println("Pedido: " + id);
        System.out.println("Data: " + data);
        System.out.println("Cliente: " + cliente.nome);
        System.out.println("Vendedor: " + vendedor.nome);
        System.out.println("Total: R$ " + total());
    }
}

// ✅ PROCESSO COM DATA FORMATADA
class ProcessaPedido {

    Pedido processar(Cliente c, Vendedor v, List<Item> itens) {
        Pedido p = new Pedido();
        p.id = new Random().nextInt(1000);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        p.data = sdf.format(new Date());

        p.cliente = c;
        p.vendedor = v;
        p.itens = itens;

        return p;
    }
}

public class main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Loja loja = new Loja();
        loja.nome = "gabrielinha store";
        loja.cnpj = "12332101112";

        Endereco e = new Endereco();
        e.cidade = "laranjal";
        e.bairro = "Centro";
        e.numero = 10;
        e.estado = "PR";
        e.complemento = "Loja";

        loja.endereco = e;

        int op;

        do {
            System.out.println("1 - Criar pedido");
            System.out.println("2 - Mostrar loja");
            System.out.println("3 - sair");
            op = sc.nextInt();

            if (op == 1) {

                Cliente c = new Cliente();
                c.nome = "Cliente";

                Vendedor v = new Vendedor();
                v.nome = "Vendedor";
                v.loja = loja.nome;

                Item i1 = new Item();
                i1.id = 1;
                i1.nome = "Planta";
                i1.tipo = "Produto";
                i1.valor = 50;

                Item i2 = new Item();
                i2.id = 2;
                i2.nome = "Vaso";
                i2.tipo = "Produto";
                i2.valor = 30;

                List<Item> itens = new ArrayList<>();
                itens.add(i1);
                itens.add(i2);

                ProcessaPedido proc = new ProcessaPedido();
                Pedido p = proc.processar(c, v, itens);

                p.mostrar();
            }

            if (op == 2) {
                loja.apresentarSe();
            }

        } while (op != 3);

        sc.close();
    }
}
