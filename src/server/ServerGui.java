package server;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Render a GUI for the server
 * 
 * @author Fika experts
 *
 */

public class ServerGui extends JFrame{
	private Server s;
	private JButton exit;
	private JButton next;
	
	/**
	 * GUI constructor
	 * @param Fika experts
	 */
	public ServerGui(Server serv){
		this.s = serv;
		this.makeFrame();
		
	}

	/**
	 * Initiate server GUI
	 */
	private void makeFrame() {
		exit = new JButton("Exit");
		next = new JButton("Next");
		//exit.addActionListener(e->);
		next.addActionListener(e->this.s.getGroup().nextFika());
		
		this.add(exit);
		this.add(next);
		this.setVisible(true);
		this.pack();
	}
	
}
