package Extras;

import java.util.Scanner;
/**
 * @author Ignacio MR
 */
public class RecorridoRobotExtra {
    static Scanner sc = new Scanner(System.in);
    static void mostrarMapa(String[] mapa){
        for (int i = 0; i < mapa.length; i++) {
            System.out.print("|");
            System.out.print(mapa[i]);
            System.out.println("|");
        }
        System.out.println();
    }
    static String pedirInstrucciones() {
        String instrucciones;
        try {
            instrucciones = sc.nextLine().strip().replace(" ", "").toUpperCase();
            for (int i = 0; i < instrucciones.length(); i++) {
                if (instrucciones.charAt(i) != 'A' && instrucciones.charAt(i) != 'L' && instrucciones.charAt(i) != 'R') {
                    System.out.println("Instrucciones invalidas, vuelve a intentarlo:");
                    sc.nextLine();
                    instrucciones = pedirInstrucciones();
                    i = 0;
                }
            }
        } catch (Exception e) {
            System.err.println("Error, vuelve a intentarlo: ");
            sc.nextLine();
            return pedirInstrucciones();
        }
        return instrucciones;
    }
    static int contarOcurenciasEnMapa (String[] mapa, char objetivo){
        int comienzo;
        String aux;
        int cont = 0;
        for (int i = 0; i < mapa.length; i++) {
            aux = mapa[i];
            comienzo = aux.indexOf(objetivo);
            while (comienzo != -1) {
                aux = aux.substring(comienzo + 1);
                comienzo = aux.indexOf(objetivo);
                cont++;
            }
        }
        return cont;
    }
    static int[] posicionEnMapa(String[] mapa, char objetivo) {
        int posicionX = -1;
        int posicionY = -1;
        for (int i = 0; i < mapa.length; i++) {
            if (mapa[i].indexOf(objetivo) != -1) {
                posicionX = mapa[i].indexOf(objetivo);
                posicionY = i;
            }
        }
        int[] Ubicacion = { posicionY, posicionX };
        return Ubicacion;
    }

    static int rotarRobot(int direccion, char rotacion) {
        // 1 ^    2 >    3 v    4 <   -1 Error
        if (rotacion == 'R') {
            if (direccion == 4) {
                direccion = 1;
            } else {
                direccion++;
            }
        } else if (rotacion == 'L') {
            if (direccion == 1) {
                direccion = 4;
            } else {
                direccion--;
            }
        } else {
            direccion = -1;
        }
        return direccion;
    }
    static String[] actualizarMapa(String[] mapa, int[] posRobot, int direccion, char instruccion, int[] posFin){
        // 1 ^    2 >    3 v    4 <   -1 Error
        String[] aux = new String[2];
        boolean enMeta = (posRobot[0] == posFin[0] && posRobot[1] == posFin[1]);
        boolean enPlacaOff = (mapa[posRobot[0]].charAt(posRobot[1]) == '-');
        switch (instruccion) {
            case 'A':
                switch (direccion) {
                    case 1:
                        mapa[posRobot[0]+1] = mapa[posRobot[0]+1].replace("^"," ");
                        aux[0] = mapa[posRobot[0]].substring(0, posRobot[1]);
                        aux[1] = mapa[posRobot[0]].substring(posRobot[1]+1, mapa[posRobot[0]].length());
                        mapa[posRobot[0]] = aux[0]+"^"+aux[1];
                        break;
                    case 2:
                        mapa[posRobot[0]] = mapa[posRobot[0]].replace(">"," ");
                        aux[0] = mapa[posRobot[0]].substring(0, posRobot[1]);
                        aux[1] = mapa[posRobot[0]].substring(posRobot[1]+1, mapa[posRobot[0]].length());
                        mapa[posRobot[0]] = aux[0]+">"+aux[1];
                        break;
                    case 3:
                        mapa[posRobot[0]-1] = mapa[posRobot[0]-1].replace("v"," ");
                        aux[0] = mapa[posRobot[0]].substring(0, posRobot[1]);
                        aux[1] = mapa[posRobot[0]].substring(posRobot[1]+1, mapa[posRobot[0]].length());
                        mapa[posRobot[0]] = aux[0]+"v"+aux[1];
                        break;
                    case 4:
                        mapa[posRobot[0]] = mapa[posRobot[0]].replace("<"," ");
                        aux[0] = mapa[posRobot[0]].substring(0, posRobot[1]);
                        aux[1] = mapa[posRobot[0]].substring(posRobot[1]+1, mapa[posRobot[0]].length());
                        mapa[posRobot[0]] = aux[0]+"<"+aux[1];
                        break;
                }
                break;
            case 'R':
                switch (direccion) {
                    case 1:
                        if(enMeta){
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("Z",">"); 
                        }
                        mapa[posRobot[0]] = mapa[posRobot[0]].replace("^",">");
                        break;
                    case 2:
                        if(enMeta){
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("Z","v"); 
                        }
                        mapa[posRobot[0]] = mapa[posRobot[0]].replace(">","v");
                        break;
                    case 3:
                        if(enMeta){
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("Z","<"); 
                        }
                        mapa[posRobot[0]] = mapa[posRobot[0]].replace("v","<");
                        break;
                    case 4:
                        if(enMeta){
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("Z","^"); 
                        }
                        mapa[posRobot[0]] = mapa[posRobot[0]].replace("<","^");
                        break;
                }
                break;
            case 'L':
                switch (direccion) {
                    case 1:
                        if(enMeta){
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("Z","<"); 
                        }
                        mapa[posRobot[0]] = mapa[posRobot[0]].replace("^","<");
                        break;
                    case 2:
                        if(enMeta){
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("Z","^"); 
                        }
                        mapa[posRobot[0]] = mapa[posRobot[0]].replace(">","^");
                        break;
                    case 3:
                        if(enMeta){
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("Z",">"); 
                        }
                        mapa[posRobot[0]] = mapa[posRobot[0]].replace("v",">");
                        break;
                    case 4:
                        if(enMeta){
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("Z","v"); 
                        }
                        if (enPlacaOff) {
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("+","v"); 
                        } else {
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("-","v"); 
                        }
                        mapa[posRobot[0]] = mapa[posRobot[0]].replace("<","v");
                        break;
                }
                
                break;
            case 'Z':
                aux[0] = mapa[posRobot[0]].substring(0, posRobot[1]);
                aux[1] = mapa[posRobot[0]].substring(posRobot[1]+1, mapa[posRobot[0]].length());
                mapa[posRobot[0]] = aux[0]+"Z"+aux[1];
                break;
            case 'P':
                if (enPlacaOff) {
                    aux[0] = mapa[posRobot[0]].substring(0, posRobot[1]);
                    aux[1] = mapa[posRobot[0]].substring(posRobot[1]+1, mapa[posRobot[0]].length());
                    mapa[posRobot[0]] = aux[0]+"+"+aux[1];
                } else {
                    aux[0] = mapa[posRobot[0]].substring(0, posRobot[1]);
                    aux[1] = mapa[posRobot[0]].substring(posRobot[1]+1, mapa[posRobot[0]].length());
                    mapa[posRobot[0]] = aux[0]+"-"+aux[1];
                }
                break;
        }
        return mapa;
    }
    static int[] avanzarRobot(String[] mapa, int[] posRobot, int direccion){

        if (posRobot[0] < mapa.length && posRobot[0] >= 0 && posRobot[1] < mapa[posRobot[0]].length() && posRobot[1] >= 0) {
            switch (direccion) {
                case 1:
                    posRobot[0]--;
                    break;
                case 2:
                    posRobot[1]++;
                    break;
                case 3:
                    posRobot[0]++;     
                    break;
                case 4:
                    posRobot[1]--;      
                    break;
            }
        } else {
            posRobot = new int[] {-1, -1};
        }
        return posRobot;
    }

    static boolean recorridoRobot(String[] mapa, String instrucciones){
        String leyenda = "";
        boolean esPosible = false;
        final char SALIDA = 'A';
        final char FINAL = 'Z';
        final char MINA = '*';
        final char PLACA_SWITCH_OFF = '-';
        final char PLACA_SWITCH_ON = '+';
        int placasOn = contarOcurenciasEnMapa(mapa, PLACA_SWITCH_ON);
        int placasTotal = contarOcurenciasEnMapa(mapa, PLACA_SWITCH_OFF) + placasOn;
        int direccion = 1; // 1 ^    2 >    3 v    4 <   -1 Error
        int[] posRobot = posicionEnMapa(mapa, SALIDA);
        int[] posFin = posicionEnMapa(mapa, FINAL);
        final int[] FUERA_LIMITE = new int[]{-1,-1};
        for (int i = 0; i < instrucciones.length(); i++) {
            leyenda = instrucciones.substring(0, i) + Character.toLowerCase(instrucciones.charAt(i)) + instrucciones.substring(i + 1);
            switch (instrucciones.charAt(i)) {
                case 'A':
                    if (posRobot[0] == posFin[0] && posRobot[1] == posFin[1]) {
                        mapa = actualizarMapa(mapa, posRobot, direccion, FINAL, posFin);
                    }
                    posRobot = avanzarRobot(mapa, posRobot, direccion);
                    if (mapa[posRobot[0]].charAt(posRobot[1]) == PLACA_SWITCH_OFF || mapa[posRobot[0]].charAt(posRobot[1]) == PLACA_SWITCH_ON){
                        mapa = actualizarMapa(mapa, posRobot, direccion, 'P', posFin);
                        placasOn = contarOcurenciasEnMapa(mapa, PLACA_SWITCH_ON);
                        placasTotal = contarOcurenciasEnMapa(mapa, PLACA_SWITCH_OFF) + placasOn;
                    }
                    if (posRobot[0] == FUERA_LIMITE[0] || posRobot[1] == FUERA_LIMITE[1]){
                        System.out.println("El robot se cayó del mapa :(");
                        return esPosible;
                    } else if(posRobot[0] == posFin[0] && posRobot[1] == posFin[1] && i == instrucciones.length()-1 && placasOn == placasTotal){
                        System.out.println(leyenda);
                        mapa = actualizarMapa(mapa, posRobot, direccion, instrucciones.charAt(i), posFin);
                        mostrarMapa(mapa);
                        return true;
                    } else if (mapa[posRobot[0]].charAt(posRobot[1]) == MINA){
                        System.out.println("El robot explotó con una mina :(");
                        return esPosible;
                    }
                    System.out.println(leyenda);
                    mapa = actualizarMapa(mapa, posRobot, direccion, instrucciones.charAt(i), posFin);
                    mostrarMapa(mapa);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("Error en la pausa");
                    }
                    break;
                    
                case 'R':
                    if(posRobot[0] == posFin[0] && posRobot[1] == posFin[1] && i == instrucciones.length()-1 && placasOn == placasTotal){
                        System.out.println(leyenda);
                        mapa = actualizarMapa(mapa, posRobot, direccion, instrucciones.charAt(i), posFin);
                        mostrarMapa(mapa);
                        return true;
                    }
                    System.out.println(leyenda);
                    mapa = actualizarMapa(mapa, posRobot, direccion, instrucciones.charAt(i), posFin);
                    mostrarMapa(mapa);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("Error en la pausa");
                    }
                    direccion = rotarRobot(direccion, instrucciones.charAt(i));
                    break;
                case 'L':
                    if(posRobot[0] == posFin[0] && posRobot[1] == posFin[1] && i == instrucciones.length()-1 && placasOn == placasTotal){
                        System.out.println(leyenda);
                        mapa = actualizarMapa(mapa, posRobot, direccion, instrucciones.charAt(i), posFin);
                        mostrarMapa(mapa);
                        return true;
                    }
                    System.out.println(leyenda);
                    mapa = actualizarMapa(mapa, posRobot, direccion, instrucciones.charAt(i), posFin);
                    mostrarMapa(mapa);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("Error en la pausa");
                    }
                    direccion = rotarRobot(direccion, instrucciones.charAt(i));
                    break;
                default:
                    return esPosible;
            }
        }
        if (placasOn == placasTotal) {
            
        }
        System.out.println(posRobot[0] == posFin[0] && posRobot[1] == posFin[1] ? "Quedan placas inactivas :(" : "El robot no llegó a la meta :(");
        return esPosible;
    }
    static String[] mapaBase(){
        String[] mapa = new String[] { //linea de prueba: araaaaalaaalaaaa
                "  Z -     ",
                " *        ",
                "  *  *    ",
                "      -   ",
                " A        "
        };
        return mapa;
    }

    public static void main(String[] args) {
        String[] mapa;
        String instruccioes;
        boolean victoria;
        System.out.println("Utilizando las letras \"A\", \"L\" y \"R\" escribe una cadena de texto que guíe al robot de \"A\" hasta \"Z\" evitando las minas (\"*\").");
         System.out.println("El robot comienza orientado hacia arriba.");
        System.out.println("\"A\" para avanzar");
        System.out.println("\"R\" para rotar horario");
        System.out.println("\"L\" para rotar antihorario");
        
        do {
            mapa = mapaBase();
            mostrarMapa(mapa);
            System.out.println("Instrucciones:");
            instruccioes = pedirInstrucciones();
            victoria = recorridoRobot(mapa, instruccioes);
        } while (!victoria);
        System.out.println("Felicidades, el robot llegó a la meta!");
    }
}
