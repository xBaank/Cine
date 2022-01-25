package models;

import java.util.Date;
import java.util.List;

public class Ticket {
    private Sala sala;
    private List<Butaca> butacas;
    private Date date;
    private float precio;

    public Ticket(Sala sala,List<Butaca> butacas,float precio) {
        this.sala = sala;
        this.butacas = butacas;
        date = new Date();
        this.precio = precio;
    }

    public Sala getSala() {
        return sala;
    }

    public List<Butaca> getButacas() {
        return butacas;
    }

    public Date getDate() {
        return date;
    }

    public float getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return "Ticket id: " + hashCode() + "\n" +
                "sala=" + sala.hashCode() + "\n" +
                "date=" + date + "\n" +
                "precio=" + precio + "\n";
    }
}
