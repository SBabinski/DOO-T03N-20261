public class Main {

    public static void main(String[] args) {

        Loja loja = new Loja("My Plant", "My Plant LTDA", "123456789",
                "Cascavel", "Centro", "Rua das Flores");

        Vendedor vendedor = new Vendedor("João", 30, "My Plant",
                "Cascavel", "Centro", "Rua A", 2000);

        vendedor.adicionarSalario(2000);
        vendedor.adicionarSalario(2100);
        vendedor.adicionarSalario(2200);

        Cliente cliente = new Cliente("Maria", 25,
                "Cascavel", "Centro", "Rua B");

        loja.adicionarVendedor(vendedor);
        loja.adicionarCliente(cliente);

        loja.apresentarSe();

        vendedor.apresentarSe();
        System.out.println("Média salarial: " + vendedor.calcularMedia());
        System.out.println("Bônus: " + vendedor.calcularBonus());

        cliente.apresentarSe();

        loja.contarClientes();
        loja.contarVendedores();
    }
}