public class Imobiliaria {
    private Contrato[] contratos;
    private int totalContratos;
    public Imobiliaria() {
        contratos = new Contrato[10];
        totalContratos = 0;
    }
    public boolean adicionarContrato(Contrato contrato) {
        if (totalContratos >= 10) {
            System.out.println("Limite de contratos atingido!");
            return false;
        }
        contratos[totalContratos] = contrato;
        totalContratos++;
        System.out.println("Contrato adicionado com sucesso!");
        return true;
    }
    public void listarContratosAtivos() {
        System.out.println("=== Contratos Ativos ===");
        boolean encontrou = false;
        for (int i = 0; i < totalContratos; i++) {
            if (!contratos[i].isEncerrado()) {
                contratos[i].exibirInfo();
                System.out.println("------------------------");
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum contrato ativo encontrado.");
        }
    }
    public Contrato[] getContratos() {
        return contratos;
    }
    public int getTotalContratos() {
        return totalContratos;
    }
}
