package ar.edu.pconc.project;

public class ThreadPool {
    private final Buffer buffer;
    private final PowWorker[] workers;
    private final long startTime;

    public ThreadPool(Buffer buffer, int cantThreads, byte[] inputBytes, int difficult){
        this.startTime = System.currentTimeMillis();
        this.buffer = buffer;
        workers = new PowWorker[cantThreads];
        runThreads(inputBytes,difficult);
    }

    private void runThreads(byte[] inputBytes, int difficult) {
        for(int i = 0 ; i < this.workers.length ; i++){
            PowWorker worker = new PowWorker(buffer,this,inputBytes,difficult);
            workers[i] = worker;
            worker.start();
        }
    }

    public synchronized void stop(){
        for (PowWorker powWorker: this.workers){
            powWorker.dejarDeTrabajar();
        }

        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Tiempo total del programa: " + endTime + " ms");
        new PoisonPill().run();
    }
}
