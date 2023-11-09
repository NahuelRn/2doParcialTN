package clases;

public class ClienteInexistenteException extends Exception {
    public ClienteInexistenteException() {
        super("El cliente no existe.");
    }
}
