**Nome:** Maria Gabriela Comiran

---

# Parallel Programming (Programação Paralela)

**Timestamp do vídeo que menciona o conceito:** 03:18

**O que é?:**
Programação paralela é um modelo de execução onde múltiplas tarefas são processadas ao mesmo tempo, utilizando diferentes núcleos do processador ou múltiplas threads.

**Pra que serve?**
Serve para aumentar o desempenho de aplicações, permitindo que tarefas complexas sejam divididas e executadas simultaneamente, reduzindo o tempo total de processamento.

**Como é normalmente utilizado?**
É utilizada principalmente com threads, pools de execução ou frameworks paralelos. Em Java, pode-se usar a API de threads ou bibliotecas como o `ExecutorService` para gerenciar tarefas concorrentes.

**Exemplo de código:**

```java
class Tarefa extends Thread {
    public void run() {
        System.out.println("Executando em paralelo: " + Thread.currentThread().getName());
    }
}

public class Main {
    public static void main(String[] args) {
        Tarefa t1 = new Tarefa();
        Tarefa t2 = new Tarefa();

        t1.start();
        t2.start();
    }
}
```

---

# Lambda Functions

**Timestamp do vídeo que menciona o conceito:** 01:45

**O que é?**
Lambda Functions são funções anônimas (sem nome) que podem ser passadas como argumento ou utilizadas diretamente no código, tornando-o mais conciso e funcional.

**Pra que serve?**
Servem para simplificar a escrita de código, especialmente em operações com coleções, como filtragem, ordenação e processamento de dados.

**Como é normalmente utilizado?**
São muito usadas em conjunto com interfaces funcionais e a API de Streams em Java, permitindo escrever menos código para operações comuns.

**Exemplo de código:**

```java
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5);

        numeros.forEach(n -> System.out.println(n)); // lambda
    }
}
```
