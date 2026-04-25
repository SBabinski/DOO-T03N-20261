public class Imobiliaria {
    private Contrato[] contratos = new Contrato[10];
    private int totalContratos = 0;

    public boolean adicionarContrato(Contrato contrato) {
        if (totalContratos < 10) {
            contratos[totalContratos++] = contrato;
            return true;
        }
        return false;
    }

    public void listarContratosAtivos() {
        System.out.println("\n=== CONTRATOS ATIVOS ===");
        for (int i = 0; i < totalContratos; i++) {
            if (!contratos[i].isEncerrado()) {
                contratos[i].exibirContrato();
            }
        }
    }

    public void encerrarContrato(int indice) {
        if (indice >= 0 && indice < totalContratos) {
            contratos[indice].encerrar();
        }
    }
}
