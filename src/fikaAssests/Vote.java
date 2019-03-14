package fikaAssests;

import java.io.Serializable;

/**
 * Vote keeps track and calculates all votes being cast on Users responsible for fika.
 * 
 * 
 * @author Group 4
 * @version 0.6
 */

public class Vote implements Serializable{
	private int noOfVotes;
	private int currRating;
	
	public Vote(){
		
	}
	/**
	 * Returns the number of user that has voted
	 * @return - Returns the number of votes
	 */
	public int getNoOfVotes() {
		return noOfVotes;
	}
	
	/**
	 * Sets the number of user that has voted
	 * @param noOfVotes - integer that indicates the amount of user that voted
	 */
	public void setNoOfVotes(int noOfVotes) {
		this.noOfVotes = noOfVotes;
	}
	
	/**
	 * Returns the Current rating for the user being voted on, this integer is the total votes for the current fika.
	 * @return - The current total rating for a user
	 */
	public int getCurrRating() {
		return currRating;
	}
	
	/**
	 * Takes in an incoming vote add adds it to the total votes(currRating) if the vote is not 0. incomingVote then increases the noOfVotes.
	 * @param vote incoming vote to be added to currRating.
	 */
	public void incomingVote(int vote) {
		if(vote!= 0){
		this.currRating = vote+this.currRating;
		noOfVotes++;
		}
	}
	
	/**
	 * Resets noOfVotes and currRating.
	 */
	public void resetVote() {
		this.noOfVotes=0;
		this.currRating=0;
	}
	
	/**
	 * Loads a new noOfVotes and currRating.
	 * 
	 * @param noOfVotes - amount of voter to be set
	 * @param currRating - total votes to be set
	 */
	public void loadVote(int noOfVotes, int currRating) {
		this.noOfVotes=noOfVotes;
		this.currRating=currRating;
	}
	
	/**
	 * Calculates the actually vote using currRating and noOfVotes.
	 * @return Returns the actual rating after the calculation
	 */
	public int calcVote() {
		if(noOfVotes<=0) {
			return 0;
		}
		return currRating/noOfVotes;
		
	}

}
