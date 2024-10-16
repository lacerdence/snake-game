//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        int quadroLargura = 600;
        int quadroAltura = quadroLargura;

        JFrame frame = new JFrame("Cobra");
        frame.setVisible(true);
        frame.setSize(quadroLargura, quadroAltura);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JogoDaCobrinha jogoCobrinha = new JogoDaCobrinha(quadroLargura,quadroAltura);
        frame.add(jogoCobrinha);
        frame.pack(); // Faz o cabeçalho NÃO pertencer ao tamanho do Panel. Ficando de fato 600 x 600
        jogoCobrinha.requestFocus();
    }
}