package models;

import exceptions.CanceledException;
import utils.Input;

import java.util.*;

public class Menu {
    private MenuEstado estado = MenuEstado.MENU;
    private Cine cine;
    private Sala sala;
    //Butacas reservadas por el usuario actual
    private List<Butaca> butacasReservadas = new ArrayList<>();

    public Menu(Cine cine) {
        this.cine = cine;
        display(cine);
    }

    private void display(Cine cine) {
        switch (estado) {
            case MENU:
                displayMenu();
                try {
                    while (!setMenu(Input.readInt())) {
                        displayMessage("Opcion no valida");
                    }
                }
                catch (CanceledException exception) {
                    estado = MenuEstado.SALIR;
                }
                break;
            case CANCELAR:
                displayCancelar();
                try {
                    while (!cine.cancelTicket(Input.readInt())) {
                        displayMessage("Ticket no encontrado");
                    }
                }
                catch (CanceledException exception) {
                    estado = MenuEstado.SALIR;
                }
                break;
            case CINE:
                displaySalas(cine);
                try {
                    while (!setSala(Input.readInt())) {
                        displayMessage("La sala no esta presente");
                    }
                }
                catch (CanceledException exception) {
                    estado = MenuEstado.MENU;
                }
                break;
            case SALA:
                diplayFilas(sala);
                try {
                    while (!setButaca(Input.readButacaPos())) {
                        displayMessage("La butaca no esta presente");
                    }
                }catch (CanceledException exception) {
                    estado = MenuEstado.CINE;
                }
                break;
        }
        //Mientras el estaado no sea salida crea un loop
        if(estado != MenuEstado.SALIR)
            display(cine);
    }

    private void displayMenu() {
        System.out.println("0. Salir");
        System.out.println("1. Ver Salas");
        System.out.println("2. Cancelar compras");
        if(butacasReservadas.size() > 0) {
            System.out.println("3. Confirmar reservas");
            System.out.println("4. Cancelar reservas");
        }
        estado = MenuEstado.MENU;
    }

    private void displayCancelar() {
        System.out.println("Introduce 0 para volver atras");
        System.out.println("Introduce el ticket de la compra: ");
        estado = MenuEstado.MENU;
    }

    private void displaySalas(Cine cine) {
        System.out.println(cine.toString());
        System.out.println("Introduce 0 para volver atras");
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

    private boolean setMenu(int value) {
        switch (value) {
            case 1:
                estado = MenuEstado.CINE;
                break;
            case 2:
                estado = MenuEstado.CANCELAR;
            case 3:
                if(butacasReservadas.size() == 0)
                    return false;
                else
                    confirmarReserva();
                break;
            case 4:
                if(butacasReservadas.size() == 0)
                    return false;
                else
                    cancelarReserva();
                break;

            default:
                return false;

        }
        return true;
    }

    private boolean setSala(int id) {
        var searchedSala = cine.searchSala(id);
        estado = MenuEstado.SALA;
        return searchedSala != null;
    }


    private boolean setButaca(ButacaPos pos) {
        char filaLetra = pos.getLetter();
        int columna = pos.getColumn();

        var butaca = sala.searchButaca(filaLetra,columna);

        if(butaca == null)
            return false;

        if(butaca.getEstado() != Estado.RESERVADO) {
            butacasReservadas.add(butaca);
            butaca.setEstado(Estado.RESERVADO);
        }
        else
        {
            butacasReservadas.remove(butaca);
            butaca.setEstado(Estado.LIBRE);
        }

        estado = MenuEstado.SALA;
        return true;

    }

    private void confirmarReserva() {
        for (var butaca:butacasReservadas) {
            butaca.setEstado(Estado.OCUPADO);
        }
        cine.addTicket(new Ticket(sala));
        butacasReservadas.clear();
    }

    private void cancelarReserva() {
        for (var butaca:butacasReservadas) {
            butaca.setEstado(Estado.LIBRE);
        }
        butacasReservadas.clear();
    }


}
