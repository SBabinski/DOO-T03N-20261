public class Item {
    int id;
    String nome;
    String tipo;
    double valor;

    public Item(int id, String nome, String tipo, double valor) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
    }

    public String gerarDescricao() {
        return "Item " + id + " - " + nome + " | Tipo: " + tipo + " | Valor: R$ " + valor;
    }
}
