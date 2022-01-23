import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class LevelTwoEnemy extends JComponent implements Enemy{
	private Image im;
	private int health;
	private int shootSeed;
	private long time;
	private long lastTimeFired;
	public LevelTwoEnemy(int x, int y, Image im, int health) {
		Random rand = new Random();
		super.setBounds(x, y, 30, 30);
		super.setLocation(x, y);
		this.im = im;
		this.health = health;
		shootSeed = rand.nextInt(1000)+5000;
		time = System.currentTimeMillis();
		lastTimeFired = 0;
	}

	public LevelTwoEnemy(int x, int y, Image im) {
		Random rand = new Random();
		super.setBounds(x, y, 30, 30);
		super.setLocation(x, y);
		this.im = im;
		this.health = 100;
		shootSeed = rand.nextInt(1000)+3000;
		time = System.currentTimeMillis();
		lastTimeFired = 0;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if((System.currentTimeMillis()-time) % shootSeed < (shootSeed/30) &&
				System.currentTimeMillis() - lastTimeFired > 5000) {
			LevelTwoScreen.enemyBullets.add(new LevelTwoBullet(this.getX()+15, this.getY()+10));
			lastTimeFired = System.currentTimeMillis();
		}
		g.drawImage(im, super.getX(), super.getY(), 30, 30, null);
	}
	
	public int getHealth() {
		return health;
	}

	public String toString() {
		return "LevelTwoEnemy @(" + this.getX() + ", " + this.getY() + ")";
	}

	public void refresh(boolean direction) {
		if (!direction)
			setLocation(getX() - 1, getY());
		else
			setLocation(getX() + 1, getY());		
	}
}
