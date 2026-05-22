public class Item {
    int id;
    String nome, tipo;
    double valor;

    public void gerarDescricao() {
        System.out.println("ID: " + id + " | " + nome + " (" + tipo + ") - R$ " + valor);
    }
}
