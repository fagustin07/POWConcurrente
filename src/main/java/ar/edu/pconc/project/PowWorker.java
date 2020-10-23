package ar.edu.pconc.project;

import java.security.MessageDigest;
import java.util.Arrays;

public class PowWorker extends Thread {
    private final Buffer buffer;
    private final ThreadPool threadPool;
    private boolean isWorking = false;

    public PowWorker(Buffer buffer, ThreadPool threadPool) {
        this.buffer = buffer;
        this.threadPool = threadPool;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void work(byte[] inputBytes, int difficult) {
        isWorking = true;
        UnidadDeTrabajo unidadDeTrabajo = (UnidadDeTrabajo) this.buffer.read();

        for (int i = unidadDeTrabajo.start; i <= unidadDeTrabajo.end; i++) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                byte[] myByteArray = new byte[inputBytes.length + 1];
                myByteArray[0] = (byte) i;
                System.arraycopy(inputBytes, 0, myByteArray, 1, inputBytes.length);
                messageDigest.update(myByteArray);
                byte[] digestedBytes = messageDigest.digest();
                check(digestedBytes, difficult);
            } catch (Exception ignored) {
            }
            ;
        }
        System.out.println("F bro");
        isWorking = false;
    }

    public void check(byte[] digestedBytes, int difficult) {
        boolean result = true;
        for (int i = 0; i < difficult; i++) {
            result = digestedBytes[i] == 0;
            if (!result)
                break;
        }
        if (result) {
            System.out.println("Lo encontre: " + Arrays.toString(digestedBytes));
            threadPool.stop();
        }
    }
}
