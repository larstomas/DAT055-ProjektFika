package fikaAssests;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *  FikaScore organize the user which will be used in the Group Class
 *  to organize the users by their rating. This class will mostly be
 *  used for the <i>createHighScore</i> and <i>setNewGroup</i> in GUI
 *  class in the <bold>Client</bold> package.
 * 
 *  @author Group 4
 *  @version 0.6
 *  
 *
 */


public class FikaScore implements Serializable{
	private ArrayList<User> users;
	
	/**
	 * Constructs a FikaScore
	 */
	public FikaScore() {
		this.users = new ArrayList<User>();
		
	}
	
	/**
	 *  Sorts the ArrayList of User according to their rating
	 */
	public void sort() {
		for(int i=0;i<= users.size()-1;i++) {
			for(int j=0;j<=users.size()-1;j++) {
				if(users.get(i).getRating() >= users.get(j).getRating()) {
					User temp = users.get(j);
					users.set(j, users.get(i));
					users.set(i, temp);
				}
			}
		}
	}
	/**
	 *  Returns the ArrayList Users
	 *  
	 * @return - ArrayList Users
	 */
	public ArrayList<User> getUsers(){
		return users;
	}
	
	/**
	 * Adds a new User in the ArrayList Users and directly sorts them
	 * after being added
	 * 
	 * @param u - New User to be added in Users
	 */
	public void addUser(User u){
		this.users.add(u);
		this.sort();
	}

}
