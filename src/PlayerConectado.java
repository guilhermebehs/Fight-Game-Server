
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author guilherme.behs
 */
public class PlayerConectado implements Runnable {
        Socket s;
    Thread thRecebeMsg;
    BufferedReader entrada;
    PrintWriter saida;
    private int id;

    public PlayerConectado(Socket s){
        configurarPlayer(s);
    }
    
    public void configurarPlayer(Socket s){
        this.s = s;
        try {
            entrada = new BufferedReader(
                    new InputStreamReader(s.getInputStream()));
            saida = new PrintWriter(s.getOutputStream());
            thRecebeMsg = new Thread(this);
            thRecebeMsg.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
   public void sinalizarNovoPlayer(){
        try {
            String msg = "newPlayerAlert";
            for(PlayerConectado player:Server.players){
                    if(player.id != this.id)
                       player.enviarMensagem(msg);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 

    public void receberMensagens(){
        try {
           sinalizarNovoPlayer();
            String msg;
            while((msg = entrada.readLine()) != null){
                for(PlayerConectado player:Server.players){
                       player.enviarMensagem(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void enviarMensagem(String msg){
        saida.println(msg);
        saida.flush();
    }
    
    @Override
    public void run() {
        id = Server.id++;
        enviarMensagem("id:"+id);
        receberMensagens();
    }
    
    
}
