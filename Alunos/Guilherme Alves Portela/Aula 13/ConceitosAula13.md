Por: Guilherme Alves Portela

---

# Funções Lambda (Lambda Functions)

### Timestamp do vídeo que menciona o conceito: **01:45**

### **O que é?**
Funções Lambda são funções anônimas (sem nome) que podem ser passadas como argumento ou utilizadas diretamente no código, tornando-o mais conciso e funcional.

### **Pra que serve?**
Servem para simplificar a escrita de código, especialmente em operações com coleções, como filtragem, ordenação e processamento de dados.

### **Como é normalmente utilizado?**
São muito usadas em conjunto com interfaces funcionais e a API de Streams em Java, permitindo escrever menos código para operações comuns.

### **Exemplo de código:**

```java
@FunctionalInterface
interface OperacaoMatematica {
    int somar(int a, int b);
}

public class Main {
    public static void main(String[] args) {
        // Função lambda que soma dois números
        OperacaoMatematica soma = (a, b) -> a + b;
        
        System.out.println(soma.somar(5, 10)); // Imprime 15
    }
}
```

# DELEGAÇÃO

### Timestamp do vídeo que menciona o conceito: **02:10**

### **O Que É:**
    Delegação é uma técnica em que uma classe transfere a responsabilidade de executar uma determinada tarefa para outra classe ou objeto especializado nessa função.

    A classe não herda os poderes de outra, mas cria uma referência para ela e pede para o outro objeto executar o trabalho.

### **Pra Que Serve:**
    Serve para reutilizar código, reduzir a complexidade e tornar o sistema mais organizado, permitindo que cada classe tenha uma responsabilidade específica.



### **Como É Normalmente Utilizado?**
    É utilizada quando uma classe precisa de uma funcionalidade que já foi implementada em outra. 

    Em vez de recriar essa funcionalidade, ela delega a execução para o objeto responsável.

### **Exemplo De Código:**

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