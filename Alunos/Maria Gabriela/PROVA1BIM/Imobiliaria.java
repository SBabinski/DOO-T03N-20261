public class Imobiliaria {
    private Contrato[] contratos = new Contrato[10];
    private int qtd = 0;

    public void adicionarContrato(Contrato c) {
        if(qtd < 10) {
            contratos[qtd++] = c;
        } else {
            System.out.println("Limite de contratos atingido!");
        }
    }

    public void listarAtivos() {
        for(int i = 0; i < qtd; i++) {
            if(contratos[i].isAtivo()) {
                contratos[i].exibir();
            }
        }
    }

    public Contrato getContrato(int index) {
        if(index >= 0 && index < qtd) {
            return contratos[index];
        }
        return null;
    }
}