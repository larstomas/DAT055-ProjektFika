package server;

import java.io.IOException;

/**
 * Initiate server and server GUI
 * 
 * @author Tomas
 *
 */

public class Main {

    public static void main(String[] args) throws IOException  
    {
    	Server s = new Server();
    	ServerGui sg = new ServerGui(s);
    	while(true){
    		s.listenForClients();
    	}
    } 
}
