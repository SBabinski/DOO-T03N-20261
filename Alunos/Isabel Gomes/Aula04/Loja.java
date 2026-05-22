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
	
	
	public void contarClientes() {
		int contaClientes = 0;
		for(int i=0; i < clientes.size(); i++) {
			contaClientes++;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
}
