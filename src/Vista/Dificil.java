/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

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
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 *
 * @author usuario
 */
public class Dificil extends javax.swing.JFrame {

    /**
     * Creates new form Dificil
     */
    ArrayList<String> listaAux = new ArrayList();
    ArrayList<JButton> botones = new ArrayList();
    ConexionMySQL conexion;
    ControladorLista controlador;

// se crea una lista auxiliar donde vamos a guardar los datos para el random
    int index = 0;
    ArrayList<String> lista;

    private Timer crono1;
    private Timer crono2;
    private int centesimas = 99;
    private int segundos = 19;
    private int puntuacion = 0;
    private int maxPunt = 0;
    Usuario usu;

    public Dificil(String listas, Usuario user) throws SQLException {
        System.out.println("aasiadsu");
        usu = user;
        initComponents();
        arrayBotones();
        desactivar();
        conexion = new ConexionMySQL("pasapalabra", "root", "");
        controlador = new ControladorLista(conexion);
        conexion.conectar();
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

    public void actualizarTiempo() {
        String tiempo = (segundos <= 9 ? "0" : "") + segundos + ":" + (centesimas <= 9 ? "0" : "") + centesimas;
        jTextField2.setText(tiempo);
    }
    
    public void gestorPunt() {
        if(puntuacion>maxPunt)
            maxPunt = puntuacion;
        puntuacion = 0;
    }

    public void mensajeDerrota() {
        JOptionPane.showMessageDialog(this, "Has perdido pedazo de CARICHIMBA");
    }

    private void desactivar() {
        for (int i = 0; i < botones.size(); i++) {
            botones.get(i).setEnabled(false);
        }
    }

    private void activar() {
        for (int i = 0; i < botones.size(); i++) {
            botones.get(i).setEnabled(true);
        }
    }

    private void texto(ArrayList<String> listaAux) {
        for (int i = 0; i < botones.size(); i++) {
            botones.get(i).setText(lista.get(i));
        }
    }

    public void ocultarTexto() {
        String text;
        for (int i = 0; i < botones.size(); i++) {
            text = String.valueOf(i + 1);
            botones.get(i).setText(text);
        }
    }

    public void actualizarRojo() {
        jTextField2.setForeground(Color.red);
    }

    private void arrayBotones() {
        botones.add(jButton1);
        botones.add(jButton2);
        botones.add(jButton3);
        botones.add(jButton4);
        botones.add(jButton5);
        botones.add(jButton6);
        botones.add(jButton7);
        botones.add(jButton8);
        botones.add(jButton9);
        botones.add(jButton10);
        botones.add(jButton11);
        botones.add(jButton12);
        botones.add(jButton13);
        botones.add(jButton14);
        botones.add(jButton15);
        botones.add(jButton16);
    }

    private ActionListener accionesC1 = new ActionListener() {
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
                centesimas = 99;
                segundos = 59;
                crono1.stop();
                vaciarBarra1();
                activar();
                ocultarTexto();
                jTextField1.setText(listaAux.get(index));
                crono2.start();
            }

        }
    };

    private ActionListener accionesC2 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            centesimas--;
            modificarBarra2();
            if (centesimas == 0) {
                segundos--;
                centesimas = 99;
            }
            if (segundos == 10) {
                actualizarRojo();
                BarraRoja();
            }
            actualizarTiempo();
            if (segundos == 0 && centesimas == 1) {
                crono2.stop();
                centesimas = 0;
                vaciarBarra2();
                actualizarTiempo();
                desactivar();
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

        jSpinner1 = new javax.swing.JSpinner();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        pbCrono1 = new javax.swing.JProgressBar();
        pbCrono2 = new javax.swing.JProgressBar();
        tCronometro = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(255, 255, 204));
        jTextField1.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.setEditable(false);
        jTextField2.setBackground(new java.awt.Color(255, 255, 204));
        jTextField2.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jButton17.setBackground(new java.awt.Color(0, 204, 204));
        jButton17.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jButton17.setForeground(new java.awt.Color(255, 255, 255));
        jButton17.setText("Comenzar");
        jButton17.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton18.setBackground(new java.awt.Color(0, 204, 204));
        jButton18.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jButton18.setForeground(new java.awt.Color(255, 255, 255));
        jButton18.setText("Volver");
        jButton18.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(204, 204, 255));
        jButton1.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jButton1.setText("1");
        jButton1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(204, 204, 255));
        jButton2.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jButton2.setText("2");
        jButton2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(204, 204, 255));
        jButton3.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jButton3.setText("3");
        jButton3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(204, 204, 255));
        jButton4.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jButton4.setText("4");
        jButton4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(204, 204, 255));
        jButton5.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jButton5.setText("5");
        jButton5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(204, 204, 255));
        jButton6.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jButton6.setText("6");
        jButton6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(204, 204, 255));
        jButton7.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jButton7.setText("7");
        jButton7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton7MouseClicked(evt);
            }
        });
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(204, 204, 255));
        jButton8.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jButton8.setText("8");
        jButton8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton8MouseClicked(evt);
            }
        });
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton12.setBackground(new java.awt.Color(204, 204, 255));
        jButton12.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jButton12.setText("12");
        jButton12.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jButton12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton12MouseClicked(evt);
            }
        });
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(204, 204, 255));
        jButton10.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jButton10.setText("10");
        jButton10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jButton10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton10MouseClicked(evt);
            }
        });
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(204, 204, 255));
        jButton11.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jButton11.setText("11");
        jButton11.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jButton11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton11MouseClicked(evt);
            }
        });
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton16.setBackground(new java.awt.Color(204, 204, 255));
        jButton16.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jButton16.setText("16");
        jButton16.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jButton16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton16MouseClicked(evt);
            }
        });
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(204, 204, 255));
        jButton9.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jButton9.setText("9");
        jButton9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jButton9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton9MouseClicked(evt);
            }
        });
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton14.setBackground(new java.awt.Color(204, 204, 255));
        jButton14.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jButton14.setText("14");
        jButton14.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jButton14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton14MouseClicked(evt);
            }
        });
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton15.setBackground(new java.awt.Color(204, 204, 255));
        jButton15.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jButton15.setText("15");
        jButton15.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jButton15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton15MouseClicked(evt);
            }
        });
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton13.setBackground(new java.awt.Color(204, 204, 255));
        jButton13.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jButton13.setText("13");
        jButton13.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jButton13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton13MouseClicked(evt);
            }
        });
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        pbCrono1.setBackground(new java.awt.Color(153, 204, 255));
        pbCrono1.setMaximum(1000);
        pbCrono1.setToolTipText("");
        pbCrono1.setValue(999);

        pbCrono2.setBackground(new java.awt.Color(153, 204, 255));
        pbCrono2.setMaximum(3000);
        pbCrono2.setToolTipText("");
        pbCrono2.setValue(2999);

        tCronometro.setEditable(false);
        tCronometro.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        tCronometro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tCronometro.setText("10:00");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pbCrono1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tCronometro, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pbCrono2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(pbCrono1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tCronometro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pbCrono2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jButton17)
                        .addGap(18, 18, 18)
                        .addComponent(jButton18)))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        if (lista.get(12).equals(jTextField1.getText())) {
            index++;
            jButton13.setBackground(new Color(153, 255, 153));
            if (index != 16) {
                jTextField1.setText(listaAux.get(index));
                jButton13.setText(lista.get(12));
                puntuacion++;
            }else{
                crono2.stop();
                JOptionPane.showMessageDialog(this, "Has ganado puto");
                puntuacion = 16 + segundos;
            }
            jButton13.setEnabled(false);

        } else {
            index = 0;
            jTextField1.setText(listaAux.get(index));
            activar();
            ocultarTexto();
            gestorPunt();
            jButton13.setBackground(new Color( 204, 204, 255));
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton13MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton13MouseClicked

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        if (lista.get(14).equals(jTextField1.getText())) {
            index++;
            jButton15.setBackground(new Color(153, 255, 153));
            if (index != 16) {
                jTextField1.setText(listaAux.get(index));
                jButton15.setText(lista.get(14));
                puntuacion++;
            }else{
                crono2.stop();
                JOptionPane.showMessageDialog(this, "Has ganado puto");
                puntuacion = 16 + segundos;
            }
            jButton15.setEnabled(false);

        } else {
            index = 0;
            jTextField1.setText(listaAux.get(index));
            activar();
            ocultarTexto();
            gestorPunt();
            jButton15.setBackground(new Color( 204, 204, 255));
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton15MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton15MouseClicked

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        if (lista.get(13).equals(jTextField1.getText())) {
            index++;
            jButton14.setBackground(new Color(153, 255, 153));
            if (index != 16) {
                jTextField1.setText(listaAux.get(index));
                jButton14.setText(lista.get(13));
                puntuacion++;
            }else{
                crono2.stop();
                JOptionPane.showMessageDialog(this, "Has ganado puto");
                puntuacion = 16 + segundos;
            }
            jButton14.setEnabled(false);

        } else {
            index = 0;
            ocultarTexto();
            jTextField1.setText(listaAux.get(index));
            activar();
            gestorPunt();
            jButton14.setBackground(new Color( 204, 204, 255));
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton14MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton14MouseClicked

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        if (lista.get(8).equals(jTextField1.getText())) {
            index++;
            jButton9.setBackground(new Color(153, 255, 153));
            if (index != 16) {
                jTextField1.setText(listaAux.get(index));
                jButton9.setText(lista.get(8));
                puntuacion++;
            }else{
                crono2.stop();
                JOptionPane.showMessageDialog(this, "Has ganado puto");
                puntuacion = 16 + segundos;
            }
            jButton9.setEnabled(false);

        } else {
            index = 0;
            ocultarTexto();
            jTextField1.setText(listaAux.get(index));
            activar();
            gestorPunt();
            jButton9.setBackground(new Color( 204, 204, 255));
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9MouseClicked

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        if (lista.get(15).equals(jTextField1.getText())) {
            index++;
            jButton16.setBackground(new Color(153, 255, 153));
            if (index != 16) {
                jTextField1.setText(listaAux.get(index));
                jButton16.setText(lista.get(15));
                puntuacion++;
            }else{
                crono2.stop();
                JOptionPane.showMessageDialog(this, "Has ganado puto");
                puntuacion = 16 + segundos;
            }
            jButton16.setEnabled(false);

        } else {
            index = 0;
            ocultarTexto();
            jTextField1.setText(listaAux.get(index));
            activar();
            gestorPunt();
            jButton16.setBackground(new Color( 204, 204, 255));
        }
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton16MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton16MouseClicked

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        if (lista.get(10).equals(jTextField1.getText())) {
            index++;
            jButton11.setBackground(new Color(153, 255, 153));
            if (index != 16) {
                jTextField1.setText(listaAux.get(index));
                jButton11.setText(lista.get(10));
                puntuacion++;
            }else{
                crono2.stop();
                JOptionPane.showMessageDialog(this, "Has ganado puto");
                puntuacion = 16 + segundos;
            }
            jButton11.setEnabled(false);

        } else {
            index = 0;
            ocultarTexto();
            jTextField1.setText(listaAux.get(index));
            activar();
            gestorPunt();
            jButton11.setBackground(new Color( 204, 204, 255));
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton11MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11MouseClicked

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        if (lista.get(9).equals(jTextField1.getText())) {
            index++;
            jButton10.setBackground(new Color(153, 255, 153));
            if (index != 16) {
                jTextField1.setText(listaAux.get(index));
                jButton10.setText(lista.get(9));
                puntuacion++;
            }else{
                crono2.stop();
                JOptionPane.showMessageDialog(this, "Has ganado puto");
                puntuacion = 16 + segundos;
            }
            jButton10.setEnabled(false);

        } else {
            index = 0;
            ocultarTexto();
            jTextField1.setText(listaAux.get(index));
            activar();
            gestorPunt();
            jButton10.setBackground(new Color( 204, 204, 255));
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton10MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10MouseClicked

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        if (lista.get(11).equals(jTextField1.getText())) {
            index++;
            jButton12.setBackground(new Color(153, 255, 153));
            if (index != 16) {
                jTextField1.setText(listaAux.get(index));
                jButton12.setText(lista.get(11));
                puntuacion++;
            }else{
                crono2.stop();
                JOptionPane.showMessageDialog(this, "Has ganado puto");
                puntuacion = 16 + segundos;
            }
            jButton12.setEnabled(false);

        } else {
            index = 0;
            ocultarTexto();
            jTextField1.setText(listaAux.get(index));
            activar();
            gestorPunt();
            jButton12.setBackground(new Color( 204, 204, 255));
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton12MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton12MouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        if (lista.get(7).equals(jTextField1.getText())) {
            index++;
            jButton8.setBackground(new Color(153, 255, 153));
            if (index != 16) {
                jTextField1.setText(listaAux.get(index));
                jButton8.setText(lista.get(7));
                puntuacion++;
            }else{
                crono2.stop();
                JOptionPane.showMessageDialog(this, "Has ganado puto");
                puntuacion = 16 + segundos;
            }
            jButton8.setEnabled(false);

        } else {
            index = 0;
            ocultarTexto();
            jTextField1.setText(listaAux.get(index));
            activar();
            gestorPunt();
            jButton8.setBackground(new Color( 204, 204, 255));
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8MouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        if (lista.get(6).equals(jTextField1.getText())) {
            index++;
            jButton7.setBackground(new Color(153, 255, 153));
            if (index != 16) {
                jTextField1.setText(listaAux.get(index));
                jButton7.setText(lista.get(6));
                puntuacion++;
            }else{
                crono2.stop();
                JOptionPane.showMessageDialog(this, "Has ganado puto");
                puntuacion = 16 + segundos;
            }
            jButton7.setEnabled(false);

        } else {
            index = 0;
            jTextField1.setText(listaAux.get(index));
            activar();
            ocultarTexto();
            gestorPunt();
            jButton7.setBackground(new Color( 204, 204, 255));
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        if (lista.get(5).equals(jTextField1.getText())) {
            index++;
            jButton6.setBackground(new Color(153, 255, 153));
            if (index != 16) {
                jTextField1.setText(listaAux.get(index));
                jButton6.setText(lista.get(5));
                puntuacion++;
            }else{
                crono2.stop();
                JOptionPane.showMessageDialog(this, "Has ganado puto");
                puntuacion = 16 + segundos;
            }
            jButton6.setEnabled(false);

        } else {
            index = 0;
            jTextField1.setText(listaAux.get(index));
            activar();
            ocultarTexto();
            gestorPunt();
            jButton6.setBackground(new Color( 204, 204, 255));
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if (lista.get(4).equals(jTextField1.getText())) {
            index++;
            jButton5.setBackground(new Color(153, 255, 153));
            if (index != 16) {
                jTextField1.setText(listaAux.get(index));
                jButton5.setText(lista.get(4));
                puntuacion++;
            }else{
                crono2.stop();
                JOptionPane.showMessageDialog(this, "Has ganado puto");
                puntuacion = 16 + segundos;
            }
            jButton5.setEnabled(false);

        } else {
            index = 0;
            jTextField1.setText(listaAux.get(index));
            activar();
            ocultarTexto();
            gestorPunt();
            jButton5.setBackground(new Color( 204, 204, 255));
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if (lista.get(3).equals(jTextField1.getText())) {
            index++;
            jButton4.setBackground(new Color(153, 255, 153));
            if (index != 16) {
                jTextField1.setText(listaAux.get(index));
                jButton4.setText(lista.get(3));
                puntuacion++;
            }else{
                crono2.stop();
                JOptionPane.showMessageDialog(this, "Has ganado puto");
                puntuacion = 16 + segundos;
            }
            jButton4.setEnabled(false);

        } else {
            index = 0;
            jTextField1.setText(listaAux.get(index));
            activar();
            ocultarTexto();
            gestorPunt();
            jButton4.setBackground(new Color( 204, 204, 255));
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (lista.get(2).equals(jTextField1.getText())) {
            index++;
            jButton3.setBackground(new Color(153, 255, 153));
            if (index != 16) {
                jTextField1.setText(listaAux.get(index));
                jButton3.setText(lista.get(2));
                puntuacion++;
            }else{
                crono2.stop();
                JOptionPane.showMessageDialog(this, "Has ganado puto");
                puntuacion = 16 + segundos;
            }
            jButton3.setEnabled(false);

        } else {
            index = 0;
            jTextField1.setText(listaAux.get(index));
            activar();
            ocultarTexto();
            gestorPunt();
            jButton3.setBackground(new Color( 204, 204, 255));
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (lista.get(1).equals(jTextField1.getText())) {
            index++;
            jButton2.setBackground(new Color(153, 255, 153));
            if (index != 16) {
                jTextField1.setText(listaAux.get(index));
                jButton2.setText(lista.get(1));
                puntuacion++;
            }else{
                crono2.stop();
                JOptionPane.showMessageDialog(this, "Has ganado puto");
                puntuacion = 16 + segundos;
            }
            jButton2.setEnabled(false);

        } else {
            index = 0;
            jTextField1.setText(listaAux.get(index));
            activar();
            ocultarTexto();
            gestorPunt();
            jButton2.setBackground(new Color( 204, 204, 255));
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (lista.get(0).equals(jTextField1.getText())) {
            index++;
            jButton1.setBackground(new Color(153, 255, 153));
            if (index != 16) {
                jTextField1.setText(listaAux.get(index));
                jButton1.setText(lista.get(0));
                puntuacion++;
            }else{
                crono2.stop();
                JOptionPane.showMessageDialog(this, "Has ganado puto");
                puntuacion = 16 + segundos;
            }
            jButton1.setEnabled(false);

        } else {
            index = 0;
            jTextField1.setText(listaAux.get(index));
            ocultarTexto();
            activar();
            gestorPunt();
            jButton1.setBackground(new Color( 204, 204, 255));
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        gestorPunt();
        if(usu.getPuntuacion() < maxPunt){
            usu.setPuntuacion(maxPunt);
        }
        Menu menu = new Menu(usu);
        crono1.stop();
        crono2.stop();
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        crono1.start();
        listaAux.clear();
        index = 0;

        Collections.shuffle(lista);
        for (int i = 0; i <= 15; i++) {
            listaAux.add(lista.get(i));
        }
        Collections.shuffle(listaAux);
        System.out.println(listaAux);
        System.out.println(lista);
        texto(listaAux);
        jButton17.setEnabled(false);
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {                                   
        crono1.stop();
        crono2.stop();
    }      
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
            java.util.logging.Logger.getLogger(Dificil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dificil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dificil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dificil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new Dificil().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JProgressBar pbCrono1;
    private javax.swing.JProgressBar pbCrono2;
    private javax.swing.JTextField tCronometro;
    // End of variables declaration//GEN-END:variables
}
