package server;
import fikaAssests.*;
import java.io.*;
import java.net.*;
import java.util.Observable;
import java.util.Observer;

/**
 * ClientHandler establishes communication between a Client and Server.
 * ClientHandler sends information regarding a Group to the Server so that the server
 * always is updated with the most recent version of the Group.
 * 
 * @author Group 4
 * @version 0.6
 */

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
  
    /**
     * Creates a ClientHandler object.
     * @param s - establishes a way of communication to a client
     * @param dis - InputStream to receive information from a client
     * @param dos - OutputSteram to send information to a client
     * @param ser - the server that handles the group
     */
    public ClientHandler(Socket s, ObjectInputStream dis, ObjectOutputStream dos, Server ser)  
    { 
        this.s = s; 
        this.ois = dis; 
        this.oos = dos; 
        this.server = ser;
    } 
  
    /**
     * Executes the protocol for communication with a client.
     * Handles login then sends the most recent Group to the client.
     * Receives eventual vote and sends the most recent Group to the client.
     * Closes the stream.
     */
    @Override
    public void run() { 
    	
        	try { 
        		
        		//Login procedure
        		handleLogin();
        		
        		
        		//Skicka Group till Client
        		oos.writeObject(server.getGroup());
        		oos.reset();
        		
        		System.out.println("First group sent");
        		        					                
                
        		//Ta emot ev röst
                handleVote();
                
                //Send group before closing thread
                oos.writeObject(server.getGroup()); 

                
     
                //Close thread
                this.ois.close(); 
                this.oos.close();
                System.out.println("Tråd stänger");
                this.s.close();
                
            } catch (IOException e) { 
                e.printStackTrace(); 
            }
        }   
        


	/**
	 * Waits for a client to vote.
	 * When a vote is received from a client it updates the amount and sum of all votes cast.
	 * Finally it records that the Client voting has voted.
	 */
    private void handleVote() {
		//Wait for vote
		while(received == null){
        	try {
				received = (int)ois.readObject();
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
        }
		
		//If vote is greater than 0, set vote for talkingUser.
		System.out.println(received.getClass());
		if((int)received > 0){
			
			server.getGroup().getV().incomingVote((int)received);
			server.getGroup().findUser(talkingUser.getID()).setHasVoted(true);
			
        }
        received = null;
		
	}
	
	/**
	 * Checks whether a client is logged in and however a client trying to log in 
	 * exists in the actual Group.
	 * 
	 */
	private void handleLogin() {
		//As long as anyone isn't logged in.
		while(loggedOn == false){
			received = null;
			
			//Receive object
			while(received == null){
				try {
					received = ois.readObject();
				} catch (ClassNotFoundException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			
			System.out.println("tar emot " + received.getClass());
			//If receibed object is of type String
			if(received instanceof String){
				
				//If the string doesn't exist as a User in Group, send 0
    			if(server.getGroup().findUser((String) received) == null){
    				try {
						oos.flush();
						oos.writeObject(0);
					} catch (IOException e) {
						
						e.printStackTrace();
					}
    				System.out.println(false);
    			//If the user exists in Group, set it as talkingUser and send 1 to the client to confirm its existence 	
    			}else{
    		
    				this.talkingUser = server.getGroup().findUser((String) received);
    				this.loggedOn = true;
    				try {
						oos.flush();
						oos.writeObject(1);
					} catch (IOException e) {
						
						e.printStackTrace();
					}

    				System.out.println(true);
    			}
    			
    		//If a User is sent instead of String, set User to talkingUser and loggedOn to true	
    		
			} else if(received instanceof User){
				User temp = (User)received;
				talkingUser = server.getGroup().findUser(temp.getID());
				this.loggedOn = true;
			}	
		}
		received = null;
		
	}
} 
