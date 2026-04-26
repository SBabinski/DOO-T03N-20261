import java.util.ArrayList;

public class Vendedor extends Pessoa {
    private Loja loja;
    private float salarioBase;
    private ArrayList<Float> salariosRecebidos;

    public Vendedor(String nome, int idade, Loja loja, Endereco endereco, float salarioBase) {
        super(nome, idade, endereco);
        this.loja = loja;
        this.salarioBase = salarioBase;
        this.salariosRecebidos = new ArrayList<>();
    }

    @Override
    public void apresentarSe() {
        System.out.println("Vendedor: " + nome + " | Idade: " + idade + " | Loja: " + (loja != null ? loja.getNomeFantasia() : "Nenhuma"));
    }

    public float calcularMedia() {
        if (salariosRecebidos.isEmpty()) return 0f;
        float soma = 0f;
        for (float s : salariosRecebidos) soma += s;
        return soma / salariosRecebidos.size();
    }

    public float calcularBonus() {
        return salarioBase * 0.2f;
    }

    public void pagarSalario(float valor) {
        this.salariosRecebidos.add(valor);
    }

    // Getters e Setters
    public Loja getLoja() { return loja; }
    public void setLoja(Loja loja) { this.loja = loja; }
    public float getSalarioBase() { return salarioBase; }
    public void setSalarioBase(float salarioBase) { this.salarioBase = salarioBase; }
    public ArrayList<Float> getSalariosRecebidos() { return salariosRecebidos; }
}
