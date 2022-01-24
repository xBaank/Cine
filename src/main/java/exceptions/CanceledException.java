package exceptions;

public class CanceledException extends Exception{
    public CanceledException(String message) {
        super(message);
    }
    public CanceledException() {
        super();
    }
}
