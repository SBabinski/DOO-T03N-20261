public class Imobiliaria {

    private Contrato[] contratos;
    private int totalContratos;

    public Imobiliaria() {
        this.contratos = new Contrato[10];
        this.totalContratos = 0;
    }

    public boolean adicionarContrato(Contrato contrato) {
        if (totalContratos >= 10) {
            System.out.println("Nao ha espaco disponivel para novos contratos.");
            return false;
        }
        contratos[totalContratos] = contrato;
        totalContratos++;
        System.out.println("Contrato registrado com sucesso.");
        return true;
    }

    public void encerrarContrato(int indice) {
        if (indice < 0 || indice >= totalContratos) {
            System.out.println("Indice de contrato invalido.");
            return;
        }
        if (contratos[indice].isEncerrado()) {
            System.out.println("Este contrato ja esta encerrado.");
            return;
        }
        contratos[indice].encerrar();
        System.out.println("Contrato encerrado com sucesso.");
    }

    public void listarContratosAtivos() {
        System.out.println("==========================================");
        System.out.println("Contratos Ativos:");
        boolean encontrou = false;
        for (int i = 0; i < totalContratos; i++) {
            if (!contratos[i].isEncerrado()) {
                contratos[i].exibirDados();
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum contrato ativo encontrado.");
        }
        System.out.println("==========================================");
    }

    public void listarTodosContratos() {
        System.out.println("==========================================");
        System.out.println("Todos os Contratos:");
        if (totalContratos == 0) {
            System.out.println("Nenhum contrato cadastrado.");
        }
        for (int i = 0; i < totalContratos; i++) {
            System.out.print("[" + i + "] ");
            contratos[i].exibirDados();
        }
        System.out.println("==========================================");
    }

    public int getTotalContratos() {
        return totalContratos;
    }

    public Contrato getContrato(int indice) {
        if (indice < 0 || indice >= totalContratos) {
            return null;
        }
        return contratos[indice];
    }
}