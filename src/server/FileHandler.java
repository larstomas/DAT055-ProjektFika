package server;
import java.io.*;
import java.util.ArrayList;
import fikaAssests.Group;
import fikaAssests.User;
import fikaAssests.Vote;

/**
 * FileHandler saves and loads information of a Group.
 * Changes that are made are saved in SavedFika.txt when the server goes offline.
 * When the server goes online SavedFika.txt is loaded.
 * 
 * @author Group 4
 * @version 0.6
 */
public class FileHandler {
	private Group g;
	
	/**
	 * Creates a FileHandler object.
	 * @param g - The Group to save/load data from.
	 */
	public FileHandler(Group g) {
		this.g = g;
	}
	
	/**
	 * Loads user data from SavedFika.txt to a Group 
	 */
	public void load() {
		try {
			BufferedReader reader =
			new BufferedReader(
			new FileReader("SavedFika.txt"));
			String line = reader.readLine();
			while(line != null) {
				if(line!=null) {
					if(line.equals("--VOTE--")) {
						Vote v = g.getV();
						line = reader.readLine();
						String[] vote = line.split("[,]");
						v.loadVote(Integer.parseInt(vote[0]),Integer.parseInt(vote[1]));
					}else if(line.equals("--WISHLIST--")){
						line = reader.readLine();
						while(line != null) {
							g.getWishlist().add(line);
							line = reader.readLine();
						}
					
					}else {
						String[] user = line.split("[,]");
						// the array of words will be used to create a new user.
						g.addUser(user[0], Integer.parseInt(user[1]) , Boolean.parseBoolean(user[2]));
					}
				}
				line = reader.readLine();
			}
			
			reader.close();
			}
			catch(FileNotFoundException e) {
				System.out.println("File not found. Make sure SavedFika.txt is in folder or outside the source folder! Exiting now!");
				System.exit(0);
			}
			catch(IOException e) {
				System.out.println("Something went wrong while reading file! Make sure SavedFika is not empty! Exiting now!");
				System.exit(0);
				}
	}
	
	/**
	 * Saves data of a Group to SavedFika.txt
	 */
	public void save() {
		try {
		    FileWriter fileWriter = new FileWriter("SavedFika.txt");
		    PrintWriter printWriter = new PrintWriter(fileWriter);
		    ArrayList<User> users = g.getQue().getUsers();
		    Vote v = g.getV();
		    for(User u : users) {
		    	printWriter.printf(u.getID()+ ","+ u.getRating()+","+u.hasVoted());
		    	printWriter.println();
		    }
		    printWriter.println("--VOTE--");
		    printWriter.println(v.getNoOfVotes() + "," + v.getCurrRating());
		    printWriter.println("--WISHLIST--");
		    for(String s: g.getWishlist()) {
		    	printWriter.println(s);	
		    }
		    printWriter.close();
			}
			catch(IOException e) {
				System.out.println("Something went wrong while saving! Exiting now!");
				System.exit(0);
			}
	}

	/**
	 * Get a Group with the data saved in SavedFika.txt
	 * @return - Returns a Group with the data saved in SavedFika.txt
	 */
	public Group getG() {
		return g;
	}

}
