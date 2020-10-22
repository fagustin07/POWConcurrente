package org.example;

public class ThreadPool {
    private final Worker[] workers;
    private int actualWorker;
    private final Buffer buffer;

    public ThreadPool(Buffer buffer, int workersNumber) {
        this.buffer = buffer; 
        this.workers = new Worker[workersNumber];

        for (int i = 0; i < workersNumber; i++) {
            workers[i] = new Worker(buffer);
        }
        this.actualWorker = 0;
    }

    public void launch(Task newTask){
        buffer.write(newTask);
        while (workers[actualWorker].isWorking()){
            actualWorker = (actualWorker+1) % workers.length;
        }
        workers[actualWorker].work();
    }

    void stop(){
        new Thread(new PoisonPill()).start();
    }
}
