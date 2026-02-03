package UD4.rol;

public class Personaje {
    String nombre;
    String raza;
    int fuerza;
    int agilidad;
    int constitucion;
    int nivel = 1;
    int experiencia = 0;
    int puntosVida = 50 + constitucion;
    final String[] RAZAS_VALIDAS = {"HUMANO", "ELFO", "ENANO", "HOBBIT", "ORCO", "TROLL"};
    public void crearPersonaje(){
        nombre = Util.pedirPorTeclado(false);
        raza = Util.pedirPorTeclado(false);
        while (!Util.verificaObjetoEnArray(raza, RAZAS_VALIDAS)) {
            System.out.println("Raza no válida. Introduce uno de los siguientes: Goblin, Orco, Elfo, Humano");
            raza = Util.pedirPorTeclado(false);
        }
        
        fuerza = Integer.parseInt(Util.pedirPorTeclado(true));
        agilidad = Integer.parseInt(Util.pedirPorTeclado(true));
        constitucion = Integer.parseInt(Util.pedirPorTeclado(true));
    }
}
