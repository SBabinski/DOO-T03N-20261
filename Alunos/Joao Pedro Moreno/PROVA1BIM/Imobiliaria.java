public class Imobiliaria {

    private static final int MAX_CONTRATOS = 10;
    private Contrato[] contratos;
    private int totalContratos;

    public Imobiliaria() {
        this.contratos = new Contrato[MAX_CONTRATOS];
        this.totalContratos = 0;
    }

    // Adiciona um novo contrato se houver espaço
    public boolean adicionarContrato(Contrato contrato) {
        if (totalContratos >= MAX_CONTRATOS) {
            System.out.println("Limite de contratos atingido (máximo: " + MAX_CONTRATOS + ").");
            return false;
        }
        contratos[totalContratos] = contrato;
        totalContratos++;
        return true;
    }

    // Encerra um contrato pelo índice (base 1 para o usuário)
    public boolean encerrarContrato(int indice) {
        int idx = indice - 1;
        if (idx < 0 || idx >= totalContratos) {
            System.out.println("Contrato não encontrado.");
            return false;
        }
        if (contratos[idx].isEncerrado()) {
            System.out.println("Este contrato já está encerrado.");
            return false;
        }
        contratos[idx].setEncerrado(true);
        System.out.println("Contrato encerrado com sucesso!");
        return true;
    }

    // Lista apenas contratos ativos
    public void listarContratosAtivos() {
        System.out.println("\n=== Contratos Ativos ===");
        boolean encontrou = false;
        for (int i = 0; i < totalContratos; i++) {
            if (!contratos[i].isEncerrado()) {
                System.out.print("Contrato #" + (i + 1) + " | ");
                contratos[i].mostrarContrato();
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum contrato ativo no momento.");
        }
    }

    // Lista todos os contratos com seus índices
    public void listarTodosContratos() {
        System.out.println("\n=== Todos os Contratos ===");
        if (totalContratos == 0) {
            System.out.println("Nenhum contrato cadastrado.");
            return;
        }
        for (int i = 0; i < totalContratos; i++) {
            System.out.print("Contrato #" + (i + 1) + " | ");
            contratos[i].mostrarContrato();
        }
    }

    public int getTotalContratos() {
        return totalContratos;
    }
}
