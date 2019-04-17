package server;
import fikaAssests.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.io.*;

/**
 * Server listens for clients trying to connect to the server.
 * For every client a ClientHandler object is created which sends objects regarding information 
 * of the group between the ClientHandler and Client. 
 * 
 * @author Group 4
 * @version 0.6
 *
 */


public class Server  implements Observer{ 
	
	private ServerSocket ss;
	private Socket s;
	private Group gr;
	private FileHandler fl;
    
    
	/**
	 * Server constructor
	 * @throws IOException
	 */
    public Server() throws IOException{
    	ss = new ServerSocket(5056);
    	gr = new Group();
    	this.gr.addObserver(this);
    	fl = new FileHandler(gr);
    	fl.load();
    	gr = fl.getG();
    }
	
    /**
     * Listens for clients trying to connect to the server.
     * Creates a Thread for each client trying to connect.
     * @throws IOException
     */
	public void listenForClients() throws IOException{
            try { 
                // socket object to receive incoming client requests 
                s = ss.accept(); 
                System.out.println("A new client is connected : " + s); 
                  
                // obtaining input and out streams 
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream()); 
                oos.flush();
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                System.out.println("Strömar skapade"); 
                
                
                
                // create a new thread object 
                Thread t = new ClientHandler(s, ois, oos, this);
                System.out.println("Tread öppen");
                
                // Invoking the start() method 
                t.start();             
                  
            } catch (Exception e){ 
                s.close(); 
                e.printStackTrace(); 
            }
        
	}
    
	/**
	 * Get current group
	 * @return - The current group handled by the server
	 */
	public Group getGroup() {
		return gr;
	}

	/**
	 * Sets the group 
	 * @param gr
	 */
	private void setGroup(Group gr) {
		this.gr = gr;
	}

	/**
	 * When notified, updates the Group handled by the server to the most recently updated one.
	 */
	@Override
	public void update(Observable o, Object arg) {
		setGroup((Group)arg);
	} 
} 