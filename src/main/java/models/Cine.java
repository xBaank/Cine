package models;

import java.util.ArrayList;
import java.util.List;

public class Cine {
    private List<Sala> salas;
    private static final int SALAS_SIZE = 5;

    public Cine (int SalasCount)  {
        salas = new ArrayList<>(SalasCount);
        initialize(SalasCount);
    }

    public List<Sala> getSalas() {
        return salas;
    }

    protected void initialize(int count) {
        for (int i = 0; i < count; i++) {
            salas.add(new Sala(SALAS_SIZE));
        }
    }


}
