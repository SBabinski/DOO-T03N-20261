import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Vendedor {
    String nome;
    int idade;
    String loja;
    double salarioBase;
    double[] salarioRecebido = {1500, 1600, 1700};

    void apresentarSe() {
        System.out.println("Nome: " + nome + ", Idade: " + idade + ", Loja: " + loja);
    }

    double calcularMedia() {
        double soma = 0;
        for (int i = 0; i < salarioRecebido.length; i++) {
            soma += salarioRecebido[i];
        }
        return soma / salarioRecebido.length;
    }

    double calcularBonus() {
        return salarioBase * 0.2;
    }
}

class Cliente {
    String nome;
    int idade;

    void apresentarSe() {
        System.out.println("Nome: " + nome + ", Idade: " + idade);
    }
}

class Loja {
    String nomeFantasia;
    String cnpj;
    String cidade;
    String bairro;
    String rua;

    List<Vendedor> vendedores = new ArrayList<>();
    List<Cliente> clientes = new ArrayList<>();

    int contarClientes() {
        return clientes.size();
    }

    int contarVendedores() {
        return vendedores.size();
    }

    void apresentarSe() {
        System.out.println("Loja: " + nomeFantasia);
        System.out.println("CNPJ: " + cnpj);
        System.out.println("Endereco: " + cidade + ", " + bairro + ", " + rua);
    }
}

class Venda {
    int quantidade;
    double valorVenda;
    LocalDate data;

    Venda(int quantidade, double valorVenda, LocalDate data) {
        this.quantidade = quantidade;
        this.valorVenda = valorVenda;
        this.data = data;
    }

    public String toString() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Data: " + data.format(f) +
               " | Quantidade: " + quantidade +
               ", Valor: R$ " + String.format("%.2f", valorVenda);
    }
}

public class main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int opcao;

        Loja loja = new Loja();
        loja.nomeFantasia = "My Plant";
        loja.cnpj = "123456789";
        loja.cidade = "Cidade";
        loja.bairro = "Centro";
        loja.rua = "Rua A";

        List<Venda> vendas = new ArrayList<>();
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        do {
            System.out.println("1 - Registrar venda");
            System.out.println("2 - Mostrar vendas");
            System.out.println("3 - Total por DIA");
            System.out.println("4 - Total por MES");
            System.out.println("5 - Cadastrar vendedor");
            System.out.println("6 - Cadastrar cliente");
            System.out.println("7 - Mostrar vendedores");
            System.out.println("8 - Mostrar clientes");
            System.out.println("9 - Dados da loja");
            System.out.println("10 - sair");
            System.out.print("Escolha: ");

            opcao = sc.nextInt();
            sc.nextLine();

            if (opcao == 1) {
                System.out.print("Quantidade: ");
                int quantidade = sc.nextInt();

                System.out.print("Preco unitario: ");
                double preco = sc.nextDouble();
                sc.nextLine();

                System.out.print("Data (dd/MM/yyyy): ");
                LocalDate data = LocalDate.parse(sc.nextLine(), f);

                double total = quantidade * preco;

                if (quantidade > 10) {
                    total = total * 0.95;
                }

                vendas.add(new Venda(quantidade, total, data));
            } 
            else if (opcao == 2) {
                for (Venda v : vendas) {
                    System.out.println(v);
                }
            } 
            else if (opcao == 3) {
                System.out.print("Data (dd/MM/yyyy): ");
                LocalDate data = LocalDate.parse(sc.nextLine(), f);

                double total = 0;

                for (Venda v : vendas) {
                    if (v.data.equals(data)) {
                        total += v.valorVenda;
                    }
                }

                System.out.println("Total: R$ " + total);
            } 
            else if (opcao == 4) {
                System.out.print("Mes: ");
                int mes = sc.nextInt();

                System.out.print("Ano: ");
                int ano = sc.nextInt();

                double total = 0;

                for (Venda v : vendas) {
                    if (v.data.getMonthValue() == mes && v.data.getYear() == ano) {
                        total += v.valorVenda;
                    }
                }

                System.out.println("Total: R$ " + total);
            } 
            else if (opcao == 5) {
                Vendedor v = new Vendedor();

                System.out.print("Nome: ");
                v.nome = sc.nextLine();

                System.out.print("Idade: ");
                v.idade = sc.nextInt();
                sc.nextLine();

                v.loja = loja.nomeFantasia;

                System.out.print("Salario base: ");
                v.salarioBase = sc.nextDouble();
                sc.nextLine();

                loja.vendedores.add(v);
            } 
            else if (opcao == 6) {
                Cliente c = new Cliente();

                System.out.print("Nome: ");
                c.nome = sc.nextLine();

                System.out.print("Idade: ");
                c.idade = sc.nextInt();
                sc.nextLine();

                loja.clientes.add(c);
            } 
            else if (opcao == 7) {
                for (Vendedor v : loja.vendedores) {
                    v.apresentarSe();
                    System.out.println("Media: " + v.calcularMedia());
                    System.out.println("Bonus: " + v.calcularBonus());
                }
            } 
            else if (opcao == 8) {
                for (Cliente c : loja.clientes) {
                    c.apresentarSe();
                }
            } 
            else if (opcao == 9) {
                loja.apresentarSe();
                System.out.println("Clientes: " + loja.contarClientes());
                System.out.println("Vendedores: " + loja.contarVendedores());
            }

        } while (opcao != 10);

        System.out.println("Encerrado.");
        sc.close();
    }
}