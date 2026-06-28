# Atividade Extra - Aula 13

**Nome:** Samuel

---

## Conceito 1: Maven

**Timestamp do vídeo:** 0:40

### O que é?
O Maven é uma ferramenta de automação e gerenciamento de projetos Java. Ele organiza a estrutura do projeto e gerencia as dependências necessárias para o desenvolvimento.

### Para que serve?
Ele facilita o desenvolvimento, pois baixa automaticamente as bibliotecas utilizadas pelo projeto, além de automatizar tarefas como compilação, testes e geração de arquivos `.jar` e `.war`.

### Como é normalmente utilizado?
O Maven utiliza um arquivo chamado `pom.xml`, onde o desenvolvedor define as dependências, plugins e configurações do projeto. Ao executar comandos como `mvn clean install`, ele realiza todas as etapas necessárias para construir a aplicação.

### Exemplo de código

```xml
<dependencies>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.10.2</version>
    </dependency>
</dependencies>
```

---

## Conceito 2: NullPointerException

**Timestamp do vídeo:** 0:47

### O que é?
NullPointerException é uma exceção lançada quando o programa tenta acessar um objeto que possui valor `null`.

### Para que serve?
Na verdade, ela não é uma funcionalidade, mas sim um aviso de que o código tentou utilizar um objeto que não foi inicializado.

### Como é normalmente utilizada?
Ela ocorre quando um método ou atributo é chamado em uma variável que não aponta para nenhum objeto. Para evitar esse erro, é comum verificar se a variável é diferente de `null` antes de utilizá-la.

### Exemplo de código

```java
public class ExemploNull {
    public static void main(String[] args) {
        String nome = null;
        System.out.println(nome.length()); 
    }
}
```