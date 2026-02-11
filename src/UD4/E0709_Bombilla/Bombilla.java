package UD4.E0709_Bombilla;

public class Bombilla {
    private boolean interruptor;
    private static boolean general = true;

    Bombilla(){
        interruptor = false;
    }

    Bombilla(boolean estado){
        interruptor = estado;
    }

    boolean estaEncendido(){
        return  general && interruptor;
    }

    boolean getInterruptor(){
        return  interruptor;
    }

    void encender(){
        interruptor = true;
    }

    void apagar(){
        interruptor = false;
    }

    void alternar(){
        interruptor = !interruptor;
    }

    static boolean getGeneral(){
        return  general;
    } 

    static void encenderGeneral(){
        general = true;
    }

    static void apagarGeneral(){
        general = false;
    }

    static void alternarGeneral(){
        general = !general;
    }

    public static void main(String[] args) {
        Bombilla b1 = new Bombilla();
        b1.encender();
        System.out.println("Está encendida?: " + b1.estaEncendido());
        System.out.println("Está el interruptor encendido?: " + b1.getInterruptor());
        Bombilla.alternarGeneral();
        System.out.println("Apagamos el general");
        System.out.println("Está encendida?: " + b1.estaEncendido());
        System.out.println("Está el interruptor encendido?: " + b1.getInterruptor());
    }
}
