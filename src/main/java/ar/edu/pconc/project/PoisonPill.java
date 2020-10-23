package ar.edu.pconc.project;

public class PoisonPill implements Runnable {
    @Override
    public void run() {
        throw new PoisonException("THREAD POOL IS END. IM A POISON EXCEPTION.");
    }
}
