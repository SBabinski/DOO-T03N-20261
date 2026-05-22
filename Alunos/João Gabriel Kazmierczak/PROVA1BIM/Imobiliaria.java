package fag;

import java.util.ArrayList;

public class Imobiliaria {
    private ArrayList<ContratoAluguel> contratos;
    private final int LIMITE_CONTRATOS = 10;

    public Imobiliaria() {
        contratos = new ArrayList<>();
    }

    public boolean adicionarContrato(ContratoAluguel contrato) {
        if (contratos.size() >= LIMITE_CONTRATOS) {
            return false;
        }

        contratos.add(contrato);
        return true;
    }

    public void listarContratosAtivos() {
        boolean encontrouAtivo = false;

        for (int i = 0; i < contratos.size(); i++) {
            ContratoAluguel contrato = contratos.get(i);

            if (!contrato.isEncerrado()) {
                System.out.println("Contrato " + i + ":");
                contrato.exibirDadosContrato();
                System.out.println("-------------------------");
                encontrouAtivo = true;
            }
        }

        if (!encontrouAtivo) {
            System.out.println("Nao existem contratos ativos.");
        }
    }

    public void listarTodosContratos() {
        if (contratos.isEmpty()) {
            System.out.println("Nenhum contrato cadastrado.");
            return;
        }

        for (int i = 0; i < contratos.size(); i++) {
            System.out.println("Contrato " + i + ":");
            contratos.get(i).exibirDadosContrato();
            System.out.println("-------------------------");
        }
    }

    public boolean encerrarContrato(int indice) {
        if (indice < 0 || indice >= contratos.size()) {
            return false;
        }

        contratos.get(indice).encerrarContrato();
        return true;
    }

    public int getQuantidadeContratos() {
        return contratos.size();
    }
}
