package models;

import java.util.Date;

public class Ticket {
    private Sala sala;
    private Date date;
    private float precio;

    public Ticket(Sala sala) {
        this.sala = sala;
        date = new Date();
    }

    public Sala getSala() {
        return sala;
    }

    public Date getDate() {
        return date;
    }

    public float getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "sala=" + sala +
                ", date=" + date +
                ", precio=" + precio +
                '}';
    }
}
