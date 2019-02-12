
public class main {
	
	public static void main(String[] args) {
		Group gr = new Group();	
		Server s = new Server(gr);
		s.load();
		
		login log = new login(gr);
		while(!log.getLoggedIn()) {
			log.checklogin();
		}
		Gui g = new Gui(gr,log.getLogin());
		g.makeFrame();
		
	}
}
