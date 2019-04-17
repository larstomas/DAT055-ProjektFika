package server;
import fikaAssests.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;


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
        		handleLogin();
        		oos.writeObject(server.getGroup());
        		oos.reset();
        		
        		System.out.println("First group sent");
                handleVote();
                handleRequest();
                oos.writeObject(server.getGroup()); 
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
		while(received == null){
        	try {
				received = (int)ois.readObject();
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
        }
		System.out.println(received.getClass());
		if((int)received > 0){
			
			server.getGroup().getV().incomingVote((int)received);
			server.getGroup().findUser(talkingUser.getID()).setHasVoted(true);
			
        }
        received = null;
		
	}
    /**
     * Handles the request for önskelistan. It gets the request from the Client and adds the request to the group saved in the server
     */
    private void handleRequest() {
		while(received == null){
        	try {
				received = ois.readObject();
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		if(received instanceof ArrayList<?>) {
			ArrayList<String> requests = (ArrayList<String>) received;
			if(requests.get(0).equals("--Nothing Here--")) {
				System.out.println("HELLO");
				
			}else {
				System.out.println(received.getClass());
			//	System.out.println(server.getGroup().getWishlist());
				for(String s: requests) {
					server.getGroup().addRequest(s);
				}
			}
		}
		received = null;
    }

	
	/**
	 * Checks whether a client is logged in and however a client trying to log in 
	 * exists in the actual Group.
	 * 
	 */
	private void handleLogin() {
		while(loggedOn == false){
			received = null;
			
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
			if(received instanceof String){
				
    			if(server.getGroup().findUser((String) received) == null){
    				try {
						oos.flush();
						oos.writeObject(0);
					} catch (IOException e) {
						
						e.printStackTrace();
					}
    				System.out.println(false); 	
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
    			
			} else if(received instanceof User){
				User temp = (User)received;
				talkingUser = server.getGroup().findUser(temp.getID());
				this.loggedOn = true;
			}	
		}
		received = null;
		
	}
} 
