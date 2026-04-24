import java.util.ArrayList;

public class Loja {
    String nomeFantasia;
    String razaoSocial;
    String cnpj;
    String cidade;
    String bairro;
    String rua;

    ArrayList<Vendedor> vendedores = new ArrayList<>();
    ArrayList<Cliente> clientes = new ArrayList<>();
  
    public Loja(String nomeFantasia, String razaoSocial, String cnpj, String cidade, String bairro, String rua) {
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;

    vendedores.add(new Vendedor("João",
     30,
      "jesuitas",
       "my plant",
        "sao pedro",
         "sao jose",
          1800.00));
     vendedores.add(new Vendedor("Ana",
      28
      , "jesuitas",
       "my plant", 
       "sao pedro",
        "sao jose",
         1800.00));

       
        clientes.add(new Cliente("Maria", 25, "cascavel", "sao pedro", "jose da silva"));
        clientes.add(new Cliente("Pedro", 40, "cascavel", "sao pedro", "jose da siçva"));
    
    }
    public void contarClientes() {
        System.out.println("Quantidade de clientes: " + clientes.size());
    }
    public void contarVendedores() {
        System.out.println("Quantidade de vendedores: " + vendedores.size());
    }
    public void apresentarSe() {
        System.out.println("Nome Fantasia: " + nomeFantasia);
        System.out.println("Razão Social: " + razaoSocial);
        System.out.println("CNPJ: " + cnpj);
        System.out.println("Endereço: " + rua + ", " + bairro + ", " + cidade);
    }
    public void mostrarDetalhes() {


    System.out.println("\n--- Vendedores ---");
    for (Vendedor v : vendedores) {
    System.out.println("Nome: " + v.nome);
    System.out.println("Idade: " + v.idade);
    System.out.println("Loja: " + v.loja);
    System.out.println("Salário Base: R$ " + v.salarioBase);
    System.out.println("Média Salarial: R$ " + v.calcularMedia());
    System.out.println("Bônus: R$ " + v.calcularBonus());
   
    }
    

    System.out.println("\n--- Clientes ---");
    for (Cliente c : clientes) {
        System.out.println(c.nome);
         System.out.println("Idade: " + c.idade);
    }
}
   
}

