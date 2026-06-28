/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 *

/**
 *
 * @author luizh
 */
import javax.swing.JOptionPane;
public class NewJFrame extends javax.swing.JFrame {

    private double num1 = 0, num2 = 0, resultado = 0;
    private char operacao = ' ';
    private boolean novoNumero = true;

    public NewJFrame() {
        initComponents();
        txtDisplay.setOpaque(true);  
        getContentPane().setBackground(new java.awt.Color(153, 153, 153));
          
        setTitle("Calculadora");
        setLocationRelativeTo(null);
    }

    // -----------------------------------------------
    // Chame este método nos eventos de TODOS os botões
    // de número (btn0 ao btn9) e btnVirgula
    // -----------------------------------------------
    private void digitarNumero(String valor) {
        if (novoNumero) {
            txtDisplay.setText(valor);
            novoNumero = false;
        } else {
            String atual = txtDisplay.getText();
            if (valor.equals(".") && atual.contains(".")) return;
            txtDisplay.setText(atual + valor);
        }
    }
    
    private void calcular(String operacao) {
        // ← shadowing do "private double num2" da classe
    try {
        // Pega os valores do display e valida
        String textoAtual = txtDisplay.getText().trim();

        if (textoAtual.isEmpty()) {
            throw new EntradaInvalidaException("Erro: Nenhum valor foi digitado!");
        }

        // Tenta converter — lança EntradaInvalidaException se falhar
        double num2local;
        try {
            num2local = Double.parseDouble(textoAtual);
        } catch (NumberFormatException e) {
            throw new EntradaInvalidaException(
                "Erro: \"" + textoAtual + "\" não é um número válido!"
            );
        }

    double resultadoLocal = 0;
    

        switch (operacao) {
            case "+":
                resultadoLocal = num1 + num2local;
// etc.
                break;

            case "-":
                resultadoLocal = num1- num2local;
                break;

            case "*":
                resultadoLocal = num1 * num2local;
                break;

            case "/":
                // Lança DivisaoPorZeroException se divisor for zero
                if (num2local == 0) {
                    throw new ZeroException();
                }
                resultado = num1 / num2;
                break;
        }

        // Exibe resultado (sem casas decimais se for inteiro)
        if (resultado == (long) resultado) {
            txtDisplay.setText(String.valueOf((long) resultado));
        } else {
            txtDisplay.setText(String.valueOf(resultado));
        }

    } catch (ZeroException e) {
        // Mensagem amigável para divisão por zero
        txtDisplay.setText("Erro: ÷ 0");
        JOptionPane.showMessageDialog(
            this,
            e.getMessage(),
            "Divisão por Zero",
            JOptionPane.ERROR_MESSAGE
        );

    } catch (EntradaInvalidaException e) {
        // Mensagem amigável para entrada inválida
        txtDisplay.setText("Inválido");
        JOptionPane.showMessageDialog(
            this,
            e.getMessage(),
            "Entrada Inválida",
            JOptionPane.WARNING_MESSAGE
        );

    } catch (Exception e) {
        // Captura qualquer outro erro inesperado
        txtDisplay.setText("Erro");
        JOptionPane.showMessageDialog(
            this,
            "Erro inesperado: " + e.getMessage(),
            "Erro",
            JOptionPane.ERROR_MESSAGE
        );
    }
}

    private void definirOperacao(char op) {
        num1 = Double.parseDouble(txtDisplay.getText());
        operacao = op;
        novoNumero = true;
    }

    
    
  private void calcular() {
    try {
        String textoAtual = txtDisplay.getText().trim();

        if (textoAtual.isEmpty()) {
            throw new EntradaInvalidaException("Nenhum valor foi digitado!");
        }

        try {
            num2 = Double.parseDouble(textoAtual);
        } catch (NumberFormatException e) {
            throw new EntradaInvalidaException("\"" + textoAtual + "\" não é um número válido!");
        }

        switch (operacao) {
            case '+' -> resultado = num1 + num2;
            case '-' -> resultado = num1 - num2;
            case '*' -> resultado = num1 * num2;
            case '/' -> {
                if (num2 == 0) {
                    throw new ZeroException();  // ✅ exception personalizada
                }
                resultado = num1 / num2;
            }
        }

        // Exibe sem decimal se for inteiro
        if (resultado == (long) resultado)
            txtDisplay.setText(String.valueOf((long) resultado));
        else
            txtDisplay.setText(String.valueOf(resultado));

        novoNumero = true;

    } catch (ZeroException e) {
        txtDisplay.setText("Erro: ÷ 0");
        javax.swing.JOptionPane.showMessageDialog(this, e.getMessage(),
            "Divisão por Zero", javax.swing.JOptionPane.ERROR_MESSAGE);

    } catch (EntradaInvalidaException e) {
        txtDisplay.setText("Inválido");
        javax.swing.JOptionPane.showMessageDialog(this, e.getMessage(),
            "Entrada Inválida", javax.swing.JOptionPane.WARNING_MESSAGE);
    }
}
    
    // ... initComponents() gerado automaticamente pelo NetBeans
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtDisplay = new javax.swing.JLabel();
        btnIgual = new javax.swing.JButton();
        btn7 = new javax.swing.JButton();
        btn4 = new javax.swing.JButton();
        btnVirgula = new javax.swing.JButton();
        btn8 = new javax.swing.JButton();
        btn5 = new javax.swing.JButton();
        btn9 = new javax.swing.JButton();
        btn6 = new javax.swing.JButton();
        btn2 = new javax.swing.JButton();
        btnDiv = new javax.swing.JButton();
        btnMult = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        btn0 = new javax.swing.JButton();
        btn3 = new javax.swing.JButton();
        btn1 = new javax.swing.JButton();
        btnSoma = new javax.swing.JButton();
        btnSub = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 153, 153));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));

        txtDisplay.setBackground(new java.awt.Color(204, 255, 204));
        txtDisplay.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        txtDisplay.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtDisplay.setText("0");
        txtDisplay.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnIgual.setBackground(new java.awt.Color(204, 204, 204));
        btnIgual.setText("=");
        btnIgual.addActionListener(this::btnIgualActionPerformed);

        btn7.setBackground(new java.awt.Color(204, 204, 204));
        btn7.setText("7");
        btn7.addActionListener(this::btn7ActionPerformed);

        btn4.setBackground(new java.awt.Color(204, 204, 204));
        btn4.setText("4");
        btn4.addActionListener(this::btn4ActionPerformed);

        btnVirgula.setBackground(new java.awt.Color(204, 204, 204));
        btnVirgula.setText(",");
        btnVirgula.addActionListener(this::btnVirgulaActionPerformed);

        btn8.setBackground(new java.awt.Color(204, 204, 204));
        btn8.setText("8");
        btn8.addActionListener(this::btn8ActionPerformed);

        btn5.setBackground(new java.awt.Color(204, 204, 204));
        btn5.setText("5");
        btn5.addActionListener(this::btn5ActionPerformed);

        btn9.setBackground(new java.awt.Color(204, 204, 204));
        btn9.setText("9");
        btn9.addActionListener(this::btn9ActionPerformed);

        btn6.setBackground(new java.awt.Color(204, 204, 204));
        btn6.setText("6");
        btn6.addActionListener(this::btn6ActionPerformed);

        btn2.setBackground(new java.awt.Color(204, 204, 204));
        btn2.setText("2");
        btn2.addActionListener(this::btn2ActionPerformed);

        btnDiv.setBackground(new java.awt.Color(204, 204, 204));
        btnDiv.setText("/");
        btnDiv.addActionListener(this::btnDivActionPerformed);

        btnMult.setBackground(new java.awt.Color(204, 204, 204));
        btnMult.setText("x");
        btnMult.addActionListener(this::btnMultActionPerformed);

        btnLimpar.setBackground(new java.awt.Color(204, 204, 204));
        btnLimpar.setText("C");
        btnLimpar.addActionListener(this::btnLimparActionPerformed);

        btn0.setBackground(new java.awt.Color(204, 204, 204));
        btn0.setText("0");
        btn0.addActionListener(this::btn0ActionPerformed);

        btn3.setBackground(new java.awt.Color(204, 204, 204));
        btn3.setText("3");
        btn3.addActionListener(this::btn3ActionPerformed);

        btn1.setBackground(new java.awt.Color(204, 204, 204));
        btn1.setText("1");
        btn1.addActionListener(this::btn1ActionPerformed);

        btnSoma.setBackground(new java.awt.Color(204, 204, 204));
        btnSoma.setText("+");
        btnSoma.addActionListener(this::btnSomaActionPerformed);

        btnSub.setBackground(new java.awt.Color(204, 204, 204));
        btnSub.setText("-");
        btnSub.addActionListener(this::btnSubActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn5, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btn0, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn6, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                                .addComponent(btnSub, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnVirgula, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnIgual, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btn3, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSoma, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(txtDisplay, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn7, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn8, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn9, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnMult, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDiv, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDiv, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn7, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn8, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn9, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMult, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn6, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn5, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn4, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSub, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSoma, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn0, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnIgual, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVirgula, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn6ActionPerformed
        // TODO add your handling code here:
       
    digitarNumero("6");
    }//GEN-LAST:event_btn6ActionPerformed

    private void btnIgualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIgualActionPerformed
        // TODO add your handling code here:
         calcular();
    }//GEN-LAST:event_btnIgualActionPerformed

    private void btnVirgulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVirgulaActionPerformed
        // TODO add your handling code here:
        digitarNumero(".");
    }//GEN-LAST:event_btnVirgulaActionPerformed

    private void btnDivActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDivActionPerformed
        // TODO add your handling code here:
        definirOperacao('/');
    }//GEN-LAST:event_btnDivActionPerformed

    private void btnMultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMultActionPerformed
        // TODO add your handling code here:
        definirOperacao('*'); 
    }//GEN-LAST:event_btnMultActionPerformed

    private void btn9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn9ActionPerformed
        // TODO add your handling code here:
       
    digitarNumero("9");
    }//GEN-LAST:event_btn9ActionPerformed

    private void btn8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn8ActionPerformed
        // TODO add your handling code here:
       
    digitarNumero("8");
    }//GEN-LAST:event_btn8ActionPerformed

    private void btn7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn7ActionPerformed
        // TODO add your handling code here:
      
    digitarNumero("7");
    }//GEN-LAST:event_btn7ActionPerformed

    private void btn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn4ActionPerformed
        // TODO add your handling code here:
  
    digitarNumero("4");
    }//GEN-LAST:event_btn4ActionPerformed

    private void btn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn5ActionPerformed
        // TODO add your handling code here:
       
    digitarNumero("5");
    }//GEN-LAST:event_btn5ActionPerformed

    private void btnSubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubActionPerformed
        // TODO add your handling code here:
        definirOperacao('-');
    }//GEN-LAST:event_btnSubActionPerformed

    private void btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn1ActionPerformed
        // TODO add your handling code here:
         digitarNumero("1");
    }//GEN-LAST:event_btn1ActionPerformed

    private void btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2ActionPerformed
        // TODO add your handling code here:
         digitarNumero("2");
    }//GEN-LAST:event_btn2ActionPerformed

    private void btn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3ActionPerformed
        // TODO add your handling code here:
         digitarNumero("3");
    }//GEN-LAST:event_btn3ActionPerformed

    private void btnSomaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSomaActionPerformed
        // TODO add your handling code here:
        definirOperacao('+');
    }//GEN-LAST:event_btnSomaActionPerformed

    private void btn0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn0ActionPerformed
        // TODO add your handling code here:
         digitarNumero("0");
    }//GEN-LAST:event_btn0ActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        // TODO add your handling code here:
          txtDisplay.setText("0");
    num1 = num2 = resultado = 0;
    operacao = ' ';
    novoNumero = true;
    }//GEN-LAST:event_btnLimparActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new NewJFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn0;
    private javax.swing.JButton btn1;
    private javax.swing.JButton btn2;
    private javax.swing.JButton btn3;
    private javax.swing.JButton btn4;
    private javax.swing.JButton btn5;
    private javax.swing.JButton btn6;
    private javax.swing.JButton btn7;
    private javax.swing.JButton btn8;
    private javax.swing.JButton btn9;
    private javax.swing.JButton btnDiv;
    private javax.swing.JButton btnIgual;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnMult;
    private javax.swing.JButton btnSoma;
    private javax.swing.JButton btnSub;
    private javax.swing.JButton btnVirgula;
    private javax.swing.JLabel txtDisplay;
    // End of variables declaration//GEN-END:variables
}



