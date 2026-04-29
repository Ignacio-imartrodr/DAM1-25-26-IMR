package UD5.praiasdegalicia;

public class BanderaAzul extends Praia {
    private boolean accesible = false;
    private boolean duchas = false;
    private boolean aseos = false;
    private boolean socorrista = false;

    BanderaAzul(Praia p){
        super(p.getId(), p.getNome(), p.getConcello(), p.getConcello(), p.getLat(), p.getLon());
    }

    public boolean isAccesible() {
        return accesible;
    }

    public boolean isDuchas() {
        return duchas;
    }

    public boolean isAseos() {
        return aseos;
    }

    public boolean isSocorrista() {
        return socorrista;
    }

    public void setAccesible(boolean accesible) {
        this.accesible = accesible;
    }

    public void setDuchas(boolean duchas) {
        this.duchas = duchas;
    }

    public void setAseos(boolean aseos) {
        this.aseos = aseos;
    }

    public void setSocorrista(boolean socorrista) {
        this.socorrista = socorrista;
    }

    @Override
    public String toString() {
        String str = super.toString();
        str += " - Certificaciones: ";
        str += "Accesible = " + (accesible ? "Sí" : "No") + ", Duchas = " + (duchas ? "Sí" : "No") + 
                ", Aseos = " + (aseos ? "Sí" : "No") + ", Socorrista = " + (socorrista ? "Sí" : "No");
        return str;
    }

    @Override
    public int compareTo(Praia o) {
        if (o instanceof BanderaAzul) {
            int compProv = this.getProvincia().compareTo(o.getProvincia());
            if (compProv != 0) {
                return compProv;
            }
            int compConcell = this.getConcello().compareTo(o.getConcello());
            if (compConcell != 0) {
                return compConcell;
            }
            int compNom = this.getNome().compareTo(o.getNome());
            return compNom;
        } else {
            return super.compareTo(o);
        }
    }
}
