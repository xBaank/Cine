package models;

import java.util.ArrayList;
import java.util.List;

public class Sala {
    private List<Fila> filas;

    public Sala(int filasCount,int filasSize) {
        filas = new ArrayList<>(filasCount);
        initialize(filasCount,filasSize);
    }
    protected void initialize(int filasCount,int filasSize) {
        char i = 'A';
        int j = 0;
        while (j < filasCount) {
            filas.add(new Fila(i,filasSize));
            i++;j++;
        }
    }

    public List<Fila> getFilas() {
        return filas;
    }

    public int getAsientos(Estado estado) {
        int result = 0;
        for (var fila:filas) {
           result += fila.stream().filter(i -> i.getEstado() == estado).count();
        }
        return result;
    }

    public Butaca searchButaca(char filaLetra,int columna) {
        var fila = getFilas().stream().filter(i -> i.getLetra() == filaLetra).findFirst();

        if(fila.isEmpty())
            return null;

        var butaca = fila.get().stream().filter(i -> fila.get().indexOf(i) == columna).findFirst();

        return butaca.orElse(null);
    }
    public Butaca searchButaca(ButacaPos butacaPos) {
        return  searchButaca(butacaPos.getLetter(),butacaPos.getColumn());
    }

    public ButacaPos getButacaPos(Butaca butaca) {
        char letter;
        int column;

        var fila = getFilas().stream().filter(i ->
                i.stream().filter(b -> b == butaca).findFirst().orElse(null) != null).findFirst();

        if(fila.isEmpty())
            return null;
        letter = fila.get().getLetra();

        var butacaResult = fila.get().stream().filter(i -> i.equals(butaca)).findFirst();

        column = fila.get().indexOf(butaca);
        return new ButacaPos(letter,column);
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        int filaLength = filas.stream().findFirst().get().size();

        result.append("  ");
        for (int i = 0; i < filaLength; i++) {
            result.append(i + 1).append(" ");
        }
        result.append("\n");
        for (var fila: filas) {
            result.append(fila.getLetra()).append(" ");
            for (var butaca: fila) {
                result.append(butaca.getEstado().toString().charAt(0)).append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }
}
