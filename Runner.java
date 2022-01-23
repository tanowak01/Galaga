import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Runner {
	public final static int SCREENHEIGHT = 1000;
	public final static int SCREENWIDTH = 800;
	private static GameFrame frame;
	public static void main(String[] args) {
		frame = new GameFrame(SCREENWIDTH, SCREENHEIGHT);
		FrameRateListener listen = new FrameRateListener();
		Timer timer = new Timer(10, listen);
		timer.start();
	}
	private static class FrameRateListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.display();
		}
		
	}
}
