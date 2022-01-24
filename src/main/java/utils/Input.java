package utils;

import exceptions.CanceledException;
import models.ButacaPos;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Input {
    public static int readInt() throws CanceledException {
        Scanner scanner = new Scanner(System.in);
        boolean isOk = false;
        int result = 0;

        while (!isOk){
            try {
                result = scanner.nextInt();

                if(result == 0)
                    throw new CanceledException();

                isOk = true;
            }
            catch (InputMismatchException ex) {
                System.out.println("Character no valido en la entrada");
                scanner.nextLine();
            }
        }
        return result;
    }

    public static ButacaPos readButacaPos() throws CanceledException {
        Scanner scanner = new Scanner(System.in);
        boolean isOk = false;
        String input = "";
        char letter;
        int column;

        ButacaPos butacaPos = null;

        while (!isOk){
            try {
                input = scanner.next();

                //Throws cancel expeption to go back
                if(input.equals("0"))
                    throw new CanceledException();

                letter = input.toUpperCase(Locale.ROOT).charAt(0);
                if(!Character.isAlphabetic(letter))
                    throw new UnsupportedOperationException();

                column = Integer.parseInt(input.substring(1,input.length()));

                butacaPos = new ButacaPos(letter,column);

                isOk = true;
            }
            catch (NumberFormatException | UnsupportedOperationException ex) {
                System.out.println("Entrada no valida");
                scanner.nextLine();
            }
        }
        return butacaPos;
    }
}
