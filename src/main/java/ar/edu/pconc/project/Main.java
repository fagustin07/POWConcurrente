package ar.edu.pconc.project;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Cantidad de threads: ");
        int threads = Integer.parseInt(scan.nextLine());
        System.out.println("Dificultad (max 4): ");
        int dificultad = Integer.parseInt(scan.nextLine());
        System.out.println("Cadena: ");
        String cadena = scan.nextLine();

        Buffer buffer = new Buffer(2);
        new ThreadPool(buffer, threads,cadena.getBytes(),dificultad);

        int inicio = 0;
        int limite = 32-threads+1;
        for (int i = 0; i <threads; i++) {
            UnidadDeTrabajo unidadDeTrabajo = new UnidadDeTrabajo(inicio,(int) Math.pow(2.0, limite)-1);
            buffer.write(unidadDeTrabajo);
            int limiteAnterior = limite;
            inicio = (int) Math.pow(2.0, limite);
            limite = limiteAnterior+1;
        }

    }
}
