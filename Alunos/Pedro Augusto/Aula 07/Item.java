public class Item {
    private int id;
    private String nome;
    private String tipo;
    private float valor;

    public Item(int id, String nome, String tipo, float valor) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
    }

    public void gerarDescricao() {
        System.out.println("Item ID: " + id + " | Nome: " + nome + " | Tipo: " + tipo + " | Valor: R$ " + valor);
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public float getValor() { return valor; }
    public void setValor(float valor) { this.valor = valor; }
}
