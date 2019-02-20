package client;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import fikaAssests.Group;
import fikaAssests.User;

import java.util.*;

@SuppressWarnings("serial")
public class Gui extends JFrame{
		JPanel jp;
		JMenu fileMenu;
		JMenu helpMenu;
		JMenuBar menubar;
		JMenuItem quit;
		JMenuItem login;
		JMenuItem nextFika;
		JMenuItem help;
		JMenuItem addRequest;
		JLabel jl;
		JLabel jl2;
		JLabel jl3;
		JLabel jl4;
		JLabel jl5;
		JTextArea requestTextArea; 
		Dimension d;
		JPanel listOfRequestsPanel;
		JPanel queuePanel;
		JPanel votePanel;
		JPanel scorePanel;
		JPanel qOrderPanel;
		JPanel qOrderList;
		JPanel votingPanel;
		JPanel scores;
		JPanel requestPanel;
		Group group;
		User currUser;
		ArrayList<JLabel> queueUsers;
		ArrayList<JLabel> highScore;
		ArrayList<JLabel> userScore;
		ArrayList<JButton> votingButtons;
		
		public Gui(Group gr, User currUser){
			this.group = gr;
			this.currUser = currUser;
			queueUsers = new ArrayList<>();
			votingButtons = new ArrayList<>();
			highScore = new ArrayList<>();
			userScore = new ArrayList<>();
			d = new Dimension(1100,200);
		}	
		
		
		
		public void makeFrame(){
			
			//MENUBAR
			makeMenuBar();
			
			//MAINWINDOW JPANEL 1,3 GRID
			jp = new JPanel(new GridLayout(1,4));
			setLocationRelativeTo(null);
			setPreferredSize(d);
			
			//CREATE USER LABELS. SAVED IN queueUsers AND highScoreUsers
			makeUserLabels();
			//CREATES QUEUE
			makeQueue(queueUsers.size());
			
			
			//CREATE VOTING MENU
			makeVotingMenu();
			
			makeHighScore(group.getUsers().size());
			
			makeRequests(group.getUsers().size());
			
			setSize(1100,500);
			add(jp);
			pack();
			setVisible(true);
			
		}


		private void sendVote(int i) {
			if(currUser.getID().equals(group.getV().getCurrent().getID())) {
				System.out.println("Du kan inte rösta på din egen fika!");
			}else{
				if(group.sendVote(i, currUser)) {
					System.out.println("Du gav " + group.getV().getCurrent().getID() + " en: " + i);
				}else{
					System.out.println("Du har redan röstat!");
				}
			}
		}
				
		//CREATES THE PANEL AND LIST FOR HIGHSCORE
		private void makeHighScore(int nrOfUsers) {
			
			scorePanel = new JPanel();
			scorePanel.setBorder(new EtchedBorder());
			scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
				jl3 = new JLabel("Fikascore");
				jl3.setBorder(new EtchedBorder());
				scores = new JPanel(new GridLayout(nrOfUsers,3));
				scores.setBorder(new EtchedBorder());
				
				
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
		
		
		//CREATES POSITIONLABELS FOR HIGH SCORE
		private ArrayList<JLabel> makePositionLabels(int noOfLabels) {
			ArrayList<JLabel> labels = new ArrayList<>();
			for(int i = 1; i <= noOfLabels; i++) {
				JLabel temp = new JLabel(Integer.toString(i));
				labels.add(temp);
			}			
			return labels;
		}
		//CREATES SCORELABELS FOR HIGHSCORE
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
		//CREATES USER LABELS
		private void makeUserLabels() {
					
			for(User u: group.getQue().getUsers()) {
				
				queueUsers.add(new JLabel(u.getID()));
				
			}	
		}
		
		private void makeRequests(int noOfUsers) {
			requestPanel = new JPanel();
			requestPanel.setLayout(new BoxLayout(requestPanel, BoxLayout.PAGE_AXIS));
			requestPanel.setBorder(new EtchedBorder());		
				jl5 = new JLabel("Önskemål", SwingConstants.CENTER);
				
				//jl5.setHorizontalAlignment(JLabel.CENTER);
				jl5.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
				jl5.setBorder(new EtchedBorder());
			
				listOfRequestsPanel = new JPanel();
				listOfRequestsPanel.setLayout(new BoxLayout(listOfRequestsPanel, BoxLayout.PAGE_AXIS));
				listOfRequestsPanel.setBorder(new EtchedBorder());
				
				requestTextArea = new JTextArea("Något med grädde\nNågot med nötter");
				requestTextArea.setEditable(false);
				requestTextArea.setOpaque(false);
				requestTextArea.setBackground(new Color(0,0,0,0));
				
				
				listOfRequestsPanel.add(requestTextArea);
				requestPanel.add(jl5);
				requestPanel.add(listOfRequestsPanel);
				
				jp.add(requestPanel);
			
		}
		
		//CREATES QUEUE PANEL AND ADDS USERLABELS
		private void makeQueue(int noOfUsers) {
			queuePanel = new JPanel();
			queuePanel.setLayout(new BoxLayout(queuePanel, BoxLayout.PAGE_AXIS));
			queuePanel.setBorder(new EtchedBorder());		
				jl = new JLabel("Kölista");
				jl.setBorder(new EtchedBorder());
				
				qOrderPanel = new JPanel();
				qOrderPanel.setBorder(new EtchedBorder());	
				
					jl4 = new JLabel("Nästa");
					jl4.setBorder(new EtchedBorder());

				
					qOrderList = new JPanel(new GridLayout(noOfUsers,1));
					
						qOrderList.setBorder(new EtchedBorder());	
						qOrderList.setLayout(new BoxLayout(qOrderList, BoxLayout.PAGE_AXIS));
						for(JLabel label: queueUsers) {
							
							qOrderList.add(label);
						}
							
					
					qOrderPanel.add(jl4, JPanel.TOP_ALIGNMENT);
					qOrderPanel.add(qOrderList);
						
				queuePanel.add(jl);	
				queuePanel.add(qOrderPanel);
				jp.add(queuePanel);
		}
		//CREATES VOTING BUTTONS
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
		//CREATES VOTING MENU
		private void makeVotingMenu() {
			votePanel = new JPanel();
			votePanel.setBorder(new EtchedBorder());
			votePanel.setLayout(new BoxLayout(votePanel, BoxLayout.PAGE_AXIS));
			
			jl2 = new JLabel("Rösta på: ");
			jl2.setBorder(new EtchedBorder());	
			
			JPanel subHeadPanel = new JPanel();
			subHeadPanel.setBorder(new EtchedBorder());
			subHeadPanel.setLayout(new BoxLayout(subHeadPanel, BoxLayout.PAGE_AXIS));
			
			votingPanel = new JPanel(new GridLayout(1,5));
			votingPanel.setBorder(new EtchedBorder());
			makeVotingButtons(5);
				
			subHeadPanel.add(new JLabel(group.getV().getCurrent().getID()+" fika"));	
			subHeadPanel.add(votingPanel);
					
			votePanel.add(jl2);
			votePanel.add(subHeadPanel);
			votePanel.add(new JPanel());
			jp.add(votePanel);
			
		}
		//MAKES MENUBAR
		private void makeMenuBar() {
			
			fileMenu = new JMenu("Menu");
			
			nextFika = new JMenuItem("Nästa fika");
			fileMenu.add(nextFika);
			nextFika.addActionListener(e->group.nextFika());
			nextFika.addActionListener(e->group.nextFika());
			
			quit = new JMenuItem("Quit");
			fileMenu.add(quit);
			quit.addActionListener(e->quitApp());
			
			addRequest = new JMenuItem("Lägg till önskemål");
			fileMenu.add(addRequest);
			
			
			helpMenu = new JMenu("Help");
			help = new JMenuItem("Help");
			helpMenu.add(help);
		
			menubar = new JMenuBar();
			menubar.add(fileMenu);
			menubar.add(helpMenu);
			setJMenuBar(menubar);
		}
		private void quitApp() {
			System.exit(0);
		}
		
		//Changes the labels of Highscore and Queue for the new group
		public void setNewGroup(Group g){
			for(int i = 0 ; i < g.getUsers().size() ; i++){
				queueUsers.get(i).setText(g.getUsers().get(i).getID());
			}
			
			
			g.getFikaScore().sort();
			for(int i = 0 ; i < highScore.size() ; i++){
				highScore.get(i).setText(Integer.toString(g.getFikaScore().getUsers().get(i).getRating()));
				userScore.get(i).setText(g.getFikaScore().getUsers().get(i).getID());
			}
			

			
		}
		
}