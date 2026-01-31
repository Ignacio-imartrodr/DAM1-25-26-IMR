package UD4.Modulos;

import UD4.Personas.Alumno;

public class Modulo {
    Alumno[] alumnadoMatriculado;
    int horas;
    String nombre;
    public void mostrar() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Horas: " + horas);
        System.out.println("Cantidad de alumnado matriculado: " + alumnadoMatriculado.length);
    }
    public void mostrarAlumnado(){
        for (Alumno alumno : alumnadoMatriculado) {
            System.out.println(alumno.getFicha());
        }
    }
    public void darNombre(String nombreModulo){
        nombre = nombreModulo;
    }
    public void darHoras(int horasModulo){
        horas = horasModulo;
    }
}
