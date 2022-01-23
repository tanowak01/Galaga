import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class User extends JComponent {
	private Image im;
	private double health;
	private double initialHealth;
	private double healthDecrement;
	private double stamina;
	private int staminaDecrement;
	private long lastBulletFired;
	private ArrayList<LevelOneBullet> bullets;

	public User(int x, int y, double health) {
		BufferedImage img = null;
		try {	
			img = ImageIO.read(new File("UserShip.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		bullets = new ArrayList<LevelOneBullet>();
		super.setLocation(x, y);
		super.setBounds(x, y, 50, 50);
		this.addKeyListener(new UserKeyboardListener());
		this.im = img;
		this.health = 100 * health;
		this.initialHealth = 100;
		this.healthDecrement = 100/5.0;
		this.stamina = 100;
		this.staminaDecrement = 10;
		this.setFocusable(true);
	}

	public User(int x, int y) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("UserShip.png"));
		} catch (IOException e) {
			System.out.println("Error Reading \"UserShip.png\"");
		}
		bullets = new ArrayList<LevelOneBullet>();
		super.setLocation(x, y);
		super.setBounds(x, y, 50, 50);
		this.addKeyListener(new UserKeyboardListener());
		this.im = img;
		this.health = 100;
		this.initialHealth = 100;
		this.healthDecrement = health/5.0;
		this.stamina = 100;
		this.staminaDecrement = 10;
		this.setFocusable(true);
	}

	public void paintComponent(Graphics g) {
		this.requestFocus();
		this.requestFocusInWindow();
		g.drawImage(im, super.getX(), super.getY(), this.getWidth(), this.getHeight(), null);

		for (LevelOneBullet b : bullets) {
			b.paintComponent(g);
		}
	}
	
	public double healthPercent() {
		return health/initialHealth;
	}
	
	public void decrementHealth() {
		health -= healthDecrement;
	}
	
	public double staminaPercent() {
		return stamina / 100.0;
	}
	
	public void decrementStamina() {
		stamina -= staminaDecrement;
	}

	public String toString() {
		return "User: " + "(" + super.getX() + ", " + super.getY() + ")";
	}

	public ArrayList<LevelOneBullet> getBullets() {
		return bullets;
	}
	
	public void refresh() {
		if (this.getKeyListeners().length > 0 &&
				this.getKeyListeners()[0] instanceof UserKeyboardListener) {
			UserKeyboardListener listen = (UserKeyboardListener) this.getKeyListeners()[0];
			if(listen.getdx() != 0) {
				if(this.getX()+listen.getdx() + this.getWidth() < this.getParent().getWidth() &&
						this.getX()+listen.getdx() > 5)
					this.setLocation(this.getX() + listen.getdx(), this.getY());
				if(this.getX() < 10) {
					this.setLocation(10, this.getY());
				}
				else if(this.getX()+this.getWidth() > this.getParent().getWidth() - 10)
					this.setLocation(this.getParent().getWidth()-10-this.getWidth(), this.getY());
			}
			if(listen.getdy() != 0) {
				if(this.getY() + listen.getdy() > 30 && 
						this.getY()+this.getHeight()+listen.getdy() < this.getParent().getHeight()-10)
					this.setLocation(this.getX(), this.getY()+listen.getdy());
				if(this.getY() < 30) {
					this.setLocation(30, this.getY());
				}
				else if(this.getY()+this.getHeight() > this.getParent().getHeight()-10)
					this.setLocation(this.getX(), this.getParent().getHeight()-10-this.getHeight());
			}
			if (listen.getShoot() > 0 && stamina > 10) {
				bullets.add(new LevelOneBullet(super.getX() + 17, super.getY() - 5, false));
				decrementStamina();
				listen.decrementShoot();
				lastBulletFired = System.currentTimeMillis();
			}
		}
		for (int i = bullets.size() - 1; i >= 0; i--) {
			bullets.get(i).refresh();
			if (bullets.get(i).getY() < -50)
				bullets.remove(i);
		}
		if(System.currentTimeMillis() - lastBulletFired > 1000 && stamina < 100) {
			stamina += .5;
		}
	}
}
