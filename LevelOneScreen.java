import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LevelOneScreen extends JPanel {
	public static ArrayList<LevelOneBullet> enemyBullets;
	private ArrayList<LevelOneEnemy> enemies;
	private ArrayList<ColorPoint> stars;
	private boolean direction;
	private Dimension dimension;
	private User user;
	private int score;
	private boolean isDone;

	public LevelOneScreen(Dimension dimension) {
		this.dimension = dimension;
		isDone = false;
		stars = new ArrayList<ColorPoint>();
		enemies = new ArrayList<LevelOneEnemy>();
		enemyBullets = new ArrayList<LevelOneBullet>();
		direction = true;
		this.setLayout(null);
		setBackground(Color.BLACK);
		createStars();
		createEnemies();
		createUser();
		user.requestFocusInWindow();
		user.requestFocus();
		score = 0;
		this.setSize(dimension);
		this.setVisible(true);
	}

	private void createUser() {
		user = new User((int) (dimension.getWidth() / 2), (int) (dimension.getHeight() - 100));
		user.setVisible(true);
		this.add(user);
	}

	private void createStars() {
		Random rand = new Random();
		for (int i = 0; i < 100; i++) {
			if (i % 2 == 0)
				stars.add(new ColorPoint(rand.nextInt(800), rand.nextInt(1000), Color.red, rand.nextInt(1000) + 2000));
			else
				stars.add(
						new ColorPoint(rand.nextInt(800), rand.nextInt(1000), Color.green, rand.nextInt(1000) + 2000));
			
		}
	}
	
	private void createEnemies() {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("Enemy1.png"));
		} catch (IOException e) {
			System.out.println("Error Reading \"Enemy1.png\"");
		}
		// dimension.width
		for (int i = 0; i < 15; i++) {
			enemies.add(new LevelOneEnemy(i * 40 + 5, 30, img));
			enemies.get(i).setVisible(true);
		}
		for (LevelOneEnemy e : enemies) {
			this.add(e);
		}
	}

	private void paintHeader(Graphics g) {
		// Creating new font to then create the header
		// - drawn last to be overlaid

		// draws the scoreboard
		g.setColor(Color.red);
		Font font = new Font("Serif", 10, 20);
		g.setFont(font);
		String displayScore = "Score: " + score;
		g.drawString(displayScore, 10, 20);

		// draws the health
		g.setColor(Color.green);
		g.drawString("Health: ", (int) (this.getWidth() / 3.0), 20);
		g.fillRect((int) (this.getWidth() / 3.0) + 65, 6, (int) (100 * user.healthPercent()), 15);

		// draws the shooting stamina
		g.setColor(Color.cyan);
		g.drawString("Stamina: ", (int) (this.getWidth() / 3.0 * 2), 20);
		g.fillRect((int) (this.getWidth() / 3.0 * 2) + 80, 6, (int) (100 * user.staminaPercent()), 15);

	}

	public void paintComponent(Graphics g) {
		this.requestFocusInWindow();
		super.paintComponent(g); // need to do

		// paint stars
		for (ColorPoint p : stars) {
			g.setColor(p.getColor());
			g.fillOval(p.x, p.y, 2, 2);
		}

		// paint the user
		user.paintComponent(g);

		// paint the enemies
		for (LevelOneEnemy e : enemies) {
			e.paintComponent(g);
		}

		// paint the bullets
		for(LevelOneBullet b : enemyBullets) {
			b.paintComponent(g);
		}
		
		// paint the header
		paintHeader(g); 

	}

	public boolean isDone() {
		return isDone;
	}

	public boolean isDead() {
		return user.healthPercent() < .1;
	}

	public int getScore() {
		return score;
	}

	public void refresh() {
		// refreshes the enemies
		for (LevelOneEnemy e : enemies) {
			e.refresh(direction);
		}
		if (enemies.get(enemies.size() - 1).getX() + 45 > dimension.getWidth() && direction) {
			direction = false;
		} else if (enemies.get(0).getX() < 5 && !direction) {
			direction = true;
		}
		
		// checks for collisions
		if (!enemies.isEmpty()) {
			if (!user.getBullets().isEmpty()) {
				for (int i = enemies.size() - 1; i >= 0; i--) {
					for (int j = user.getBullets().size() - 1; j >= 0; j--) {
						if (enemies.get(i).getBounds().intersects(user.getBullets().get(j).getBounds())) {
							this.remove(enemies.get(i));
							enemies.remove(i);
							user.getBullets().remove(j);
							score += 100;
							// To prevent ArrayOutOfBoundsException when
							// Enemies are destroyed faster than they're removed
							if (enemies.size() == 0)
								break;
						}
					}
				}
			}
			if (!enemies.isEmpty()) {
				for (int i = enemies.size() - 1; i >= 0; i--) {
					if (enemies.get(i).getBounds().intersects(user.getBounds())) {
						enemies.get(i).setLocation(0, getParent().getHeight() + 100);
						this.remove(enemies.get(i));
						enemies.remove(i);
						user.decrementHealth();
						score += 100;
					}
				}
			}
		}
		if (!enemyBullets.isEmpty()) {
			for (int i = enemyBullets.size() - 1; i >= 0; i--) {
				enemyBullets.get(i).refresh();
				if (enemyBullets.get(i).getY() > getParent().getHeight() + 50) {
					enemyBullets.remove(i);
				} else if (enemyBullets.get(i).getBounds().intersects(user.getBounds())) {
					enemyBullets.remove(i);
					user.decrementHealth();
				}
			}
		}
		// refreshes the user
		user.refresh();
		
		// Check whether all enemies are eliminated
		if (!isDone && enemies.isEmpty())
			isDone = true;
	}

	public String toString() {
		String s = "Level One Screen\n";
		for (int i = 0; i < this.getComponentCount(); i++) {
			s = s + this.getComponent(i) + "\n";
		}
		return s;
	}

	public double getHealth() {
		return user.healthPercent();
	}
}
