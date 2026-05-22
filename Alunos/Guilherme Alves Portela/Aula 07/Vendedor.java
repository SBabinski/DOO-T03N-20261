public class Vendedor extends Funcionario {

    public Vendedor(String nome, int idade, Loja loja, Endereco endereco, double salarioBase){
        super(nome, idade, loja, endereco, salarioBase);
    }

    //Métodos específicos da classe Vendedor

    public double calcularBonus(){
        return this.salarioBase * 0.2f;
    }
    
    public void apresentarSe(){
        System.out.println(String.format("Nome: %s | Idade: %d | Loja: %s",this.nome, this.idade, (this.loja != null ? this.loja.getNomeFantasia() : "Nenhuma")));
    }
}
