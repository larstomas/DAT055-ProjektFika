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
	Object received = null; 
    Object toreturn; 
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


        	try { 
  
        		oos.writeObject(g); 

        			System.out.println("First group set");
        		        					                
                while(received == null){
                	received = ois.readObject();
                }
                
                if((int)received < 0){
                	//setVote
                	//ChangeGroup
                }
                received = null;
                oos.writeObject(g); 
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
                this.ois.close(); 
                this.oos.close(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
            } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }   
        



    

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Har fått fika");
		if( arg instanceof Group && arg != null){
			this.g = (Group) arg;
			System.out.println("har satt fika");
			setSetGroup(false);
			//notify();
		}
	}

	public Boolean getSetGroup() {
		return setGroup;
	}

	public void setSetGroup(Boolean v) {
		this.setGroup = v;
		System.out.println("setGroup is:" + this.setGroup);
	}    
} 
