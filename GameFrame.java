import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	private Dimension dimension;
	private final int WIDTH, HEIGHT;
	private JPanel screen;
	private int score;
	private double health;
	public GameFrame(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
		dimension = new Dimension(WIDTH, HEIGHT);
		this.setPreferredSize(dimension);
		this.setResizable(false);
		this.setMinimumSize(dimension);
		this.setMaximumSize(dimension);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setTitle("Galaga");
		this.setBackground(Color.black);
		this.setForeground(Color.white);
		screen = new MenuScreen(dimension);
		score = 0;
		this.getContentPane().add(screen);
		screen.requestFocus();
		screen.requestFocusInWindow();
	}

	public void display() {
		this.pack();
		this.setVisible(true);
		this.repaint();
		if ((screen instanceof MenuScreen) && ((MenuScreen) screen).isDone()) {
			this.getContentPane().removeAll();
			screen = new LevelOneScreen(dimension);
			this.getContentPane().add(screen);
		} 
		else if ((screen instanceof MenuScreen) && !((MenuScreen) screen).isDone()) {
			((MenuScreen) screen).refresh();
		}
		else if (screen instanceof LevelOneScreen) {
			if (((LevelOneScreen) screen).isDone() && !((LevelOneScreen) screen).isDead()) {
				health = ((LevelOneScreen) screen).getHealth();
				this.getContentPane().removeAll();
				screen = new LevelTwoScreen(dimension, health);
				this.getContentPane().add(screen);
				score = 1500;
			} 
			else if (!((LevelOneScreen) screen).isDone() && !((LevelOneScreen) screen).isDead()) {
				((LevelOneScreen) screen).refresh();
			}
			else if (((LevelOneScreen) screen).isDead()) {
				score = ((LevelOneScreen) screen).getScore();
				this.getContentPane().removeAll();
				screen = new LoserScreen(dimension, score);
				this.getContentPane().add(screen);
			}
		} else if (screen instanceof LevelTwoScreen) {
			if (((LevelTwoScreen) screen).isDone()) {
				this.getContentPane().removeAll();
				screen = new WinnerScreen(dimension);
				this.getContentPane().add(screen);
			} 
			else if (!((LevelTwoScreen) screen).isDone() && !((LevelTwoScreen) screen).isDead()) {
				((LevelTwoScreen) screen).refresh();
			}else if (((LevelTwoScreen) screen).isDead()) {
				score = ((LevelTwoScreen) screen).getScore();
				this.getContentPane().removeAll();
				screen = new LoserScreen(dimension, score);
				this.getContentPane().add(screen);
			}
		} else if (screen instanceof WinnerScreen && ((WinnerScreen) screen).isDone()) {
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		} else if (screen instanceof LoserScreen && ((LoserScreen) screen).isDone()) {
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}	
	}
}
