/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Usuario;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author usuario
 */
public class ControladorUsuario {
    private ConexionMySQL conexion;

    public ControladorUsuario(ConexionMySQL conexion) {
        this.conexion = conexion;
    }
    public ArrayList <Usuario> obtenerUsuario() throws SQLException {
        ArrayList <Usuario> lista= new ArrayList<>();
        String consulta= "Select * From usuarios order by puntuacion desc";
        ResultSet rset = conexion.ejecutarSelect(consulta);
        while (rset.next()){
            String nombre= rset.getString("nombre");
            String contraseña = rset.getString("contraseña");
            Integer puntuacion =  Integer.valueOf(rset.getString("puntuacion")) ;
            Usuario usuario = new Usuario(nombre, contraseña, puntuacion);
            lista.add(usuario);
        }
        return lista;
    }
}
