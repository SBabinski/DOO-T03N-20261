

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;

public class Main {

    public static void main(String[] args) {

        aplicarTemaNetflix();

        SwingUtilities.invokeLater(() -> {
            Tela tela = new Tela();
            tela.setVisible(true);
        });
    }

    // Configura as cores padrao de TODOS os componentes Swing.
    // deixando a tela parecida com a paleta da Netflix(Minha Escolha).
    // fundo preto/cinza escuro, detalhes em vermelho e texto branco.
    private static void aplicarTemaNetflix() {
        Color pretoFundo = new Color(20, 20, 20);       // fundo principal
        Color cinzaEscuro = new Color(35, 35, 35);       // fundo de campos
        Color vermelhoNetflix = new Color(229, 9, 20);   // cor de destaque
        Color branco = new Color(245, 245, 245);         // texto

        try {
            // Procura o tema Nimbus, que permite customizar bem as cores.
            for (UIManager.LookAndFeelInfo tema : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(tema.getName())) {
                    UIManager.setLookAndFeel(tema.getClassName());
                    break;
                }
            }

            // Cores gerais de fundo
            UIManager.put("control", pretoFundo);
            UIManager.put("nimbusBase", pretoFundo);
            UIManager.put("nimbusBlueGrey", cinzaEscuro);
            UIManager.put("nimbusLightBackground", cinzaEscuro);
            UIManager.put("info", cinzaEscuro);

            // Cores  selecao, foco, botoes
            UIManager.put("nimbusFocus", vermelhoNetflix);
            UIManager.put("nimbusSelectionBackground", vermelhoNetflix);
            UIManager.put("nimbusSelectedText", branco);
            UIManager.put("nimbusOrange", vermelhoNetflix);
            UIManager.put("nimbusRed", vermelhoNetflix);

            // Cor do texto 
            UIManager.put("text", branco);
            UIManager.put("controlText", branco);
            UIManager.put("Label.foreground", branco);
            UIManager.put("TextField.foreground", branco);
            UIManager.put("TextArea.foreground", branco);
            UIManager.put("List.foreground", branco);
            UIManager.put("TabbedPane.foreground", branco);

            // Fonte
            Font fonte = new Font("Segoe UI", Font.PLAIN, 14);
            UIManager.put("defaultFont", fonte);

        } catch (Exception e) {
            System.out.println("Nao foi possivel aplicar o tema Netflix. Usando o padrao.");
        }
    }
}