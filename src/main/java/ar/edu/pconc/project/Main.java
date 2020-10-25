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
        new ThreadPool(buffer, threads, cadena.getBytes(), dificultad);

        generarUnidadesDeTrabajo(buffer, threads);
    }

    private static void generarUnidadesDeTrabajo(Buffer buffer, long threads) {
        long capDe4Bytes = (long) Math.pow(2.0, 32);
        long inicioPorcion = 0;
        long finalPorcion = capDe4Bytes / threads;
        long udtConMasUno = capDe4Bytes % threads;
        long finalDeLosQueTienenDeMas = 0;

        int i;
        for (i = 1; i <= udtConMasUno; i++) {
            agregarUnidadDeTrabajoSumandole(inicioPorcion, finalPorcion, buffer, 1);
            inicioPorcion += finalPorcion + 1;
            finalDeLosQueTienenDeMas = inicioPorcion;
        }

        inicioPorcion = finalDeLosQueTienenDeMas;

        for (; i <= threads; i++) {
            agregarUnidadDeTrabajoSumandole(inicioPorcion, finalPorcion, buffer, 0);
            inicioPorcion += finalPorcion;
        }
    }

    private static void agregarUnidadDeTrabajoSumandole(long inicioPorcion,
                                                        long finalPorcion,
                                                        Buffer buffer,
                                                        int numeroASumar) {
        UnidadDeTrabajo unidadDeTrabajo = new
                UnidadDeTrabajo(
                inicioPorcion,
                inicioPorcion + finalPorcion + numeroASumar
        );
        buffer.write(unidadDeTrabajo);
    }
}

