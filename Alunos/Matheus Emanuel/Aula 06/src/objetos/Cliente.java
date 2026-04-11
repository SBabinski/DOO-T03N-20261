package objetos;

public class Cliente {

    private String nome;
    private int idade;
    private String cidade;
    private String bairro;
    private String rua;

    public Cliente(String nome, int idade, String cidade, String bairro, String rua) {
        this.nome = nome;
        this.idade = idade;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
    }

    public Cliente(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) {
        if (nome.isBlank()) {
            System.out.println("O nome do cliente não pode ser vazio.");
            return;
        }
        this.nome = nome;
    }

    public int getIdade() { return idade; }
    public void setIdade(int idade) {
        if (idade < 0) {
            System.out.println("A idade do cliente não pode ser negativa.");
            return;
        }
        this.idade = idade;
    }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) {
        if (cidade.isBlank()) {
            System.out.println("A cidade do cliente não pode ser vazia.");
            return;
        }
        this.cidade = cidade;
    }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) {
        if (bairro.isBlank()) {
            System.out.println("O bairro do cliente não pode ser vazio.");
            return;
        }
        this.bairro = bairro;
    }

    public String getRua() { return rua; }
    public void setRua(String rua) {
        if (rua.isBlank()) {
            System.out.println("A rua do cliente não pode ser vazia.");
            return;
        }
        this.rua = rua;
    }

    public void apresentarse() {
        System.out.println("Nome: " + nome + " | Idade: " + idade);
    }
}