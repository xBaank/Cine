package models;

import exceptions.CineException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import testData.cineData;

import java.util.ArrayList;
import java.util.List;

class CineTest {
    Cine cine;

    @BeforeEach
    public void beforeEach() {
        cine = new Cine(5,5,5,9.50f);
    }

    @Test
    public void cineConstructorShouldThrow() {
        Assertions.assertThrows(CineException.class,() -> new Cine(0,1,1,1));
        Assertions.assertThrows(CineException.class,() -> new Cine(1,0,1,1));
        Assertions.assertThrows(CineException.class,() -> new Cine(1,1,0,1));
        Assertions.assertThrows(CineException.class,() -> new Cine(1,1,1,0));
        }

    @Test
    public void cineShouldSearchButacas() {
        var butaca = cine.getSalas().stream().findAny().get().searchButaca(cineData.ROW,cineData.COLUMN);
        Assertions.assertNotEquals(butaca,null);
    }

    @ParameterizedTest
    @ValueSource(ints = {cineData.COLUMN_OUT,cineData.COLUMN_OUT_NEGATIVE})
    public void salaShouldNotSearchColumn(int column) {
        var butaca = cine.getSalas().stream().findAny().get().searchButaca('A',column);
        Assertions.assertNull(butaca);

    }

    @ParameterizedTest
    @ValueSource(chars = {cineData.ROW_OUT})
    public void salaShouldNotSearchRow(char row) {
        var butaca = cine.getSalas().stream().findAny().get().searchButaca(row,cineData.COLUMN);
        Assertions.assertNull(butaca);

    }

    @ParameterizedTest
    @EnumSource(Estado.class)
    public void cineShouldGetEstadosButacas(Estado estado) {
        var butaca = cine.getSalas().stream().findAny().get().getAsientos(estado);
        Assertions.assertTrue(butaca >= 0);
    }

    @Test
    public void cineShouldGetFilas() {
        var filas = cine.getSalas().stream().findAny().get().getFilas();
        Assertions.assertTrue(filas.size() > 0);
    }

    @Test
    public void cineShouldSetandGetPrice() {
        cine.setPrice(8);
        var price = cine.getPrice();
        Assertions.assertEquals(8, price);
    }

    @Test
    public void cineShouldSearchSala() {
        var sala = cine.getSalas().stream().findAny().get();
        var searchedSala = cine.searchSala(sala.hashCode());
        Assertions.assertEquals(searchedSala, sala);
    }

    @Test
    public void cineShouldCancelCompra() {
        var sala = cine.getSalas().stream().findAny().get();
        var butaca = sala.searchButaca(cineData.ROW,cineData.COLUMN);
        var ticket = cine.confirmarCompra(List.of(butaca),sala);
        cine.cancelarCompra(ticket);
        Assertions.assertNull(cine.searchSala(ticket.hashCode()));
    }

    @Test
    public void cineShouldThrowCompra() {
        var sala = cine.getSalas().stream().findAny().get();
        Assertions.assertAll(
            () -> Assertions.assertThrows(CineException.class,() -> cine.confirmarCompra(new ArrayList<>(),sala)),
            () -> Assertions.assertThrows(CineException.class,() -> cine.confirmarCompra(new ArrayList<>(),null))
        );
    }

    @Test
    public void cineShouldGetRecaudacion() {
        var sala = cine.getSalas().stream().findAny().get();
        var butaca = sala.searchButaca(cineData.ROW,cineData.COLUMN);
        var butaca2 = sala.searchButaca(cineData.ROW,cineData.COLUMN+1);
        var ticket = cine.confirmarCompra(List.of(butaca),sala);
        var ticket2 = cine.confirmarCompra(List.of(butaca2),sala);
        Assertions.assertEquals(cine.getTakins(),cine.getPrice()*2);
    }

}