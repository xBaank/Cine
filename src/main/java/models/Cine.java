package models;

import java.util.ArrayList;
import java.util.List;

public class Cine {
    private List<Sala> salas;

    public Cine (int salasCount,int filasCount,int filasSize)  {
        salas = new ArrayList<>(salasCount);
        initialize(salasCount,filasCount,filasSize);
    }

    public List<Sala> getSalas() {
        return salas;
    }

    protected void initialize(int count,int filasCount,int filasSize) {
        for (int i = 0; i < count; i++) {
            salas.add(new Sala(filasCount,filasSize));
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (var sala: salas) {
            builder.append("Sala: ").append(sala.hashCode()).append("\n");
            builder.append("Libres: ").append(sala.getAsientos(Estado.LIBRE)).append("\n");
            builder.append("Reservados: ").append(sala.getAsientos(Estado.RESERVADO)).append("\n");
            builder.append("Ocupados: ").append(sala.getAsientos(Estado.OCUPADO)).append("\n");
        }
        return builder.toString();
    }
}
