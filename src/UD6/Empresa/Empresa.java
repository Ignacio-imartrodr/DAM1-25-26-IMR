package UD6.Empresa;

public class Empresa implements Comparable<Empresa> {

    int id;
    String nombre;
    String web;
    
    public Empresa(int id, String nombre, String web) {
        this.id = id;
        this.nombre = nombre;
        this.web = web;
    }

    public Empresa(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return id + " - " + nombre;
    }

    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Empresa other = (Empresa) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public int compareTo(Empresa o) {
        return nombre.compareTo(o.nombre);
    }

}
