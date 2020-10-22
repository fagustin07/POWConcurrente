package unq.edu.ar.nashe.pow;

public class Buffer {
    private int size;
    private Object[] data;
    private int begin = 0, end = 0;

    public  Buffer(int size){
        this.size = size;
        this.data = new Object[size+1];
    }

    public synchronized void write(Object o) {
        while(this.isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        data[begin] = o;
        begin = next(begin);
        notifyAll();
    }

    public synchronized Object read(){
        while (this.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Object result = data[end];
        end = this.next(end);
        notifyAll();
        return result;
    }

    private boolean isEmpty() {
        return begin == end;
    }

    private boolean isFull() {
        return next(begin) == end;
    }

    private int next(int i) {
        return (i + 1) % (size+1);
    }
}

class App {

    public static void main( String[] args ) {
        Buffer buffer = new Buffer(2);
        Productor productor = new Productor(buffer);
        Consumidor consumidor = new Consumidor(buffer);
        Thread productorThread = new Thread(productor);

        productorThread.start();
        consumidor.start();

    }
}
