/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author luizh
 */
public class EntradaInvalidaException extends Exception {
    public EntradaInvalidaException() {
        super("Erro: Entrada inválida! Digite apenas números.");
    }

    public EntradaInvalidaException(String mensagem) {
        super(mensagem);
    }
}