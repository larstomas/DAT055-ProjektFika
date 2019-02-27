package server;
import fikaAssests.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.io.*;


public class Server  implements Observer{ 
	
	private ServerSocket ss;
	private Socket s;
	private Group gr;
	private FileHandler fl;
    
    
    public Server() throws IOException{
    	
    	ss = new ServerSocket(5056);
    	gr = new Group();
    	this.gr.addObserver(this);
    	fl = new FileHandler(gr);
    	fl.load();
    	gr = fl.getG();
    }
	
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
    

	public Group getGroup() {
		return gr;
	}

	private void setGroup(Group gr) {
		this.gr = gr;
	}

	@Override
	public void update(Observable o, Object arg) {
		setGroup((Group)arg);
		
		
	} 
} 