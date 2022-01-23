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
import javax.swing.JComponent;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LevelTwoScreen extends JPanel {
	public static ArrayList<JComponent> enemyBullets;
	private ArrayList<JComponent> enemies;
	private ArrayList<ColorPoint> stars;
	private boolean direction;
	private Dimension dimension;
	private User user;
	private int score;
	private boolean isDone;

	public LevelTwoScreen(Dimension dimension, double health) {
		this.dimension = dimension;
		isDone = false;
		stars = new ArrayList<ColorPoint>();
		enemies = new ArrayList<JComponent>();
		enemyBullets = new ArrayList<JComponent>();
		direction = true;
		this.setLayout(null);
		setBackground(Color.BLACK);
		createStars();
		createEnemies();
		createUser(health);
		// if they beat first level then they are at 1500 points
		score = 1500;	
		this.setSize(dimension);
		this.setVisible(true);
	}

	private void createUser(double health) {
		user = new User((int) (dimension.getWidth() / 2), (int) (dimension.getHeight() - 100), health);
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
		BufferedImage img1 = null;
		try {
			img1 = ImageIO.read(new File("Enemy1.png"));
		} catch (IOException e) {
			System.out.println("Error Reading \"Enemy1.png\"");
		}
		BufferedImage img2 = null;
		try {
			img2 = ImageIO.read(new File("Enemy2.png"));
		} catch (IOException e) {
			System.out.println("Error Reading \"Enemy2.png\"");
		}
		for (int i = 0; i < 15; i++) {
			if (i % 3 == 0) {
				enemies.add(new LevelTwoEnemy(i * 40 + 5, 30, img2));
			} else {
				enemies.add(new LevelOneEnemy(i * 40 + 5, 30, img1, false));
			}
			enemies.get(i).setVisible(true);
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
		for (JComponent e : enemies) {
			((Enemy)e).paintComponent(g);
		}
		
		// paint the bullets
		for(JComponent b : enemyBullets) {
			((Bullet)b).paintComponent(g);
		}
		
		//paint the header
		paintHeader(g);

	}

	public boolean isDone() {
		return isDone;
	}
	
	public boolean isDead() {
		return user.healthPercent() < 0.1;
	}
	
	public int getScore() {
		return score;
	}
	
	public void refresh() {
		for (JComponent e : enemies) {
			((Enemy) e).refresh(direction);
		}
		if (enemies.get(enemies.size() - 1).getX() + 45 > dimension.getWidth() && direction) {
			direction = false;
		} else if (enemies.get(0).getX() < 5 && !direction) {
			direction = true;
		}
		
		// checks for collisions
		if (!enemies.isEmpty()) {
//			paintEnemies(g);
			// check for user bullet collision
			if (!user.getBullets().isEmpty()) {
				for (int i = enemies.size() - 1; i >= 0; i--) {
					for (int j = user.getBullets().size() - 1; j >= 0; j--) {
						if (enemies.get(i).getBounds().intersects(user.getBullets().get(j).getBounds())) {
							this.remove(enemies.get(i));
							if(enemies.get(i) instanceof LevelOneEnemy)
								score += 100;
							else if(enemies.get(i) instanceof LevelTwoEnemy)
								score += 200;
							enemies.remove(i);
							user.getBullets().remove(j);
							// To prevent ArrayOutOfBoundsException when
							// Enemies are destroyed faster than they're removed
							if (enemies.size() == 0)
								break;
						}
					}
				}
			}
			// check for user collision
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
			// check for enemy bullet collision
			if (!enemyBullets.isEmpty()) {
				//System.out.println(enemyBullets);
				for (int i = enemyBullets.size() - 1; i >= 0; i--) {
					((Bullet)enemyBullets.get(i)).refresh();
					if (enemyBullets.get(i) instanceof LevelTwoBullet) {
						if (enemyBullets.get(i).getX() < user.getX()+20) {
							((LevelTwoBullet) enemyBullets.get(i)).setDx(1);
						} else if (enemyBullets.get(i).getX() > user.getX()) {
							((LevelTwoBullet) enemyBullets.get(i)).setDx(-1);
						} else {
							((LevelTwoBullet) enemyBullets.get(i)).setDx(0);
						}
					}
					if (enemyBullets.get(i).getY() > getParent().getHeight() + 50) {
						enemyBullets.remove(i);
					}  else if (enemyBullets.get(i).getBounds().intersects(user.getBounds())) {
						enemyBullets.remove(i);
						user.decrementHealth();
					}
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
		String s = "Level Two Screen\n";
		for (int i = 0; i < this.getComponentCount(); i++) {
			s = s + this.getComponent(i) + "\n";
		}
		return s;
	}
}
