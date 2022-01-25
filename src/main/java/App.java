import models.Cine;
import menu.Menu;

public class App {
    private static final int FILAS_SIZE = 9;
    private static final int FILAS_COUNT = 5;
    private static final int SALAS_COUNT = 5;
    private static final float PRICE = 9.5f;

    public static void main(String args[]) {
        Cine cine = new Cine(SALAS_COUNT, FILAS_COUNT,FILAS_SIZE,PRICE);
        Menu menu = new Menu(cine);
    }
}
