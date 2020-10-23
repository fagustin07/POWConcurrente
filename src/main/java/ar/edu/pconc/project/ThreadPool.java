package ar.edu.pconc.project;

public class ThreadPool {
    private final PowWorker[] workers;
    private int actualWorker;
    private final Buffer buffer;
    private final byte[] inputByte;
    public ThreadPool(Buffer buffer, int workersNumber, byte[] inputByte) {
        this.buffer = buffer; 
        this.workers = new PowWorker[workersNumber];
        this.inputByte = inputByte;

        for (int i = 0; i < workersNumber; i++) {
            workers[i] = new PowWorker(buffer,this);
        }
        this.actualWorker = 0;
    }

    public void launch(UnidadDeTrabajo nuevaUnidadDeTrabajo){
        buffer.write(nuevaUnidadDeTrabajo);
        while (workers[actualWorker].isWorking()){
            actualWorker = (actualWorker+1) % workers.length;
        }
        workers[actualWorker].work(inputByte,2);
    }

    void stop(){
        new Thread(new PoisonPill()).start();
    }
}
