package Tetris.Interfaz;
//Clase que junta todas las clases anteriores ara crear el juego
import Tetris.Logica.Tablero;
import Tetris.Tetris;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.util.Scanner;

public class Ventana extends javax.swing.JFrame implements ActionListener,Runnable{
    //Atributos
    private String nombre;
    public static Timer timer;
    private Tetris juego;
    //Constructor
    public Ventana() {
	Scanner scanner = new Scanner(System.in);
	System.out.println("Dame tu nombre:");
	nombre = scanner.nextLine();
        timer=new Timer(500,this);
        juego=new Tetris();
        initComponents();
        panel.setFocusable(true);
          panel.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent evt) {
                juego.keyPressed(evt);
            }
        });
          
        setScores();
        setVisible(true);
    }
    
    private void setScores(){
        mejores.setText(nombre+"  "+ juego.puntuacion);
    }

    public static Timer getTimer(){
        return timer;
    }

    public void actionPerformed(ActionEvent e) {
        BufferedImage canvas=new BufferedImage(Tetris.tamPixel*Tablero.X_LENGTH,Tetris.tamPixel*Tablero.Y_LENGTH,BufferedImage.TYPE_INT_RGB);
        Graphics pinzel=canvas.getGraphics();
        juego.update();
	this.setScores();
        juego.draw(pinzel);
        panel.setIcon(new ImageIcon(canvas));
    }

    public void run() {
        timer.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JLabel();
        textScore = new javax.swing.JLabel();
        score = new javax.swing.JLabel();
        textMejores = new javax.swing.JLabel();
        mejores = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tetris");
        setResizable(false);

        textScore.setFont(new java.awt.Font("Book Antiqua", 1, 14)); // NOI18N
        textScore.setText("Score:");

        score.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        score.setText("0");

        textMejores.setFont(new java.awt.Font("Book Antiqua", 1, 14)); // NOI18N
        textMejores.setText("Mejores:");

        mejores.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mejores.setText("0");
        mejores.setToolTipText("");
        mejores.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(score, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textScore)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(textMejores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mejores, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textScore)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(score)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textMejores)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mejores, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel mejores;
    private javax.swing.JLabel panel;
    private javax.swing.JLabel score;
    private javax.swing.JLabel textMejores;
    private javax.swing.JLabel textScore;
    // End of variables declaration//GEN-END:variables
}
