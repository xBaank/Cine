package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ticket {
    private Sala sala;
    private List<ButacaPos> butacas = new ArrayList<>();
    private Date date;
    private float precio;

    public Ticket(Sala sala,List<Butaca> butacas,float precio) {
        this.sala = sala;
        butacas.forEach(i -> {
            this.butacas.add(sala.getButacaPos(i));
        });
        date = new Date();
        this.precio = precio;
    }

    public Sala getSala() {
        return sala;
    }

    public List<ButacaPos> getButacasPos() {
        return butacas;
    }

    public Date getDate() {
        return date;
    }

    public float getPrecio() {
        return precio * butacas.size();
    }

    private String butacasPosToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ButacaPos butaca : butacas) {
            stringBuilder.append(butaca.toString()).append(" ");
        }
        return  stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "Ticket id: " + hashCode() + "\n" +
                "sala=" + getSala().hashCode() + "\n" +
                "date=" + getDate() + "\n" +
                "butacas=" + butacasPosToString() + "\n" +
                "precio=" + getPrecio() + "\n";
    }
}
