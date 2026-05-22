import java.util.Date;
import java.util.Calendar;

/**
 * Exemplos de uso das classes do sistema My Plant
 */
public class ExemplosUso {

    public static void main(String[] args) {
        System.out.println("🌱 ===== EXEMPLOS DE USO MY PLANT =====\n");

        // EXEMPLO 1: Criar Endereço
        System.out.println("--- EXEMPLO 1: Criando um Endereço ---");
        Endereco enderecoSP = new Endereco("São Paulo", "São Paulo", "Vila Mariana", 
                                           "Rua das Plantas", 123, "Apto 405");
        enderecoSP.apresentarLogradouro();

        // EXEMPLO 2: Criar Gerente
        System.out.println("\n--- EXEMPLO 2: Criando um Gerente ---");
        Gerente gerente = new Gerente("Roberto Silva", 50, enderecoSP, "Loja Paulista", 7000);
        gerente.adicionarSalario(7200);
        gerente.adicionarSalario(6800);
        
        gerente.apresentarse();
        System.out.println("💰 Média Salarial: R$ " + String.format("%.2f", gerente.calcularMedia()));
        System.out.println("🎁 Bônus (35%): R$ " + String.format("%.2f", gerente.calcularBonus()));

        // EXEMPLO 3: Criar Cliente
        System.out.println("\n--- EXEMPLO 3: Criando um Cliente ---");
        Endereco enderecoCliente = new Endereco("SP", "São Paulo", "Pinheiros", 
                                               "Avenida Faria Lima", 1500, "Sala 200");
        Cliente cliente = new Cliente("Fernanda Costa", 38, enderecoCliente, 
                                      "fernanda@email.com", "11991234567");
        cliente.apresentarse();

        // EXEMPLO 4: Criar Vendedor
        System.out.println("\n--- EXEMPLO 4: Criando um Vendedor ---");
        Endereco enderecoVendedor = new Endereco("SP", "São Paulo", "Centro", 
                                                "Rua 25 de Março", 500, "");
        Vendedor vendedor = new Vendedor("Lucas Oliveira", 28, enderecoVendedor, 
                                        "Loja Centro", 3500);
        vendedor.adicionarComissao(500);
        vendedor.adicionarComissao(750);
        vendedor.adicionarComissao(600);
        
        vendedor.apresentarse();
        System.out.println("📊 Média de Comissões: R$ " + 
                          String.format("%.2f", vendedor.calcularMedia()));

        // EXEMPLO 5: Criar Itens
        System.out.println("\n--- EXEMPLO 5: Criando Itens ---");
        Item item1 = new Item(1, "Orquídea Branca", "Planta", 250.00);
        Item item2 = new Item(2, "Adubo Esterlina", "Insumo", 45.50);
        Item item3 = new Item(3, "Vaso Decorativo", "Acessório", 89.90);
        
        item1.gerarDescricao();
        item2.gerarDescricao();
        item3.gerarDescricao();

        // EXEMPLO 6: Criar Pedido
        System.out.println("\n--- EXEMPLO 6: Criando um Pedido ---");
        Date dataCriacao = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataCriacao);
        calendar.add(Calendar.DAY_OF_MONTH, 10);
        Date dataVencimento = calendar.getTime();

        Item[] itens = {item1, item2};
        ProcessaPedido processador = new ProcessaPedido();
        Pedido pedido = processador.processar(1001, dataCriacao, dataVencimento,
                                              cliente, vendedor, gerente, 
                                              "Loja Paulista", itens);

        if (pedido != null) {
            pedido.gerarDescricaoVenda();
            
            // EXEMPLO 7: Adicionar mais itens
            System.out.println("--- EXEMPLO 7: Adicionando mais itens ao pedido ---");
            pedido.adicionarItem(item3);
            System.out.println("✅ Item adicionado!");
            
            // EXEMPLO 8: Calcular valor total atualizado
            System.out.println("\n--- EXEMPLO 8: Novo Valor Total ---");
            System.out.println("💰 Valor Total Atualizado: R$ " + 
                              String.format("%.2f", pedido.calcularValorTotal()));
        }

        System.out.println("\n✅ Todos os exemplos foram executados com sucesso!");
    }
}
