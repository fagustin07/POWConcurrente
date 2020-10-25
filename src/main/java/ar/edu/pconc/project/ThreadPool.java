package ar.edu.pconc.project;

public class ThreadPool {
    private final Buffer buffer;
    private final PowWorker[] workers;
    private final long startTime;
    private int finalizados = 0;
    private final int dificultad;

    public ThreadPool(Buffer buffer, int cantThreads, byte[] inputBytes, int difficult) {
        this.startTime = System.currentTimeMillis();
        this.buffer = buffer;
        this.dificultad = difficult;
        workers = new PowWorker[cantThreads];
        runThreads(inputBytes, difficult);
    }

    private void runThreads(byte[] inputBytes, int difficult) {
        for (int i = 0; i < this.workers.length; i++) {
            PowWorker worker = new PowWorker(buffer, this, inputBytes, difficult);
            workers[i] = worker;
            worker.start();
        }
    }

    public synchronized void stop() {
        for (PowWorker powWorker : this.workers) {
            powWorker.dejarDeTrabajar();
        }

        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Tiempo total de busqueda: " + endTime + " ms");
    }

    public void finalizo() {
        finalizados++;
        if (finalizados == workers.length) {
            String printRojo = "\u001B[31m";
            System.out.println( printRojo + "No pudimos encontrar el nonce con la dificultad " + this.dificultad);
            this.stop();
        }
    }
}
