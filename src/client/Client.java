
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
	
	public Client(){
		try{
			
	        ip = InetAddress.getByName("localhost"); 
	        s = new Socket(ip, 5056); 
	        //ois = new ObjectInputStream(s.getInputStream()); 
	        //oos = new ObjectOutputStream(s.getOutputStream());
	        
		} catch (Exception e){
			
		}
	}
	
	public void recieveAndSend(){
		try
	     { 
			ois = new ObjectInputStream(s.getInputStream()); 
	        oos = new ObjectOutputStream(s.getOutputStream());
       	 	log = new Login(group);
       	 	
       	 	while(!log.isLoggedIn()) {
       	 			received = null;
       	 			log.checklogin();
       	 			oos.writeObject(log.getUserID());
       	 			System.out.println(" innan received är null");
       	 			while(received == null){
       	 				System.out.println("received INANAN MOTAG");
       	 				received = ois.readObject();
       	 				System.out.println("received är null");
       	 			}
       	 			if((int)received == 1){
       	 				log.setLoggedIn();
       	 			}
       	 		}
	        received = null;
	        
			while(received == null){
	             
				received = ois.readObject();
				System.out.println("har tagit emot object");
	             
	             if(received instanceof Group && gui == null){
	            	 this.group = (Group)received;

	            	 gui = new Gui(group, log.getLogin());
	                 gui.makeFrame();
	             } else if(received instanceof Group){
	            	 System.out.println("Client har fått fika");
	            	 
	            	 this.group = (Group)received;
	            	 gui.setNewGroup(group);
	             }
			}     
	        received = null;
	             
	        if(false){
	        	sending = new castingVote(gui.getCurrUser(),gui.getCurrUser().getVoteValue());

	         } else {
	        	 sending = 0;
	         } 
	        oos.writeObject(sending);
	        
	        while(received == null){
	             
				received = ois.readObject();
				System.out.println("Client har fått fika");
	            	 
	            this.group = (Group)received;
	            gui.setNewGroup(group);
	             
			}     
	        received = null;
	        
	         // closing resources 

	         ois.close(); 
	         oos.close(); 
	     }catch(Exception e){ 
	         e.printStackTrace(); 
	     } 
		
	}
	

	
} 