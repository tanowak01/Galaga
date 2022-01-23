import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class MenuScreen extends JPanel{
	private UserMouseListener listen;
	private DummyPanel moving;
	public MenuScreen(Dimension dimension) {
		this.setSize(dimension);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(Color.black);
		this.setForeground(Color.white);
		
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN,15);
		Font headerFont = new Font(Font.SANS_SERIF, Font.PLAIN, 30);
		Dimension compDimension = new Dimension((dimension.width), (dimension.height-100)/3);

		//Creating the start button
		listen = new UserMouseListener();
		JButton button = new JButton("Start Galaga");
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setFont(font);
		button.addMouseListener(listen);
		button.setBackground(Color.black);
		button.setForeground(Color.white);
	    button.setBorder(BorderFactory.createLineBorder(Color.black,5));
		button.setFont(font);
		button.setPreferredSize(compDimension);

		//Importing from file the table
		Scanner scan;
		try {
			scan = new Scanner(new File("GalagaDirections.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Error Reading \"GalagaDirections.txt\"");
			System.out.println("Please make sure it is in the current directory");
			scan = null;
		}
		String totalString = scan.nextLine();
		String[] split = totalString.split(",");
		String[] columnNames = {"Action", "Control"};
		String[][] rowData = {columnNames,{split[1],split[2]},{split[3],split[4]},{split[5],split[6]}};

		//Creating the table of directions
		JTable table = new JTable(rowData, columnNames);
		table.setBackground(Color.black);
		table.setForeground(Color.white);//sets the font color
		table.setGridColor(Color.white);
		table.setFont(font);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(1).setMinWidth(500);
		table.setBorder(BorderFactory.createLineBorder(Color.white,1));
		
		//Creating the Header
		JLabel header = new JLabel("Galaga");
		header.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		header.setBackground(Color.black);
		header.setForeground(Color.white);
		header.setFont(headerFont);
		
		JPanel center = new JPanel();
		center.setBackground(Color.black);
		center.setForeground(Color.white);
		center.setLayout(new BoxLayout(center, BoxLayout.PAGE_AXIS));
		center.setFont(font);
		JLabel tableHeader = new JLabel(split[0]);
		tableHeader.setBackground(Color.black);
		tableHeader.setForeground(Color.white);
		tableHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
		tableHeader.setFont(font);
		
		center.add(tableHeader);
		center.add(table);
		center.setPreferredSize(compDimension);
		moving = new DummyPanel(this.getWidth());
		moving.setBackground(Color.black);
		moving.setPreferredSize(compDimension);
		
		this.add(header);
		this.add(center);
		this.add(moving);
		this.add(button);
		this.setVisible(true);

	}
	public boolean isDone() {
		return listen.wasClicked();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		moving.paintComponent(g);
	}
	
	public void refresh() {
		moving.refresh();
	}
	
	private class DummyPanel extends JPanel{
		private DummyUser user;
		public DummyPanel(int width){
			this.setLayout(null);
			user = new DummyUser(width);
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			user.paintComponent(g);
		}
		public void refresh() {
			user.refresh();
		}
	}

	
	private class DummyUser extends Component{
		private Image im;
		private int panelWidth;
		private boolean direction;
		public DummyUser(int panelWidth) {
			BufferedImage img = null;
			try {
				img = ImageIO.read(new File("UserShip.png"));
			} catch (IOException e) {
				System.out.println("Error Reading \"Enemy 1.png\"");
			}
			super.setBounds(25,50,800,50);
			this.im = img;
			direction = true;
			this.panelWidth = panelWidth;
		}
		public void paintComponent(Graphics g) {
			g.drawImage(im, super.getX(), super.getY(), 50, 50, null);
		}
		public void refresh() {
			if(!direction) {
				super.setLocation(super.getX()-1,super.getY());
			}
			else {
				super.setLocation(super.getX()+1,super.getY());
			}
			if(super.getX() < 15)
				direction = true;
			else if(super.getX() + 50 > panelWidth - 25)
				direction = false;
		}
	}
}
