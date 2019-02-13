
public class Main {
	
	public static void main(String[] args) {
		Group gr = new Group();	
		Server s = new Server(gr);
		s.load();
		
		Login log = new Login(gr);
		while(!log.getLoggedIn()) {
			log.checklogin();
		}
		Gui g = new Gui(gr,log.getLogin());
		g.makeFrame();
		
	}
}
//Nej du 