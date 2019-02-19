
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
	
	public Client(){
		try{
			
	        ip = InetAddress.getByName("localhost"); 
	        s = new Socket(ip, 5056); 
	        ois = new ObjectInputStream(s.getInputStream()); 
	        oos = new ObjectOutputStream(s.getOutputStream());
	        
		} catch (Exception e){
			
		}
	}
	
	public void recieveAndSend(){
		try
	     { 
	         while (true)  
	         {   
	             Object received = ois.readObject();
	             System.out.println("har tagit emot object");
	             if(received instanceof Group && gui == null){
	            	 this.group = (Group)received;
	            	 log = new Login(group);
	            	 while(!log.isLoggedIn()) {
	        			log.checklogin();
	            	 }
	            	 gui = new Gui(group, log.getLogin());
	                 gui.makeFrame();
	             } else if(received instanceof Group){
	            	 this.group = (Group)received;
	            	 gui.setNewGroup(group);
	             }

	         } 
	           
	         // closing resources 
	         /*scn.close(); 
	         ois.close(); 
	         oos.close(); */
	     }catch(Exception e){ 
	         e.printStackTrace(); 
	     } 
		
	}
	

	
} 