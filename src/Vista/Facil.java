package Vista;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import Controlador.ConexionMySQL;
import Controlador.ControladorLista;
import Modelo.Usuario;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 *
 * @author usuari
 */
public class Facil extends javax.swing.JFrame {

    /**
     * Creates new form Facil
     */
    ConexionMySQL conexion;
    ControladorLista controlador;
    int contador; // este contador nos va a permitir saber el número de aciertos que tiene el jugador

// se crea una lista auxiliar donde vamos a guardar los datos para el random
    int index = 0;
    ArrayList<String> listaAux = new ArrayList();
    ArrayList<String> lista;
    static String listas;

    private Timer crono1;
    private Timer crono2;
    private int centesimas = 99;
    private int segundos = 4;
    private int puntuacion = 0;
    private int maxPunt = 0;
    Usuario user;

    public Facil(String listas, Usuario usuario) throws SQLException {
        initComponents();
        // colocamos en false los enable para que el jugador no pueda interactuar con los botones hasta que no empiece a jugar
        b1.setEnabled(false);
        b2.setEnabled(false);
        b3.setEnabled(false);
        b4.setEnabled(false);
        user = usuario;
        conexion = new ConexionMySQL("pasapalabra", "root", "");
        controlador = new ControladorLista(conexion);
        conexion.conectar();
        lista = controlador.obtenerTodaLaListas(listas);  // se carga la lista con la que vamos a trabajar
        this.listas = listas;
        contador = 0;
        crono1 = new Timer(10, acciones);
        crono2 = new Timer(10, accionesC2);
        
        pbCrono1.setUI(new BasicProgressBarUI(){
            @Override
            protected void paintDeterminate(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g;
                int ancho = (int) (pbCrono1.getWidth()*pbCrono1.getPercentComplete());
                int alto = pbCrono1.getHeight();
                g2d.setColor(Color.GREEN);
                RoundRectangle2D barra = new RoundRectangle2D.Double(0,0,ancho,alto,0,0);
                g2d.fill(barra);
            }
        });   
        
        pbCrono2.setUI(new BasicProgressBarUI(){
            @Override
            protected void paintDeterminate(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g;
                int ancho = (int) (pbCrono2.getWidth()*pbCrono2.getPercentComplete());
                int alto = pbCrono2.getHeight();
                g2d.setColor(Color.GREEN);
                RoundRectangle2D barra = new RoundRectangle2D.Double(0,0,ancho,alto,0,0);
                g2d.fill(barra);
            }
        });
    }
    //Métodos

    public void activarBotones() {

        b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
        b4.setEnabled(true);

    }

    public void desactivarBotones() {

        b1.setEnabled(false);
        b2.setEnabled(false);
        b3.setEnabled(false);
        b4.setEnabled(false);
    }
    
    public void gestorPunt() {
        if(puntuacion>maxPunt){
            maxPunt = puntuacion;
            puntuacion = 0;
            tPuntuacion.setText(String.valueOf(maxPunt));
        }
    }

    public void actualizarTiempo() {
        String tiempo = (segundos <= 9 ? "0" : "") + segundos + ":" + (centesimas <= 9 ? "0" : "") + centesimas;
        jTCrono.setText(tiempo);
    }

    public void actualizarRojo() {
        jTCrono.setForeground(Color.red);
    }
       
    public void mensajeDerrota(){
    JOptionPane.showMessageDialog(this, "Has perdido Carajaula");
    
    
    }
    
    public void modificarBarra1(){
        pbCrono1.setValue(pbCrono1.getValue()-1);
    }
    
    public void vaciarBarra1(){
        pbCrono1.setValue(0);
    }
    
    public void modificarBarra2(){
        pbCrono2.setValue(pbCrono2.getValue()-1);
    }
    
    public void vaciarBarra2(){
        pbCrono2.setValue(0);
    }
    
    public void BarraRoja(){
        pbCrono2.setUI(new BasicProgressBarUI(){
            @Override
            protected void paintDeterminate(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g;
                int ancho = (int) (pbCrono2.getWidth()*pbCrono2.getPercentComplete());
                int alto = pbCrono2.getHeight();
                g2d.setColor(Color.RED);
                RoundRectangle2D barra = new RoundRectangle2D.Double(0,0,ancho,alto,0,0);
                g2d.fill(barra);
            }
        });
    }
    
    public void ocultarPalabra() {
        b1.setText("1");
        b2.setText("2");
        b3.setText("3");
        b4.setText("4");
        jTextField1.setText(listaAux.get(index));
        
    }
    
    private ActionListener acciones = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            centesimas--;
            modificarBarra1();
            if (centesimas == 0) {
                segundos--;
                centesimas = 99;
            }
            
            actualizarTiempo();

            if (segundos == 0 && centesimas == 1) {
                crono1.stop();
                centesimas = 99;
                segundos=19;
                vaciarBarra1();
                activarBotones();
                ocultarPalabra();
                crono2.start();
               
                
            }

            

        }
    };
    
    private ActionListener accionesC2 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {              
                centesimas --;          
                modificarBarra2();
                if (centesimas == 0) {
                    segundos--;
                    centesimas=99;
                }
                
                if (segundos == 5) {
                    actualizarRojo();
                    BarraRoja();
                }
                
                actualizarTiempo();
                
                if (segundos==0 && centesimas == 1) {
                    crono2.stop();
                    centesimas=0;
                    vaciarBarra2();
                    actualizarTiempo();
                    desactivarBotones();
                    mensajeDerrota();
                }
                
        }
    };

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        b1 = new javax.swing.JButton();
        b2 = new javax.swing.JButton();
        b3 = new javax.swing.JButton();
        b4 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        bComenzar = new javax.swing.JButton();
        jTCrono = new javax.swing.JTextField();
        pbCrono2 = new javax.swing.JProgressBar();
        pbCrono1 = new javax.swing.JProgressBar();
        tPuntuacion = new javax.swing.JTextField();
        bVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new javax.swing.OverlayLayout(getContentPane()));

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        b1.setText("1");
        b1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b1MouseClicked(evt);
            }
        });
        b1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b1ActionPerformed(evt);
            }
        });

        b2.setText("2");
        b2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b2MouseClicked(evt);
            }
        });
        b2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b2ActionPerformed(evt);
            }
        });

        b3.setText("3");
        b3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b3MouseClicked(evt);
            }
        });
        b3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b3ActionPerformed(evt);
            }
        });

        b4.setText("4");
        b4.setAutoscrolls(true);
        b4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b4MouseClicked(evt);
            }
        });
        b4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b4ActionPerformed(evt);
            }
        });

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(255, 255, 204));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        bComenzar.setText("Comenzar");
        bComenzar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bComenzarActionPerformed(evt);
            }
        });

        jTCrono.setEditable(false);
        jTCrono.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTCrono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTCronoActionPerformed(evt);
            }
        });

        pbCrono2.setBackground(new java.awt.Color(153, 204, 255));
        pbCrono2.setMaximum(2000);
        pbCrono2.setValue(1999);

        pbCrono1.setBackground(new java.awt.Color(153, 204, 255));
        pbCrono1.setMaximum(500);
        pbCrono1.setValue(499);

        tPuntuacion.setEditable(false);
        tPuntuacion.setBackground(new java.awt.Color(255, 255, 204));
        tPuntuacion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tPuntuacion.setText("0");

        bVolver.setText("Volver");
        bVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bVolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(b1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pbCrono1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTCrono)
                            .addComponent(pbCrono2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(bComenzar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField1)
                            .addComponent(tPuntuacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(bVolver, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(b4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pbCrono1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTCrono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pbCrono2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tPuntuacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bComenzar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bVolver)
                .addGap(81, 81, 81))
        );

        getContentPane().add(jPanel3);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void b1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b1ActionPerformed
        // TODO add your handling code here:

        if (lista.get(0).equals(jTextField1.getText())) {

            b1.setText(lista.get(0));
            b1.setEnabled(false);

            contador++;
            index++;
            puntuacion++;
            if (contador != 4) {

                jTextField1.setText(listaAux.get(index));
            } else {
                crono2.stop();
                JOptionPane.showMessageDialog(rootPane, "Enhorabuena! has ganado");
                puntuacion = 4 + segundos;
                
            }
            gestorPunt();
            
        } else {
            index = 0;
            jTextField1.setText(lista.get(index));
            activarBotones();
            contador = 0;
            puntuacion = 0;
        }

    }//GEN-LAST:event_b1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_jTextField1ActionPerformed

    private void b1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b1MouseClicked
        // TODO add your handling code here:


    }//GEN-LAST:event_b1MouseClicked

    private void bComenzarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bComenzarActionPerformed
        // TODO add your handling code here:

        Collections.shuffle(lista);

        for (int i = 0; i < 4; i++) {

            listaAux.add(lista.get(i));

        }
        Collections.shuffle(listaAux);

        b1.setText(lista.get(0));
        b2.setText(lista.get(1));
        b3.setText(lista.get(2));
        b4.setText(lista.get(3));

        crono1.start();

         bComenzar.setEnabled(false);
    }//GEN-LAST:event_bComenzarActionPerformed

    private void b2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b2MouseClicked
        // TODO add your handling code here:


    }//GEN-LAST:event_b2MouseClicked

    private void b3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b3MouseClicked
        // TODO add your handling code here:


    }//GEN-LAST:event_b3MouseClicked

    private void b4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b4MouseClicked
        // TODO add your handling code here:


    }//GEN-LAST:event_b4MouseClicked

    private void b4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b4ActionPerformed
        // TODO add your handling code here:

        if (lista.get(3).equals(jTextField1.getText())) {

            b4.setText(lista.get(3));
            b4.setEnabled(false);
            contador++;
            index++;
            puntuacion++;
            if (contador != 4) {

                jTextField1.setText(listaAux.get(index));
            } else {
                crono2.stop();
                JOptionPane.showMessageDialog(rootPane, "Enhorabuena! has ganado");
                puntuacion = 4 + segundos;
              
            }
            gestorPunt();
            
        } else {
            index = 0;
            jTextField1.setText(lista.get(index));
            activarBotones();
            ocultarPalabra();
            contador = 0;
            puntuacion = 0;
        }
    }//GEN-LAST:event_b4ActionPerformed

    private void jTCronoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTCronoActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_jTCronoActionPerformed

    private void b2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b2ActionPerformed
        // TODO add your handling code here:

        if (lista.get(1).equals(jTextField1.getText())) {

            b2.setText(lista.get(1));
            b2.setEnabled(false);

            contador++;
            index++;
            puntuacion++;
            if (contador != 4) {

                jTextField1.setText(listaAux.get(index));
            } else {
                crono2.stop();
                JOptionPane.showMessageDialog(rootPane, "Enhorabuena! has ganado");
                puntuacion = 4 + segundos;
                
            }
            gestorPunt();
            
        } else {
            index = 0;
            jTextField1.setText(lista.get(index));
            activarBotones();
            ocultarPalabra();
            contador = 0;
            puntuacion = 0;
        }
    }//GEN-LAST:event_b2ActionPerformed

    private void b3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b3ActionPerformed
        // TODO add your handling code here:
        if (lista.get(2).equals(jTextField1.getText())) {

            b3.setText(lista.get(2));
            b3.setEnabled(false);

            contador++;
            index++;
            puntuacion++;
            if (contador != 4) {

                jTextField1.setText(listaAux.get(index));
            } else {
                   crono2.stop();
                JOptionPane.showMessageDialog(rootPane, "Enhorabuena! has ganado");
                puntuacion = 4 + segundos;
               
            }
            gestorPunt();
            
        } else {
            index = 0;
            jTextField1.setText(lista.get(index));
            activarBotones();
            ocultarPalabra();
            contador = 0;
            puntuacion = 0;
        }
    }//GEN-LAST:event_b3ActionPerformed

    private void bVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bVolverActionPerformed
        gestorPunt();
        if(user.getPuntuacion() < maxPunt){
            user.setPuntuacion(maxPunt);
        }
        crono1.stop();
        crono2.stop();
        Menu menu = new Menu(user);
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_bVolverActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        crono1.stop();
        crono2.stop();
    }//GEN-LAST:event_formWindowClosing

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Facil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Facil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Facil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Facil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                /*try {
                    new Facil(listas).setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Facil.class.getName()).log(Level.SEVERE, null, ex);
                }*/
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b1;
    private javax.swing.JButton b2;
    private javax.swing.JButton b3;
    private javax.swing.JButton b4;
    private javax.swing.JButton bComenzar;
    private javax.swing.JButton bVolver;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTCrono;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JProgressBar pbCrono1;
    private javax.swing.JProgressBar pbCrono2;
    private javax.swing.JTextField tPuntuacion;
    // End of variables declaration//GEN-END:variables
}
