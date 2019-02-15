package fikaAssests;
import java.util.*;
import server.Server;
public class Group {
	private ArrayList<User> users;
	private Vote v;
	private FikaScore fs;
	private Que q;
	
	public Group(){
		this.users = new ArrayList<User>();  
		this.v 	   = new Vote();
		fs = new FikaScore(this.users);
		q = new Que(this.users);
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
	}
	public void addUser(String ID) {
		User u = new User(ID);
		users.add(u);
	}
	public String getFirstUserID() {
		return users.get(0).getID();
	}

	public boolean sendVote(int voteValue, User votingUser) {
		if(!votingUser.isHasVoted()) {
			v.incomingVote(voteValue);
			votingUser.setHasVoted(true);
			Server s = new Server(this);
			s.save();
			return true;
		}else {
			return false;
		}
	}
	public void nextFika() {
		v.calcVote();
		q.nextUser();
		v.resetVote(q.getUsers().get(0));
		resetHasVoted();
		Server s = new Server(this);
		s.save();
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
