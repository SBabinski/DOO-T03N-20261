package Objetos;

import java.util.ArrayList;
import java.util.List;

public class Loja {

    private String nomeFantasia;
    private String razaoSocial;
    private String cnpj;
    private Endereco endereco;

    private List<Vendedor> vendedores = new ArrayList<>();
    private List<Gerente> gerentes = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();

    public Loja(String nomeFantasia, String razaoSocial, String cnpj, Endereco endereco) {
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.endereco = endereco;
    }

    public void adicionarVendedor(Vendedor vendedor) { vendedores.add(vendedor); }
    public void removerVendedor(Vendedor vendedor) { vendedores.remove(vendedor); }
    public List<Vendedor> getVendedores() { return vendedores; }

    public int contarVendedores() {
        System.out.println("Total de vendedores em " + nomeFantasia + ": " + vendedores.size());
        return vendedores.size();
    }

    public void adicionarGerente(Gerente gerente) { gerentes.add(gerente); }
    public void removerGerente(Gerente gerente) { gerentes.remove(gerente); }
    public List<Gerente> getGerentes() { return gerentes; }

    public int contarGerentes() {
        System.out.println("Total de gerentes em " + nomeFantasia + ": " + gerentes.size());
        return gerentes.size();
    }

    public void adicionarCliente(Cliente cliente) { clientes.add(cliente); }
    public void removerCliente(Cliente cliente) { clientes.remove(cliente); }
    public List<Cliente> getClientes() { return clientes; }

    public int contarClientes() {
        System.out.println("Total de clientes em " + nomeFantasia + ": " + clientes.size());
        return clientes.size();
    }

    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }

    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }

    public void apresentarse() {
        System.out.println("=== " + nomeFantasia + " ===");
        System.out.println("Razão Social: " + razaoSocial);
        System.out.println("CNPJ: " + cnpj);
        endereco.apresentarLogradouro();
    }
}
