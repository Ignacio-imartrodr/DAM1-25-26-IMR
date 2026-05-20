package UD6.AjedrezConFx.Entity;

public class Torre extends Pieza{
    public Torre(String posicion, boolean esBlanca){
        posAct = parsePos(posicion);
        if (posAct == null) {
            throw new IllegalArgumentException("Posición invalida");
        }
        color = esBlanca;
    }

    @Override
    public boolean validarMovimientoIndiv(String ini, String dest) {
        if (ini.equalsIgnoreCase(dest)) {
            return false;
        }
        Byte[] start = parsePos(ini);
        Byte[] end = parsePos(dest);

        if (start[0] == end[0] || start[1] == end[1]) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return color ? "T" : "t";
    }
}
