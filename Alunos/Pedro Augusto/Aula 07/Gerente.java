import java.util.ArrayList;

public class Gerente extends Pessoa {
    private Loja loja;
    private float salarioBase;
    private ArrayList<Float> salariosRecebidos;

    public Gerente(String nome, int idade, Loja loja, Endereco endereco, float salarioBase) {
        super(nome, idade, endereco);
        this.loja = loja;
        this.salarioBase = salarioBase;
        this.salariosRecebidos = new ArrayList<>();
        
        // Atributo salarioRecebido DEVE armazenar no mínimo três valores
        this.salariosRecebidos.add(salarioBase);
        this.salariosRecebidos.add(salarioBase * 1.1f);
        this.salariosRecebidos.add(salarioBase * 1.2f);
    }

    @Override
    public void apresentarSe() {
        System.out.println("Gerente: " + nome + " | Idade: " + idade + " | Loja: " + (loja != null ? loja.getNomeFantasia() : "Nenhuma"));
    }

    public float calcularMedia() {
        if (salariosRecebidos.isEmpty()) return 0f;
        float soma = 0f;
        for (float s : salariosRecebidos) soma += s;
        return soma / salariosRecebidos.size();
    }

    public float calcularBonus() {
        return salarioBase * 0.35f;
    }

    // Getters e Setters
    public Loja getLoja() { return loja; }
    public void setLoja(Loja loja) { this.loja = loja; }
    public float getSalarioBase() { return salarioBase; }
    public void setSalarioBase(float salarioBase) { this.salarioBase = salarioBase; }
    public ArrayList<Float> getSalariosRecebidos() { return salariosRecebidos; }
}
