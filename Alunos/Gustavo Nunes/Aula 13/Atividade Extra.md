# Atividade Extra

**Nome:** Nunes

---

## Conceito 1 — Tipagem Estática e Forte (*Static and Strong Typing*)

**Timestamp:** 2:22

### O que é?

Em Java, as variáveis precisam ter um tipo definido no momento em que são declaradas, e esse tipo não muda enquanto o programa está rodando. Isso é o que chamamos de **tipagem estática**: o compilador sabe o tipo de cada variável antes mesmo de executar o código. Já a **tipagem forte** significa que o Java não permite misturar tipos incompatíveis sem uma conversão explícita — por exemplo, você não pode somar um `int` com uma `String` diretamente sem fazer um *cast* ou usar um método de conversão.

### Para que serve?

Serve principalmente para evitar bugs relacionados a uso incorreto de tipos. Se você tentar colocar um valor do tipo errado em uma variável, o compilador vai acusar o erro antes mesmo de o programa rodar. Isso torna o código mais seguro e mais fácil de entender, já que qualquer pessoa que leia o código sabe exatamente que tipo de dado cada variável guarda.

### Como normalmente é utilizado?

Na prática, toda vez que você declara uma variável em Java, precisa informar o tipo dela: `int`, `double`, `String`, `boolean`, etc. O compilador garante que você só vai usar essa variável de formas compatíveis com esse tipo. Tentativas de atribuir um valor incompatível resultam em erro de compilação, antes mesmo de o programa ser executado.

### Exemplo

```java
public class ExemploTipagem {
    public static void main(String[] args) {
        int idade = 20;
        String nome = "Ana";

        // Isso funciona normalmente
        System.out.println(nome + " tem " + idade + " anos.");

        // Isso causaria erro de compilação:
        // int numero = "texto"; // incompatível: String não é int
    }
}
```

---

## Conceito 2 — Garbage Collector

**Timestamp:** 2:36

### O que é?

O **Garbage Collector** (coletor de lixo) é um mecanismo automático do Java que gerencia a memória do programa. Quando objetos são criados com `new`, eles ocupam espaço na memória. Em algumas linguagens, o programador precisa liberar essa memória manualmente quando o objeto não for mais necessário. No Java, isso é feito automaticamente: a JVM identifica os objetos que não estão sendo mais referenciados por nenhuma parte do programa e os remove da memória por conta própria.

### Para que serve?

Serve para evitar dois problemas comuns em linguagens sem esse recurso: o **vazamento de memória** (quando o programa vai ocupando cada vez mais memória sem liberar) e o erro de tentar acessar um objeto que já foi removido manualmente. Com o Garbage Collector, o programador pode focar na lógica do programa sem precisar se preocupar com alocação e desalocação de memória a todo momento.

### Como normalmente é utilizado?

O programador não precisa fazer nada de especial para ativá-lo — ele já funciona automaticamente em segundo plano. Basta deixar de referenciar um objeto (por exemplo, atribuindo `null` a uma variável ou quando ela sai do escopo), e eventualmente o Garbage Collector vai identificar que aquele objeto não é mais acessível e vai liberar a memória ocupada por ele.

### Exemplo

```java
public class ExemploGC {
    public static void main(String[] args) {
        // Objeto criado e referenciado por "texto"
        String texto = new String("Olá, mundo!");
        System.out.println(texto);

        // A referência é removida; o objeto fica elegível para coleta
        texto = null;

        // A partir daqui, o Garbage Collector pode liberar a memória
        // que o objeto "Olá, mundo!" estava ocupando.
        System.out.println("Referência removida.");
    }
}
```