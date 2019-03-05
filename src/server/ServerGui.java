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

		next = new JButton("Next");
		next.addActionListener(e->this.s.getGroup().nextFika());


		this.add(next);
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.setVisible(true);
		this.pack();
	}

}
