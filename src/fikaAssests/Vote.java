package fikaAssests;

import java.io.Serializable;

public class Vote implements Serializable{
	private int noOfVotes;
	private int currRating;
	
	public Vote(){
		
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
		if(vote!= 0){
		this.currRating = vote+this.currRating;
		noOfVotes++;
		}
	}
	public void resetVote() {
		this.noOfVotes=0;
		this.currRating=0;
	}
	public void loadVote(int noOfVotes, int currRating) {
		this.noOfVotes=noOfVotes;
		this.currRating=currRating;
	}
	
	public int calcVote() {
		if(noOfVotes<=0) {
			return 0;
		}
		return currRating/noOfVotes;
		
	}

}
