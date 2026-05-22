import java.util.Date;
import java.util.Calendar;

public class TestProcessaPedido {
    public static void main(String[] args) {
        System.out.println("🧪 ==== TESTES DO PROCESSADOR DE PEDIDOS ====\n");

        // Criar endereços
        Endereco enderecoCLiente = new Endereco("SP", "São Paulo", "Pinheiros", "Rua A", 123, "Apt 301");
        Endereco enderecoVendedor = new Endereco("SP", "São Paulo", "Centro", "Avenida Paulista", 1000, "");
        Endereco enderecoGerente = new Endereco("SP", "São Paulo", "Vila Mariana", "Rua B", 456, "");

        // Criar pessoas
        Cliente cliente = new Cliente("João Silva", 35, enderecoCLiente, "joao@email.com", "11987654321");
        Vendedor vendedor = new Vendedor("Maria Santos", 28, enderecoVendedor, "Loja Centro", 2500);
        Gerente gerente = new Gerente("Carlos Oliveira", 45, enderecoGerente, "Loja Centro", 5000);

        // Criar itens
        Item item1 = new Item(1, "Sementes Premium", "Sementes", 45.50);
        Item item2 = new Item(2, "Adubo Orgânico", "Adubo", 120.00);
        Item item3 = new Item(3, "Vaso Cerâmico", "Acessório", 85.75);

        System.out.println("--- TESTE 1: Pedido com Reserva Válida ---");
        // Criar datas para teste 1
        Date dataCriacao1 = new Date();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(dataCriacao1);
        calendar1.add(Calendar.DAY_OF_MONTH, 5); // Vencimento em 5 dias
        Date dataVencimento1 = calendar1.getTime();

        ProcessaPedido processador1 = new ProcessaPedido();
        Item[] itens1 = {item1, item2};
        Pedido pedido1 = processador1.processar(1, dataCriacao1, dataVencimento1, 
                                                cliente, vendedor, gerente, "Loja Centro", itens1);

        if (pedido1 != null) {
            pedido1.gerarDescricaoVenda();
            System.out.println("💰 Valor Total: R$ " + String.format("%.2f", pedido1.calcularValorTotal()));
        }

        System.out.println("\n--- TESTE 2: Pedido com Reserva Vencida ---");
        // Criar datas para teste 2
        Date dataCriacao2 = new Date();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(dataCriacao2);
        calendar2.add(Calendar.DAY_OF_MONTH, -2); // Vencimento há 2 dias
        Date dataVencimento2 = calendar2.getTime();

        ProcessaPedido processador2 = new ProcessaPedido();
        Item[] itens2 = {item3};
        Pedido pedido2 = processador2.processar(2, dataCriacao2, dataVencimento2, 
                                                cliente, vendedor, gerente, "Loja Centro", itens2);

        if (pedido2 == null) {
            System.out.println("Pedido não foi processado - reserva expirada.");
        }

        System.out.println("\n--- TESTE 3: Informações dos Gerentes ---");
        System.out.println("Salários Recebidos: ");
        for (double salario : gerente.getSalarioRecebido()) {
            System.out.println("  R$ " + String.format("%.2f", salario));
        }
        System.out.println("Média de Salários: R$ " + String.format("%.2f", gerente.calcularMedia()));
        System.out.println("Bônus (35% do salário base): R$ " + String.format("%.2f", gerente.calcularBonus()));

        System.out.println("\n✅ Testes concluídos!");
    }
}
