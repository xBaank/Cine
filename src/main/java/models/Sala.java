package models;

import java.util.ArrayList;
import java.util.List;

public class Sala {
    private List<Fila> filas;
    private static final int FILAS_SIZE = 9;

    public Sala(int size) {
        filas = new ArrayList<>(size);
        initialize(size);
    }
    protected void initialize(int size) {
        char i = 'A';
        int j = 0;
        while (j < size) {
            filas.add(new Fila(i, FILAS_SIZE));
            i++;j++;
        }
    }

    public List<Fila> getFilas() {
        return filas;
    }
}
