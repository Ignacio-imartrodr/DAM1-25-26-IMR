package UD6.Examen;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Puntuacion implements Comparable<Puntuacion>, Serializable{
    String usuario;
    Integer puntuacion;
    LocalDateTime fechaYHora;

    
    public String getUsuario() {
        return usuario;
    }
    public Integer getPuntuacion() {
        return puntuacion;
    }
    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }
    @Override
    public String toString() {
        return usuario + " - " + puntuacion + "Pts";
    }

    @Override
    public int compareTo(Puntuacion o) {
        return o.getPuntuacion() - puntuacion;
    }

    public Puntuacion(String usuario, Integer puntuacion, LocalDateTime fechaYHora) {
        if (usuario == null || puntuacion == null || fechaYHora == null || puntuacion < 0) {
            throw new IllegalArgumentException("Valores invalidos");
        }
        
        this.usuario = usuario;
        this.puntuacion = puntuacion;
        this.fechaYHora = fechaYHora;
    }
    
    
}
