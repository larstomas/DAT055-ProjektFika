package server;
import fikaAssests.*;
import java.io.*;
import java.net.*;
import java.util.Observable;
import java.util.Observer;

public class ClientHandler extends Thread{ 
    final ObjectInputStream ois; 
    final ObjectOutputStream oos; 
    final Socket s; 
    Group g;
	Object received = null; 
    Object toreturn; 
    Boolean setGroup = false;
    User talkingUser;
    Boolean loggedOn = false;
    Server server;
  
    // Constructor 
    public ClientHandler(Socket s, ObjectInputStream dis, ObjectOutputStream dos, Server ser)  
    { 
        this.s = s; 
        this.ois = dis; 
        this.oos = dos; 
        this.server = ser;
        

    } 
  
    @Override
    public void run() { 
    	
        	try { 
        		
        		//Login procedure
        		
        		//Sålänge någon inte är inloggad på tråden
        		while(loggedOn == false){
        			received = null;
        			
        			//Ta emot objekt
        			while(received == null){
        				received = ois.readObject();
        			}
        			
        			System.out.print("tar emot " + received.getClass());
        			//Om mottaget object är String 
        			if(received instanceof String){
        				
        				//Och Stringen inte finns som User i Group skicka 0
	        			if(server.getGroup().findUser((String) received) == null){
	        				oos.flush();
	        				oos.writeObject(0);
	        				System.out.println(false);
	        			
	        			//Om Usern finns i Group, sätt den till talkingUser och skicka 1 till klienten så den vet att den finns. 	
	        			}else{
	        		
	        				this.talkingUser = server.getGroup().findUser((String) received);
	        				this.loggedOn = true;
	        				oos.flush();
	        				oos.writeObject(1);
	        				System.out.println(true);
	        			}
	        			
	        			
	        		//Om User skickas istället för String sätt, talkingUser och loggedOn true
        			} else if(received instanceof User){
        				User temp = (User)received;
        				talkingUser = server.getGroup().findUser(temp.getID());
        				this.loggedOn = true;
        			}	
        		}
        		received = null;
        		
        		
        		//Skicka Group till Client
        		oos.writeObject(server.getGroup());
        		oos.reset();
        		System.out.println("First group sent");
        		        					                
                
        		//Vänta på vote
        		while(received == null){
                	received = ois.readObject();
                }
                
        		server.getGroup().findUser(talkingUser.getID()).setHasVoted(true);
        		
        		
        		//Om röst är större än 0 sätt rösten för talkingUser i Group-> Vote
        		
        		if((int)received < 0){
        			
        			server.getGroup().getV().incomingVote((int)received);
        			server.getGroup().findUser(talkingUser.getID()).setHasVoted(true);
        			
                }
        		
                System.out.println("Röst mottagen: " + received);
                received = null;
                
                
                //Send group before closing thread
                oos.writeObject(server.getGroup()); 
     
                //Close thread
                this.ois.close(); 
                this.oos.close();
                System.out.println("Tråd stänger");
                this.s.close();
                
            } catch (IOException e) { 
                e.printStackTrace(); 
            } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
