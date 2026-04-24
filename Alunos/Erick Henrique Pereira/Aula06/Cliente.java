public non-sealed class Cliente extends Pessoa {    

    public Cliente(String name, int age, Endereco endereco) {
        super(name, age, endereco);
    }

    public void apresentarse() {
        System.out.println("Nome: " + getName());
        System.out.println("Idade: " + getAge());
    }

    @Override
    public int getAge() {
        return super.getAge();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Person{");
        sb.append("name=").append(getName());
        sb.append(", age=").append(getAge());
        sb.append('}');
        return sb.toString();
    }

    
}
