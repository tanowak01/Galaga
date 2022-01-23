import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UserMouseListener implements MouseListener {
	private boolean ready;
	public UserMouseListener() {
		ready = false;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("Clicked");
		ready = true;
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
	
	public boolean wasClicked() {
		return ready;
	}

}
