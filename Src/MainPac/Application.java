package MainPac;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Model.SemaphoreManager;
import Model.SemaphoreState;
import Model.Train;
import Model.TrainDirection;
import Model.TrainManager;
import Model.TrainModel;
import View.TrainFrame;
import View.TrainPanel;

public class Application {
	private static final int DEFAULT_RIGHT_X = 19;
	private static final int DEFAULT_RIGHT_Y = 247;
	private static final int DEFAULT_LEFT_X = 763;
	private static final int DEFAULT_LEFT_Y = 338;
	
	private static final int DEFAULT_SEMAPHORE_LEFT_X = 200;
	private static final int DEFAULT_SEMAPHORE_LEFT_Y = 180;
	private static final int DEFAULT_SEMAPHORE_RIGHT_X = 582;
	private static final int DEFAULT_SEMAPHORE_RIGHT_Y = 180;
	
	public static void main(String[] args) {
		TrainFrame frm = createFrame("Train Manager Application");
		TrainPanel panel = createPanel();
		frm.add(panel);
		frm.setVisible(true);
		
		addTrains(panel.getTrainModel().getTrainManager(),panel);
		addSemaphores(panel.getTrainModel().getSemaphoreManager());
		
		Train tremTeste1 = panel.getTrainModel().getTrainManager().getTrain(0);
		Train tremTeste2 = panel.getTrainModel().getTrainManager().getTrain(1);
		new Thread(tremTeste1).start();
		new Thread(tremTeste2).start(); 
	}
	
	private static TrainPanel createPanel() {
		TrainModel model = new TrainModel(new TrainManager(), new SemaphoreManager());
		TrainPanel panel = new TrainPanel(model);
		return panel;
	}
	
	private static void addSemaphores(SemaphoreManager semaphoreManager) {
		semaphoreManager.addSemaphore(SemaphoreState.closed,DEFAULT_SEMAPHORE_LEFT_X,
				DEFAULT_SEMAPHORE_LEFT_Y);
		semaphoreManager.addSemaphore(SemaphoreState.open,DEFAULT_SEMAPHORE_RIGHT_X,
				DEFAULT_SEMAPHORE_RIGHT_Y);
	}
	
	private static void addTrains(TrainManager trainManager, TrainPanel panel) {
		trainManager.addTrain(TrainDirection.right,panel.getWidth(),60,DEFAULT_RIGHT_X,DEFAULT_RIGHT_Y);
		trainManager.addTrain(TrainDirection.left,0,60,DEFAULT_LEFT_X,DEFAULT_LEFT_Y);
	}
	
	public static BufferedImage loadImage(File file) {
		BufferedImage img;
		String message = "Could not load " + file.getName();
		String title = "IOException has been raised";
		try {
			img = ImageIO.read(file);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
			img = null;
		}
		return img;
	}
	
	public static TrainFrame createFrame(String title) {
		TrainFrame frm = new TrainFrame(title);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.setMidScreen();		
		return frm;
	}
}
