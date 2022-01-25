package models;

import exceptions.CineException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cine {
    private List<Sala> salas;
    private List<Ticket> tickets = new ArrayList<>();

    public Cine (int salasCount,int filasCount,int filasSize)  {
        salas = new ArrayList<>(salasCount);
        initialize(salasCount,filasCount,filasSize);
    }

    protected void initialize(int count,int filasCount,int filasSize) {
        for (int i = 0; i < count; i++) {
            salas.add(new Sala(filasCount,filasSize));
        }
    }

    public List<Sala> getSalas() {
        return salas;
    }

    public Sala searchSala(int id) {
        var salaOptional = getSalas().stream().filter(i -> i.hashCode() == id).findFirst();
        return salaOptional.orElse(null);
    }

    public Ticket confirmarCompra(List<Butaca> butacas,Sala sala) {
        for (var butaca:butacas) {
            butaca.setEstado(Estado.OCUPADO);
        }
        var ticket = new Ticket(sala,butacas,1);
        addTicket(ticket);
        return ticket;
    }

    public void cancelarCompra(Ticket ticket) {
        if(ticket == null)
            throw new CineException("Ticket no puede ser nulo");

        if(!tickets.contains(ticket))
            throw new CineException("Ticket no existente");

        for (var butaca:ticket.getButacas()) {
            butaca.setEstado(Estado.LIBRE);
        }
        tickets.remove(ticket);
    }

    public Ticket getTicket(int id) {
        return tickets.stream().filter(i -> i.hashCode() == id).findFirst().orElse(null);
    }


    private void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (var sala: salas) {
            builder.append("Sala: ").append(sala.hashCode()).append("\n");
            builder.append("Libres: ").append(sala.getAsientos(Estado.LIBRE)).append("\n");
            builder.append("Reservados: ").append(sala.getAsientos(Estado.RESERVADO)).append("\n");
            builder.append("Ocupados: ").append(sala.getAsientos(Estado.OCUPADO)).append("\n");
            builder.append("\n");
        }
        return builder.toString();
    }
}
