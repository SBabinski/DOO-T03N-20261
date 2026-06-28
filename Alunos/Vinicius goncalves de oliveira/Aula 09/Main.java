import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame janela = new JFrame("Meu Programa");
        janela.setLayout(new BorderLayout());

        // visor
        JTextField visor = new JTextField();
        visor.setEditable(false);
        visor.setFont(new Font("Arial", Font.BOLD, 40));

        janela.add(visor, BorderLayout.NORTH);

        // painel dos botoes
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));

        String[] botoes = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        //salvar numero
        StringBuilder calculo = new StringBuilder();

        //vetores pra pegar os numero e operadores
        double[] numero1 = {0};

        String[] operador = {""};

        //mostrnando os numeros da tela (treco chato de entender e de fazer )
        for (String texto : botoes) {

            JButton botaoADD = new JButton(texto);

            //corzinha pra deixar top
            botaoADD.setBackground(Color.black);
            botaoADD.setForeground(Color.WHITE);


            botaoADD.addActionListener(e -> {

                //dar clear nos comandos
                if(texto.equals("C")){
                    calculo.setLength(0);
                    visor.setText("");
                }

                else if(texto.equals("=")){
//try cat pra erro e nao matar o sistema
                    try {

                        double resultado =
                                calcular(calculo.toString());

                        visor.setText(
                                calculo + " = " + resultado
                        );

                    } catch (Exception ex){

                        visor.setText("Erro");

                    }

                }

                // adicionar texto
                else {

                    calculo.append(texto);

                    visor.setText(
                            calculo.toString()
                    );

                }

            });

            panel.add(botaoADD);
        }
//monta a janela (e faz com que qunado feche ele encera em vez de ficar rodando)
        janela.add(panel, BorderLayout.CENTER);
        janela.setSize(400,500);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setVisible(true);
    }

    //calCUlo
    public static double calcular(String calculo){

        String operador = "";
        double resultado = 0;

        String numeroAtual = "";

        for(int i = 0; i < calculo.length(); i++){

            char x = calculo.charAt(i);

            if(Character.isDigit(x) || x == '.'){

                numeroAtual += x;

            } else {

                double numero = Double.parseDouble(numeroAtual);

                if(operador.equals("")){
                    resultado = numero;
                }

                else if(operador.equals("+")){
                    resultado += numero;
                }

                else if(operador.equals("-")){
                    resultado -= numero;
                }

                else if(operador.equals("*")){
                    resultado *= numero;
                }

                else if(operador.equals("/")){
                    resultado /= numero;
                }

                operador = String.valueOf(x);

                numeroAtual = "";
            }
        }

        double ultimoNumero =
                Double.parseDouble(numeroAtual);

        if(operador.equals("+")){
            resultado += ultimoNumero;
        }

        else if(operador.equals("-")){
            resultado -= ultimoNumero;
        }

        else if(operador.equals("*")){
            resultado *= ultimoNumero;
        }

        else if(operador.equals("/")){
            resultado /= ultimoNumero;
        }

        return resultado;
    }
}