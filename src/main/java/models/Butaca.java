package models;

public class Butaca {
    private Estado estado = Estado.LIBRE;

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
