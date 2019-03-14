package client;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.*;

import fikaAssests.Group;
import fikaAssests.User;

import java.util.*;

/**
 * GUI creates the user interface for the application. GUI uses information from the
 * class Group to display information such as the Queue list and the Fika scores.
 * GUI is constructed by the client.
 * 
 * 
 * @author group 4
 * @version 0.6
 *
 */

@SuppressWarnings("serial")
public class Gui extends JFrame{
		private JPanel jp;
		private JMenu fileMenu;
		private JMenu helpMenu;
		private JMenuBar menubar;
		private JMenuItem quit;
		private JMenuItem help;
		private JMenuItem credit;
		private JMenuItem addRequest;
		private JMenuItem addRefresh;
		private JLabel jl;
		private JLabel jl2;
		private JLabel jl3;
		private JLabel jl5;
		private JLabel whosFika;
		private JTextArea requestTextArea; 
		private Dimension d;
		private JPanel listOfRequestsPanel;
		private JPanel queuePanel;
		private JPanel votePanel;
		private JPanel scorePanel;
		private JPanel qOrderPanel;
		private JPanel qOrderList;
		private JPanel votingPanel;
		private JPanel scores;
		private JPanel requestPanel;
		private Group group;
		private Client client;
		private ArrayList<JLabel> queueUsers;
		private ArrayList<JLabel> highScore;
		private ArrayList<JLabel> userScore;
		private ArrayList<JButton> votingButtons;
		
		/**
		 * GUI constructor
		 * 
		 * @param gr - Group object
		 * @param c - Client object
		 * 
		 */
		public Gui(Group gr, Client c){
			this.group = gr;
			this.client = c;
			queueUsers = new ArrayList<>();
			votingButtons = new ArrayList<>();
			highScore = new ArrayList<>();
			userScore = new ArrayList<>();
			setTitle("FikaListan: " + client.getUser().getID());
			d = new Dimension(1100,200);
			Timer timer = new Timer(1000, e->this.client.recieveAndSend());
	        timer.start();
		}
		
		/**
		 * Render Client GUI. This is done by calling methods which constructs different
		 * parts of the application such as the menubar, Queue list, Score list and voting menu.
		 * 
		 */
		public void makeFrame(){
			makeMenuBar();
			jp = new JPanel(new GridLayout(1,4));
			
			setPreferredSize(d);
			makeUserLabels();
			makeQueue(queueUsers.size());
			makeVotingMenu();
			
			makeHighScore(group.getUsers().size());
			
			makeRequests(group.getUsers().size());
			setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
			
			add(jp);
			pack();
			setVisible(true);
		}

		/**
		 * Send the users vote, if the user has voted, the buttons are then disabled using setButtons.
		 * @param i - The vote in integer from 1 to 5
		 */
		private void sendVote(int i) {
			System.out.println("Sended vote is: "+i);
			this.client.setSendVote(true);
			System.out.println("this.user.isSendVote() " + this.client.isSendVote());
			this.client.setVoteValue(i);
			this.client.recieveAndSend();
			setButtons(false);
		}
				
		/**
		 * Creates the panel and list for highscore
		 * @param nrOfUsers - Number of users in integer
		 */
		private void makeHighScore(int nrOfUsers) {
			
			scorePanel = new JPanel();
			scorePanel.setBorder(new EtchedBorder());
			scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
			
				jl3 = new JLabel("Fikascore");
			//	jl3.setBorder(new EtchedBorder());
				scores = new JPanel(new GridLayout(nrOfUsers,3));
				
				createHighScore();
				for(int i = 0; i < nrOfUsers; i++) {
									
					
					scores.add(makePositionLabels(nrOfUsers).get(i));
					scores.add(userScore.get(i));
					scores.add(highScore.get(i));
				}
			
			scorePanel.add(jl3);
			scorePanel.add(scores);
			jp.add(scorePanel);
		}
		
		
		/**
		 * Creates position labels for high score
		 * 
		 * @param noOfLabels - Number of Labels in Integer
		 * 
		 * @return - ArrayList<JLabel>
		 */
		private ArrayList<JLabel> makePositionLabels(int noOfLabels) {
			ArrayList<JLabel> labels = new ArrayList<>();
			for(int i = 1; i <= noOfLabels; i++) {
				JLabel temp = new JLabel(Integer.toString(i));
				labels.add(temp);
			}			
			return labels;
		}
		
		/**
		 * Creates scorelabels for highscore
		 */
		private void createHighScore() {
			group.getFikaScore().sort();
			JLabel userID;
			JLabel userRating;
			for(User u: group.getFikaScore().getUsers()) {
				if(u.getID()==null) {
					 userID = new JLabel("");
					 userRating = new JLabel("");		
				}else {
					 userID = new JLabel(u.getID());
					 userRating = new JLabel(Integer.toString(u.getRating()));
				}
				userScore.add(userID);
				highScore.add(userRating);

			}
		}
		
		/**
		 * Creates user labels
		 */
		private void makeUserLabels() {
					
			for(User u: group.getQue().getUsers()) {
				
				queueUsers.add(new JLabel(u.getID()));
				
			}	
		}
		
		/**
		 * Creates requestpanel and textarea for special fika requests
		 * 
		 * @param noOfUsers - Number of users in Integer
		 */
		private void makeRequests(int noOfUsers) {
			requestPanel = new JPanel();
			requestPanel.setLayout(new BoxLayout(requestPanel, BoxLayout.PAGE_AXIS));
			requestPanel.setBorder(new EtchedBorder());		
				jl5 = new JLabel("Önskemål", SwingConstants.CENTER);
				
				//jl5.setHorizontalAlignment(JLabel.CENTER);
				jl5.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
				//jl5.setBorder(new EtchedBorder());
			
				listOfRequestsPanel = new JPanel();
				listOfRequestsPanel.setLayout(new BoxLayout(listOfRequestsPanel, BoxLayout.PAGE_AXIS));
				listOfRequestsPanel.setBorder(new EtchedBorder());
				if(group.getWishlist().equals("")) {
					requestTextArea = new JTextArea("Inga önskemål!");
				}else {
					requestTextArea = new JTextArea("");
					for(String s : group.getWishlist()) {
					requestTextArea.insert(s + " \n", 0);
					
					}
				}
				requestTextArea.setEditable(false);
				requestTextArea.setOpaque(false);
				requestTextArea.setBackground(new Color(0,0,0,0));
				

				
				listOfRequestsPanel.add(requestTextArea);
				requestPanel.add(jl5);
				requestPanel.add(listOfRequestsPanel);

				jp.add(requestPanel);
			
		}
		
		/**
		 * Creates queue panel and adds user labels
		 * 
		 * @param noOfUsers - Number of users in Integer
		 */
		private void makeQueue(int noOfUsers) {
			queuePanel = new JPanel();
			queuePanel.setLayout(new BoxLayout(queuePanel, BoxLayout.PAGE_AXIS));
			
			queuePanel.setBorder(new EtchedBorder());		
				jl = new JLabel("Kölista");
				
				qOrderPanel = new JPanel();
				qOrderPanel.setBorder(new EtchedBorder());
					
					qOrderList = new JPanel(new GridLayout(noOfUsers,1));
					
						qOrderList.setBorder(new EtchedBorder());	
						qOrderList.setLayout(new BoxLayout(qOrderList, BoxLayout.PAGE_AXIS));
						for(JLabel label: queueUsers) {
							
							qOrderList.add(label);
						}
							
					
					
					qOrderPanel.add(qOrderList);
					
				queuePanel.add(jl);	
				queuePanel.add(qOrderPanel);
				jp.add(queuePanel);
		}
		
		/**
		 * Creates voting buttons
		 * 
		 * @param noOfButtons - Number of buttons in Integer
		 */
		private void makeVotingButtons(int noOfButtons) {
			for(int i = 0; i < noOfButtons; i++) {
				final Integer innerMi = new Integer(i+1);
				JButton temp = new JButton(Integer.toString(i+1));
				
				temp.setPreferredSize(new Dimension(40,40));
				votingButtons.add(temp);
				votingPanel.add(temp);
				temp.addActionListener((e) -> {sendVote(innerMi);});
			}	
		}
		
		/**
		 * Creates voting menu
		 */
		private void makeVotingMenu() {
			votePanel = new JPanel();
			votePanel.setBorder(new EtchedBorder());
			votePanel.setLayout(new BoxLayout(votePanel, BoxLayout.PAGE_AXIS));
			
			jl2 = new JLabel("Rösta på: ");
			
			JPanel subHeadPanel = new JPanel();
			subHeadPanel.setBorder(new EtchedBorder());
			subHeadPanel.setLayout(new BoxLayout(subHeadPanel, BoxLayout.PAGE_AXIS));
			
			votingPanel = new JPanel(new GridLayout(1,5));
			votingPanel.setBorder(new EtchedBorder());
			makeVotingButtons(5);
			
			whosFika = new JLabel(group.getQue().getUsers().get(0).getID()+"s fika");
			subHeadPanel.add(whosFika);	
			subHeadPanel.add(votingPanel);
			votePanel.add(jl2);
			votePanel.add(subHeadPanel);
			votePanel.add(new JPanel());
			jp.add(votePanel);
			
		}
		
		/**
		 * Creates a menubar which holds different functionalities in the application.
		 * Menubar contains buttons to add a request for the fika, an update button,
		 * quit button and help button
		 */
		private void makeMenuBar() {
			
			fileMenu = new JMenu("Menu");
			
			addRequest = new JMenuItem("Lägg till önskemål");
			addRequest.addActionListener(e->newRequest());
			fileMenu.add(addRequest);
			
			addRefresh = new JMenuItem("Uppdatera");
			addRefresh.addActionListener(e->this.client.recieveAndSend());
			fileMenu.add(addRefresh);
			
			quit = new JMenuItem("Quit");
			fileMenu.add(quit);
			quit.addActionListener(e->quitApp());
			
			helpMenu = new JMenu("Help");
			help = new JMenuItem("Help");
			helpMenu.add(help);
			help.addActionListener(e->helpNotif());
			credit = new JMenuItem("Credit");
			helpMenu.add(credit);
			credit.addActionListener(e->helpCredit());
			menubar = new JMenuBar();
			menubar.add(fileMenu);
			menubar.add(helpMenu);
			setJMenuBar(menubar);
		}
		
		/**
		 * Exit the program
		 */
		private void quitApp() {
			System.exit(0);
		}
		
		/**
		 * Changes the labels of Highscore and Queue for the new group
		 * @param g - A group obrect
		 */
		public void setNewGroup(Group g){		
			for(int i = 0 ; i < g.getQue().getUsers().size() ; i++){
				queueUsers.get(i).setText(g.getQue().getUsers().get(i).getID());
			}
			
			
			g.getFikaScore().sort();
			for(int i = 0 ; i < highScore.size() ; i++){
				highScore.get(i).setText(Integer.toString(g.getFikaScore().getUsers().get(i).getRating()));
				userScore.get(i).setText(g.getFikaScore().getUsers().get(i).getID());
			}
			
			whosFika.setText(g.getQue().getUsers().get(0).getID()+"s fika");
			g.getQue().getUsers().get(0).setHasVoted(true);
			requestTextArea.setText("");
			//System.out.println(g.getWishlist());
			for(String s : g.getWishlist()) {
			requestTextArea.insert(s + " \n", 0);
			}
			if(g.findUser(this.client.getUser().getID()).hasVoted() == false){
				setButtons(true);
			}else {
				setButtons(false);
			}
			
		}

		/**
		 * Enables or disables the voting buttons
		 * @param bool - for enabling or disabling buttons
		 */
		public void setButtons(Boolean bool){
			for(JButton b : votingButtons){
				b.setEnabled(bool);
			}
			
		}
		
		/**
		 * Prompts the user to write a request for the fika and adds it to the client
		 */
		public void newRequest() {
			String request = JOptionPane.showInputDialog("Skriv in ditt önskemål här: ");
			client.addNewRequests(request);
		}
		
		/**
		 * Shows the help notification
		 */
		public void helpNotif() {
			JOptionPane.showMessageDialog(null,"Kolla genom användarmanualen för hjälp!");
		}
		/**
		 * Shows the credits 
		 */
		public void helpCredit() {
			JOptionPane.showMessageDialog(null, "Group 4: Alexander Al-Hakeem, Jonatan Berko, Mellard Buhian, Tomas Bäckman. Julius Hopf");
		}
}