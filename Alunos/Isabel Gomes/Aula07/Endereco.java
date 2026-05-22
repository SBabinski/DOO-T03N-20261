package fag.objetos;

public class Endereco {

    private String estado;
    private String cidade;
    private String bairro;
    private String numero;
    private String rua;
    private String complemento;

    public Endereco(String estado, String cidade, String bairro, String rua, String numero, String complemento) {
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.numero = numero;
        this.rua = rua;
        this.complemento = complemento;
    }

    // getters
    
    public String getRua() {
    	return rua;
    }
    
    public String getEstado() { 
    	return estado; 
    }
    
    public String getCidade() { 
    	return cidade; 
    }
    
    public String getBairro() { 
    	return bairro; 
    }
    
    public String getNumero() { 
    	return numero; 
    }
    public String getComplemento() { 
    	return complemento; 
    }

    //seters
    
    public void setRua(String rua) {
    	this.rua = rua;
    }
    
    public void setEstado(String estado) { 
    	this.estado = estado; 
    }
    
    public void setCidade(String cidade) { 
    	this.cidade = cidade; 
    }
    
    public void setBairro(String bairro) {
    	this.bairro = bairro;
    }
    
    public void setNumero(String numero) {
    	this.numero = numero; 
    }
    
    public void setComplemento(String complemento) {
    	this.complemento = complemento;
    }
    
    //metodos

    public void apresentarLogradouro() {
    	System.out.println("\n-- Endereco --");
    	System.out.println("Estado: " + estado);        
    	System.out.println("Cidade: " + cidade);
    	System.out.println("Rua: "+ rua);
    	System.out.println("nº: " + numero);
    	System.out.println("Complemento: " + complemento);
    }
    
    
}