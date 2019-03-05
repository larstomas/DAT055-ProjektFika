package server;

import java.io.IOException;

/**
 * Initiate server and server GUI
 * 
 * @author Group 4
 * @version 0.6
 *
 */

public class Main {
	/**
	 * Creates a ServerGui and Server and listens for Clients
	 * @param args
	 * @throws IOException
	 */
    public static void main(String[] args) throws IOException  
    {
    	Server s = new Server();
    	new ServerGui(s);
    	while(true){
    		s.listenForClients();
    	}
    } 
}
