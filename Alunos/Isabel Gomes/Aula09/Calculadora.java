package fag;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calculadora {
	
	static double numero1;
	static String operacao;
	
	// Controla se o usuario ja clicou em uma operacao e esta esperando o segundo numero
	static boolean esperandoSegundoNumero = false;

	public static void main(String[] args) {
	
		//janela 
		JFrame frame = new JFrame("Calculadora simples");	//janela com titulo
		frame.setSize(350,450);	//tam janela
        frame.setResizable(false);	//n deixa o usuario mexer dos lados pra aumentar
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//a aplicacao termina quando clica no x
		
		//outra janela
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);	//cor de fundo
		
		//cria o visor da caluclçadora
		panel.setLayout(null);
		JTextField visor =  new JTextField();
	    visor.setBounds(0, 0, 350, 80);
	    
	    
	    visor.requestFocusInWindow();
	    
	    
	//    visor.setEditable(false);
		panel.add(visor);	//coloca em cima o visor
		
		//afdicionando no janelao(frame)
		frame.add(panel);
		
		//botoes
		//numeros
		JButton b0 = new JButton("0");
		b0.setBounds(90, 310, 60, 60);
		b0.setFocusable(false);
		b0.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				visor.setText(visor.getText() + "0");
				 visor.requestFocusInWindow();
			}
		});
		panel.add(b0);
		
		JButton b1 = new JButton("1");
		b1.setBounds(20, 240, 60, 60);
		b1.setFocusable(false);
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				visor.setText(visor.getText() + "1");
				 visor.requestFocusInWindow();
			}
		});
		panel.add(b1);
		
		JButton b2 = new JButton("2");
		b2.setBounds(90, 240, 60, 60);
		b2.setFocusable(false);
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				visor.setText(visor.getText() + "2");
				  visor.requestFocusInWindow();
			}
		});
		panel.add(b2);
		
		JButton b3 = new JButton("3");
		b3.setBounds(160, 240, 60, 60);
		b3.setFocusable(false);
		b3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				visor.setText(visor.getText() + "3");
				  visor.requestFocusInWindow();
			}
		});
		panel.add(b3);
		
		JButton b4 = new JButton("4");
		b4.setBounds(20, 170, 60, 60);
		b4.setFocusable(false);
		b4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				visor.setText(visor.getText() + "4");
				  visor.requestFocusInWindow();
			}
		});
		panel.add(b4);
		
		JButton b5 = new JButton("5");
		b5.setBounds(90, 170, 60, 60);
		b5.setFocusable(false);
		b5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				visor.setText(visor.getText() + "5");
				  visor.requestFocusInWindow();
			}
		});
		panel.add(b5);
		
		JButton b6 = new JButton("6");
		b6.setBounds(160, 170, 60, 60);
		b6.setFocusable(false);
		b6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				visor.setText(visor.getText() + "6");
				  visor.requestFocusInWindow();
			}
		});
		panel.add(b6);
		
		JButton b7 = new JButton("7");
		b7.setBounds(20, 100, 60, 60);
		b7.setFocusable(false);
		b7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				visor.setText(visor.getText() + "7");
				  visor.requestFocusInWindow();
			}
		});
		panel.add(b7);
		
		JButton b8 = new JButton("8");
		b8.setBounds(90, 100, 60, 60);
		b8.setFocusable(false);
		b8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				visor.setText(visor.getText() + "8");
				  visor.requestFocusInWindow();
			}
		});
		panel.add(b8);
		
		JButton b9 = new JButton("9");
		b9.setBounds(160, 100, 60, 60);
		b9.setFocusable(false);
		b9.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				visor.setText(visor.getText() + "9");
				  visor.requestFocusInWindow();
			}
		});
		panel.add(b9);
		
		
		//operacoes
		JButton soma = new JButton("+");
		soma.setBounds(240, 100, 60, 60);
		soma.setFocusable(false);
		soma.setBackground(Color.ORANGE);
		soma.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (visor.getText().isEmpty() || esperandoSegundoNumero) {
					return;
				}
				
				try {
					numero1 = Double.parseDouble(visor.getText());
					operacao = "+";
					esperandoSegundoNumero = true;
					visor.setText("");
				}catch (NumberFormatException erro) {
					JOptionPane.showMessageDialog(frame, 
							"Digite apenas numeros!", 
							"Erro", 
							JOptionPane.ERROR_MESSAGE);
				}
				  visor.requestFocusInWindow();

			}
		});
		panel.add(soma);
		
		JButton div = new JButton("/");
		div.setBounds(240, 310, 60, 60);
		div.setFocusable(false);
		div.setBackground(Color.ORANGE);
		div.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (visor.getText().isEmpty() || esperandoSegundoNumero) {
					return;
				}
				
				try {
					numero1 = Double.parseDouble(visor.getText());
					operacao = "/";
					esperandoSegundoNumero = true;
					visor.setText("");
				} catch (NumberFormatException erro) {
					JOptionPane.showMessageDialog(frame, 
							"Digite apenas numeros!",
							"Erro",
							JOptionPane.ERROR_MESSAGE);
				}
				  visor.requestFocusInWindow();

				
			}	
		});
		panel.add(div);
		
		JButton mult = new JButton("*");
		mult.setBounds(240, 240, 60, 60);
		mult.setFocusable(false);
		mult.setBackground(Color.ORANGE);
		mult.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (visor.getText().isEmpty() || esperandoSegundoNumero) {
					return;
				}
				
				try {
					numero1 = Double.parseDouble(visor.getText());
					operacao = "*";
					esperandoSegundoNumero = true;
					visor.setText("");
					  
				} catch (NumberFormatException erro) {
					JOptionPane.showMessageDialog(frame, 
							"Digite apenas numeros!", 
							"Erro", 
							JOptionPane.ERROR_MESSAGE);
				}
				 visor.requestFocusInWindow();

			}	
		});
		panel.add(mult);
		
		JButton menos = new JButton("-");
		menos.setBounds(240, 170, 60, 60);
		menos.setFocusable(false);
		menos.setBackground(Color.ORANGE);
		menos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (visor.getText().isEmpty() || esperandoSegundoNumero) return;
				
				try {
					numero1 = Double.parseDouble(visor.getText());
					operacao = "-";
					esperandoSegundoNumero = true;
					visor.setText("");
				} catch (NumberFormatException erro) {
					JOptionPane.showMessageDialog(frame,
							"Digite apenas numeros!", 
							"Erro", 
							JOptionPane.ERROR_MESSAGE);
					}
			  visor.requestFocusInWindow();

			}		
		});
		panel.add(menos);
		
		
		//outros dois botao
		JButton limpar = new JButton("<--");
		limpar.setBounds(160, 310, 60, 60);
		limpar.setFocusable(false);
		limpar.setBackground(Color.ORANGE);
		limpar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				esperandoSegundoNumero = false;
				operacao = null;
				visor.setText("");
				  visor.requestFocusInWindow();
			}
		});
		panel.add(limpar);

		JButton igual = new JButton("=");
		igual.setFocusable(false);
		igual.setBounds(20, 310, 60, 60);
		igual.setBackground(Color.ORANGE);
		//igual.addActionListener(acaoIgual);
		igual.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			    if (visor.getText().isEmpty()) {
			        return;
			    }

			    try {
			        double resultado;

			        if (esperandoSegundoNumero) {
			            // calcula quando a operação foi escolhida pelos botoes
			            double numero2 = Double.parseDouble(visor.getText());
			            resultado = calcular(numero1, numero2, operacao, frame);
			        } else {
			            // calcula quando a operação foi digitada no teclado, exemplo: 5+3
			            String texto = visor.getText();

			            int posOperador = -1;
			            for (int i = 1; i < texto.length(); i++) {
			                char c = texto.charAt(i);
			                if (c == '+' || c == '-' || c == '*' || c == '/') {
			                    posOperador = i;
			                    break;
			                }
			            }

			            if (posOperador == -1) {
			                return;
			            }

			            numero1 = Double.parseDouble(texto.substring(0, posOperador));
			            operacao = String.valueOf(texto.charAt(posOperador));
			            double numero2 = Double.parseDouble(texto.substring(posOperador + 1));

			            resultado = calcular(numero1, numero2, operacao, frame);
			        }

			        visor.setText(String.valueOf(resultado));
			        esperandoSegundoNumero = false;
			        operacao = null;

			    } catch (DividirPorZeroException erro) {
			        JOptionPane.showMessageDialog(frame,
			                erro.getMessage(),
			                "Erro de divisao",
			                JOptionPane.ERROR_MESSAGE);

			    } catch (NumberFormatException erro) {
			        JOptionPane.showMessageDialog(frame,
			                "Digite apenas numeros!",
			                "Erro",
			                JOptionPane.ERROR_MESSAGE);
			    }

			    visor.requestFocusInWindow();
			}
			
			private double calcular(double numero1, double numero2, String operacao, JFrame frame) throws DividirPorZeroException{
				switch(operacao) {
				
				case "+":
					return numero1 + numero2;
				
				case "-":
					return numero1 - numero2;
				
				case "*":
					return numero1 * numero2;
				
				case "/":
					if (numero2 == 0) {
						throw new DividirPorZeroException("Nao pode dividir por zero !!");	
					}
					return numero1 / numero2;
				}
				return 0;
			}
		});
		
		panel.add(igual);
		
		frame.setVisible(true);
		visor.requestFocusInWindow();
	
	}

}
