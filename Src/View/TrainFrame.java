package View;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class TrainFrame extends JFrame{
	/**********************CONSTANTS DECLARATION**********************/
	static final private int DEFAULT_WIDTH = 800;
	static final private int DEFAULT_HEIGHT = 600;
	
	/**********************PUBLIC METHODS DECLARATION**********************/
	public TrainFrame(String title, int width, int height) {
		super(title);
		setSize(width, height);
	}
	
	public TrainFrame(String title) {
		this(title,DEFAULT_WIDTH,DEFAULT_HEIGHT);
	}
	
	public void setMidScreen() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		Point p = new Point(d.width/2 - getWidth()/2,d.height/2 - getHeight()/2);
		this.setLocation(p);
	}
}
