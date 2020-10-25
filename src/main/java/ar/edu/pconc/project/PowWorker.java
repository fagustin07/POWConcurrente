package ar.edu.pconc.project;

import java.security.MessageDigest;
import java.util.Arrays;

public class PowWorker extends Thread {
    private final Buffer buffer;
    private final ThreadPool threadPool;
    private final int difficult;
    private final byte[] inputBytes;
    private boolean tieneQueTrabajar = true;
    private boolean encontroElNonce = false;

    public PowWorker(Buffer buffer, ThreadPool threadPool, byte[] inputBytes, int difficult) {
        this.buffer = buffer;
        this.threadPool = threadPool;
        this.inputBytes = inputBytes;
        this.difficult = difficult;
    }

    @Override
    public void run() {
        Object objetoLeido = this.buffer.read();
        UnidadDeTrabajo unidadDeTrabajo = (UnidadDeTrabajo) objetoLeido;
        for (long i = unidadDeTrabajo.start; i < unidadDeTrabajo.end; i++) {
            byte[] nonce = String.valueOf(i).getBytes();

            byte[] miByteArray = generarByteArrayConNonce(nonce);
            if (!tieneQueTrabajar) break;
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                byte[] digestedBytes = messageDigest.digest(miByteArray);
                validarSiCumpleLaCondicion(digestedBytes, nonce);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        if (!encontroElNonce)
            threadPool.finalizo();
    }

    private byte[] generarByteArrayConNonce(byte[] nonce) {
        byte[] miByteArray = new byte[inputBytes.length + nonce.length];

        System.arraycopy(inputBytes, 0, miByteArray, 0, inputBytes.length);
        System.arraycopy(nonce, 0, miByteArray, inputBytes.length, nonce.length);
        return miByteArray;
    }

    public void validarSiCumpleLaCondicion(byte[] digestedBytes, byte[] nonce) {
        boolean cumpleDificultad = true;
        for (int i = 0; i < this.difficult; i++) {
            if (digestedBytes[i] != 0) {
                cumpleDificultad = false;
                break;
            }
        }
        this.encontroElNonce = cumpleDificultad;
        if (encontroElNonce) {
            String printVerde = "\u001B[32m";
            System.out.println(printVerde + "[FOUND] " + Arrays.toString(digestedBytes));
            System.out.println("[WITH]: " + Arrays.toString(nonce));
            this.threadPool.stop();
        }
    }

    public void dejarDeTrabajar() {
        this.tieneQueTrabajar = false;
    }
}
