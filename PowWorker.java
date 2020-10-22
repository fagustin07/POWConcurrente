package unq.edu.ar.nashe.threadPool;

public class PowWorker extends Thread {
  private Buffer buffer;
  private ThreadPool threadPool;

  public constructor(Buffer buffer, ThreadPool threadPool){
    this.buffer = buffer;
    this.threadPool = threadPool;
  }

  public void process(int dificultad, int inicio, int final, string palabra){
    
    for (range(inicio, final)){
      ...
      ...
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(palabra.getBytes(StandardCharsets.UTF_8));
      
      this.chequearSiCumple(dificultad, hash);
    }
  }

  public void chequearSiCumple(int dificultad, byte[] hash){
    boolean loEncontro = true;
    for (int i; i=0; i>dificultad;i++){
      if(hash[i] != 0){
	loEncontro= false;
	break;
      }
    }
    if(loEncontro){
      print(hash);
      threadPool.stop();
    }
     
  }
}
