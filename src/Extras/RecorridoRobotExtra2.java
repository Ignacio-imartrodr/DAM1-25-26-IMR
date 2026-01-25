package Extras;

import java.util.Scanner;

/**
 * @author Ignacio MR
 */
 
public class RecorridoRobotExtra2 {
    static Scanner sc = new Scanner(System.in);
    static void mostrarMapa(String[] mapa){
        for (int i = 0; i < mapa.length; i++) {
            System.out.print("|");
            System.out.print(mapa[i]);
            System.out.println("|");
        }
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
    static String[] alternarPlaca(String[] mapa, int[] posRobot, int direccion){
        String[] aux = copiarMapa(mapa);
        boolean esOn = (mapa[posRobot[0]].charAt(posRobot[1]) == '+');
        if (esOn) {
            aux[0] = mapa[posRobot[0]].substring(0, posRobot[1]);
            aux[1] = mapa[posRobot[0]].substring(posRobot[1]+1, mapa[posRobot[0]].length());
            mapa[posRobot[0]] = aux[0]+"-"+aux[1];
        } else {
            aux[0] = mapa[posRobot[0]].substring(0, posRobot[1]);
            aux[1] = mapa[posRobot[0]].substring(posRobot[1]+1, mapa[posRobot[0]].length());
            mapa[posRobot[0]] = aux[0]+"+"+aux[1];
        }
        return mapa;
    }
    static String[] actualizarMapa(String[] mapa, int[] posRobot, int direccion, char instruccion, int[] posFinOrIni, boolean enPlaca){
        // 1 ^    2 >    3 v    4 <   -1 Error
        String[] aux = new String[2];
        boolean enMeta = (posRobot[0] == posFinOrIni[0] && posRobot[1] == posFinOrIni[1]);
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
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("A",">"); 
                        }
                        if(enPlaca){
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("+",">"); 
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("-",">"); 
                        }
                        mapa[posRobot[0]] = mapa[posRobot[0]].replace("^",">");
                        break;
                    case 2:
                        if(enMeta){
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("Z","v");
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("A","v"); 
                        }
                        if(enPlaca){
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("+","v"); 
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("-","v"); 
                        }
                        mapa[posRobot[0]] = mapa[posRobot[0]].replace(">","v");
                        break;
                    case 3:
                        if(enMeta){
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("Z","<"); 
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("A","<"); 
                        }
                        if(enPlaca){
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("+","<"); 
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("-","<"); 
                        }
                        mapa[posRobot[0]] = mapa[posRobot[0]].replace("v","<");
                        break;
                    case 4:
                        if(enMeta){
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("Z","^"); 
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("A","^");
                        }
                        if(enPlaca){
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("+","^"); 
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("-","^"); 
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
                            
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("A","<"); 
                        }
                        if(enPlaca){
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("+","<"); 
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("-","<"); 
                        }
                        mapa[posRobot[0]] = mapa[posRobot[0]].replace("^","<");
                        break;
                    case 2:
                        if(enMeta){
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("Z","^"); 
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("A","^"); 
                        }
                        if(enPlaca){
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("+","^"); 
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("-","^"); 
                        }
                        mapa[posRobot[0]] = mapa[posRobot[0]].replace(">","^");
                        break;
                    case 3:
                        if(enMeta){
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("Z",">"); 
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("A",">");
                        }
                        if(enPlaca){
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("+",">"); 
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("-",">"); 
                        }
                        mapa[posRobot[0]] = mapa[posRobot[0]].replace("v",">");
                        break;
                    case 4:
                        if(enMeta){
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("Z","v");
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("A","v"); 
                        }
                        if(enPlaca){
                            mapa[posRobot[0]] = mapa[posRobot[0]].replace("+","v"); 
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
            case 'I':
                aux[0] = mapa[posRobot[0]].substring(0, posRobot[1]);
                aux[1] = mapa[posRobot[0]].substring(posRobot[1]+1, mapa[posRobot[0]].length());
                mapa[posRobot[0]] = aux[0]+"A"+aux[1];
                break;
        }
        return mapa;
    }
    static String[] copiarMapa(String[] mapaACopiar){
        String[] mapaCopia = new String[mapaACopiar.length];
        for (int i = 0; i < mapaACopiar.length; i++) {
            mapaCopia[i] = new String(mapaACopiar[i]);
        }
        return mapaCopia;
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
        if (posRobot[0] == mapa.length || posRobot[1] == mapa[0].length() || posRobot[0] < 0 || posRobot[1] < 0) {
            posRobot = new int[] {-1, -1};
        }
        return posRobot;
    }

    static boolean recorridoRobot(String[] mapa, String instrucciones){
        boolean enPlaca = false;
        boolean victoria = false;
        boolean derrota = false;
        String leyenda = "";
        String[] mapaPlacas = copiarMapa(mapa);
        final char SALIDA = 'A';
        final char FINAL = 'Z';
        final char MINA = '*';
        final char PLACA_SWITCH_OFF = '-';
        final char PLACA_SWITCH_ON = '+';
        final int[] FUERA_LIMITE = new int[]{-1,-1};
        int[] posFin = posicionEnMapa(mapa, FINAL);
        int[] posInicial = posicionEnMapa(mapa, SALIDA);
        int[] posRobot = new int[] {posInicial[0], posInicial[1]};
        int placasOn = contarOcurenciasEnMapa(mapa, PLACA_SWITCH_ON);
        int placasTotal = contarOcurenciasEnMapa(mapa, PLACA_SWITCH_OFF) + placasOn;
        int direccion = 1; // 1 ^    2 >    3 v    4 <   -1 Error
        for (int i = 0; i < instrucciones.length(); i++) {
            leyenda = instrucciones.substring(0, i) + Character.toLowerCase(instrucciones.charAt(i)) + instrucciones.substring(i + 1);
            switch (instrucciones.charAt(i)) {
                case 'A':
                    enPlaca = false;
                    if (posRobot[0] == posFin[0] && posRobot[1] == posFin[1]) {
                        mapa = actualizarMapa(mapa, posRobot, direccion, FINAL, posFin, enPlaca);
                    } else if(posRobot[0] == posInicial[0] && posRobot[1] == posInicial[1]){
                        mapa = actualizarMapa(mapa, posRobot, direccion, 'I', posInicial, enPlaca);
                    }
                    posRobot = avanzarRobot(mapa, posRobot, direccion);
                    if (posRobot[0] == FUERA_LIMITE[0] || posRobot[1] == FUERA_LIMITE[1]){
                        System.out.println("El robot se cayó del mapa :(");
                        derrota = true;
                        break;
                    } else if (mapa[posRobot[0]].charAt(posRobot[1]) == MINA){
                        System.out.println("El robot explotó con una mina :(");
                        derrota = true;
                        break;
                    }
                    if (mapaPlacas[posRobot[0]].charAt(posRobot[1]) == PLACA_SWITCH_OFF || mapaPlacas[posRobot[0]].charAt(posRobot[1]) == PLACA_SWITCH_ON) {
                        enPlaca = true;
                        mapaPlacas = alternarPlaca(mapaPlacas, posRobot, direccion);
                        placasOn = contarOcurenciasEnMapa(mapaPlacas, PLACA_SWITCH_ON);
                    }
                    if(posRobot[0] == posFin[0] && posRobot[1] == posFin[1] && i == instrucciones.length()-1 && placasOn == placasTotal){
                        System.out.println(leyenda);
                        mapa = actualizarMapa(mapa, posRobot, direccion, instrucciones.charAt(i), posFin, enPlaca);
                        mostrarMapa(mapa);
                        System.out.println();
                        victoria = true;
                    } 
                    if (!victoria){
                       System.out.println(leyenda);
                        mapa = actualizarMapa(mapa, posRobot, direccion, instrucciones.charAt(i), posFin, enPlaca);
                        mostrarMapa(mapa);
                        System.out.println();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            System.out.println("Error en la pausa");
                        }
                        if (enPlaca && i != instrucciones.length()-1) {
                            mapa[posRobot[0]] = mapaPlacas[posRobot[0]];
                        }
                    }
                    
                    break;
                case 'R':
                    if(posRobot[0] == posFin[0] && posRobot[1] == posFin[1] && i == instrucciones.length()-1 && placasOn == placasTotal){
                        System.out.println(leyenda);
                        mapa = actualizarMapa(mapa, posRobot, direccion, instrucciones.charAt(i), posFin, enPlaca);
                        mostrarMapa(mapa);
                        System.out.println();
                        victoria = true;
                    }
                    if (!victoria) {
                        System.out.println(leyenda);
                        mapa = actualizarMapa(mapa, posRobot, direccion, instrucciones.charAt(i), posFin, enPlaca);
                        mapa = actualizarMapa(mapa, posRobot, direccion, instrucciones.charAt(i), posInicial, enPlaca);
                        mostrarMapa(mapa);
                        System.out.println();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            System.out.println("Error en la pausa");
                        }
                        if (enPlaca && i != instrucciones.length()-1) {
                            mapa[posRobot[0]] = mapaPlacas[posRobot[0]];
                        }
                    }
                    direccion = rotarRobot(direccion, instrucciones.charAt(i));
                    break;
                case 'L':
                    if(posRobot[0] == posFin[0] && posRobot[1] == posFin[1] && i == instrucciones.length()-1 && placasOn == placasTotal){
                        System.out.println(leyenda);
                        mapa = actualizarMapa(mapa, posRobot, direccion, instrucciones.charAt(i), posFin, enPlaca);
                        mostrarMapa(mapa);
                        System.out.println();
                        victoria = true;
                    }
                    if (!victoria){
                        System.out.println(leyenda);
                        mapa = actualizarMapa(mapa, posRobot, direccion, instrucciones.charAt(i), posFin, enPlaca);
                        mapa = actualizarMapa(mapa, posRobot, direccion, instrucciones.charAt(i), posInicial, enPlaca);
                        mostrarMapa(mapa);
                        System.out.println();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            System.out.println("Error en la pausa");
                        }
                        if (enPlaca && i != instrucciones.length()-1) {
                            mapa[posRobot[0]] = mapaPlacas[posRobot[0]];
                        }
                    }
                    direccion = rotarRobot(direccion, instrucciones.charAt(i));
                    break;
                default:
                    break;
            }
            if (derrota) break;
        }
        if(!victoria && !derrota) System.out.println(posRobot[0] == posFin[0] && posRobot[1] == posFin[1] ? "Quedan placas inactivas :(" : "El robot no llegó a la meta :(");
        return victoria;
    }
    static String[] mapaBase(int lvl){
        String[] mapa;
        switch (lvl) {
            case 0:
                mapa = new String[] { //linea de prueba: araaaaalaaalaaaa
                    "  Z -     ",
                    " *        ",
                    "  *  *    ",
                    "      -   ",
                    " A        "
                };
                break;
            case 1:
                mapa = new String[] { //linea de prueba: aaraaaaaaalalaaaaararalaaraaaaaaa
                    "     -    Z   ",
                    "  *     *     ",
                    "      *       ",
                    "  -       *   ",
                    "       -      ",
                    " *            ",
                    "A             "
                };
                break;
            default:
                mapa = new String[] { //linea de prueba: aaaaara
                    "  Z       ",
                    "          ",
                    "          ",
                    "          ",
                    " A        "
                };
                break;
        }
        return mapa;
    }

    public static void main(String[] args) {
        String instruccioes;
        boolean victoria;
        int nivel = 0;
        int lvlMax = 1;
        String[] mapa = mapaBase(nivel);
        System.out.println("Utilizando las letras \"A\", \"L\" y \"R\" escribe una cadena de texto que guíe al robot de \"A\" hasta \"Z\" evitando las minas (\"*\") y activando todas las placas de presión (\"+/-\").");
        System.out.println("El robot comienza orientado hacia arriba.");
        System.out.println("\"A\" para avanzar");
        System.out.println("\"R\" para rotar horario");
        System.out.println("\"L\" para rotar antihorario");
        System.out.println();
        do {
            System.out.println("Nivel: " + (nivel + 1));
            mostrarMapa(mapa);
            mapa = mapaBase(nivel);
            System.out.println("Instrucciones:");
            instruccioes = pedirInstrucciones();
            victoria = recorridoRobot(mapa, instruccioes);
            if (victoria) {
                System.out.println("El robot llegó a la meta!");
                nivel++;
                mapa = mapaBase(nivel);
            }
        } while (!((nivel > lvlMax) && victoria) || !victoria);
        System.out.println("Felicidades, ganaste el juego!");
    }
}
