package fikaAssests;
import java.util.*;

public class Que {
	Group g;
	ArrayList<User> users;
	public Que(ArrayList<User> users) {
		this.users = users;
	}
	public void nextUser() {
		User temp;
        for (int i = 0; i < users.size()+1; i++){
            temp = users.get(0);
            for (int j = 0; j < users.size()-1; j++){
            	users.set(j, users.get(j+1));
            }
            users.set(users.size() - 1, temp);
        }	
	}
	public ArrayList<User> getUsers(){
		return users;
	}
}
