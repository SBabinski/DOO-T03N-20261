package fag.objeto;
import java.util.ArrayList;
import java.util.List;


public class Loja {
	
	private String razaoSocial;
	private String nomeFantasia;
	private String cnpj;
	private String cidade;
	private String bairro;
	private String rua;
	
	private List<Cliente> clientes = new ArrayList<>();
	private List<Vendedor> vendedores = new ArrayList<>();
	private List<Vendas> vendas = new ArrayList<>();
	
	
	public Loja(String razaoSocial, String nomeFantasia, String cnpj, String cidade, String bairro, String rua) {
		this.razaoSocial = razaoSocial;
		this.nomeFantasia = nomeFantasia;
		this.cnpj = cnpj;
		this.cidade = cidade;
		this.bairro = bairro;
		this.rua = rua;
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
	
	public String getCidade() {
		return cidade;
	}
	
	public String getBairro() {
		return bairro;
	}
	
	public String getRua() {
		return rua;
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
	
	public void setCidade(String cidade) {
		if(cidade!= null && !cidade.isBlank()) {
			this.cidade = cidade;
		}
	}
	
	public void setBairro(String bairro) {
		if(bairro!= null && !bairro.isBlank()) {
			this.bairro = bairro;
		}
	}
	
	public void setRua(String rua) {
		if(rua!= null && !rua.isBlank()) {
			this.rua = rua;
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
		System.out.println("Endereco - Cidade: " + cidade 
				+ ", Rua: " + rua + ", Bairro: " + bairro  );
	}
	
	public void adicionarVendedor(Vendedor vendedor) {
	    vendedores.add(vendedor);
	}

	public void adicionarCliente(Cliente cliente) {
	    clientes.add(cliente);
	}
	
	public void adicionarVenda(Vendas venda) { 
		vendas.add(venda); }
	
	
	
	
}
