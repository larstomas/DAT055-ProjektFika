package server;
import java.io.*;
import java.util.ArrayList;

import fikaAssests.Group;
import fikaAssests.User;
import fikaAssests.Vote;
public class FileHandler {
	Group g;
	public FileHandler(Group g) {
		this.g = g;
	}
	
	public FileHandler() {	
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
						v.loadVote(g.findUser(vote[0]),Integer.parseInt(vote[1]),Integer.parseInt(vote[2]));
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
	
	// Save settings
	public void save() {
		try {
		    FileWriter fileWriter = new FileWriter("SavedFika.txt");
		    PrintWriter printWriter = new PrintWriter(fileWriter);
		    ArrayList<User> users = g.getQue().getUsers();
		    Vote v = g.getV();
		    for(User u : users) {
		    // Saves all users in group into a text file where each variable  is with an "," at the end to seperate them.
		    	printWriter.printf(u.getID()+ ","+ u.getRating()+","+u.isHasVoted());
		    	printWriter.println();
		    }
		    printWriter.println("--VOTE--");
		    printWriter.println(v.getCurrent().getID() + "," + v.getNoOfVotes() + "," + v.getCurrRating());
		    printWriter.close();
			}
			catch(IOException e) {
				System.out.println("Something went wrong while saving! Exiting now!");
				System.exit(0);
			}
	}

	public Group getG() {
		return g;
	}

	public void setG(Group g) {
		this.g = g;
	}
}
