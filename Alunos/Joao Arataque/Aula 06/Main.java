import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Loja minhaLoja = new Loja();
        minhaLoja.nomeFantasia = "My Plant - Centro";
        minhaLoja.cnpj = "12.345.678/0001-99";
        minhaLoja.rua = "Avenida Brasil";
        minhaLoja.bairro = "Centro";
        minhaLoja.cidade = "Cascavel";

        Vendedor v1 = new Vendedor();
        v1.nome = "João Gnoatto";
        v1.idade = 18;
        v1.loja = minhaLoja.nomeFantasia;
        v1.salarioBase = 2500.0;

        v1.salarioRecebido.add(2500.0);
        v1.salarioRecebido.add(2700.0); // talvez com uma comissão
        v1.salarioRecebido.add(2600.0);

        Cliente c1 = new Cliente();
        c1.nome = "Gabriel";
        c1.idade = 10;
        c1.cidade = "Cascavel";

        minhaLoja.vendedores.add(v1);
        minhaLoja.clientes.add(c1);


        System.out.println("=== DADOS DA LOJA ===");
        minhaLoja.apresentarse();
        minhaLoja.contarVendedores();
        minhaLoja.contarClientes();

        System.out.println("\n=== DADOS DO VENDEDOR ===");
        v1.apresentarse();
        System.out.printf("Média Salarial: R$ %.2f%n", v1.calcularMedia());
        System.out.printf("Bônus: R$ %.2f%n", v1.calcularBonus());

        System.out.println("\n=== DADOS DO CLIENTE ===");
        c1.apresentarse();
    }
}