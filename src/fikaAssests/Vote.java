package fikaAssests;

import java.io.Serializable;

public class Vote implements Serializable{
	private User current;
	private int noOfVotes;
	private int currRating;
	
	public Vote(){
		
	}
	
	
	
	
	public User getCurrent() {
		return current;
	}
	public void setCurrent(User current) {
		this.current = current;
	}
	public int getNoOfVotes() {
		return noOfVotes;
	}
	public void setNoOfVotes(int noOfVotes) {
		this.noOfVotes = noOfVotes;
	}
	public int getCurrRating() {
		return currRating;
	}
	public void incomingVote(int vote) {
		this.currRating = vote+this.currRating;
		noOfVotes++;
	}
	public void resetVote(User curr) {
		this.current = curr;
		this.noOfVotes=0;
		this.currRating=0;
	}
	public void loadVote(User user, int noOfVotes, int currRating) {
		this.current = user;
		this.noOfVotes=noOfVotes;
		this.currRating=currRating;
	}
	public void calcVote() {
		if(noOfVotes<=0) {
			return;
		}
		currRating = currRating/noOfVotes;
		current.setRating(currRating);
	}

}
