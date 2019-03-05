package server;
import java.io.*;
import java.util.ArrayList;

import fikaAssests.Group;
import fikaAssests.User;
import fikaAssests.Vote;
public class FileHandler {
	private Group g;

	public FileHandler(Group g) {
		this.g = g;
	}

	public void load() {
		try {
			BufferedReader reader =
					new BufferedReader(
							new FileReader("SavedFika.txt"));
			String line = reader.readLine();
			while(line != null) {
				//Splits the text line where "," is present and each word will be stored in an array
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
	 * Save settings
	 */
	public void save() {
		try {
			FileWriter fileWriter = new FileWriter("SavedFika.txt");
			PrintWriter printWriter = new PrintWriter(fileWriter);
			ArrayList<User> users = g.getQue().getUsers();
			Vote v = g.getV();
			for(User u : users) {
				// Saves all users in group into a text file where each variable  is with an "," at the end to seperate them.
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
	 * Get server group
	 * @return
	 */
	public Group getG() {
		return g;
	}

	/**
	 * Get server group
	 * @param g
	 */
	public void setG(Group g) {
		this.g = g;
	}
}
