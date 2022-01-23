import java.awt.Graphics;

public interface Enemy {
	public abstract void refresh(boolean direction);
	public abstract void paintComponent(Graphics g);
}
