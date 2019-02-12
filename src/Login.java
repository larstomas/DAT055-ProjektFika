import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Login {
    Group gr;
    String userID; 
    boolean loggedin = false;
    public Login(Group gr) {
    	this.gr = gr;    	
    }
    
    public void checklogin() {
    	//Opens Pane for log in and checks all users from group if there is a match user sends to gui
    	userID = JOptionPane.showInputDialog("Logga in här: ");
    	ArrayList<User> users = gr.getUsers();
    	for(User u : users) {
    		if(u.getID().equals(userID)) {
    			loggedin=true;
    			break;
    		}    		
    	}
    	//If there is no match the user will get to choose between closing the app or try to log in again
    	if(!loggedin) {
    		if (JOptionPane.showConfirmDialog(null, "Your login was not correct! Try again?", "Login Failed!",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {    
    		} else {
    			System.exit(0);
    		}
    	}
    }


public String getLogin() {
	return this.userID;

	}
public Boolean getLoggedIn() {
	return loggedin;

	}
}