package client;
import fikaAssests.*;
import java.net.*;
import java.util.*;

import javax.swing.JOptionPane;

import java.io.*;

/**
 * Client handles communicating with the server and processes
 * which involves user interaction to the application. Client's
 * connection to server is in the form of a thread through clientHandler.
 * Client then handles posting votes, checking logins, and fetching 
 * groups which are needed for the GUI to show information about the 
 * queue and scores.
 * 
 * @see Gui
 * @see User
 *
 * @author group 4
 * @version 0.
 *
 */

public class Client  { 
	private InetAddress ip;
	private Socket s;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Group group;
	private Gui gui = null;
	private Login log = null;
	private Object received = null;
	private Object sending = null;
	private boolean sendVote;
	private int voteValue;
	private User user;
	private ArrayList<String> newRequests;
	
	/**
	 * Constructor for the Client
	 */
	public Client(){
		try{
			
	        ip = InetAddress.getByName("localhost"); 
	        log = new Login(group);
	        
		} catch (Exception e){
			
		}
		this.newRequests = new ArrayList<String>();
	}
	
	/**
	 * Handles the communications with the server. First it sets a socket for which the
	 * client will connect and created object input and output streams. recieveAndSend then
	 * calls on the method checkForLogin to identify which user is communicating with the server.
	 * Following this, recieveAndSend ask the server about the group through askForGroup.
	 * postVote is then called to cast in votes that occurred and then askForGroup is 
	 * called again to fetch a new Group with the votes implemented.
	 *
	 */
	public void recieveAndSend(){
		try { 
	        s = new Socket(ip, 5056); 
			ois = new ObjectInputStream(s.getInputStream()); 
	        oos = new ObjectOutputStream(s.getOutputStream());
	        checkForLogin();
	        askForGroup();
	             
			postVote();
			postRequest();
			askForGroup();
	        ois.close(); 
	        oos.close(); 
	        this.s.close();
	         
	     } catch(Exception e) { 
	    	 e.printStackTrace(); 
	     } 
		
	}
	
	/**
	 * postVote manages all the voting for the Group. First it check if the user who logged in has voted.
	 * If the user has voted, which is indicated in user's hasVoted variable and sendVote(this variable
	 * indicates if this user is voting or not), postVote will send 0 votes otherwise postVote will send
	 * the current vote value(voteValue variable in this class) and set sentVote as false to indicate that
	 * this user will not send votes anymore. postVote then sends the vote to clientHandler via the objectStream
	 * 
	 * @throws IOException - if sending value is null
	 * 
	 */
	private void postVote() {
        if(!this.user.hasVoted() && this.isSendVote()){
        	sending = this.getVoteValue();
        	this.setSendVote(false);

         } else {
        	 sending = 0;
        	 
         } 
        try {
			oos.writeObject(sending);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Todo
	 */

	private void postRequest() {
		if(newRequests.size()==0) {
			newRequests.add("--Nothing Here--");
		}else {
			sending = this.newRequests;
		}
        try {
			oos.writeObject(sending);
		} catch (IOException e) {
			e.printStackTrace();
		}
        newRequests.remove(0);
	}

	
	/**
	 * askForGroup manages the changes which occurs in the Group object. askForGroup fetches
	 * a Group object from the object stream and checks if the GUI for the client is created.
	 * If no GUI exist then askForGroup creates the GUI. askForGroup then sets the user who is
	 * logged in and the group which will be used. Finally received is then set to null again
	 * to allow fetching for new Group later on.
	 * 
	 */

	private void askForGroup() {
		while(received == null){
             
			try {
				received = ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
             
             if(received instanceof Group && gui == null){
            	 this.group = (Group)received;
            	 this.user = group.findUser(log.getUserID());
            	 gui = new Gui(group, this);
            	 gui.makeFrame();
                    
             } else if(received instanceof Group){
            	 
            	 this.group = (Group)received;
            	 gui.setNewGroup(group);
            	 this.user = group.findUser(log.getUserID());
             }
		}     
        
		received = null;
		
	}

	/**
	 * checkForLogin manages the user who logs in to the application. Using Login class from the fikaAssets,
	 * it checks if the user exist in Group. If the user is not logged, checkForLogin ask the user(using
	 * checklogin from Login class) to log in. Once the user has logged in the userId is then sent to the
	 * clientHandler and waits for an integer. If checkForLogin receives 0 from clientHandler, the user was
	 * not found and is prompted to try again. if checkForLogin receives 1, the user is then set. Finally
	 * if the Client is already logged in, the user is sent in the object stream to identify itself in the
	 * threads in ClientHandler
	 * 
	 */
	private void checkForLogin() {

   	 	if(!log.isLoggedIn()){
       	 	while(!log.isLoggedIn()) {
       	 			received = null;
       	 			log.checklogin();
       	 			
       	 			try {
						oos.writeObject(log.getUserID());
					} catch (IOException e) {
						e.printStackTrace();
					}

       	 			while(received == null){
       	 				
       	 				try {
							received = ois.readObject();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}

       	 			}
       	 			if((int)received == 1){
       	 				log.setLoggedIn();
       	 			} else
       	 				JOptionPane.showMessageDialog(null, "User not found, try again");
       	 		}	
   	 	} else {
   	 		try {
				oos.writeObject(user);
			} catch (IOException e) {
				e.printStackTrace();
			}
   	 	}
   	 	received = null;
		
	}

	/**
	 * Sets the the Group to be used
	 * @param g - Instance of Group
	 */
	public void setGroup(Group g){
		this.group = g;
	}
	
	/**
	 * Returns instance of the Group being used
	 * @return - Returns the object Group currrently used 
	 */
	public Group getGroup(){
		return this.group;
	}

	/**
	 * Returns a Users Login identification
	 * @return - Returns a Users Login identification as String 
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the the user for the client
	 * @param user - user to be set as the client
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Returns a boolean as identification if user is sending vote
	 * @return - Returns a boolean which identifies if user is voting
	 */
	public boolean isSendVote() {
		return sendVote;
	}

	/**
	 * Sets the sendVote boolean to identify if the user is voting or not
	 * @param sendVote - The value of the sendVote
	 */
	public void setSendVote(boolean sendVote) {
		this.sendVote = sendVote;
	}
	
	/**
	 * Returns an integer containing the vote value
	 * @return - Returns voteValue as String 
	 */
	public int getVoteValue() {
		return voteValue;
	}

	/**
	 * Sets the voteValue
	 * @param voteValue - The value of the vote
	 */
	public void setVoteValue(int voteValue) {
		this.voteValue = voteValue;
	}
	
	/**
	 * Returns the new request for a fika
	 * @return - ArrayList of the new request
	 */
	public ArrayList<String> getNewRequests() {
		return newRequests;
	}
	/**
	 * Adds a new request
	 * @param request - The string value of request
	 */
	public void addNewRequests(String request) {
		newRequests.add(request);
	}
	
} 