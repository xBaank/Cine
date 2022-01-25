package models;

import exceptions.CineException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class CineTest {
    Cine cine;
    Menu menu;

    @BeforeEach
    public void beforeEach() {
        cine = new Cine(1,5,5);
        menu = new Menu(cine);
    }

    @Test
    public void cineConstructorShouldThrow() {
        Assertions.assertThrows(CineException.class,() -> new Cine(0,1,1));
        Assertions.assertThrows(CineException.class,() -> new Cine(1,0,1));
        Assertions.assertThrows(CineException.class,() -> new Cine(1,1,0));
        }

    @Test
    public void cineShouldSearchButacas() {
    }
}