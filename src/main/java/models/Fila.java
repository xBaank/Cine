package models;

import java.util.ArrayList;

public class Fila extends ArrayList<Butaca>{
    private char letra;
    public Fila(char letra,int size) {
        initialize(size);
        this.letra = letra;
    }
    protected void initialize(int size) {
        for (int i = 0; i < size; i++) {
            add(new Butaca());
        }
    }

    public char getLetra() {
        return letra;
    }
}
