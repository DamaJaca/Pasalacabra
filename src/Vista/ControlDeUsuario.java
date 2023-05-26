/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import Controlador.ConexionMySQL;
import Controlador.ControladorLista;
import Controlador.ControladorUsuario;
import Modelo.Usuario;
import java.awt.Image;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author usuario
 */
public class ControlDeUsuario extends javax.swing.JFrame {

    /**
     * Creates new form ControlDeUsuario
     */
    boolean condi= true;
    ArrayList <Usuario> users;
    ConexionMySQL conexion;
    ControladorUsuario controladorUser;
    
    private static boolean isPasswordCorrect(char[] input, String contraseña) {
    boolean isCorrect = true;
    char[] correctPassword = contraseña.toCharArray();

    if (input.length != correctPassword.length) {
        isCorrect = false;
    } else {
        isCorrect = Arrays.equals (input, correctPassword);
    }

    //Zero out the password.
    Arrays.fill(correctPassword,'0');

    return isCorrect;
}
    public ControlDeUsuario() throws SQLException {
        initComponents();
        Image icon = new ImageIcon(getClass().getResource("cabra (2).png")).getImage();
        this.setIconImage(icon);
        conexion = new ConexionMySQL("pasapalabra", "root", "");
        controladorUser = new ControladorUsuario (conexion);
        conexion.conectar();
        
        users = controladorUser.obtenerUsuario();       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        tfNombre = new javax.swing.JTextField();
        bConectar = new javax.swing.JButton();
        bRegistrarse = new javax.swing.JButton();
        psContraseña = new javax.swing.JPasswordField();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));

        jPanel2.setBackground(new java.awt.Color(0, 204, 204));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

        tfNombre.setFont(new java.awt.Font("MV Boli", 0, 14)); // NOI18N
        tfNombre.setText("Usuario");
        tfNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNombreActionPerformed(evt);
            }
        });

        bConectar.setBackground(new java.awt.Color(153, 204, 255));
        bConectar.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        bConectar.setForeground(new java.awt.Color(255, 255, 255));
        bConectar.setText("Conectarse");
        bConectar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        bConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bConectarActionPerformed(evt);
            }
        });

        bRegistrarse.setBackground(new java.awt.Color(153, 204, 255));
        bRegistrarse.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        bRegistrarse.setForeground(new java.awt.Color(255, 255, 255));
        bRegistrarse.setText("Registrarse");
        bRegistrarse.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        bRegistrarse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRegistrarseActionPerformed(evt);
            }
        });

        psContraseña.setText("Password");
        psContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                psContraseñaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                    .addComponent(psContraseña))
                .addGap(39, 39, 39))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bRegistrarse, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                    .addComponent(bConectar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(113, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(tfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(psContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bConectar)
                .addGap(18, 18, 18)
                .addComponent(bRegistrarse)
                .addGap(15, 15, 15))
        );

        jPanel3.setBackground(new java.awt.Color(0, 204, 204));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));

        jLabel1.setFont(new java.awt.Font("MV Boli", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("PASALACABRA");

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/cabra (1).png"))); // NOI18N

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/cabra (1).png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addGap(31, 31, 31)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel10)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1))
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, 340));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bConectarActionPerformed
        if (users.isEmpty()){
            JOptionPane.showMessageDialog(this, "No existe ningun usuario con ese nombre, intente registrandose", "ERROR!",
                    JOptionPane.WARNING_MESSAGE);
            
        }else{
            for (int i=0; i<users.size(); i++){
                if (users.get(i).getNombre().equalsIgnoreCase(tfNombre.getText())){
                    char [] input = psContraseña.getPassword();
                    if (isPasswordCorrect(input, users.get(i).getContraseña())){
                        JOptionPane.showMessageDialog(this, "Bienvenido " +  users.get(i).getNombre(), "Se ha conectado",
                                JOptionPane.INFORMATION_MESSAGE);
                        condi=false;
                        Menu menu = new Menu(users.get(i));
                        menu.setVisible(true);
                        this.dispose();
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Contraseña incorrecta", "ERROR!",
                                JOptionPane.WARNING_MESSAGE);
                        i=users.size();
                        
                    }
                }
            }
            if (condi){
            JOptionPane.showMessageDialog(this, "No existe ningun usuario con ese nombre, intente registrandose", "ERROR!",
                    JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_bConectarActionPerformed

    private void bRegistrarseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRegistrarseActionPerformed
        char [] input = psContraseña.getPassword();
        if (tfNombre.getText().equalsIgnoreCase("Usuario")|| tfNombre.getText().equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(this, "Debe introducir un usuario", "ERROR!",
                    JOptionPane.WARNING_MESSAGE);
        }
        else{
            if (String.valueOf(input).equalsIgnoreCase("Password")||String.valueOf(input).equalsIgnoreCase("")){
                JOptionPane.showMessageDialog(this, "Debe introducir una contraseña util.", "ERROR!",
                    JOptionPane.WARNING_MESSAGE);
            }
        }
        if (!tfNombre.getText().equalsIgnoreCase("Usuario") && !tfNombre.getText().equalsIgnoreCase("")){
            if (!String.valueOf(input).equalsIgnoreCase("Password") && !String.valueOf(input).equalsIgnoreCase("")){
                
                condi= true;
                for (int i=0; i<users.size();i++){
                    if (users.get(i).getNombre().equalsIgnoreCase(tfNombre.getText()))
                        condi=false;
                }
                if (condi){
                    String consulta = "Insert into usuarios (nombre, contraseña, puntuacion) VALUES ('" + tfNombre.getText() + "', " + String.valueOf(psContraseña.getPassword()) + ", 0);";

                    try {
                        conexion.ejecutarInsetDeleteUpdate(consulta);
                        JOptionPane.showMessageDialog(this, "Se ha creado correctamente el usuario " + tfNombre.getText(), "Registrado",
                            JOptionPane.INFORMATION_MESSAGE);
                        users=controladorUser.obtenerUsuario();

                    } catch (SQLException ex) {
                        Logger.getLogger(ControlDeUsuario.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                }
                else{
                    JOptionPane.showMessageDialog(this, "Ya existe ese usuario. Intente con otro nombre", "ERROR!",
                    JOptionPane.WARNING_MESSAGE);
                }
                
        

            }
        }
                
    }//GEN-LAST:event_bRegistrarseActionPerformed

    private void tfNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNombreActionPerformed

    private void psContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_psContraseñaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_psContraseñaActionPerformed

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
            java.util.logging.Logger.getLogger(ControlDeUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ControlDeUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ControlDeUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ControlDeUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ControlDeUsuario().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(ControlDeUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bConectar;
    private javax.swing.JButton bRegistrarse;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField psContraseña;
    private javax.swing.JTextField tfNombre;
    // End of variables declaration//GEN-END:variables
}
