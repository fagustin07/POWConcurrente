package ar.edu.pconc.project;

import javax.xml.bind.DatatypeConverter;
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
            } catch (Exception ignored) { }
        }
        isWorking = false;
    }

    public void check(byte[] digestedBytes, int difficult) {
        StringBuilder zeros = new StringBuilder();
        String hashValue = DatatypeConverter.printHexBinary(digestedBytes).toLowerCase();
        for (int i = 0; i < difficult; i++) {
            zeros.append("0");
        }
        if (hashValue.startsWith(zeros.toString())){
            System.out.println("Lo encontré: " + hashValue);
            threadPool.stop();
        }
    }
}
