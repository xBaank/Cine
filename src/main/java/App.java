import models.Cine;
import models.Menu;
import models.Sala;

public class App {
    private static final int FILAS_SIZE = 9;
    private static final int SALAS_SIZE = 5;

    public static void main(String args[]) {
        Cine cine = new Cine(1, SALAS_SIZE,FILAS_SIZE);
        Menu menu = new Menu();
        menu.display(cine);
    }
}
