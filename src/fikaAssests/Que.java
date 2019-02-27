package fikaAssests;
import java.io.Serializable;
import java.util.*;

public class Que implements Serializable{
	private Group g;
	private ArrayList<User> users;
	
	public Que() {
		this.users = new ArrayList<User>(); 
	}
	
	public void nextUser() {

		User temp = users.remove(0);
        users.add(users.size(), temp);

	}
	public ArrayList<User> getUsers(){
		return users;
	}
	
	public void addUser(User u){
		this.users.add(u);
	}
}
