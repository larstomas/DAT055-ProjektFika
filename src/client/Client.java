
package client;
import fikaAssests.*;
import java.net.*;
import java.util.*;
import java.io.*;



public class Client  { 
	InetAddress ip;
	Socket s;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	Group group;
	Gui gui = null;
	Login log = null;
	Object received = null;
	Object sending = null;
	boolean sendVote;
	int voteValue;
	User user;
	
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
       	 	
	        
	       //Om inte inloggad
       	 	if(!log.isLoggedIn()){
       	 		//Be om username tills inloggad
	       	 	while(!log.isLoggedIn()) {
	       	 			received = null;
	       	 			log.checklogin();
	       	 			
	       	 			//Skicka username
	       	 			oos.writeObject(log.getUserID());

	       	 			//Vänta på svar
	       	 			while(received == null){
	       	 				received = ois.readObject();

	       	 			}
	       	 			//Om svar = 1, så finns usern i Group, sätt loggedIn
	       	 			if((int)received == 1){
	       	 				log.setLoggedIn();
	       	 			}
	       	 		}
       	 	
	       	//Om klienten redan är inloggad så skicka user, så tråden vet vem den pratar med 	
       	 	} else {
       	 		oos.writeObject(user);
       	 	}
	       
       	 	received = null;
	        
	        //Väntar på group
			while(received == null){
	             
				received = ois.readObject();
	             
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
	             
	        //Skicka eventuell röst, skicka 0 ingen röst gjorts
			
			System.out.println("this.user.hasVoted() " + this.getUser().hasVoted());
			System.out.println("this.user.isSendVote() " + this.isSendVote());
			
	        if(!this.user.hasVoted() && this.isSendVote()){
	        	sending = this.getVoteValue();
	        	System.out.println("Clieent skickar röst" + sending);
	        	this.setSendVote(false);

	         } else {
	        	 sending = 0;
	        	 System.out.println("Clieent skickar röst" + sending);
	        	 
	         } 
	        oos.writeObject(sending);
	        System.out.println("Client har skickat röst");
	        
	        //Be om ny group innan stänger tråd
	        while(received == null){  
				received = ois.readObject();	     
			}    				

	        Group temp = (Group)received;
	        
	        
	        setGroup(temp);
            gui.setNewGroup(group);
            
            this.user = getGroup().findUser(log.getUserID());
	        received = null;
	        
	        
	         // closing resources 
	         ois.close(); 
	         oos.close(); 
	         this.s.close();
	         
	     }catch(Exception e){ 
	         e.printStackTrace(); 
	     } 
		
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