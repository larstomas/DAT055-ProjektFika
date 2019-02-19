package server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException  
    {
    	Server s = new Server();
    	
    	while(true){
    		s.listenForClients();
    	}
    } 
}
