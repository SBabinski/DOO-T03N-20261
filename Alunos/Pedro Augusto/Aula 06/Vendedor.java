import java.util.ArrayList;

public class Vendedor {
    private String nome;
    private int idade;
    private Loja loja;
    private String bairro;
    private String cidade;
    private String rua;
    private float salarioBase;
    private ArrayList<Float> salariosRecebidos;

    public Vendedor(String nome, int idade, Loja loja, String bairro, String cidade, String rua, float salarioBase) {
        this.setNome(nome);
        this.setIdade(idade);
        this.setLoja(loja);
        this.setBairro(bairro);
        this.setCidade(cidade);
        this.setRua(rua);
        this.setSalarioBase(salarioBase);
        this.salariosRecebidos = new ArrayList<Float>();
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return this.idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getCidade() {
        return this.cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return this.bairro;
    }

    public void setBairro(String novoBairro) {
        this.bairro = novoBairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Loja getLoja() {
        return this.loja;
    }

    public void setLoja(Loja novaLoja) {
        this.loja = novaLoja;
    }

    public float getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(float salarioBase) {
        this.salarioBase = salarioBase;
    }

    public ArrayList<Float> getSalariosRecebidos() {
        return salariosRecebidos;
    }

    public void pagarSalario(Float novoSalario) {
        // método que serve como um "setter" do salário recebido
        this.salariosRecebidos.add(novoSalario);
    }

    public void apresentarSe() {
        System.out.println("Nome: " + this.nome);
        System.out.println("Idade: " + this.idade);
        System.out.println("Loja: " + (this.loja != null ? this.loja.getNomeFantasia() : "Nenhuma"));
        System.out.println("Bairro: " + this.bairro);
    }

    public float calcularMedia() {
        // método para calcular a media salarial
        if (salariosRecebidos == null || salariosRecebidos.isEmpty()) {
            return 0.0f;
        }

        float somaSalarios = 0.0f;
        for (var salario : salariosRecebidos) {
            somaSalarios += salario;
        }

        float mediaSalarial = somaSalarios / salariosRecebidos.size();

        return mediaSalarial;
    }

    public float calcularBonus() {
        return this.salarioBase * 0.2f;
    }
}
