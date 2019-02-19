package server;
import fikaAssests.*;
import java.io.*;
import java.net.*;
import java.util.Observable;
import java.util.Observer;

public class ClientHandler extends Thread implements Observer{ 
    final ObjectInputStream ois; 
    final ObjectOutputStream oos; 
    final Socket s; 
    Group g;
    Boolean setGroup = false;
  
    // Constructor 
    public ClientHandler(Socket s, ObjectInputStream dis, ObjectOutputStream dos, Group gr)  
    { 
        this.s = s; 
        this.ois = dis; 
        this.oos = dos; 
        this.g = gr;

    } 
  
    @Override
    public void run() { 
        
    	Object received; 
        Object toreturn; 
        
        while (true)  { 
        	try { 
  
                // Ask user what he wants 
        		
        		if(setGroup == false){
        			oos.writeObject(g); 
        			setGroup = true;
        			System.out.println("First group set");
        			
        	}
                
        		
                  
                // receive the answer from client 
                //received = ois.readUTF();
                //System.out.println(received);
                //oos.writeUTF("Server fick meddelande"); 
                
               /*if(received.equals("Exit")) 
                {  
                    System.out.println("Client " + this.s + " sends exit..."); 
                    System.out.println("Closing this connection."); 
                    this.s.close(); 
                    System.out.println("Connection closed"); 
                    break; 
                }*/
                     
                 
            } catch (IOException e) { 
                e.printStackTrace(); 
            }
        }   
        
        /*try{ 
            this.ois.close(); 
            this.oos.close(); 
              
        }catch(IOException e){ 
            e.printStackTrace(); 
        }*/ 
    }

	@Override
	public void update(Observable o, Object arg) {
		if( arg instanceof Group && arg != null){
			this.g = (Group) arg;
			setGroup = false;
		}
	}    
} 
