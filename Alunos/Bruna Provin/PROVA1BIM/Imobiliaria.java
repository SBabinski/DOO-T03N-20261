public class Imobiliaria {
    private ContratoAluguel[] contratos;
    private int totalContratos;

    public Imobiliaria() {
        contratos = new ContratoAluguel[10];
        totalContratos = 0;
    }

    public boolean adicionarContrato(ContratoAluguel contrato) {
        if (totalContratos >= 10) {
            System.out.println("Capacidade maxima de contratos atingida.");
            return false;
        }
        contratos[totalContratos] = contrato;
        totalContratos++;
        System.out.println("Contrato #" + contrato.getId() + " adicionado com sucesso!");
        return true;
    }

    public void listarContratosAtivos() {
        System.out.println("\n--- Contratos Ativos ---");
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

    public ContratoAluguel[] getContratos() { return contratos; }
    public int getTotalContratos() { return totalContratos; }

    public ContratoAluguel buscarContratoPorId(int id) {
        for (int i = 0; i < totalContratos; i++) {
            if (contratos[i].getId() == id) {
                return contratos[i];
            }
        }
        return null;
    }
}