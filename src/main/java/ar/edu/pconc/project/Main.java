package ar.edu.pconc.project;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scan = new Scanner(System.in);

            System.out.println("Cantidad de threads: ");
            int threads = Integer.parseInt(scan.nextLine());
            System.out.println("Dificultad (max 4): ");
            int dificultad = Integer.parseInt(scan.nextLine());
            System.out.println("Cadena: ");
            String cadena = scan.nextLine();


            long startTime = System.currentTimeMillis();

            Buffer buffer = new Buffer(2);
            ThreadPool threadPool = new ThreadPool(buffer, threads, cadena.getBytes());

            long endTime = System.currentTimeMillis() - startTime;
            System.out.println("Tiempo total del programa: " + endTime + " ms");

        } catch (Exception ignored) {
        }
        ;
    }


}
