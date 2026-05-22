public class Imobiliaria {
    private ContratoAluguel[] contratos;
    private int quantidade;

    public Imobiliaria(){
        contratos = new ContratoAluguel[10];
        quantidade = 0;
    }

    public boolean adicionarContrato(ContratoAluguel contrato) {
        if (quantidade < contratos.length) {
            contratos[quantidade] = contrato;
            quantidade++;
            return true;
        }
        return false;
    }

    public void listarContratosAtivos() {
        System.out.println("contratos ativos:");
        boolean encontrou = false;

        for (int i = 0; i < quantidade; i++) {
            if (!contratos[i].isEncerrado()) {
                contratos[i].exibirContrato();
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum contrato ativo encontrado.");
        }
    }
    public ContratoAluguel getContrato(int indice) {
        if (indice >= 0 && indice < quantidade) {
            return contratos[indice];
        }
        return null;
    }

}