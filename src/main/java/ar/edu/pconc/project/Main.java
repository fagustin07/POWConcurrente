package org.example;

public class Main{
    public static void main(String[] args) {
      int threads = 8;
      int dificultad = 2;
      String cadena = "jnsdlasd";

      Buffer buffer = new Buffer(2);
      ThreadPool threadPool = new ThreadPool(buffer,threads);
    }
}
