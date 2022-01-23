import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class LevelTwoBullet extends JComponent implements Bullet{
	private BufferedImage img;
	private int dy;
	private int dx;
	public LevelTwoBullet(int x, int y) {
		BufferedImage img = null;
		try {
				img = ImageIO.read(new File("EnemyLaserShot2.png"));
		} catch (IOException e) {
			System.out.println("Error Reading \"EnemyLaserShot2.png\"");
		}
		this.img = img;
		super.setLocation(x, y);
		this.setVisible(true);
		this.setBounds(x, y, 16, 30);
		dy = 1;
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, super.getX(), super.getY(), 16, 30, null);
	}
	
	public void refresh() {		
		this.setBounds(super.getX()+dx, super.getY()+dy, 16, 30);
	}
	
	public void setDx(int dx) {
		this.dx = dx;
	}
	public String toString() {
		return "LevelTwoBullet: @" + super.getX() + ", " +super.getY();
	}
}
