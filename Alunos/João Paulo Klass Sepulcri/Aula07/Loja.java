public class Loja {
    String nomeFantasia;
    String razaoSocial;
    String cnpj;
    Endereco endereco;
    Cliente[] clientes;
    Vendedor[] vendedores;
    Gerente[] gerentes;

    public Loja(String nomeFantasia, String razaoSocial, String cnpj, Endereco endereco) {
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.endereco = endereco;
    }

    public Loja(String nomeFantasia, String razaoSocial, String cnpj, String cidade, String bairro, String rua) {
        this(nomeFantasia, razaoSocial, cnpj, new Endereco("PR", cidade, bairro, "S/N", rua));
    }

    public int contarClientes() {
        if (clientes == null) {
            return 0;
        }
        return clientes.length;
    }

    public int contarVendedores() {
        if (vendedores == null) {
            return 0;
        }
        return vendedores.length;
    }

    public int contarGerentes() {
        if (gerentes == null) {
            return 0;
        }
        return gerentes.length;
    }

    public void apresentarse() {
        System.out.println("Nome fantasia: " + nomeFantasia);
        System.out.println("Razao social: " + razaoSocial);
        System.out.println("CNPJ: " + cnpj);
        System.out.println("Endereco: " + endereco.formatarLogradouro());
        System.out.println("Clientes cadastrados: " + contarClientes());
        System.out.println("Vendedores cadastrados: " + contarVendedores());
        System.out.println("Gerentes cadastrados: " + contarGerentes());
    }
}
