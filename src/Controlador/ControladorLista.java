/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author usuario
 */
public class ControladorLista {
    private final ConexionMySQL conexion;

    public ControladorLista(ConexionMySQL conexion) {
        this.conexion = conexion;
    }
    public ArrayList <String> obtenerTodaLaListas(String listas) throws SQLException {
        ArrayList <String> lista= new ArrayList<>();
        String consulta= "Select * From "+ listas;
        ResultSet rset = conexion.ejecutarSelect(consulta);
        while (rset.next()){
            String nombre= rset.getString("nombre");
            lista.add(nombre);
        }
        return lista;
    }
}