
public class User {
	private String ID;
	private int rating;
	private boolean hasVoted;
	
	
	
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
		this.rating = rating;
	}
	public boolean isHasVoted() {
		return hasVoted;
	}
	public void setHasVoted(boolean hasVoted) {
		this.hasVoted = hasVoted;
	}


}
