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
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public double getValor() { return valor; }
    public void gerarDescricao() {
        System.out.println("Item #" + id + " | Nome: " + nome + " | Tipo: " + tipo + " | Valor: R$ " + valor);
    }
}
