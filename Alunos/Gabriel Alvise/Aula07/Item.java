public class Item {
    private int id;
    private String nome;
    private String tipo;
    private Double valor;

    public Item(int id, String nome, String tipo, Double valor) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
    }

    public void gerarDescricao() {
        System.out.printf("ID: %d | Nome: %s | Tipo: %s | Valor: %.2f", id, nome, tipo, valor);
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public Double getValor() {
        return valor;
    }
}
