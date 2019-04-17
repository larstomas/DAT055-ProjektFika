package fikaAssests;
import java.io.Serializable;
import java.util.*;

/**
 * Queue is a list of Users and keeps track of the order which the responsibility of fika is set. 
 * It also rolls the list after each fika is completed.
 * Created in class Group.
 * 
 * @author Group 4
 * @version 0.6
 */

public class Que implements Serializable{
	private ArrayList<User> users;
	
	public Que() {
		this.users = new ArrayList<User>(); 
	}
	
	/**
	 * Moves user from first position to last position in queue
	 */
	public void nextUser() {

		User temp = users.remove(0);
        users.add(users.size(), temp);

	}
	
	/**
	 * Returns the queue
	 * @return Returns the queue
	 */
	public ArrayList<User> getUsers(){
		return users;
	}
	
	/**
	 * Adds user to the queue
	 * @param u		The user to add.
	 */
	public void addUser(User u){
		this.users.add(u);
	}
}
