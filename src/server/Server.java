package server;
import fikaAssests.*;
import java.net.*;
import java.util.ArrayList;
import java.io.*;


public class Server  
{ 
	
	DataInputStream dis;
    DataOutputStream dos;
    ServerSocket ss;
    Socket s;
    Group gr;
    FileHandler fl;
    
    
    public Server() throws IOException{
    	
    	ss = new ServerSocket(5056);
    	gr = new Group();
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
                Thread t = new ClientHandler(s, ois, oos, gr);
                System.out.println("Tread öppen");
  
                // Invoking the start() method 
                t.start();             
                  
            } catch (Exception e){ 
                s.close(); 
                e.printStackTrace(); 
            }
        
	}
    
    
    public static void main(String[] args) throws IOException  
    {
    	Server s = new Server();
    	
    	while(true){
    		s.listenForClients();
    	}
    } 
} 