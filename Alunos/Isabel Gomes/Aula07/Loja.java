package fag.objetos;
import java.util.ArrayList;
import java.util.List;
import fag.objetos.Gerente;

public class Loja {
	
	private Endereco endereco;
	private String razaoSocial;
	private String nomeFantasia;
	private String cnpj;
	
	
	

	private List<Cliente> clientes = new ArrayList<>();
	private List<Vendedor> vendedores = new ArrayList<>();
	private List<Vendas> vendas = new ArrayList<>();
	private List<Gerente> gerentes = new ArrayList<>();

	
	
	public Loja(String razaoSocial, String nomeFantasia, String cnpj, Endereco endereco) {
		setRazaoSocial(razaoSocial);
		setNomeFantasia(nomeFantasia);
		setCnpj(cnpj);
		this.endereco = endereco;
	}
	
	
	//getters
	
	public String getRazaoRocial() {
		return razaoSocial;
	}
	
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	
	public String getCnpj() {
		return cnpj;
	}
	
	
	public List<Cliente> getClientes() { 
		return clientes; 
	}
	
    public List<Vendedor> getVendedores() {
    	return vendedores; 
    }
    
    public List<Vendas> getVendas() {
    	return vendas; 
    }
	
   


	
	
	//setters
    
    
	
	public void setRazaoSocial(String razaoSocial) {
		if(razaoSocial != null && !razaoSocial.isBlank()) {
			this.razaoSocial = razaoSocial;
		}
	}
	
	public void setNomeFantasia(String nomeFantasia) {
		if(nomeFantasia != null && !nomeFantasia.isBlank()) {
			this.nomeFantasia = nomeFantasia;
		}
	}
	
	public void setCnpj(String cnpj) {
		if(cnpj!= null && !cnpj.isBlank()) {
			this.cnpj = cnpj;
		}
	}
	

	
	
	//metodos
	
	public void contarClientes() {
		int contaClientes = 0;
		for(int i=0; i < clientes.size(); i++) {
			contaClientes++;
		}
		
	}
	
	
	public void contarVendedores() {
		int contaVendedor = 0;
		for(int i = 0; i< vendedores.size(); i++) {
			contaVendedor++;
		}
	}
	
	public void apresentarSe () {
		System.out.println("\nNome Fantasia: " + nomeFantasia);
		System.out.println("CNPJ: " + cnpj);
	//	System.out.println("Gerencia: " + gerentes.getNome()));
		endereco.apresentarLogradouro();
	}
	
	public void adicionarVendedor(Vendedor vendedor) {
	    vendedores.add(vendedor);
	}

	public void adicionarCliente(Cliente cliente) {
	    clientes.add(cliente);
	}
	
	public void adicionarGerente(Gerente gerente) {
		gerentes.add(gerente);
	}
	
	public void adicionarVenda(Vendas venda) { 
		vendas.add(venda); 
	}
	
	
	
	
}
