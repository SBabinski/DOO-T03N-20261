package Entities;

import repository.ClienteRepository;
import repository.VendedorRepository;

public class Loja {
    private static int contId = 1;
    private int id;
    private String nomeFantasia;
    private String razaoSocial;
    private String cnpj;
    private String cidade;
    private String bairro;
    private String rua;
    private VendedorRepository vendedores;
    private ClienteRepository clientes;
    
    public Loja(String nomeFantasia, String razaoSocial,
                String Cnpj, String cidade, String bairro,
                String rua) {
        this.id = contId++; // Gera id
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = Cnpj;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.vendedores = new VendedorRepository();
        this.clientes = new ClienteRepository();
    }
    
    public ClienteRepository getClientes() {
        return clientes;
    }
    
    public VendedorRepository getVendedores() {
        return vendedores;
    }
    
    // Mosta a quantidade de clientes
    public int contarClientes() {
        return clientes.retornaQuantidade();
    }
    
    // Mosta a quantidade de vendedores
    public int contarVendedores() {
        return vendedores.retornaQuantidade();
    }
    
    public String apresentarse(){
        return String.format("\n" + id + "  |  " + nomeFantasia + "  |  " +
                cnpj + "  |  " + cidade + "  |  " +
                bairro + "  |  " + rua + "  |  "
                + contarClientes() + "  |  " + contarVendedores());
    }
    
    // Retorna o ID
    public int getID() {
        return id;
    }
}
