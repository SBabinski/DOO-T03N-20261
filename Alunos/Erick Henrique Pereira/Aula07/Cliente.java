public non-sealed class Cliente extends Pessoa {    

    public Cliente(String name, int age, Endereco endereco) {
        super(name, age, endereco);
    }

    @Override
    public int getAge() {
        return super.getAge();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public String apresentarse() {
        return "Cliente: " + getName() + ", Idade: " + getAge();
    }

    
}
