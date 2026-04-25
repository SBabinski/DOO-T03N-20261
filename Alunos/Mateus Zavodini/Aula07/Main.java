import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int op = -1;

        while (op != 0) {
            System.out.println("\n=== MENU MY PLANT ===");
            System.out.println("1 - Apresentar Gerente");
            System.out.println("2 - Apresentar Vendedor");
            System.out.println("3 - Apresentar Cliente");
            System.out.println("4 - Criar Pedido (dados fake)");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            op = sc.nextInt();
            sc.nextLine();

            if (op == 1) {

                Endereco endGerente = new Endereco("SP", "São Paulo", "Centro", "das Flores", 100, "Apto 10");
                Gerente gerente = new Gerente("Carlos", 40, "Loja Centro", endGerente, 5000);
                gerente.apresentarse();
                System.out.println("Média salarial: R$ " + gerente.calcularMedia());
                System.out.println("Bônus: R$ " + gerente.calcularBonus());

            } else if (op == 2) {

                Endereco endVendedor = new Endereco("SP", "Campinas", "Jardim", "das Rosas", 200, "Casa");
                Vendedor vendedor = new Vendedor("Ana", 28, "222.222.222-22", 3000, endVendedor);
                vendedor.apresentarse();

            } else if (op == 3) {

                Endereco endCliente = new Endereco("RJ", "Rio de Janeiro", "Copacabana", "Atlântica", 500, "Bloco B");
                Cliente cliente = new Cliente("João", 35, "111.111.111-11", endCliente);
                cliente.apresentarse();

            } else if (op == 4) {

                // Dados fake para o pedido
                Endereco endV = new Endereco("SP", "São Paulo", "Centro", "das Flores", 100, "Sala 1");
                Vendedor vendedor = new Vendedor("Ana", 28, "222.222.222-22", 3000, endV);

                Endereco endC = new Endereco("SP", "São Paulo", "Vila Nova", "das Orquídeas", 300, "Casa");
                Cliente cliente = new Cliente("Pedro", 30, "333.333.333-33", endC);

                Item[] itens = {
                    new Item(1, "Samambaia", "Planta", 45.90),
                    new Item(2, "Vaso Cerâmica", "Acessório", 89.90),
                    new Item(3, "Adubo Premium", "Insumo", 32.50)
                };

                Date agora = new Date();
                Date vencimento = new Date(agora.getTime() + 86400000L); // +1 dia

                ProcessaPedido pp = new ProcessaPedido();
                Pedido pedido = pp.processar(1, agora, agora, vencimento, cliente, vendedor, "Loja Centro", itens);
                pedido.gerarDescricaoVenda();
            }
        }

        sc.close();
    }
}