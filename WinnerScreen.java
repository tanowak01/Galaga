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
public class WinnerScreen extends JPanel{
	private int score;
	private UserMouseListener listen;
	public WinnerScreen(Dimension dimension) {
		score = 3500;
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBackground(Color.black);
		this.setForeground(Color.white);
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 30);
		
		JLabel winner = new JLabel("Winner!");
		winner.setFont(font);
		winner.setForeground(Color.white);
		winner.setBackground(Color.black);
		winner.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		winner.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel center = new JLabel("You won with a score of " + score + "!");
		center.setFont(font);
		center.setForeground(Color.white);
		center.setBackground(Color.black);
		center.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		center.setHorizontalAlignment(JLabel.CENTER);
		
		JButton button = new JButton("Click to exit");
		button.setFont(font);
		button.setForeground(Color.white);
		button.setBackground(Color.black);
		button.setBorder(BorderFactory.createLineBorder(Color.black,5));
		listen = new UserMouseListener();
		button.addMouseListener(listen);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.add(Box.createVerticalGlue());
		this.add(winner);
		this.add(center);
		this.add(button);
		this.add(Box.createVerticalGlue());
	}
	
	public boolean isDone() {
		return listen.wasClicked();
	}
}
