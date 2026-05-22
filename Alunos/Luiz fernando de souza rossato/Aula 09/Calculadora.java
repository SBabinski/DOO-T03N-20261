import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Calculadora {

    static double numero1 = 0;
    static double numero2 = 0;
    static double resultado = 0;
    static String operador = "";


public static void main(String[] args) {

    JFrame frame = new JFrame("Calculadora");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400,500);
    frame.setLayout(null);

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(4,4,5,5));
    panel.setBounds(20,100,340,320);
    panel.setBackground(Color.WHITE);

    JTextField display = new JTextField();
    display.setBounds(20,20,340,50);
    display.setHorizontalAlignment(SwingConstants.RIGHT);
    display.setEditable(false);

    String[] buttonPainel = {
            "7","8","9","/",
            "4","5","6","*",
            "1","2","3","-",
            "+","0","C","="
    };

    for(String text : buttonPainel){

        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(80,80));

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try{

                    if(text.equals("C")){

                        numero1 = 0;
                        numero2 = 0;
                        resultado = 0;
                        operador = "";

                        display.setText("");
                    }

                    else if(text.equals("+") ||
                            text.equals("-") ||
                            text.equals("*") ||
                            text.equals("/")){

                        if(display.getText().isEmpty()){

                            throw new CalculadoraException(
                                    "Digite um número primeiro!"
                            );
                        }

                        numero1 = Double.parseDouble(display.getText());
                        operador = text;
                        display.setText("");
                    }

                    else if(text.equals("=")){

                        if(display.getText().isEmpty()){

                            throw new CalculadoraException(
                                    "Digite um número!"
                            );
                        }

                        numero2 = Double.parseDouble(display.getText());

                        switch(operador){

                            case "+":
                                resultado = numero1 + numero2;
                                break;

                            case "-":
                                resultado = numero1 - numero2;
                                break;

                            case "*":
                                resultado = numero1 * numero2;
                                break;

                            case "/":

                                if(numero2 == 0){

                                    throw new CalculadoraException(
                                            "Não é possível dividir por zero!"
                                    );
                                }

                                resultado = numero1 / numero2;
                                break;
                        }

                        display.setText(String.valueOf(resultado));
                    }

                    else{

                        display.setText(display.getText() + text);
                    }

                }catch(NumberFormatException ex){

                    display.setText(
                            "Entrada inválida, por favor digite um número."
                    );

                }catch(CalculadoraException ex){

                    display.setText(ex.getMessage());

                }catch(Exception ex){

                    display.setText(
                            "Erro inesperado. tente novamente."
                    );
                }
            }
        });

        panel.add(button);
    }

    frame.add(display);
    frame.add(panel);

    frame.setVisible(true);
}
}