package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Model.Semaphore;
import Model.SemaphoreManager;
import Model.Train;
import Model.TrainManager;
import Model.TrainModel;

@SuppressWarnings("serial")
public class TrainPanel extends JPanel implements Observer{
	/**********************PRIVATE VARIABLE DECLARATION**********************/
	private BufferedImage image;
	private TrainModel trainModel;
	
	/**********************PRIVATE METHODS DECLARATION**********************/
	private boolean hasTrainManager() {
		return (trainModel.getTrainManager() != null);
	}
	
	private boolean hasSemaphoreManager() {
		return (trainModel.getSemaphoreManager() != null);
	}
	
	private void drawTrain(Graphics g, Train train) {
		Color defaultColor = g.getColor();
		Point position = train.getPosition();
		g.setColor(train.getColor());
		g.fillOval(position.x - train.getWidth()/2, position.y - train.getHeight()/2, 
				train.getWidth(), train.getHeight());
		g.setColor(defaultColor);
	}
	
	private void drawSemaphore(Graphics g, Semaphore semaphore) {
		Color defaultColor = g.getColor();
		Point position = semaphore.getPosition();
		g.setColor(Color.BLACK);
		g.fillRect(position.x - semaphore.getWidth()/2, position.y - semaphore.getHeight()/2, 
				semaphore.getWidth(), semaphore.getHeight());
		g.setColor(semaphore.getColor());
		g.fillOval(position.x - semaphore.getWidth()/2, position.y - semaphore.getHeight()/2, 
				semaphore.getWidth(), semaphore.getHeight());
		g.setColor(defaultColor);
	}
	
	private void drawTrains(Graphics g) {
		if (!hasTrainManager()) {
			return;
		}
		
		TrainManager trainManager = trainModel.getTrainManager();
		
		for (int i = 0; i < trainManager.getTrainCount(); i++) {
			drawTrain(g,trainManager.getTrain(i));
		}
	}
	
	private void drawSemaphores(Graphics g) {
		if (!hasSemaphoreManager()) {
			return;
		}
		
		SemaphoreManager semaphoreManager = trainModel.getSemaphoreManager();
		
		for (int i = 0; i < semaphoreManager.getSemaphoreCount(); i++) {
			drawSemaphore(g, semaphoreManager.getSemaphore(i));
		}
	}
	
	private BufferedImage loadImage() {
		BufferedImage img;
		try {
			img = ImageIO.read(this.getClass().getResourceAsStream("/Trem.jpg"));
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(this, "Filename is null", "NullPointerException has been raised", 
					JOptionPane.ERROR_MESSAGE);
			img = null;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Could not load " + "Trem.jpg", "IOException has been raised", JOptionPane.ERROR_MESSAGE);
			img = null;
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, "Could not load " + "Trem.jpg", "IllegalArgumentException has been raised", JOptionPane.ERROR_MESSAGE);
			img = null;
		}
		return img;
	}
	/**********************PUBLIC METHODS DECLARATION**********************/
	public TrainPanel (TrainModel trainModel) {
		super();
		this.image = loadImage();
		this.trainModel = trainModel;
		trainModel.addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}

	public TrainModel getTrainModel() {
		return trainModel;
	}
	
	public void setTrainModel (TrainModel model) {
		this.trainModel.deleteObserver(this);
		this.trainModel = model;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		drawTrains(g);
		drawSemaphores(g);
	}
}
