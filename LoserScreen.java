import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LoserScreen extends JPanel{
	private UserMouseListener listen;

	public LoserScreen(Dimension dimension, int score) {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBackground(Color.black);
		this.setForeground(Color.white);
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 30);
		
		JButton button = new JButton("Click to exit");
		button.setFont(font);
		button.setForeground(Color.white);
		button.setBackground(Color.black);
		button.setBorder(BorderFactory.createLineBorder(Color.black,5));
		listen = new UserMouseListener();
		button.addMouseListener(listen);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel label = new JLabel("You lost with a score of " +score);
		label.setFont(font);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBackground(Color.black);
		label.setForeground(Color.white);
		
		this.add(Box.createVerticalGlue());
		this.add(label);
		this.add(button);
		this.add(Box.createVerticalGlue());
	}
	public boolean isDone() {
		return listen.wasClicked();
	}
	

}
