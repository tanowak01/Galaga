import java.awt.Color;
import java.awt.Point;

@SuppressWarnings("serial")
public class ColorPoint extends Point {
	private Color color;
	private long time;
	private int blinkSeed;
	public ColorPoint(int x, int y, Color color, int seed) {
		super();
		super.x = x;
		super.y = y;
		this.color = color;
		time = System.currentTimeMillis();
		blinkSeed = seed;
	}
	public Color getColor() {
		//System.out.println((System.currentTimeMillis()-time) % blinkSeed);
		//Changes whether the star is blinking or not
		if(((System.currentTimeMillis()-time) % blinkSeed) < (blinkSeed/2))
			return color;
		return Color.black;
	}
	
}
