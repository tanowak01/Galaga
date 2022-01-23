import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class LevelOneEnemy extends JComponent implements Enemy{
	private Image im;
	private int health;
	private int shootSeed;
	private long time;
	private long lastTimeFired;
	private boolean isLevelOne;
	public LevelOneEnemy(int x, int y, Image im, int health) {
		Random rand = new Random();
		super.setBounds(x, y, 30, 30);
		super.setLocation(x, y);
		this.im = im;
		this.health = health;
		shootSeed = rand.nextInt(1000)+5000;
		time = System.currentTimeMillis();
		lastTimeFired = 0;
		isLevelOne = true;
	}

	public LevelOneEnemy(int x, int y, Image im) {
		Random rand = new Random();
		super.setBounds(x, y, 30, 30);
		super.setLocation(x, y);
		this.im = im;
		this.health = 100;
		shootSeed = rand.nextInt(1000)+6000;
		time = System.currentTimeMillis();
		lastTimeFired = 0;
		isLevelOne = true;
	}
	public LevelOneEnemy(int x, int y, Image im, boolean isLevelOne) {
		Random rand = new Random();
		super.setBounds(x, y, 30, 30);
		super.setLocation(x, y);
		this.im = im;
		this.health = 100;
		shootSeed = rand.nextInt(1000)+6000;
		time = System.currentTimeMillis();
		lastTimeFired = 0;
		this.isLevelOne = isLevelOne;
	}

	public void paintComponent(Graphics g) {
		if((System.currentTimeMillis()-time) % shootSeed < (shootSeed/30) &&
				System.currentTimeMillis() - lastTimeFired > 5000) {
			if(isLevelOne)
				LevelOneScreen.enemyBullets.add(new LevelOneBullet(this.getX()+15, this.getY()+10, true));
			else
				LevelTwoScreen.enemyBullets.add(new LevelOneBullet(this.getX()+15, this.getY()+10, true));
			lastTimeFired = System.currentTimeMillis();
		}
		g.drawImage(im, super.getX(), super.getY(), 30, 30, null);
	}
	
	public void refresh(boolean direction) {
		if (!direction)
			setLocation(getX() - 1, getY());
		else
			setLocation(getX() + 1, getY());
	}

	public int getHealth() {
		return health;
	}

	public String toString() {
		return "LevelOneEnemy @(" + this.getX() + ", " + this.getY() + ")";
	}
}
