import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class LevelOneBullet extends JComponent implements Bullet{
	private Image img;
	private int dy;
	public LevelOneBullet(int x, int y, boolean isEnemy) {
		BufferedImage img = null;
		try {
			if(isEnemy)
				img = ImageIO.read(new File("EnemyLaserShot.png"));
			else
				img = ImageIO.read(new File("UserLaserShot.png"));
		} catch (IOException e) {
			if(isEnemy)
				System.out.println("Error Reading \"EnemyLaserShot.png\"");
			else
				System.out.println("Error Reading \"UserLaserShot.png\"");
		}
		this.img = img;
		super.setLocation(x, y);
		this.setVisible(true);
		this.setBounds(x, y, 16, 30);
		dy = isEnemy ? 3 : -3;
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, super.getX(), super.getY(), 16, 30, null);
		//super.setLocation(super.getX(), super.getY() + dy);
	}
	
	public void refresh() {		
		this.setBounds(super.getX(), super.getY()+dy, 16, 30);
	}
	
	public String toString() {
		return "LevelOneBullet: @" + super.getX() + ", " +super.getY();
	}
}
