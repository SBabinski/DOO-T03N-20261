# __ATIVIDADE SOBRE CONCEITOS NOVOS DE PROGRAMAÇÃO__
        Nessa atividade, vou abordar dois conceitos que para mim, são novos na programação, levando em consideração 
        que são relativos também à linguagem Kotlin, abordada no vídeo.

## __CONCEITO 1__

**CONCEITO** : *DATACLASS* <br>
**TIME STAMP DO VIDEO QUE MENCIONA ELE** : *02:10* 

### **O Que É:**
        Data Class é um tipo de classe utilizada principalmente para armazenar dados. Seu objetivo é representar 
        informações de forma simples e organizada, sem a necessidade de criar muitos métodos ou comportamentos complexos.

### **Pra Que Serve:**
    Serve para modelar entidades do sistema, como: 
- pessoas, 
- produtos, 
- clientes 
- ou qualquer objeto que possua atributos que precisam 
ser armazenados e manipulados.

### **Como É Normalmente Utilizado?**
        É normalmente utilizada quando uma classe tem como principal função guardar informações. 
        Em muitas linguagens, as Data Classes geram automaticamente métodos úteis para comparação, 
    exibição e acesso aos dados.
        No Java, esse conceito não é comumente usado, o mais próximo, segundo minhas pesquisas, são os Records, 
    similares às dataclasses, porém, tudo feito manualmente.
        No Kotlin é onde as implementações de getters, setters, equals(), hashCode() e toString() etc 
    são feitas automaticamente na criação da classe dataclass. 

### **Exemplo De Código:**

```código Java```

```java
public class Pessoa {
    private String nome;
    private int idade;

    public Pessoa(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    @Override
    public String toString() {
        return "Pessoa{nome='" + nome + "', idade=" + idade + "}";
    }

    public static void main(String[] args) {
        Pessoa p = new Pessoa("Maria", 20);
        System.out.println(p);
    }
}
```

<br>

```código Kotlin```

```Kotlin
data class Pessoa(
    val nome: String,
    val idade: Int
)

fun main() {
    val p = Pessoa("Maria", 20)
    println(p)
}
```

<br>

---
<br>

## __CONCEITO 2__

**CONCEITO** : *DELEGAÇÃO*<br>
**TIME STAMP DO VIDEO QUE MENCIONA ELE** : *02:10*

### **O Que É:**
        Delegação é uma técnica em que uma classe transfere a responsabilidade de executar 
    uma determinada tarefa para outra classe ou objeto especializado nessa função.
        A classe não herda os poderes de outra, mas cria uma referência para ela e pede para o outro 
    objeto executar o trabalho.

### **Pra Que Serve:**
        Serve para reutilizar código, reduzir a complexidade e tornar o sistema mais organizado, 
    permitindo que cada classe tenha uma responsabilidade específica.



### **Como É Normalmente Utilizado?**
    É utilizada quando uma classe precisa de uma funcionalidade que já foi implementada em outra. 
    Em vez de recriar essa funcionalidade, ela delega a execução para o objeto responsável.

### **Exemplo De Código:**

```código Java```

```java
interface Impressora {
    void imprimir();
}

class ImpressoraLaser implements Impressora {
    @Override
    public void imprimir() {
        System.out.println("Imprimindo documento...");
    }
}

class Computador {
    private Impressora impressora;

    public Computador(Impressora impressora) {
        this.impressora = impressora;
    }

    public void imprimirDocumento() {
        impressora.imprimir();
    }

    public static void main(String[] args) {
        Impressora impressora = new ImpressoraLaser();
        Computador pc = new Computador(impressora);

        pc.imprimirDocumento();
    }
}
```

<br>

```código Kotlin```

```kotlin
interface Impressora {
    fun imprimir()
}

class ImpressoraLaser : Impressora {
    override fun imprimir() {
        println("Imprimindo documento...")
    }
}

class Computador(private val impressora: Impressora) {

    fun imprimirDocumento() {
        impressora.imprimir()
    }
}

fun main() {
    val pc = Computador(ImpressoraLaser())
    pc.imprimirDocumento()
}
```