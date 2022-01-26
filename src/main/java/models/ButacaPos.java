package models;

public class ButacaPos {
    private char letter;
    private int column;

    public ButacaPos(char letter,int column) {
        this.letter = letter;
        this.column = column;
    }

    public char getLetter() {
        return letter;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return String.valueOf(getLetter())+String.valueOf(getColumn());
    }
}
