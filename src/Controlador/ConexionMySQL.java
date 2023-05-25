/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.util.TimeZone;
import java.sql.*;
import java.util.Calendar;

/**
 *
 * @author usuario
 */
public class ConexionMySQL {
    private String BD;
    private String USUARIO;
    private String PASS;
    public Connection connection;
    private String HOST;
    private TimeZone zonaHoraria;

    public ConexionMySQL(String BD, String USUARIO, String PASS) {
        this.BD = BD;
        this.USUARIO = USUARIO;
        this.PASS = PASS;
        HOST= "localhost";
        connection=null;
    }
    
    /**
     *
     * @throws java.sql.SQLException
     */
    
    //Conectar a la BDD
    public void conectar() throws SQLException{
        if (connection== null || connection.isClosed()){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Calendar now = Calendar.getInstance();
                TimeZone zone = now.getTimeZone();
                connection = (Connection)DriverManager.getConnection("jdbc:mysql://"+HOST+"/"+BD+"?user=" + USUARIO + "&password=" + PASS + "&userLegacyDateTimeCode=false&serverTimeZone=" + zone.getID());
            }
            catch(ClassNotFoundException e){
                System.out.println("No se ha encontrado. Error: " + e);
            }
        }
    }

    /**
     *
     * @throws java.sql.SQLException
     */
    
    //Desconectar a la BDD
    public void desconectar() throws SQLException{
        if (connection!=null && !connection.isClosed()){
            connection.close();
        }
    }
    
    /**
     *
     * @param consulta
     * @return 
     * @throws java.sql.SQLException
     */
    
    //Consulta con Select
    public ResultSet ejecutarSelect(String consulta) throws SQLException{
        Statement stmt = connection.createStatement();
        ResultSet rset = stmt.executeQuery(consulta);
        return rset;
    }
    
    //Ejecutar una consulta INSERT, DELETE or UPDATE
    public int ejecutarInsetDeleteUpdate(String consulta) throws SQLException{
        Statement stmt = connection.createStatement();
        int fila = stmt.executeUpdate(consulta);
        return fila;
    }
    
}