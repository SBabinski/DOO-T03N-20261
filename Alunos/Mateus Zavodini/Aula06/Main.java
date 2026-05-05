import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        
        System.out.println("Cadastro da Loja");

        System.out.print("Nome Fantasia: ");
        String nomeFantasia = sc.nextLine();

        System.out.print("Razão Social: ");
        String razaoSocial = sc.nextLine();

        System.out.print("CNPJ: ");
        String cnpj = sc.nextLine();

        System.out.print("Cidade: ");
        String cidade = sc.nextLine();

        System.out.print("Bairro: ");
        String bairro = sc.nextLine();

        System.out.print("Rua: ");
        String rua = sc.nextLine();

        Loja loja = new Loja(nomeFantasia, razaoSocial, cnpj, cidade, bairro, rua);

        
        System.out.print("\nQuantos vendedores deseja cadastrar? ");
        int qtdVendedores = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < qtdVendedores; i++) {
            System.out.println("\n--- Vendedor " + (i + 1) + " ---");

            System.out.print("Nome: ");
            String nome = sc.nextLine();

            System.out.print("Idade: ");
            int idade = sc.nextInt();
            sc.nextLine();

            System.out.print("Cidade: ");
            String cidadeV = sc.nextLine();

            System.out.print("Bairro: ");
            String bairroV = sc.nextLine();

            System.out.print("Rua: ");
            String ruaV = sc.nextLine();

            System.out.print("Salário Base: ");
            double salarioBase = sc.nextDouble();
            sc.nextLine();

            Vendedor vendedor = new Vendedor(
                    nome, idade, nomeFantasia,
                    cidadeV, bairroV, ruaV, salarioBase
            );

            loja.adicionarVendedor(vendedor);
        }

      
        System.out.print("\nQuantos clientes deseja cadastrar? ");
        int qtdClientes = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < qtdClientes; i++) {
            System.out.println("\n--- Cliente " + (i + 1) + " ---");

            System.out.print("Nome: ");
            String nome = sc.nextLine();

            System.out.print("Idade: ");
            int idade = sc.nextInt();
            sc.nextLine();

            System.out.print("Cidade: ");
            String cidadeC = sc.nextLine();

            System.out.print("Bairro: ");
            String bairroC = sc.nextLine();

            System.out.print("Rua: ");
            String ruaC = sc.nextLine();

            Cliente cliente = new Cliente(nome, idade, cidadeC, bairroC, ruaC);
            loja.adicionarCliente(cliente);
        }

        
        System.out.println("\n Dados cadastrais da loja");
        loja.apresentarSe();

        System.out.println("\nTotal de clientes: " + loja.contarClientes());
        System.out.println("Total de vendedores: " + loja.contarVendedores());

        System.out.println("\n Vendedores");
        for (Vendedor v : loja.vendedores) {
            v.apresentarSe();
            System.out.println("Média salários: " + v.calcularMedia());
            System.out.println("Bônus: " + v.calcularBonus());
            System.out.println();
        }

        System.out.println("=== CLIENTES ===");
        for (Cliente c : loja.clientes) {
            c.apresentarSe();
        }

        sc.close();
    }
}