package loja.app;

import loja.controller.VendaController;

public class Main {

    public static void main(String[] args) {

        // Cria instancia do sistema
        VendaController vendaController = new VendaController();

        // inicia instancia
        vendaController.iniciar();
    }
}