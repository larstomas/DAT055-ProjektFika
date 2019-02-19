package fikaAssests;
import java.io.Serializable;
import java.util.*;

public class Que implements Serializable{
	Group g;
	ArrayList<User> users;
	public Que(ArrayList<User> users) {
		this.users = users;
	}
	public void nextUser() {
		User temp = users.remove(0);
        users.add(users.size(), temp);
	}
	public ArrayList<User> getUsers(){
		return users;
	}
}
