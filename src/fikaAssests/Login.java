package fikaAssests;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Login implements Serializable{
	private Group gr;
	private String userID; 
	private User currUser;
	private boolean loggedin = false;

	public Login(Group gr) {
		this.gr = gr;    	
	}

	public void checklogin() {
		//Opens Pane for log in and checks all users from group if there is a match user sends to gui
		userID = JOptionPane.showInputDialog("Logga in här: ");
		//ArrayList<User> users = gr.getUsers();
		//for(User u : users) {
		//if(u.getID().equals(userID)) {
		//currUser = u;
		//loggedin=true;
		//break;
		//}    		
		//If there is no match the user will get to choose between closing the app or try to log in again
	}


	public User getLogin() {
		return this.currUser;

	}
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public Boolean isLoggedIn() {
		return loggedin;

	}

	public void setLoggedIn() {
		this.loggedin = true;

	}
}