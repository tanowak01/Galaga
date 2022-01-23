import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class UserKeyboardListener implements KeyListener {
	private int dx, dy;
	private int shoot;
	private int speed;
	private ArrayList<Integer> keysPressed;

	public UserKeyboardListener() {
		keysPressed = new ArrayList<Integer>();
		shoot = 0;
		speed = 1;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public int getdx() {
		return dx;
	}

	public int getdy() {
		return dy;
	}

	public int getShoot() {
		return shoot;
	}

	public boolean decrementShoot() {
		if (shoot - 1 < 0) {
			return false;
		} else {
			shoot = shoot - 1;
			return true;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_SPACE) {
		} else {
			if (!keysPressed.contains(key))
				keysPressed.add(key);
			if(keysPressed.contains(KeyEvent.VK_C))
				speed = 8;
			else
				speed = 4;
			if (keysPressed.contains(KeyEvent.VK_RIGHT) && keysPressed.contains(KeyEvent.VK_LEFT)) {
				dx = 0;
			} else if (keysPressed.contains(KeyEvent.VK_RIGHT)) {
				dx = 1*speed;
			} else if (keysPressed.contains(KeyEvent.VK_LEFT)) {
				dx = -1*speed;
			} else {
				dx = 0;
			}
			if (keysPressed.contains(KeyEvent.VK_UP) && keysPressed.contains(KeyEvent.VK_DOWN)) {
				dy = 0;
			} else if (keysPressed.contains(KeyEvent.VK_UP)) {
				dy = -1*speed;
			} else if (keysPressed.contains(KeyEvent.VK_DOWN)) {
				dy = 1*speed;
			} else {
				dy = 0;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_SPACE) {
			shoot++;
		} else {
			if (keysPressed.contains(key))
				keysPressed.remove(keysPressed.indexOf(key));
			if(keysPressed.contains(KeyEvent.VK_C))
				speed = 2;
			else
				speed = 1;
			if (keysPressed.contains(KeyEvent.VK_RIGHT) && keysPressed.contains(KeyEvent.VK_LEFT)) {
				dx = 0;
			} else if (keysPressed.contains(KeyEvent.VK_RIGHT)) {
				dx = 1*speed;
			} else if (keysPressed.contains(KeyEvent.VK_LEFT)) {
				dx = -1*speed;
			} else {
				dx = 0;
			}
			if (keysPressed.contains(KeyEvent.VK_UP) && keysPressed.contains(KeyEvent.VK_DOWN)) {
				dy = 0;
			} else if (keysPressed.contains(KeyEvent.VK_UP)) {
				dy = -1*speed;
			} else if (keysPressed.contains(KeyEvent.VK_DOWN)) {
				dy = 1*speed;
			} else {
				dy = 0;
			}
		}
	}

	public String toString() {
		return "UserKeyListener: (" + dx + ", " + dy + ")";
	}
}
