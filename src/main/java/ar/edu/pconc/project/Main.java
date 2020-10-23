package ar.edu.pconc.project;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//            Scanner scan = new Scanner(System.in);
//
//            System.out.println("Cantidad de threads: ");
//            int threads = Integer.parseInt(scan.nextLine());
//            System.out.println("Dificultad (max 4): ");
//            int dificultad = Integer.parseInt(scan.nextLine());
//            System.out.println("Cadena: ");
//            String cadena = scan.nextLine();
//
//
            long startTime = System.currentTimeMillis();
//
//            Buffer buffer = new Buffer(2);
//            ThreadPool threadPool = new ThreadPool(buffer, threads, cadena.getBytes());

        UnidadDeTrabajo unidadDeTrabajo = new UnidadDeTrabajo(-1000, 1500);
        byte[] inputBytes = "".getBytes();

        for (int i = unidadDeTrabajo.start; i <= unidadDeTrabajo.end; i++) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                byte[] myByteArray = new byte[inputBytes.length + 1];
                myByteArray[0] = (byte) i;
                System.arraycopy(inputBytes, 0, myByteArray, 1, inputBytes.length);
                messageDigest.update(myByteArray);
                byte[] digestedBytes = messageDigest.digest();

                StringBuilder zeros = new StringBuilder();
                String hashValue = DatatypeConverter.printHexBinary(digestedBytes).toLowerCase();
                for (int j = 0; j < 1; j++) {
                    zeros.append("0");
                }
                if (hashValue.startsWith(zeros.toString())){
                    long endTime = System.currentTimeMillis() - startTime;
                    System.out.println("Tiempo total del programa: " + endTime + " ms");
                    throw new RuntimeException("Lo encontré: " + hashValue);
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Tiempo total del programa: " + endTime + " ms");
        throw new RuntimeException("No lo encontré");


    }
}
