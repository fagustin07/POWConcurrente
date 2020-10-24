package ar.edu.pconc.project;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.Arrays;

public class PowWorker extends Thread {
    private final Buffer buffer;
    private final ThreadPool threadPool;
    private final int difficult;
    private final byte[] inputBytes;

    public PowWorker(Buffer buffer,ThreadPool threadPool, byte[] inputBytes, int difficult) {
        this.buffer = buffer;
        this.threadPool = threadPool;
        this.inputBytes = inputBytes;
        this.difficult = difficult;
    }

    @Override
    public void run() {
        System.out.println("Pow worker trabajando");
        while(true){
            UnidadDeTrabajo unidadDeTrabajo = (UnidadDeTrabajo) this.buffer.read();

            for (int i = unidadDeTrabajo.start; i < unidadDeTrabajo.end; i++) {
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                    byte[] myByteArray = new byte[this.inputBytes.length + 1];
                    myByteArray[myByteArray.length-1] = (byte) i;
                    System.arraycopy(this.inputBytes, 0, myByteArray, 0, this.inputBytes.length);
                    messageDigest.update(myByteArray);
                    byte[] digestedBytes = messageDigest.digest();
                    check(digestedBytes);
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }

        }
    }

    public void check(byte[] digestedBytes) {
        boolean result = true;
        for (int i = 0; i < this.difficult; i++) {
            if (digestedBytes[i]!=0){
                result = false;
                break;
            }
        }
        if (result){
            System.out.println("Lo encontre: " + Arrays.toString(digestedBytes));
            this.threadPool.stop();
        }
    }
}
