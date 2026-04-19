import java.util.ArrayList;

public class Loja {

    private String nome;
    private String razaoSocial;
    private String cnpj;
    private String cidade;
    private String bairro;
    private String rua;

    ArrayList<Vendedor> v = new ArrayList<>();
    ArrayList<Cliente> c = new ArrayList<>();

    public Loja(){
    }

    public Loja(String nome, String razaoSocial, String cnpj,
                String cidade, String bairro, String rua) {

        this.nome = nome;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;

        //arraylist de clientes
		c.add(new Cliente ("João", 30, "São Paulo", "Centro", "Rua A"));
		c.add(new Cliente ("Maria", 25, "Rio de Janeiro", "Copacabana", "Rua B"));
		c.add(new Cliente ("José", 33, "Curitiba", "bairro C", "Rua C"));
		c.add(new Cliente ("Matheus", 40, "Floripa", "ilha grande", "Rua D"));
		c.add(new Cliente ("Julia", 18, "Toledo", "Panorama", "Rua E"));

    	//arraylist de vendedores
    	v.add(new Vendedor ("Carlos", 28, "Loja A", "São Paulo", "Centro", 
                            "Rua F", new double[]{2500, 2600, 2550}, 2500));
    	v.add(new Vendedor ("Ana", 35, "Loja B", "Riode Janeiro", "Copacabana", 
                            "Rua G", new double[]{2500, 2600, 2550}, 2500));
    	v.add(new Vendedor ("Pedro", 30, "Loja C", "Curitiba", "bairro C",
                            "Rua H", new double[]{2500, 2600, 2550}, 2500));
    	v.add(new Vendedor ("Lucas", 27, "Loja D", "Floripa", "ilha grande", 
                            "Rua I", new double[]{2500, 2600, 2550}, 2500));
    	v.add(new Vendedor ("Mariana", 32, "Loja E", "Toledo", "Panorama", 
                            "Rua J", new double[]{2500, 2600, 2550}, 2500));

    }

    public int contarClientes() {
        return c.size();
    }

    public int contarVendedores() {
        return v.size();
    }

    public void mostrarLoja() {
        System.out.println("Loja: " + nome);
        System.out.println("CNPJ: " + cnpj);
        System.out.println("Endereço: " + rua + ", " + bairro + ", " + cidade);
    }

    public ArrayList<Cliente> getClientes() {
        return c;
    }

    public ArrayList<Vendedor> getVendedores() {
        return v;
    }
}