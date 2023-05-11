package pl.javastart.tasks;

public class taskNotFoundException extends RuntimeException {
    public taskNotFoundException(String message) {
        super(message);
    }
}
