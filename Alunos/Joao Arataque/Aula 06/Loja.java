import java.util.ArrayList;
import java.util.List;

public class Loja {
    String nomeFantasia;
    String razaoSocial;
    String cnpj;
    String cidade;
    String bairro;
    String rua;

    List<Vendedor> vendedores = new ArrayList<>();
    List<Cliente> clientes = new ArrayList<>();

    public void contarClientes() {
        System.out.println("Total de clientes: " + clientes.size());
    }

    public void contarVendedores(){
        System.out.println("Total de vendededores: " + vendedores.size());
    }

    public void apresentarse(){
        System.out.println("Loja: " + nomeFantasia + " | CNPJ: " + cnpj);
        System.out.println("Endereço: " + rua + "," + bairro + " - " + cidade);
    }
}
