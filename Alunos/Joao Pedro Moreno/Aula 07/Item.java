public class Item {

    private int id;
    private String nome;
    private double valor;
    private String tipo;
    private int quant;

    public Item() {
    }

    // Construtor completo
    public Item(int id, String nome, String tipo, double valor, int quant) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
        this.quant = quant;
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }

    public int getQuant() {
        return quant;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    // Método para exibir a descrição do item
    public String exibirDescricao() {
        return "  [" + id + "] " + nome + " | Tipo: " + tipo
                + " | Valor unit.: R$ " + valor + " | Qtd: " + quant;
    }
}
