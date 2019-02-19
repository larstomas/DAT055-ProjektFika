package server;
import fikaAssests.*;
import java.io.*;
import java.net.*;

public class ClientHandler extends Thread  
{ 
    final ObjectInputStream ois; 
    final ObjectOutputStream oos; 
    final Socket s; 
    Group g;
    Group g2;
    Boolean firstGroup = false;
  
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
        		
        		if(firstGroup == false){
        			oos.writeObject(g); 
        			firstGroup = true;
        			System.out.println("First group set");
        			
        			
        			
        			//oos.writeObject(g2);
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
} 
