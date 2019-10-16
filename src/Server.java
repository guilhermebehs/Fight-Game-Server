import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author guilherme.behs
 */
public class Server implements Runnable {

    static List<PlayerConectado> players = new ArrayList<>();
    static int id = 1;
    
    public void iniciar(){
      
        try {
            ServerSocket ss = new ServerSocket(8200);
            Socket socketNovoPlayer;

            while (true) {
                socketNovoPlayer = ss.accept();
                PlayerConectado novoPlayer = new PlayerConectado(socketNovoPlayer);
                players.add(novoPlayer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void run() {
        iniciar();
    }

  
}
