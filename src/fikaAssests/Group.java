package fikaAssests;
import java.io.Serializable;
import java.util.*;
import server.FileHandler;
public class Group  extends Observable implements Serializable{
	private ArrayList<User> users;
	private Vote v;
	private FikaScore fs;
	private Que q;
	
	public Group(){
		this.users = new ArrayList<User>();  
		this.v 	   = new Vote();
		fs = new FikaScore();
		q = new Que();
	}

	public ArrayList<User> getUsers() {
		return users;
	}
	public FikaScore getFikaScore() {
		return fs;
	}
	public Que getQue() {
		return q;
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
		fs.addUser(u);
		q.addUser(u);
	}
	public void addUser(String ID) {
		User u = new User(ID);
		users.add(u);
	}
	public String getFirstUserID() {
		return users.get(0).getID();
	}

	public boolean sendVote(int voteValue, User votingUser) {
		if(!votingUser.hasVoted()) {
			v.incomingVote(voteValue);
			votingUser.setHasVoted(true);
			FileHandler s = new FileHandler(this);
			s.save();
			return true;
		}else {
			return false;
		}
	}
	
	public void nextFika() {
		q.getUsers().get(0).setRating(v.calcVote());
		q.nextUser();
		v.resetVote();
		resetHasVoted();
		q.getUsers().get(0).setHasVoted(true);
		FileHandler s = new FileHandler(this);
		s.save();
		System.out.println("redo för nästa fika");


		setChanged();
		notifyObservers(this);
	}
	public User findUser(String user) {
		for(User u: users) {
			if(u.getID().equals(user)) {
				return u;
			}
		}
		return null;
		
	}
	public void resetHasVoted() {
		for(User u: users) {
			u.setHasVoted(false);
		}
	}
	
}
