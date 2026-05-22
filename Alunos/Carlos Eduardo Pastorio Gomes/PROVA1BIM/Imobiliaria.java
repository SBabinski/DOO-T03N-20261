public class Imobiliaria {
    private Contrato[] contratos = new Contrato[10];
    private int total = 0;

    public void adicionarContrato(Contrato c) {
        if (total < 10) {
            contratos[total] = c;
            total++;
            System.out.println("Contrato cadastrado.");
        } else {
            System.out.println("Limite atingido.");
        }
    }

    public void listarAtivos() {
        for (int i = 0; i < total; i++) {
            if (!contratos[i].isEncerrado()) {
                contratos[i].exibirContrato();
            }
        }
    }

    public void encerrarContrato(int posicao) {
        if (posicao >= 0 && posicao < total) {
            contratos[posicao].encerrarContrato();
            System.out.println("Contrato encerrado.");
        }
    }
}