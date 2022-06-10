package pl.mikolaj.exceptions;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(int id) {
        super("Could not find object with id: " + id);
    }
}
