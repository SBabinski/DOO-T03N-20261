Paradigmas de Programação: Imperativo e Declarativo

Na ciência da computação, os paradigmas definem a abordagem lógica utilizada para resolver problemas. Nesta análise, exploramos as distinções entre os modelos Imperativo e Declarativo.

1. Paradigma Imperativo

Foca no "como" a tarefa deve ser realizada. O programador descreve uma sequência detalhada de passos e comandos que alteram o estado do programa para atingir o resultado. É o modelo padrão de linguagens como Java e C.

2. Paradigma Declarativo

Foca no "o quê" deve ser resolvido. O programador descreve relações, fatos ou a lógica do problema, sem detalhar os passos exatos de execução. Exemplos clássicos incluem SQL e Prolog.

Comparação de Código: Java vs. Prolog

Abaixo, comparamos como ambos validam se uma pessoa tem direito à gratuidade (idade >= 65).

Java (Imperativo)

public boolean temGratuidade(int idade) {
    if (idade >= 65) {
        return true;
    } else {
        return false;
    }
}
