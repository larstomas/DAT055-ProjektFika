package client;
import java.io.IOException;

import fikaAssests.Group;
import fikaAssests.Login;
import server.FileHandler;

public class Main {
	
	public static void main(String[] args) throws IOException { 
		Client c = new Client();
		c.recieveAndSend();
	} 
}