package unq.edu.ar.nashe.threadPool;

import unq.edu.ar.nashe.productor_consumidor.Buffer;

public class Worker extends Thread{
    private Buffer buffer;
    private boolean isWorking = false;

    public Worker(Buffer buffer) {
        this.buffer = buffer;
    }

    public void work(){
        this.isWorking = true;
        Task task = (Task) buffer.read();
        task.run();
        this.isWorking = false;
    }

    public boolean isWorking(){
        return isWorking;
    }
}
