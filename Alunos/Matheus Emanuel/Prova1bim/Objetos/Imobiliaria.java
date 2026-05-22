public class Imobiliaria {
    private static final int capacidade_max = 10;
    private Contrato[] contratos;
    private int totalContratos;

    public Imobiliaria() {
        this.contratos = new Contrato[capacidade_max];
        this.totalContratos = 0;
    }

    public boolean adicionarContrato(Contrato contrato) {
        if (totalContratos >= capacidade_max) {
            System.out.println("Capacidade máxima de contratos atingida (" + capacidade_max + ").");
            return false;
        }
        contratos[totalContratos] = contrato;
        totalContratos++;
        System.out.println("Contrato cadastrado com sucesso! (" + totalContratos + "/" + capacidade_max + " contratos)");
        return true;
    }

    public void listarContratosAtivos() {
        System.out.println("======= Contratos Ativos =======");
        boolean encontrou = false;
        for (int i = 0; i < totalContratos; i++) {
            if (!contratos[i].isEncerrado()) {
                contratos[i].exibirDados();
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum contrato ativo no momento.");
        }
    }

    public boolean encerrarContrato(int indice) {
        if (indice < 0 || indice >= totalContratos) {
            System.out.println("Índice inválido.");
            return false;
        }
        if (contratos[indice].isEncerrado()) {
            System.out.println("Este contrato já está encerrado.");
            return false;
        }
        contratos[indice].encerrar();
        return true;
    }

    public void listarTodosContratos() {
        if (totalContratos == 0) {
            System.out.println("Nenhum contrato cadastrado.");
            return;
        }
        for (int i = 0; i < totalContratos; i++) {
            System.out.println("[" + i + "] Inquilino: " + contratos[i].getInquilino().getNome()
                + " | Imóvel: " + contratos[i].getImovel().getEndereco()
            + " | Situação: " + (contratos[i].isEncerrado() ? "Encerrado" : "Ativo"));
        }
    }

    public int getTotalContratos() {
        return totalContratos;
    }
}