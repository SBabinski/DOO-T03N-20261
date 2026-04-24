import java.util.ArrayList;
import java.util.List;

public class Loja {

    private String nome_fantasia = "My Plant";
    private String razao_social = "My Plant LTDA";
    private String cnpj = "40.440.346/0001-52";
    private String cidade = "Cascavel/PR";
    private String bairro = "Centro";
    private String rua = "Rua das Flores";

    private List<Vendedor> vendedores = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();

    public Loja() {

        clientes.add(new Cliente("João", "30", "Cascavel", "Centro", "Rua das Flores"));
        clientes.add(new Cliente("Maria", "25", "Cascavel", "Centro", "Rua das Flores"));
        clientes.add(new Cliente("Pedro", "35", "Cascavel", "Centro", "Rua das Flores"));

        vendedores.add(new Vendedor("Carlos", "40", "My Plant", "Cascavel", "Centro", "Rua das Flores", 2000));
        vendedores.add(new Vendedor("Ana", "28", "My Plant", "Cascavel", "Centro", "Rua das Flores", 2500));
        vendedores.add(new Vendedor("Lucas", "32", "My Plant", "Cascavel", "Centro", "Rua das Flores", 2200));
    }

    // GETTERS
    public List<Vendedor> getVendedores() {
        return vendedores;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public String getNome_fantasia() {
        return nome_fantasia;
    }

    public String getRazao_social() {
        return razao_social;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getCidade() {
        return cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public String getRua() {
        return rua;
    }

    // MÉTODOS DE EXIBIÇÃO
    public void apresentarVendedores() {
        System.out.println("Vendedores da loja:");
        for (Vendedor v : vendedores) {
            System.out.println("Nome: " + v.getNome() +
                    ", Idade: " + v.getIdade() +
                    ", Cidade: " + v.getCidade() +
                    ", Salário: " + v.getSalarioBase());
        }
        System.out.println();
    }

    public void apresentarClientes() {
        System.out.println("Clientes da loja:");
        for (Cliente c : clientes) {
            System.out.println("Nome: " + c.getNome() +
                    ", Idade: " + c.getIdade() +
                    ", Cidade: " + c.getCidade());
        }
        System.out.println();
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
        double bonus = loja.calcularBonus();
        System.out.println("Bônus para os vendedores: " + bonus);
    }
}