package Tetris.Interfaz;

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
import java.io.*;

public class Ventana extends javax.swing.JFrame implements ActionListener,Runnable{

    public static Timer timer;
    private Tetris juego;
    public static int puntos=0;
    public static String[] nombres = new String[17];
    public static int[] matrizpuntos = new int[17];
    
    public Ventana(String foo) throws IOException, ClassNotFoundException{ 
        nombres[15] = foo;
        timer=new Timer(500,this);
        juego=new Tetris();
        initComponents();
        panel.setFocusable(true);
        panel.addKeyListener(new KeyAdapter(){
            @Override //Metodo que interactua con el teclado en caso de que se presiona la tecla espaciadora para bajar la pieza
            public void keyPressed(KeyEvent evt) {
                if(evt.getKeyCode() == KeyEvent.VK_SPACE){
                    timer.setDelay(1);
                }
                juego.keyPressed(evt);
            }
        });
        getLectura();
        setScores();
        setVisible(true);
    }
    /*
    *Metodo que suma puntos 
    *
    */
    public static void sumaPuntos(){
       puntos++;
       score.setText(Integer.toString(puntos)); 
    }
    /*
    *Metodo que obtiene la lectura de un archivo.
    *
    */
    public static void getLectura() throws IOException, ClassNotFoundException{
        int aux4 = 0;
        ObjectInputStream ois = null;
        try{
            File f = new File("datos.obj");
            FileInputStream fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);
            while (true) {
                Personas p = (Personas) ois.readObject();
                nombres[aux4]= p.getNombre();
                matrizpuntos[aux4]= p.getPuntos();
                aux4++;
            }
        }
        catch (IOException io) {
            System.out.print("FIN..."+ aux4);
        }
        finally {
            ois.close();
        } 
    }
    /*
    *Metodo que intercambia valores en un arreglo de enteros.
    *
    */
    public static void swapPuntos(int[] a,int i, int j){
        int t = a[j];
        a[j] = a[i];
        a[i] = t;
    }
    /*
    *Metodo que intercambia valores en un arreglo de Strings
    *
    */
    public static void swapNombres(String[] a,int i, int j){
        String t = a[j];
        a[j] = a[i];
        a[i] = t;
    }
    /*
    *Metodo que Escribe en el archivo las puntuaciones.
    *
    */
    public static void setEscritura(){
        try {
        //System.out.println("setEscrituraaaa"); 
        File f=new File("datos.obj");
        FileOutputStream fos = new FileOutputStream(f);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        matrizpuntos[15] = puntos;
        //System.out.println(puntos);

        boolean desorden=true;
        while (desorden) {
            desorden = false;
            for (int i=0;i<=14 ;i++ ) {
                if (matrizpuntos[i]<matrizpuntos[i+1]) {
                    swapPuntos(matrizpuntos,i,i+1);
                    swapNombres(nombres,i,i+1);
                    desorden=true;
                    System.out.println(i);
                    break;
                }
            }
        }
            for (int j=0;j<=14; j++) {
            oos.writeObject(new Personas(nombres[j],matrizpuntos[j])); 
            }
            oos.close();
        }catch (IOException e){
            System.out.println("2");
        }
    }

    /*
    *Metodo que Escribe los puntajes.
    *
    */
    private void setScores(){
       String s = "";
       s="<html>";
       for (int i =0;i<=14 ;i++ ) {
        s= s + nombres[i] + "->" +  Integer.toString(matrizpuntos[i])  + "<br>";
        } 
        s= s + "</html>";
       // mejores.setText("<html>1.-Manuel 500<br>2.-Jose 499</html>");
        mejores.setText(s);
        score.setText(Integer.toString(puntos));
    }
    /*
    *Metodo que regresa el timer.
    *
    */
    public static Timer getTimer(){
        return timer;
    }
    /*
    *Metodo que pinta y actualiza.
    *
    */
    public void actionPerformed(ActionEvent e){
        BufferedImage canvas=new BufferedImage(Tetris.tamPixel*Tablero.X_LENGTH,Tetris.tamPixel*Tablero.Y_LENGTH,BufferedImage.TYPE_INT_RGB);
        Graphics pinzel=canvas.getGraphics();
        juego.update();
        juego.draw(pinzel);
        panel.setIcon(new ImageIcon(canvas));
    }
    /*
    *Metodo que empieza a contar el timer y empieza el juego
    *
    */
    public void run() {
        timer.start();
    }

    /*
    *Metodo que inicializa todos los componentes de la pantalla
    *
    */
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
        score.setText(Integer.toString(puntos));

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
    static javax.swing.JLabel score;
    private javax.swing.JLabel textMejores;
    private javax.swing.JLabel textScore;
    // End of variables declaration//GEN-END:variables
}
