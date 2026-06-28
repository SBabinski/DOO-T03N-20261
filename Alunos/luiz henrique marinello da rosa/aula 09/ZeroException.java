/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author luizh
 */
public class ZeroException extends Exception{
    public ZeroException(){
    super("Erro: divisão impossivel");
            }
  public ZeroException(String mensagem){
  super (mensagem);
  }   
}
