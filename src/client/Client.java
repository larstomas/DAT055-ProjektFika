
package client;
import fikaAssests.*;
import java.net.*;
import java.util.*;

import javax.swing.JOptionPane;

import java.io.*;



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
	
	public Client(){
		try{
			
	        ip = InetAddress.getByName("localhost"); 
	        log = new Login(group);
	        
		} catch (Exception e){
			
		}
	}
	
	public void recieveAndSend(){
		try
	     { 
			//Sätter upp connection med server
	        s = new Socket(ip, 5056); 
			ois = new ObjectInputStream(s.getInputStream()); 
	        oos = new ObjectOutputStream(s.getOutputStream());
       	 	
	       //Hantera Login
	       checkForLogin();
	       
	       //FrågraServerOmGrupp
	       askForGroup();
	             
	        //Skicka eventuell röst, skicka 0 ingen röst gjorts
			postVote();

	        //Be om ny group innan stänger tråd
			askForGroup();	        
	        
	         //Stäng Resurser
	         ois.close(); 
	         oos.close(); 
	         this.s.close();
	         
	     }catch(Exception e){ 
	         e.printStackTrace(); 
	     } 
		
	}
	
	
	private void postVote() {
        if(!this.user.hasVoted() && this.isSendVote()){
        	sending = this.getVoteValue();
        	System.out.println("Clieent skickar röst" + sending);
        	this.setSendVote(false);

         } else {
        	 sending = 0;
        	 System.out.println("Clieent skickar röst" + sending);
        	 
         } 
        try {
			oos.writeObject(sending);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Client har skickat röst");
		
	}

	private void askForGroup() {
		//Väntar på group
		while(received == null){
             
			try {
				received = ois.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             
			//Om Gui inte gjorts gör Gui och sätt group
             if(received instanceof Group && gui == null){
            	 this.group = (Group)received;
            	 this.user = group.findUser(log.getUserID());
            	 gui = new Gui(group, this);
            	 gui.makeFrame();
            	 System.out.println("Client har fått fika");
                 
             //annars sätt bara group    
             } else if(received instanceof Group){
            	 System.out.println("Client har fått fika");
            	 
            	 this.group = (Group)received;
            	 gui.setNewGroup(group);
            	 this.user = group.findUser(log.getUserID());
             }
		}     
        
		received = null;
		
	}

	private void checkForLogin() {
		
		//Om inte inloggad
   	 	if(!log.isLoggedIn()){
   	 		//Be om username tills inloggad
       	 	while(!log.isLoggedIn()) {
       	 			received = null;
       	 			log.checklogin();
       	 			
       	 			
       	 			//Skicka username
       	 			try {
						oos.writeObject(log.getUserID());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

       	 			//Vänta på svar
       	 			while(received == null){
       	 				
       	 				try {
							received = ois.readObject();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

       	 			}
       	 			//Om svar = 1, så finns usern i Group, sätt loggedIn
       	 			if((int)received == 1){
       	 				log.setLoggedIn();
       	 			} else
       	 				JOptionPane.showMessageDialog(null, "User not found, try again");
       	 		}
   	 	
       	//Om klienten redan är inloggad så skicka user, så tråden vet vem den pratar med 	
   	 	} else {
   	 		try {
				oos.writeObject(user);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   	 	}
   	 	received = null;
		
	}

	public void setGroup(Group g){
		this.group = g;
	}
	
	public Group getGroup(){
		return this.group;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isSendVote() {
		return sendVote;
	}

	public void setSendVote(boolean sendVote) {
		this.sendVote = sendVote;
	}
	public int getVoteValue() {
		return voteValue;
	}

	public void setVoteValue(int voteValue) {
		this.voteValue = voteValue;
	}

	
} 