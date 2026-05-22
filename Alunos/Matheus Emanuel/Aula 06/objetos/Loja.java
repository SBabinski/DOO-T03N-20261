package objetos;

import java.util.ArrayList;
import java.util.List;

public class Loja {

    private String nomeFantasia;
    private String razaoSocial;
    private String cnpj;
    private String cidade;
    private String bairro;
    private String rua;

    private List<Vendedor> vendedores = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();

    public Loja(String nomeFantasia, String razaoSocial, String cnpj, String cidade, String bairro, String rua) {
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
    }

    public void adicionarVendedor(Vendedor vendedor) {
        vendedores.add(vendedor);
    }

    public void removerVendedor(Vendedor vendedor) {
        vendedores.remove(vendedor);
    }

    public int contarVendedores() {
        System.out.println("Total de vendedores em " + nomeFantasia + ": " + vendedores.size());
        return vendedores.size();
    }

    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void removerCliente(Cliente cliente) {
        clientes.remove(cliente);
    }

    public int contarClientes() {
        System.out.println("Total de clientes em " + nomeFantasia + ": " + clientes.size());
        return clientes.size();
    }

    // Getters e Setters
    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) {
        if (nomeFantasia.isBlank()) {
            System.out.println("O nome fantasia da loja não pode ser vazio.");
            return;
        }
        this.nomeFantasia = nomeFantasia;
    }

    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) {
        if (razaoSocial.isBlank()) {
            System.out.println("A razão social da loja não pode ser vazia.");
            return;
        }
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) {
        if (cnpj.isBlank()) {
            System.out.println("O CNPJ da loja não pode ser vazio.");
            return;
        }
        this.cnpj = cnpj;
    }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) {
        if (cidade.isBlank()) {
            System.out.println("A cidade da loja não pode ser vazia.");
            return;
        }
        this.cidade = cidade;
    }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) {
        if (bairro.isBlank()) {
            System.out.println("O bairro da loja não pode ser vazio.");
            return;
        }
        this.bairro = bairro;
    }

    public String getRua() { return rua; }
    public void setRua(String rua) {
        if (rua.isBlank()) {
            System.out.println("A rua da loja não pode ser vazia.");
            return;
        }
        this.rua = rua;
    }

    public List<Vendedor> getVendedores() { return vendedores; }
    public List<Cliente> getClientes() { return clientes; }

    public void apresentarse() {
        System.out.println("=== " + nomeFantasia + " ===");
        System.out.println("Razão Social: " + razaoSocial);
        System.out.println("CNPJ: " + cnpj);
        System.out.println("Endereço: " + rua + ", " + bairro + ", " + cidade);
    }
}