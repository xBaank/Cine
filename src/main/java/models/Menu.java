package models;

import exceptions.CanceledException;
import utils.Input;

import java.util.*;

public class Menu {
    private MenuEstado estado = MenuEstado.MENU;
    private final Cine cine;
    private Sala sala;
    //Butacas reservadas por el usuario actual, asi no hay que buscar las butacas reservadas
    private Map<Sala,List<Butaca>> butacasReservadas = new HashMap<>();

    public Menu(Cine cine) {
        this.cine = cine;
        display();
    }

    private void display() {
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
                    while (!cancelarCompra(Input.readInt())) {
                        displayMessage("Ticket no encontrado");
                    }
                }
                catch (CanceledException exception) {
                    estado = MenuEstado.MENU;
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
        //Mientras el estado no sea salida crea un loop
        if(estado != MenuEstado.SALIR)
            display();
    }

    private void displayMenu() {
        System.out.println("0. Salir");
        System.out.println("1. Ver Salas");
        System.out.println("2. Cancelar compras");
        if(butacasReservadas.size() > 0) {
            System.out.println("3. Confirmar reservas");
            System.out.println("4. Cancelar reservas");
        }
    }

    private void displayCancelar() {
        System.out.println("Introduce 0 para volver atras");
        System.out.println("Introduce el ticket de la compra: ");
    }

    private void displaySalas(Cine cine) {
        System.out.println(cine.toString());
        System.out.println("Introduce 0 para volver atras");
        System.out.println("Selecciona una sala: ");
    }

    private void diplayFilas(Sala sala) {
        System.out.println(sala.toString());
        System.out.println("Introduce 0 para volver atras");
        System.out.println("Selecciona una butaca: ");
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
                break;
            case 3:
                if(butacasReservadas.size() == 0)
                    return false;
                System.out.println(confirmarReserva().toString());
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
        if (searchedSala != null) {
            sala = searchedSala;
            butacasReservadas.put(sala,new ArrayList<>());
            estado = MenuEstado.SALA;
        }
        return searchedSala != null;
    }


    private boolean setButaca(ButacaPos pos) {
        char filaLetra = pos.getLetter();
        int columna = pos.getColumn();

        var butaca = sala.searchButaca(filaLetra,columna);

        if(butaca == null)
            return false;

        if(butaca.getEstado() != Estado.RESERVADO) {
            butacasReservadas.get(sala).add(butaca);
            butaca.setEstado(Estado.RESERVADO);
        }
        else
        {
            butacasReservadas.get(sala).remove(butaca);
            butaca.setEstado(Estado.LIBRE);
        }

        estado = MenuEstado.SALA;
        return true;

    }

    private List<Ticket> confirmarReserva() {
        List<Ticket> tickets = new ArrayList<>();
        for(var salaButacas:butacasReservadas.entrySet()) {
            var ticket = cine.confirmarCompra(salaButacas.getValue(), salaButacas.getKey());
            butacasReservadas = new Hashtable<>();
            tickets.add(ticket);
        }
        return tickets;
    }

    private void cancelarReserva() {
        for (var salaButacas:butacasReservadas.entrySet()) {
            salaButacas.getValue().forEach(i -> i.setEstado(Estado.LIBRE));
        }
        butacasReservadas.clear();
    }

    private boolean cancelarCompra(int id) {
        var ticket = cine.getTicket(id);
        if(ticket == null) return false;
        cine.cancelarCompra(ticket);
        estado = MenuEstado.MENU;
        return true;
    }


}
