public class Item {
    private int id;
    private String nome;
    private String tipo;
    private double valor;

    public Item(int id, String nome, String tipo, double valor) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
    }

    // Getters
    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getTipo() {
        return this.tipo;
    }

    public double getValor() {
        return this.valor;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    // Método para gerar descrição
    public void gerarDescricao() {
        System.out.println(String.format("ID: %d | Nome: %s | Tipo: %s | Valor: R$ %.2f", 
                this.id, this.nome, this.tipo, this.valor));
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Nome: %s | Tipo: %s | Valor: R$ %.2f", 
                this.id, this.nome, this.tipo, this.valor);
    }
}
