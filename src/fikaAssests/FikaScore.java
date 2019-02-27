package fikaAssests;
import java.io.Serializable;
import java.util.ArrayList;

public class FikaScore implements Serializable{
	private ArrayList<User> users;
	
	public FikaScore() {
		this.users = new ArrayList<User>();
		
	}
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
	public ArrayList<User> getUsers(){
		return users;
	}
	
	public void addUser(User u){
		this.users.add(u);
		this.sort();
	}

}
