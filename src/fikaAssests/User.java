package fikaAssests;

import java.io.Serializable;

public class User implements Serializable{
	private String ID;
	private int rating;
	private boolean hasVoted;
	private int voteValue;
	
	
	public int getVoteValue() {
		return voteValue;
	}


	public void setVoteValue(int voteValue) {
		this.voteValue = voteValue;
	}


	public User(String name){
		this.ID = name;
		this.rating = 0;
		hasVoted = false;
	}
	
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = this.rating+rating;
	}
	public boolean hasVoted() {
		return hasVoted;
	}
	public void setHasVoted(boolean hasVoted) {
		this.hasVoted = hasVoted;
	}


}
