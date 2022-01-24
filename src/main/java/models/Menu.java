package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private MenuEstado estado = MenuEstado.MENU;
    private Cine cine;
    private Sala sala;
    private List<Butaca> butacasReservadas = new ArrayList<>();

    public void display(Cine cine) {
        this.cine = cine;

        switch (estado) {
            case MENU:
                displayMenu();
                while (!readMenu(readInt())) {
                    displayMessage("Opcion no valida");
                }
                break;
            case CINE:
                displaySalas(cine);
                while (!readSala(readInt())) {
                    displayMessage("La sala no esta presente");
                }
                break;
            case SALA:
                diplayFilas(sala);
                while (!readButaca(readCharWithInt())) {
                    displayMessage("La butaca no esta presente");
                }
                break;
        }
        if(estado != MenuEstado.SALIR)
            display(cine);
    }

    private void displayMenu() {
        System.out.println("1. Ver Salas");
        System.out.println("2. Salir");
        estado = MenuEstado.MENU;
    }

    private void displaySalas(Cine cine) {
        System.out.println(cine.toString());
        System.out.println("Selecciona una sala: ");
        estado = MenuEstado.CINE;
    }

    private void diplayFilas(Sala sala) {
        System.out.println(sala.toString());
        System.out.println("Introduce 0 para volver atras");
        System.out.println("Selecciona una butaca: ");
        estado = MenuEstado.SALA;
    }

    private void displayMessage(String message) {
        System.out.println(message);
    }

    private int readInt() {
        Scanner scanner = new Scanner(System.in);
        boolean isOk = false;
        int result = 0;

        while (!isOk){
            try {
                result = scanner.nextInt();
                isOk = true;
            }
            catch (Exception ex) {
                System.out.println("Character no valido en la entrada");
                scanner.nextLine();
            }
        }
        return result;
    }

    private String readCharWithInt() {
        Scanner scanner = new Scanner(System.in);
        boolean isOk = false;
        String result = "";

        while (!isOk){
            try {
                result = scanner.next();
                if(Character.isDigit(result.charAt(0)) || !Character.isDigit(result.charAt(1)))
                    throw new Exception();
                isOk = true;
            }
            catch (Exception ex) {
                System.out.println("Entrada no valida");
                scanner.nextLine();
            }
        }
        return result;
    }

    private boolean readMenu(int value) {
        switch (value) {
            case 1:
                estado = MenuEstado.CINE;
                break;
            case 2:
                estado = MenuEstado.SALIR;
                break;
            default:
                return false;

        }
        return true;
    }

    private boolean readSala(int value) {
        var salaOptional = cine.getSalas().stream().filter(i -> i.hashCode() == value).findFirst();
        if(salaOptional.isPresent())
        {
            sala = salaOptional.get();
            estado = MenuEstado.SALA;
            return true;
        }
        else
            return false;
    }

    private boolean readButaca(String pos) {
        char filaLetra = pos.charAt(0);
        int columna = Character.getNumericValue(pos.charAt(1));

        var filas = sala.getFilas();
        var fila = filas.stream().filter(i -> i.getLetra() == filaLetra).findFirst();

        if(fila.isEmpty())
            return false;

        var butaca = fila.get().stream().filter(i -> fila.get().indexOf(i)+1 == columna).findFirst();

        if(butaca.isPresent())
        {
            var butacaReservada = butaca.get();
            butacasReservadas.add(butacaReservada);
            butacaReservada.setEstado(Estado.RESERVADO);
            estado = MenuEstado.SALA;
            return true;
        }
        else
            return false;
    }


}
