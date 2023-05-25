/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author usuario
 */
public class Usuario implements Comparable <Usuario>{
    private String nombre;
    private String contraseña;
    private Integer puntuacion;

    public Usuario(String nombre, String contraseña) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.puntuacion = 0;
    }

    public Usuario(String nombre, String contraseña, Integer puntuacion) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.puntuacion = puntuacion;
    }
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    @Override
    
    public int compareTo(Usuario o) {
        if (this.puntuacion < o.getPuntuacion() ) {
            return -1;
        } else if (this.puntuacion > o.getPuntuacion()) {
            return 1;
        } else {
            return nombre.compareTo(((Usuario)o).nombre);
        }
    }
}
