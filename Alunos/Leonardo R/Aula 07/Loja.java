import java.util.ArrayList;
import java.util.List;

public class Loja {

    private String nome_fantasia = "My Plant";
    private String razao_social = "My Plant LTDA";
    private String cnpj = "40.440.346/0001-52";

    private Endereco enderecoLoja = new Endereco("PR", "Cascavel", "Centro", "123", "Loja");

    private List<Vendedor> vendedores = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();

    public Loja() {

        Endereco e1 = new Endereco("PR", "Cascavel", "Centro", "100", "Casa");
        Endereco e2 = new Endereco("PR", "Cascavel", "Centro", "200", "Casa");
        Endereco e3 = new Endereco("PR", "Cascavel", "Centro", "300", "Casa");

        clientes.add(new Cliente("João", "30", e1));
        clientes.add(new Cliente("Maria", "25", e2));
        clientes.add(new Cliente("Pedro", "35", e3));

        vendedores.add(new Vendedor("Carlos", "40", "My Plant", e1, 2000));
        vendedores.add(new Vendedor("Ana", "28", "My Plant", e2, 2500));
        vendedores.add(new Vendedor("Lucas", "32", "My Plant", e3, 2200));
    }

    public List<Vendedor> getVendedores() {
        return vendedores;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public String getNome_fantasia() {
        return nome_fantasia;
    }

    public Endereco getEnderecoLoja() {
        return enderecoLoja;
    }

    public void apresentarVendedores() {

        for (Vendedor v : vendedores) {
            System.out.println("Nome: " + v.getNome());
            System.out.println("Idade: " + v.getIdade());
            v.getEndereco().apresentarLogradouro();
            System.out.println("----------------");
        }
    }

    public void apresentarClientes() {

        for (Cliente c : clientes) {
            System.out.println("Nome: " + c.getNome());
            System.out.println("Idade: " + c.getIdade());
            c.getEndereco().apresentarLogradouro();
            System.out.println("----------------");
        }
    }

    public double calcularMedia() {

        double soma = 0;

        for (Vendedor v : vendedores) {
            soma += v.getSalarioBase();
        }

        return soma / vendedores.size();
    }

    public double calcularBonus() {
        return calcularMedia() * 0.2;
    }

    public static void mostrarbonus(Loja loja) {
        System.out.println("Bonus: " + loja.calcularBonus());
    }
}