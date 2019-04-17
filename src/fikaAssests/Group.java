package fikaAssests;
import java.io.Serializable;
import java.util.*;
import server.FileHandler;

/**
 * Group is a class which holds all the information needed for the application
 * which are used rendering valuable information in the GUI for the Client.
 * The information Group holds include:
 * <ul>
 * 		<li>Collection of user
 * 		<li>Amount of vote per week
 *		<li>The queue of user having fika
 *		<li>Rating list of all the users in the group organized in descending order
 * </ul>
 * Group also holds necessary method for saving information to the file and starting 
 * a new iteration of the week for the fika group.
 * 
 * @author Group 4
 * @version 0.6
 *
 */

public class Group  extends Observable implements Serializable{
	private ArrayList<User> users;
	private Vote v;
	private FikaScore fs;
	private Que q;
	private ArrayList<String> wishlist;
	
	/**
	 * Constructs a Group
	 */
	public Group(){
		this.users = new ArrayList<User>();  
		this.v 	   = new Vote();
		this.wishlist = new ArrayList<String>();
		fs = new FikaScore();
		q = new Que();
	}

	/**
	 * Returns the ArrayList of User
	 * @return ArrayList of user
	 */
	public ArrayList<User> getUsers() {
		return users;
	}
	
	/**
	 * Returns an object FikaScore
	 * @see FikaScore
	 *
	 * @return FikaScore object
	 */
	public FikaScore getFikaScore() {
		return fs;
	}
	
	/**
	 * Returns an object Que
	 * @see Que
	 *
	 * @return Que object
	 */	
	public Que getQue() {
		return q;
	}
	
	/**
	 * Adds an ArrayList of User to Group
	 * @see User
	 * 
	 * @param users - ArrayList of user
	 */
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	/**
	 * Returns an object Vote
	 * @see Vote
	 *
	 * @return Vote object
	 */		
	public Vote getV() {
		return v;
	}

	/**
	 *  Sets a new Vote object to Group
	 *  @see vote
	 *  
	 * @param v - Vote object to be added to Group
	 */
	public void setV(Vote v) {
		this.v = v;
	}
	
	/**
	 * Gets the wishlist for this Group
	 * These are string which holds the desired or requested fika
	 * 
	 * @return Arraylist of string
	 */
	public ArrayList<String> getWishlist() {
		return wishlist;
	}
	
	/**
	 * Adds a new request on the wishlist
	 * 
	 * @param request - String describing the required/ requested fika
	 */
	public void addRequest(String request) {
		wishlist.add(request);
		FileHandler s = new FileHandler(this);
		s.save();
	}
	
	
	/**
	 * Adds a new User to the group and adds the user to to Que and FikaScore
	 * @see Que
	 * @see FikaScore
	 * 
	 * @param ID - user's ID
	 * @param rating - user's rating
	 * @param hasVoted - boolean which tell if the user has voted or not
	 */
	public void addUser(String ID, int rating, boolean hasVoted) {
		User u = new User(ID);
		u.setHasVoted(hasVoted);
		u.setRating(rating);
		users.add(u);
		fs.addUser(u);
		q.addUser(u);
	}
	
	/**
	 * Returns the first user in the ArrayList
	 * 
	 * @return instance of User
	 */
	public String getFirstUserID() {
		return users.get(0).getID();
	}

	/**
	 * Checks if the user has voted by checking user's hasVoted variable and
	 * sets a vote by using Vote class method "incomingVote" for the user.
	 * 
	 * user's hasVoted is then set to true to indicate that it has voted and
	 * will not be able to vote again and the group is then saved to the file
	 * 
	 * sendVote then returns false if the user has already voted and true if it has not
	 * @see FileHandler
	 * @see Vote
	 * @see User
	 * 
	 * @param voteValue
	 * @param votingUser
	 * @return
	 */
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
	
	/**
	 * Sets up a new week of fika by first setting the calculated vote for the user having
	 * fika on the current week. Then the queue is refreshed to set the new user having the next fika.
	 * Votes are reset in the class vote and the variable hasVoted is set to false so all other user
	 * can vote. The hasVoted for the user who will have the fika in the new week will have theirs 
	 * set as true so the one who brings fika can not vote on their own fika.
	 * 
	 * Finally the changes are saved in the file and all observer for Group are notified.
	 * @see Que
	 * @see Vote
	 * @see FileHandler
	 */
	public void nextFika() {
		q.getUsers().get(0).setRating(v.calcVote());
		q.nextUser();
		v.resetVote();
		resetHasVoted();
		q.getUsers().get(0).setHasVoted(true);
		int wishlistSize = wishlist.size();
		for(int i=0;i<wishlistSize;i++) {
			wishlist.remove(0);
		}
		FileHandler s = new FileHandler(this);
		s.save();
		System.out.println("redo för nästa fika");

		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * Finds and returns a user in Group
	 * 
	 * @param user - user needed to be found
	 * @return instance of the user
	 */
	public User findUser(String user) {
		for(User u: users) {
			if(u.getID().equals(user)) {
				return u;
			}
		}
		return null;
		
	}
	
	/**
	 * Resets and sets all hasVoted for all user in the group to false
	 * @see User
	 */
	public void resetHasVoted() {
		for(User u: users) {
			u.setHasVoted(false);
		}
	}
	
}

