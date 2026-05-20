package UD6.AjedrezConFx.Entity;

import java.util.function.Predicate;

public abstract class Pieza {
    protected Byte[] posAct;//Columna y fila (en ese orden) con el formato de ajedrez de "a1" a "h8"
    protected boolean color;
    
    protected static final Predicate<String> VALIDAR_POSICION = pos -> pos != null && pos.matches("[a-hA-H][1-8]");

    protected static final Byte[] parsePos(String pos){
        Byte[] posicion = null;

        if (VALIDAR_POSICION.test(pos)) {
            posicion = new Byte[2];
            String col = pos.substring(0, 1);
            String letras = "abcdefgh";
            for (int i = 0; i < letras.length(); i++) {
                if (col.equalsIgnoreCase(String.valueOf(letras.charAt(i)))) {
                    posicion[0] = Byte.valueOf(String.valueOf(i));
                }
            }
            
            if (posicion[0] == null) {
                return null;
            }

            String fila = pos.substring(1);
            posicion[1] = Byte.valueOf(fila);
            posicion[1]--;
        }
        return posicion;
    }

    abstract boolean validarMovimientoIndiv(String ini, String dest);

    final public boolean isBlanca() {
        return color;
    }

    @Override
    abstract public String toString();

    
}
