import java.util.*;

public class Group {
	private ArrayList<User> users;
	private Vote v;
	private FikaScore fs;
	
	public Group(){
		this.users = new ArrayList<User>();  
		this.v 	   = new Vote();
		fs = new FikaScore(this.users);
	}

	public ArrayList<User> getUsers() {
		return users;
	}
	
	public FikaScore getFikaScore() {
		return fs;
	}
	
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public Vote getV() {
		return v;
	}

	public void setV(Vote v) {
		this.v = v;
	}
	
	public void addUser(String ID, int rating, boolean hasVoted) {
		User u = new User(ID);
		u.setHasVoted(hasVoted);
		u.setRating(rating);
		users.add(u);
	}
	
	public String getFirstUserID() {
		return users.get(0).getID();
	}
	
	public void nextUser() {
		User temp = users.get(0); 
		for(int i=1; i <= users.size()-1;) {
			users.set(i-1, users.get(i));
			i++;
		}
		users.set(users.size()-1, temp);		
		Server s = new Server(this);
		s.save();
		
	}
	
}
