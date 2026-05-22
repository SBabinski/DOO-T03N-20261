import java.util.ArrayList;

public class Loja {
    String nomeFantasia;
    String razaoSocial;
    String cnpj;
    Endereco endereco;
    Vendedor[] vendedores;
    Cliente[] clientes;

    public Loja(String nomeFantasia, String razaoSocial, String cnpj,
                Endereco endereco, Vendedor[] vendedores, Cliente[] clientes) {
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial  = razaoSocial;
        this.cnpj         = cnpj;
        this.endereco     = endereco;
        this.vendedores   = vendedores;
        this.clientes     = clientes;
    }

    public void apresentarSe() {
        System.out.println("Nome Fantasia: " + nomeFantasia);
        System.out.println("CNPJ: " + cnpj);
        endereco.apresentarLogradouro();
    }

    public int contarVendedores() { return vendedores.length; }
    public int contarClientes()   { return clientes.length; }

    public static void consultarLojas(ArrayList<Loja> lojas) {
        if (lojas.isEmpty()) {
            System.out.println("Nenhuma loja cadastrada.");
            return;
        }
        System.out.println("\n===== LOJAS CADASTRADAS =====");
        int i = 1;
        for (Loja loja : lojas) {
            System.out.println("\n[Loja " + i + "]");
            System.out.println("Nome Fantasia: " + loja.nomeFantasia);
            System.out.println("Razão Social: " + loja.razaoSocial);
            System.out.println("CNPJ: " + loja.cnpj);
            System.out.print("Endereço: ");
            loja.endereco.apresentarLogradouro();
            System.out.println("Total de Vendedores: " + loja.contarVendedores());
            System.out.println("Total de Clientes: " + loja.contarClientes());
            i++;
        }
    }
}