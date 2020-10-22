package unq.edu.ar.nashe.threadPool;

public class PoisonException extends RuntimeException {
    public PoisonException(String msg) {
        super(msg);
    }
}
