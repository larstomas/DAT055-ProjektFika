package client;
import java.io.IOException;

import fikaAssests.Group;
import server.FileHandler;

public class Main {
	
	/**
	 * Initiate client app
	 * 
	 * @param args
	 * @throws IOException
	 */
	
	public static void main(String[] args) throws IOException { 
		Client c = new Client();
		c.recieveAndSend();
	} 
}