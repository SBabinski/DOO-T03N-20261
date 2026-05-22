public class Gerente extends Funcionario {

    public Gerente(String nome, int idade, Loja loja, double salarioBase, Endereco endereco) {
        super(nome, idade, loja, endereco, salarioBase);
    }

    // Métodos da classe Gerente
    @Override
    public double calcularBonus() {
        return this.salarioBase * 0.35;
    }

    @Override
    public void apresentarSe() {
        System.out.println(String.format("Nome: %s | Idade: %d | Loja: %s", this.nome, this.idade,
                (this.loja != null ? this.loja.getNomeFantasia() : "Nenhuma")));
    }
}
