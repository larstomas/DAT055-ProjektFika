package fikaAssests;

import java.io.Serializable;

/**
 * User handles all information regarding Users of a Group.
 * Created as a list of users in Group and a list of users in Queue.
 * 
 * @author Group 4
 * @version 0.6
 */

public class User implements Serializable{
	private String ID;
	private int rating;
	private boolean hasVoted;
	private int voteValue;
	

	public User(String name){
		this.ID = name;
		this.rating = 0;
		hasVoted = false;
	}
	
	/**
	 * Returns the value of the vote cast by a user
	 * @return - The value of the vote 
	 */
	public int getVoteValue() {
		return voteValue;
	}
	
	/**
	 * Sets the value of the vote
	 * @param voteValue - The value of the vote
	 */
	public void setVoteValue(int voteValue) {
		this.voteValue = voteValue;
	}
	
	/**
	 * Returns a Users Login identification
	 * @return - Returns a Users Login identification as String 
	 */
	public String getID() {
		return ID;
	}
	
	/**
	 * Set the ID of a User
	 * @param input - The ID you wish to give a User
	 */
	public void setID(String input) {
		ID = input;
	}
	
	/**
	 * Returns the rating of a User
	 * @return - Returns the rating of type int for a User
	 */
	public int getRating() {
		return rating;
	}
	
	/**
	 * Set the rating of User
	 * @param rating - The rating you wish to set for the User
	 */
	public void setRating(int rating) {
		this.rating = this.rating+rating;
	}
	
	/**
	 * Indicates whether or not a User has already voted or not.
	 * If hasVoted is set to true a User has voted. If hasVoted is set to false a User has not voted.
	 * @return - Returns a boolean indicating whether or not a User has voted
	 */
	public boolean hasVoted() {
		return hasVoted;
	}
	
	/**
	 * Sets the state of whether or not a User has voted.
	 * @param hasVoted - The state that hasVoted should be set to.
	 */
	public void setHasVoted(boolean hasVoted) {
		this.hasVoted = hasVoted;
	}

}
