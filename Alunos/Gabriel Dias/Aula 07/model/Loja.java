package myplant.model;

import java.util.ArrayList;
import java.util.List;

public class Loja {

    private String nomeFantasia;
    private String razaoSocial;
    private String cnpj;
    private Endereco endereco;
    private List<Vendedor> vendedores;
    private List<Cliente> clientes;

    public Loja(String nomeFantasia, String razaoSocial, String cnpj, Endereco endereco) {
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.vendedores = new ArrayList<>();
        this.clientes = new ArrayList<>();
    }

    public void adicionarVendedor(Vendedor v) { vendedores.add(v); }
    public void adicionarCliente(Cliente c)   { clientes.add(c); }

    public int contarClientes()   { return clientes.size(); }
    public int contarVendedores() { return vendedores.size(); }

    public void apresentarse() {
        System.out.println("🌿 Loja: " + nomeFantasia + " | CNPJ: " + cnpj);
        System.out.print("   Endereço: ");
        endereco.apresentarLogradouro();
    }

    // Getters
    public String getNomeFantasia()    { return nomeFantasia; }
    public String getRazaoSocial()     { return razaoSocial; }
    public String getCnpj()            { return cnpj; }
    public Endereco getEndereco()      { return endereco; }
    public List<Vendedor> getVendedores() { return vendedores; }
    public List<Cliente> getClientes()    { return clientes; }
}
