public class Imobiliaria {
    private Contrato[] contratos;
    private int totalContratos;

    public Imobiliaria() {
        contratos = new Contrato[10];
        totalContratos = 0;
    }

    public boolean adicionarContrato(Contrato contrato) {
        if (totalContratos >= 10) {
            System.out.println("Limite de contratos atingido.");
            return false;
        }
        contratos[totalContratos] = contrato;
        totalContratos++;
        System.out.println("Contrato cadastrado com sucesso.");
        return true;
    }

    public void encerrarContrato(int indice) {
        if (indice < 0 || indice >= totalContratos) {
            System.out.println("Índice inválido.");
            return;
        }
        contratos[indice].encerrar();
        System.out.println("Contrato encerrado.");
    }

    public void listarContratosAtivos() {
        System.out.println("===== Contratos Ativos =====");
        boolean encontrou = false;
        for (int i = 0; i < totalContratos; i++) {
            if (!contratos[i].isEncerrado()) {
                contratos[i].exibirDados();
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum contrato ativo.");
        }
    }

    public int getTotalContratos() { return totalContratos; }
    public Contrato[] getContratos() { return contratos; }
}
