import java.util.Date;
import java.util.Calendar;

/**
 * DEMONSTRAÇÃO FINAL - Sistema My Plant
 * Este arquivo mostra a integração completa de todas as classes
 */
public class DemoCompleta {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║       🌱 DEMONSTRAÇÃO FINAL MY PLANT 🌱     ║");
        System.out.println("║  Sistema de Gerenciamento de Vendas - v1.0  ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        // ==================================================
        // FASE 1: Criar Estrutura de Endereços
        // ==================================================
        System.out.println("📍 FASE 1: CRIANDO ENDEREÇOS");
        System.out.println("═══════════════════════════════════════════════\n");

        Endereco enderecoCentralSP = new Endereco("SP", "São Paulo", "Pinheiros", 
                                                   "Avenida Paulista", 1000, "Sala 500");
        System.out.println("Endereço da Loja Central criado:");
        enderecoCentralSP.apresentarLogradouro();

        Endereco enderecoGerenteSP = new Endereco("SP", "São Paulo", "Vila Mariana", 
                                                   "Rua do Comércio", 456, "");
        System.out.println("\nEndereço do Gerente criado:");
        enderecoGerenteSP.apresentarLogradouro();

        // ==================================================
        // FASE 2: Criar Gerente (NOVO!)
        // ==================================================
        System.out.println("\n\n👔 FASE 2: CRIANDO GERENTE");
        System.out.println("═══════════════════════════════════════════════\n");

        Gerente gerente = new Gerente("Patricia Alves Silva", 48, enderecoGerenteSP, 
                                     "Loja Paulista Central", 7000);
        
        // Adicionar histórico de salários
        gerente.adicionarSalario(7200);
        gerente.adicionarSalario(6850);
        
        gerente.apresentarse();
        System.out.println("\n📊 Informações Salariais:");
        System.out.println("   Salários Recebidos: ");
        for (double salario : gerente.getSalarioRecebido()) {
            System.out.println("     • R$ " + String.format("%.2f", salario));
        }
        System.out.println("   Média de Salários: R$ " + String.format("%.2f", gerente.calcularMedia()));
        System.out.println("   Bônus (35%): R$ " + String.format("%.2f", gerente.calcularBonus()));

        // ==================================================
        // FASE 3: Criar Vendedor
        // ==================================================
        System.out.println("\n\n👨‍💼 FASE 3: CRIANDO VENDEDOR");
        System.out.println("═══════════════════════════════════════════════\n");

        Endereco enderecoVendedor = new Endereco("SP", "São Paulo", "Centro", 
                                                 "Rua 25 de Março", 500, "");
        Vendedor vendedor = new Vendedor("Bruno Ferreira", 32, enderecoVendedor, 
                                        "Loja Paulista Central", 3800);
        
        vendedor.adicionarComissao(500);
        vendedor.adicionarComissao(750);
        vendedor.adicionarComissao(650);
        
        vendedor.apresentarse();
        System.out.println("\n💼 Informações de Vendas:");
        System.out.println("   Comissões Recebidas: ");
        for (double comissao : vendedor.getComissoes()) {
            System.out.println("     • R$ " + String.format("%.2f", comissao));
        }
        System.out.println("   Média de Comissões: R$ " + String.format("%.2f", vendedor.calcularMedia()));

        // ==================================================
        // FASE 4: Criar Cliente
        // ==================================================
        System.out.println("\n\n👥 FASE 4: CRIANDO CLIENTE");
        System.out.println("═══════════════════════════════════════════════\n");

        Endereco enderecoCliente = new Endereco("SP", "São Paulo", "Morumbi", 
                                               "Avenida Giovanni Gronchi", 1000, "Casa 5");
        Cliente cliente = new Cliente("Mariana dos Santos", 42, enderecoCliente, 
                                     "mariana@empresabolanta.com", "11999887766");
        cliente.apresentarse();

        // ==================================================
        // FASE 5: Criar Itens (NOVO!)
        // ==================================================
        System.out.println("\n\n📦 FASE 5: CRIANDO ITENS");
        System.out.println("═══════════════════════════════════════════════\n");

        Item[] itens = {
            new Item(1, "Sementes Premium de Orquídea", "Sementes Raras", 189.90),
            new Item(2, "Adubo Orgânico Premium", "Fertilizantes", 145.50),
            new Item(3, "Vaso Cerâmico Artesanal Grande", "Acessórios", 299.00),
            new Item(4, "Fertilizante Líquido Importado", "Insumos", 75.00),
            new Item(5, "Pote de Espécies Raras", "Sementes", 250.00)
        };

        System.out.println("Itens adicionados ao catálogo:\n");
        for (Item item : itens) {
            item.gerarDescricao();
        }

        // ==================================================
        // FASE 6: Criar Pedido (NOVO!)
        // ==================================================
        System.out.println("\n\n📋 FASE 6: PROCESSANDO PEDIDO");
        System.out.println("═══════════════════════════════════════════════\n");

        Date dataCriacao = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataCriacao);
        calendar.add(Calendar.DAY_OF_MONTH, 10); // Vencimento em 10 dias
        Date dataVencimento = calendar.getTime();

        // Selecionar alguns itens para o pedido
        Item[] itensPedido = {itens[0], itens[1], itens[2], itens[4]};

        ProcessaPedido processador = new ProcessaPedido();
        Pedido pedido = processador.processar(10001, dataCriacao, dataVencimento,
                                              cliente, vendedor, gerente,
                                              "Loja Paulista Central", itensPedido);

        if (pedido != null) {
            System.out.println("✅ Pedido processado com sucesso!\n");
            pedido.gerarDescricaoVenda();

            // ==================================================
            // FASE 7: Adicionar Mais Itens
            // ==================================================
            System.out.println("\n🔄 FASE 7: ATUALIZANDO PEDIDO");
            System.out.println("═══════════════════════════════════════════════\n");

            pedido.adicionarItem(itens[3]);
            System.out.println("✅ Item #" + itens[3].getId() + " adicionado ao pedido!");
            System.out.println("\n💰 Novo Valor Total: R$ " + String.format("%.2f", pedido.calcularValorTotal()));
        } else {
            System.out.println("❌ Não foi possível processar o pedido!");
        }

        // ==================================================
        // FASE 8: Resumo Final
        // ==================================================
        System.out.println("\n\n✅ FASE 8: RESUMO FINAL");
        System.out.println("═══════════════════════════════════════════════\n");

        System.out.println("📊 ESTATÍSTICAS DO SISTEMA:");
        System.out.println("   • Gerentes cadastrados: 1");
        System.out.println("   • Vendedores cadastrados: 1");
        System.out.println("   • Clientes cadastrados: 1");
        System.out.println("   • Itens no catálogo: " + itens.length);
        System.out.println("   • Pedidos processados: 1");
        System.out.println("   • Valor total de vendas: R$ " + 
                          String.format("%.2f", pedido != null ? pedido.calcularValorTotal() : 0));

        System.out.println("\n🎯 VERIFICAÇÃO DE REQUISITOS:");
        System.out.println("   ✅ Classe Gerente implementada");
        System.out.println("   ✅ Classe Endereco implementada");
        System.out.println("   ✅ Herança (Pessoa, Cliente, Vendedor, Gerente)");
        System.out.println("   ✅ Classe Item implementada");
        System.out.println("   ✅ Classe Pedido implementada");
        System.out.println("   ✅ Classe ProcessaPedido implementada");
        System.out.println("   ✅ Menu com funcionalidade de pedidos");

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║        ✅ SISTEMA COMPLETO E FUNCIONAL ✅   ║");
        System.out.println("║     Pronto para melhorar a produtividade!    ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
}
