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
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 *
 * @author usuario
 */
public class Medio extends javax.swing.JFrame {

    /**
     * Creates new form Facil
     */
    

    ArrayList<String> listaAux=new ArrayList();
    ConexionMySQL conexion;
    ControladorLista controlador;

// se crea una lista auxiliar donde vamos a guardar los datos para el random
    int index = 0;
    int cont = 0;
    ArrayList<String> lista;
    private Timer crono1;
    private Timer crono2;
    private int centesimas=99;
    private int segundos=9;
    private int puntuacion = 0;
    private int maxPunt = 0;
    Usuario user;

    public Medio(String listas, Usuario usuario) throws SQLException {
        initComponents();
        activarBotones();
        Image icon = new ImageIcon(getClass().getResource("cabra(2).png")).getImage();
        this.setIconImage(icon);
        user=usuario;
        bComenzar.setEnabled(true);
        conexion = new ConexionMySQL("pasapalabra", "root", "");   
        controlador = new ControladorLista(conexion);
        conexion.conectar();        
        desactivarBotones();
        lista = controlador.obtenerTodaLaListas(listas);  // se carga la lista con la que vamos a trabajar
        crono1 = new Timer(10, accionesC1);
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
    public void desactivarBotones() {
        b1.setEnabled(false);
        b2.setEnabled(false);
        b3.setEnabled(false);
        b4.setEnabled(false);
        b5.setEnabled(false);
        b6.setEnabled(false);
        b7.setEnabled(false);
        b8.setEnabled(false);
        b9.setEnabled(false);
    }
    
    public void activarBotones() {
            b1.setEnabled(true);
            b2.setEnabled(true);
            b3.setEnabled(true);
            b4.setEnabled(true);
            b5.setEnabled(true);
            b6.setEnabled(true);
            b7.setEnabled(true);
            b8.setEnabled(true);
            b9.setEnabled(true);
    }
    
    public void gestorPunt() {
        if(puntuacion>maxPunt){
            maxPunt = puntuacion;
            puntuacion = 0;
            tPuntuacion.setText(String.valueOf(maxPunt));
        }
    }
    
    public void actualizarTiempo(){
        String tiempo = (segundos<=9?"0":"")+segundos+":"+(centesimas<=9?"0":"")+centesimas;
        tCronometro.setText(tiempo);
    }
    
    public void actualizarRojo(){
        tCronometro.setForeground(Color.red);
    }
    
    public void mensajeDerrota() {
        JOptionPane.showMessageDialog(this, "Has perdido pedazo de CARICHIMBA");
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
    
    public void ocultarPalabras(){
        b1.setText("1");
        b2.setText("2");
        b3.setText("3");
        b4.setText("4");
        b5.setText("5");
        b6.setText("6");
        b7.setText("7");
        b8.setText("8");
        b9.setText("9");
        tPalabras.setText(listaAux.get(index));
    }
        
    private ActionListener accionesC1 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
                centesimas --;           
                modificarBarra1();
                if (centesimas == 0) {
                    segundos--;
                    centesimas=99;
                }
                
                actualizarTiempo();
                
                if (segundos==0 && centesimas == 1) {
                    centesimas = 99;
                    segundos = 29;
                    crono1.stop();
                    vaciarBarra1();
                    activarBotones();
                    ocultarPalabras();
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
                
                if (segundos == 10) {
                    actualizarRojo();
                    BarraRoja();
                }
                
                actualizarTiempo();
                
                if (segundos==0 && centesimas == 1) {
                    crono2.stop();
                    vaciarBarra2();
                    centesimas=0;
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
        tPalabras = new javax.swing.JTextField();
        bComenzar = new javax.swing.JButton();
        b5 = new javax.swing.JButton();
        b6 = new javax.swing.JButton();
        b8 = new javax.swing.JButton();
        b7 = new javax.swing.JButton();
        b9 = new javax.swing.JButton();
        tCronometro = new javax.swing.JTextField();
        pbCrono2 = new javax.swing.JProgressBar();
        pbCrono1 = new javax.swing.JProgressBar();
        tPuntuacion = new javax.swing.JTextField();
        bVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.OverlayLayout(getContentPane()));

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        b1.setBackground(new java.awt.Color(204, 204, 255));
        b1.setFont(new java.awt.Font("MV Boli", 1, 18)); // NOI18N
        b1.setText("1");
        b1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        b1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b1ActionPerformed(evt);
            }
        });

        b2.setBackground(new java.awt.Color(204, 204, 255));
        b2.setFont(new java.awt.Font("MV Boli", 1, 18)); // NOI18N
        b2.setText("2");
        b2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        b2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b2ActionPerformed(evt);
            }
        });

        b3.setBackground(new java.awt.Color(204, 204, 255));
        b3.setFont(new java.awt.Font("MV Boli", 1, 18)); // NOI18N
        b3.setText("3");
        b3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        b3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b3ActionPerformed(evt);
            }
        });

        b4.setBackground(new java.awt.Color(204, 204, 255));
        b4.setFont(new java.awt.Font("MV Boli", 1, 18)); // NOI18N
        b4.setText("4");
        b4.setAutoscrolls(true);
        b4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        b4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b4ActionPerformed(evt);
            }
        });

        tPalabras.setEditable(false);
        tPalabras.setBackground(new java.awt.Color(255, 255, 204));
        tPalabras.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        tPalabras.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        bComenzar.setBackground(new java.awt.Color(0, 204, 204));
        bComenzar.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        bComenzar.setForeground(new java.awt.Color(255, 255, 255));
        bComenzar.setText("Comenzar");
        bComenzar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        bComenzar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bComenzarActionPerformed(evt);
            }
        });

        b5.setBackground(new java.awt.Color(204, 204, 255));
        b5.setFont(new java.awt.Font("MV Boli", 1, 18)); // NOI18N
        b5.setText("5");
        b5.setAutoscrolls(true);
        b5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        b5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b5ActionPerformed(evt);
            }
        });

        b6.setBackground(new java.awt.Color(204, 204, 255));
        b6.setFont(new java.awt.Font("MV Boli", 1, 18)); // NOI18N
        b6.setText("6");
        b6.setAutoscrolls(true);
        b6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        b6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b6ActionPerformed(evt);
            }
        });

        b8.setBackground(new java.awt.Color(204, 204, 255));
        b8.setFont(new java.awt.Font("MV Boli", 1, 18)); // NOI18N
        b8.setText("8");
        b8.setAutoscrolls(true);
        b8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        b8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b8ActionPerformed(evt);
            }
        });

        b7.setBackground(new java.awt.Color(204, 204, 255));
        b7.setFont(new java.awt.Font("MV Boli", 1, 18)); // NOI18N
        b7.setText("7");
        b7.setAutoscrolls(true);
        b7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        b7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b7ActionPerformed(evt);
            }
        });

        b9.setBackground(new java.awt.Color(204, 204, 255));
        b9.setFont(new java.awt.Font("MV Boli", 1, 18)); // NOI18N
        b9.setText("9");
        b9.setAutoscrolls(true);
        b9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        b9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b9ActionPerformed(evt);
            }
        });

        tCronometro.setEditable(false);
        tCronometro.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        tCronometro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tCronometro.setText("10:00");

        pbCrono2.setBackground(new java.awt.Color(153, 204, 255));
        pbCrono2.setMaximum(3000);
        pbCrono2.setToolTipText("");
        pbCrono2.setValue(2999);

        pbCrono1.setBackground(new java.awt.Color(153, 204, 255));
        pbCrono1.setMaximum(1000);
        pbCrono1.setToolTipText("");
        pbCrono1.setValue(999);

        tPuntuacion.setEditable(false);
        tPuntuacion.setBackground(new java.awt.Color(255, 255, 204));
        tPuntuacion.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        tPuntuacion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tPuntuacion.setText("0");

        bVolver.setBackground(new java.awt.Color(0, 204, 204));
        bVolver.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        bVolver.setForeground(new java.awt.Color(255, 255, 255));
        bVolver.setText("Volver");
        bVolver.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
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
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(b4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(b7, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(b5, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(b6, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(b8, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(b9, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(b1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(b2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(b3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(bComenzar, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tPalabras, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tPuntuacion, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(bVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pbCrono1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pbCrono2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tCronometro, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b5, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b6, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b8, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b7, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b9, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(tPuntuacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tPalabras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bComenzar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bVolver)
                        .addGap(17, 17, 17))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(pbCrono1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tCronometro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pbCrono2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        getContentPane().add(jPanel3);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bComenzarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bComenzarActionPerformed
        // TODO add your handling code here:
        crono1.start();
        bComenzar.setEnabled(false);
        
        Collections.shuffle(lista);
        
        for (int i = 0; i < 9; i++) {
            listaAux.add(lista.get(i));
        }
        
        Collections.shuffle(listaAux);

        b1.setText(lista.get(0));
        b2.setText(lista.get(1));
        b3.setText(lista.get(2));
        b4.setText(lista.get(3));
        b5.setText(lista.get(4));
        b6.setText(lista.get(5));
        b7.setText(lista.get(6));
        b8.setText(lista.get(7));
        b9.setText(lista.get(8));
        
    }//GEN-LAST:event_bComenzarActionPerformed

    private void b1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b1ActionPerformed
        if (lista.get(0).equals(tPalabras.getText())) {
            b1.setText(lista.get(0));
            b1.setEnabled(false);
            b1.setBackground(new Color(153, 255, 153));
            cont++;
            index++;
            puntuacion++;
            if (cont!=9) {
                tPalabras.setText(listaAux.get(index));
            }else{
                crono2.stop();
                JOptionPane.showMessageDialog(this, "Has ganado puto");
                puntuacion = 9 + segundos;
            }
            gestorPunt();
        } else {
            index = 0;
            ocultarPalabras();
            activarBotones();
            cont=0;
            puntuacion=0;
            b1.setBackground(new Color( 204, 204, 255));
        }
    }//GEN-LAST:event_b1ActionPerformed

    private void b2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b2ActionPerformed
        if (lista.get(1).equals(tPalabras.getText())) {
            b2.setText(lista.get(1));
            b2.setEnabled(false);
            b2.setBackground(new Color(153, 255, 153));
            cont++;
            index++;
            puntuacion++;
            if (cont!=9) {
                tPalabras.setText(listaAux.get(index));
            }else{
                crono2.stop();
                JOptionPane.showMessageDialog(this, "Has ganado puto");
                puntuacion = 9 + segundos;
            }
            gestorPunt();
        } else {
            index = 0;
            ocultarPalabras();
            activarBotones();
            cont=0;
            puntuacion=0;
            b2.setBackground(new Color( 204, 204, 255));
        }
    }//GEN-LAST:event_b2ActionPerformed

    private void b3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b3ActionPerformed
        if (lista.get(2).equals(tPalabras.getText())) {
            b3.setText(lista.get(2));
            b3.setEnabled(false);
            b3.setBackground(new Color(153, 255, 153));
            cont++;
            index++;
            puntuacion++;
            if (cont!=9) {
                tPalabras.setText(listaAux.get(index));
            }else{
                crono2.stop();
                JOptionPane.showMessageDialog(this, "Has ganado puto");
                puntuacion = 9 + segundos;
            }
            gestorPunt();
        } else {
            index = 0;
            ocultarPalabras();
            activarBotones();
            cont=0;
            puntuacion=0;
            b3.setBackground(new Color(204, 204, 255));
        }
    }//GEN-LAST:event_b3ActionPerformed

    private void b4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b4ActionPerformed
        if (lista.get(3).equals(tPalabras.getText())) {
            b4.setText(lista.get(3));
            b4.setEnabled(false);
            b4.setBackground(new Color(153, 255, 153));
            cont++;
            index++;
            puntuacion++;
            if (cont!=9) {
                tPalabras.setText(listaAux.get(index));
            }else{
                crono2.stop();
                JOptionPane.showMessageDialog(this, "Has ganado puto");
                puntuacion = 9 + segundos;
            }
            gestorPunt();
        } else {
            index = 0;
            ocultarPalabras();
            activarBotones();
            cont=0;
            puntuacion=0;
            b4.setBackground(new Color(204, 204, 255));
        }
    }//GEN-LAST:event_b4ActionPerformed

    private void b5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b5ActionPerformed
        if (lista.get(4).equals(tPalabras.getText())) {
            b5.setText(lista.get(4));
            b5.setEnabled(false);
            b5.setBackground(new Color(153, 255, 153));
            cont++;
            index++;
            puntuacion++;
            if (cont!=9) {
                tPalabras.setText(listaAux.get(index));
            }else{
                crono2.stop();
                JOptionPane.showMessageDialog(this, "Has ganado puto");
                puntuacion = 9 + segundos;
            }
            gestorPunt();
        } else {
            index = 0;
            ocultarPalabras();
            activarBotones();
            cont=0;
            puntuacion=0;
            b5.setBackground(new Color(204, 204, 255));
        }
    }//GEN-LAST:event_b5ActionPerformed

    private void b6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b6ActionPerformed
        if (lista.get(5).equals(tPalabras.getText())) {
            b6.setText(lista.get(5));
            b6.setEnabled(false);
            b6.setBackground(new Color(153, 255, 153));
            cont++;
            index++;
            puntuacion++;
            if (cont!=9) {
                tPalabras.setText(listaAux.get(index));
            }else{
                crono2.stop();
                JOptionPane.showMessageDialog(this, "Has ganado puto");
                puntuacion = 9 + segundos;
            }
            gestorPunt();
        } else {
            index = 0;
            ocultarPalabras();
            activarBotones();
            cont=0;
            puntuacion=0;
            b6.setBackground(new Color(204, 204, 255));
        }
    }//GEN-LAST:event_b6ActionPerformed

    private void b7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b7ActionPerformed
        if (lista.get(6).equals(tPalabras.getText())) {
            b7.setText(lista.get(6));
            b7.setEnabled(false);
            b7.setBackground(new Color(153, 255, 153));
            cont++;
            index++;
            puntuacion++;
            if (cont!=9) {
                tPalabras.setText(listaAux.get(index));
            }else{
                crono2.stop();
                JOptionPane.showMessageDialog(this, "Has ganado puto");
                puntuacion = 9 + segundos;
            }
            gestorPunt();
        } else {
            index = 0;
            ocultarPalabras();
            activarBotones();
            cont=0;
            puntuacion=0;
            b7.setBackground(new Color(204, 204, 255));
        }
    }//GEN-LAST:event_b7ActionPerformed

    private void b8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b8ActionPerformed
        if (lista.get(7).equals(tPalabras.getText())) {
            b8.setText(lista.get(7));
            b8.setEnabled(false);
            b8.setBackground(new Color(153, 255, 153));
            cont++;
            index++;
            puntuacion++;
            if (cont!=9) {
                tPalabras.setText(listaAux.get(index));
            }else{
                crono2.stop();
                JOptionPane.showMessageDialog(this, "Has ganado puto");
                puntuacion = 9 + segundos;
            }
            gestorPunt();
        } else {
            index = 0;
            ocultarPalabras();
            activarBotones();
            cont=0;
            puntuacion=0;
            b8.setBackground(new Color(204, 204, 255));
        }
    }//GEN-LAST:event_b8ActionPerformed

    private void b9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b9ActionPerformed
        if (lista.get(8).equals(tPalabras.getText())) {
            b9.setText(lista.get(8));
            b9.setEnabled(false);
            b9.setBackground(new Color(153, 255, 153));
            cont++;
            index++;
            puntuacion++;
            if (cont!=9) {
                tPalabras.setText(listaAux.get(index));
            }else{
                crono2.stop();
                JOptionPane.showMessageDialog(this, "Has ganado puto");
                puntuacion = 9 + segundos;
            }
            gestorPunt();
        } else {
            index = 0;
            ocultarPalabras();
            activarBotones();
            cont=0;
            puntuacion=0;
            b9.setBackground(new Color(204, 204, 255));
        }
    }//GEN-LAST:event_b9ActionPerformed

    private void bVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bVolverActionPerformed
        gestorPunt();
        if(user.getPuntuacion() < maxPunt){
            user.setPuntuacion(maxPunt);
        }
        Menu menu = new Menu(user);
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_bVolverActionPerformed

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
            java.util.logging.Logger.getLogger(Medio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Medio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Medio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Medio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new Medio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b1;
    private javax.swing.JButton b2;
    private javax.swing.JButton b3;
    private javax.swing.JButton b4;
    private javax.swing.JButton b5;
    private javax.swing.JButton b6;
    private javax.swing.JButton b7;
    private javax.swing.JButton b8;
    private javax.swing.JButton b9;
    private javax.swing.JButton bComenzar;
    private javax.swing.JButton bVolver;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JProgressBar pbCrono1;
    private javax.swing.JProgressBar pbCrono2;
    private javax.swing.JTextField tCronometro;
    private javax.swing.JTextField tPalabras;
    private javax.swing.JTextField tPuntuacion;
    // End of variables declaration//GEN-END:variables
}
