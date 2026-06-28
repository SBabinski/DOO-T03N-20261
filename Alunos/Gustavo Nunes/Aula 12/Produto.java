public class Produto {
    
    private String nome;
    private double preco;
    
    public Produto(String nome, double preco) {
        this.setNome(nome);
        this.setPreco(preco);
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public double getPreco() {
        return preco;
    }
    
    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder()
                .append(this.getNome())
                .append(" | Valor: ")
                .append(String.format("R$ %.2f",this.getPreco()));
        
        return builder.toString();
    }
}
