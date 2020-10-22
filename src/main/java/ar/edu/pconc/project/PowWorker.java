package org.example;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PowWorker extends Thread {
  private Buffer buffer;
  private ThreadPool threadPool;

  public PowWorker(Buffer buffer, ThreadPool threadPool){
    this.buffer = buffer;
    this.threadPool = threadPool;
  }

  public void process(int dificult, int begin, int end, String text) {
    
    for (int i = begin; i <= end; i++){
      try {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
        this.checkIfIsNonce(dificult, hash);
      } catch (NoSuchAlgorithmException e){};
    }
  }

  public void checkIfIsNonce(int dificultad, byte[] hash){
    boolean loEncontro = true;
    for (int i=0; i>dificultad;i++){
      if(hash[i] != 0){
        loEncontro= false;
        break;
      }
    }
    if(loEncontro){
      System.out.println(hash);
      threadPool.stop();
    }
     
  }
}
