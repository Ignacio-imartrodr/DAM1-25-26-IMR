package UD6.Recu.Corregido;

import java.io.Serializable;

/**
 * @author Ignacio Martínez Rodríguez
 */
public class Producto implements Serializable, Comparable<Producto> {
    int codigo;
    String nombre;
    int cantidad;
    double precio;

    public Producto(int codigo, String nombre, int cantidad, double precio) {
        setCodigo(codigo);
        setNombre(nombre);
        setCantidad(cantidad);
        setPrecio(precio);
    }

    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estár en blanco");
        }
        this.nombre = nombre.strip();
    }

    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad debe ser positiva");
        }
        this.cantidad = cantidad;
    }
    
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio debe ser positivo");
        }
        this.precio = precio;
    }
    @Override
    public String toString() {
        return codigo + " - " + nombre + ", cantidad: " + cantidad + ", precio: " + precio;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Producto other = (Producto) obj;
        if (codigo != other.codigo)
            return false;
        return true;
    }
    
    @Override
    public int compareTo(Producto o) {
        return this.codigo - o.codigo;
    }
}
