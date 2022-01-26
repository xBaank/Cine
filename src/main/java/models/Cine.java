package models;

import exceptions.CineException;

import java.util.ArrayList;
import java.util.List;

public class Cine {
    private List<Sala> salas;
    private List<Ticket> tickets = new ArrayList<>();
    private float price;

    public Cine (int salasCount,int filasCount,int filasSize,float price)  {
        if(salasCount <= 0)
            throw new CineException("Debe haber al menos 1 sala");
        if(filasCount <= 0)
            throw new CineException("Debe haber al menos 1 fila");
        if(filasSize <= 0)
            throw new CineException("Debe haber al menos 1 butaca por fila");


        salas = new ArrayList<>(salasCount);
        setPrice(price);
        initialize(salasCount,filasCount,filasSize);
    }

    protected void initialize(int count,int filasCount,int filasSize) {
        for (int i = 0; i < count; i++) {
            salas.add(new Sala(filasCount,filasSize));
        }
    }

    public float getPrice() {
        return price;
    }

    public float getTakins() {
        float result = 0;
        for (Ticket ticket : tickets) {
            result += ticket.getPrecio();
        }
        return result;
    }

    public void setPrice(float price) {
        if(price <= 0)
            throw new CineException("El precio debe ser mayor que 0");
        this.price = price;
    }

    public List<Sala> getSalas() {
        return salas;
    }

    public Ticket getTicket(int id) {
        return tickets.stream().filter(i -> i.hashCode() == id).findFirst().orElse(null);
    }

    private void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public Sala searchSala(int id) {
        var salaOptional = getSalas().stream().filter(i -> i.hashCode() == id).findFirst();
        return salaOptional.orElse(null);
    }

    public Ticket confirmarCompra(List<Butaca> butacas,Sala sala) {
        if(butacas.size() == 0)
            throw new CineException("La lista de butacas esta vacia");

        if(sala == null)
            throw new CineException("La lista de butacas esta vacia");

        for (var butaca:butacas) {
            butaca.setEstado(Estado.OCUPADO);
        }
        var ticket = new Ticket(sala,butacas,getPrice());
        addTicket(ticket);
        return ticket;
    }

    public void cancelarCompra(Ticket ticket) {
        if (ticket == null)
            throw new CineException("Ticket no puede ser nulo");

        if (!tickets.contains(ticket))
            throw new CineException("Ticket no existente");

        for (var butacaPos : ticket.getButacasPos()) {
            ticket.getSala().searchButaca(butacaPos).setEstado(Estado.LIBRE);
        }
        tickets.remove(ticket);
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
