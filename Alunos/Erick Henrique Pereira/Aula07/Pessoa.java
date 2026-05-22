public abstract sealed class Pessoa permits Cliente, Vendedor, Gerente {

    private String name;
    private int age;
    private Endereco endereco;

    
    public Pessoa(String name, int age, Endereco endereco) {
        this.name = name;
        this.age = age;
        this.endereco = endereco;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public abstract String apresentarse ();
}
