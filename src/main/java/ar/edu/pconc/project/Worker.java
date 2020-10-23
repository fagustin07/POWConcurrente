package ar.edu.pconc.project;

public class Worker extends Thread {
    private Buffer buffer;
    private boolean isWorking = false;

    public Worker(Buffer buffer) {
        this.buffer = buffer;
    }

    public void work() {
        this.isWorking = true;
        Task task = (Task) buffer.read();
        task.run();
        this.isWorking = false;
    }

    public boolean isWorking() {
        return isWorking;
    }
}
