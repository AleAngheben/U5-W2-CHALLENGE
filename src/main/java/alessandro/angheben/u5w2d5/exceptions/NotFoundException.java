package alessandro.angheben.u5w2d5.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("item with " + id + " not found.");
    }
}
