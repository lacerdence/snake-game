import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class JogoDaCobrinha extends JPanel implements ActionListener, KeyListener {
    private class Ladrilho{
        int x;
        int y;

        Ladrilho(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    int quadroLargura;
    int quadroAltura;
    int tamanhoLadrilho = 25;

    //Cobra
    Ladrilho cabecaDaCobra;
    ArrayList<Ladrilho> corpoDaCobra;

    //Comida
    Ladrilho comida;
    Random randomico;

    //Lógica do jogo
    Timer loopDoJogo;
    int velocidadeX;
    int velocidadeY;
    boolean gameOver = false;

    JogoDaCobrinha(int quadroLargura, int quadroAltura) {
        this.quadroLargura = quadroLargura;
        this.quadroAltura = quadroAltura;
        setPreferredSize(new Dimension(this.quadroLargura,this.quadroAltura));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        cabecaDaCobra = new Ladrilho(5,5);
        corpoDaCobra = new ArrayList<Ladrilho>();

        comida = new Ladrilho(10,10);
        randomico = new Random();
        setarComida();
        velocidadeX = 0;
        velocidadeY = 1;

        loopDoJogo = new Timer(100, this);
        loopDoJogo.start();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        desenhar(g);
    }

    public void desenhar(Graphics g) {
        //Grid da tela
        for (int i = 0; i < quadroLargura/tamanhoLadrilho; i++) {
           g.drawLine(i*tamanhoLadrilho,0,i*tamanhoLadrilho, quadroAltura);
           g.drawLine(0,i*tamanhoLadrilho, quadroLargura,i*tamanhoLadrilho);

        }
        //Comida
        g.setColor(Color.red);
        g.fillRect(comida.x*tamanhoLadrilho, comida.y*tamanhoLadrilho, tamanhoLadrilho, tamanhoLadrilho);

        //Cabeça da cobra
        g.setColor(Color.green);
        g.fillRect(cabecaDaCobra.x * tamanhoLadrilho, cabecaDaCobra.y * tamanhoLadrilho,
                    tamanhoLadrilho, tamanhoLadrilho);

        //Corpo da cobra
        for (int i = 0; i < corpoDaCobra.size(); i++) {
            Ladrilho pedacoDaCobra = corpoDaCobra.get(i);
            g.fillRect(pedacoDaCobra.x * tamanhoLadrilho, pedacoDaCobra.y * tamanhoLadrilho, tamanhoLadrilho, tamanhoLadrilho);
            
        }

        //Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game over: "+ String.valueOf(corpoDaCobra.size()), tamanhoLadrilho - 16, tamanhoLadrilho);
        }
        else {
            g.drawString("Score: " + String.valueOf(corpoDaCobra.size()), tamanhoLadrilho - 16, tamanhoLadrilho);
        }
        
    }

    public void setarComida(){
        comida.x = randomico.nextInt(quadroLargura/tamanhoLadrilho); //600/25 = 24
        comida.y = randomico.nextInt(quadroAltura/tamanhoLadrilho);
    }

    public boolean colisao(Ladrilho ladrilho1, Ladrilho ladrilho2) {
        return ladrilho1.x == ladrilho2.x && ladrilho1.y == ladrilho2.y;
    }

    public void move(){
        //comer a comida
        if (colisao(cabecaDaCobra, comida)) {
            corpoDaCobra.add(new Ladrilho(comida.x, comida.y));
            setarComida();
        }
        //Corpo da cobra
        for (int i = corpoDaCobra.size()-1; i >= 0 ; i--) {
            Ladrilho pedacoDaCobra = corpoDaCobra.get(i);
            if (i == 0) {
                pedacoDaCobra.x = cabecaDaCobra.x;
                pedacoDaCobra.y = cabecaDaCobra.y;
            }
            else {
                Ladrilho pedacoDaCobraAnt = corpoDaCobra.get(i-1);
                pedacoDaCobra.x = pedacoDaCobraAnt.x;
                pedacoDaCobra.y = pedacoDaCobraAnt.y;
            }
        }
        //Cabeça da cobra
        cabecaDaCobra.x += velocidadeX;
        cabecaDaCobra.y += velocidadeY;

       // Game Over
        for (int i = 0; i < corpoDaCobra.size(); i++) {
            Ladrilho pedacoDaCobra = corpoDaCobra.get(i);
            if (colisao(cabecaDaCobra, pedacoDaCobra)) {
                gameOver = true;
            }
            
        }
        if (cabecaDaCobra.x*tamanhoLadrilho < 0 || cabecaDaCobra.x*tamanhoLadrilho > quadroLargura ||
            cabecaDaCobra.y*tamanhoLadrilho < 0 || cabecaDaCobra.y*tamanhoLadrilho > quadroAltura) {
            gameOver = true;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            loopDoJogo.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && velocidadeY != 1) {
           velocidadeX = 0;
           velocidadeY = -1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocidadeY != -1) {
            velocidadeX = 0;
            velocidadeY = 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocidadeX != 1) {
            velocidadeX = -1;
            velocidadeY = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocidadeX != -1) {
            velocidadeX = 1;
            velocidadeY = 0;
        }
    }
    //Não precisamos
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }



}
