package AULA06;

public class Loja {
    public String nomeFantasia;
    public String razaoSocial;
    public String cnpj;
    public String cidade;
    public String bairro;
    public String rua;
    public Vendedor[] vendedores;
    public Cliente[] clientes;

    public void contarClientes() {
        int total = (clientes != null) ? clientes.length : 0;
        System.out.println("Total de clientes: " + total);
    }

    public void contarVendedores() {
        int total = (vendedores != null) ? vendedores.length : 0;
        System.out.println("Total de vendedores: " + total);
    }

    public void apresentarse() {
        System.out.println("Loja: " + nomeFantasia + " | CNPJ: " + cnpj + " | Endereço: " + rua + ", " + bairro + " - " + cidade);
    }
}
