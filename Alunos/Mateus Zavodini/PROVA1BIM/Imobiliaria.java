public class Imobiliaria {
    Contrato[] contratos = new Contrato[10];
    int qtd = 0;

    public void adicionar(Contrato c) {
        if (qtd < 10) {
            contratos[qtd] = c;
            qtd++;
        } else {
            System.out.println("Limite de contratos atingido");
        }
    }

    public void listarAtivos() {
        for (int i = 0; i < qtd; i++) {
            if (!contratos[i].encerrado) {
                contratos[i].mostrar();
            }
        }
    }

    public void encerrar(int pos) {
        if (pos >= 0 && pos < qtd) {
            contratos[pos].encerrar();
        }
    }
}