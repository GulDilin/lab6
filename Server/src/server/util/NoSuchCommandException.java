package server.util;

public class NoSuchCommandException extends Exception {
    NoSuchCommandException(String message) {
        super(message);
    }
}
