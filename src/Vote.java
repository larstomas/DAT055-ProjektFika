
public class Vote {
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
	
	public void setCurrRating(int currRating) {
		this.currRating = currRating+this.currRating;
	}
	
	public void resetVote(User curr) {
		this.current = curr;
		this.noOfVotes=0;
		this.currRating=0;
	}

}
