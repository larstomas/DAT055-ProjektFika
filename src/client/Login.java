package client;
import java.io.Serializable;
import javax.swing.JOptionPane;

import fikaAssests.Group;
import fikaAssests.User;

/**
 * 
 * Login allows the user to log in to the client.
 * 
 * @author Group 4
 * @version 0.6
 *
 */

public class Login implements Serializable{
    private Group gr;
    private String userID; 
    private User currUser;
    private boolean loggedin = false;
    
    public Login(Group gr) {
    	this.gr = gr;    	
    }
    
    /**
     * creates a panel which prompts the user to login to the application. The user is prompted again to try again if the log in fails.
     */
    public void checklogin() {
    	userID = JOptionPane.showInputDialog("Logga in här: ");

    }
    /**
	 * Returns the userID of the user that logged in
	 * @return string which indicates the userID
	 */
	public User getLogin() {
		return this.currUser;
	
	}
	/**
	 * Sets the userId of Login
	 * 
	 * @param userID - The userId to be added
	 */
	public String getUserID() {
		return userID;
	}
	
	/**
	 * Sets the userId of Login
	 * 
	 * @param userID - The userId to be added
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	/**
	 * Returns a boolean which indicates if the user is logged in or not
	 * 
	 * @return Boolean which indicate if the user is logged in
	 */
	public Boolean isLoggedIn() {
		return loggedin;
	
		}
	
	/**
	 * Sets the loggedIn variable to true
	 * 
	 */
	public void setLoggedIn() {
		this.loggedin = true;
		
	}
}