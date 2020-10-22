package unq.edu.ar.nashe.threadPool;

public class Main{
    public static void main(String[] args) {
      int threads = read();
      int dificultad = read();
      string cadena = read();
      
      Buffer buffer = new Buffer(2);
      ThreadPool threadPool = new ThreadPool(buffer,threads);

       
      
    }
}
