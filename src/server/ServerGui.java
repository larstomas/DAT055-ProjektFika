package server;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ServerGui extends JFrame{
	Server s;
	JButton exit;
	JButton next;
	
	
	public ServerGui(Server serv){
		this.s = serv;
		this.makeFrame();
		
	}


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
