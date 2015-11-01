package MainPac;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Controller.InterfaceController;
import Model.Railroad.TrainModel;
import Model.Semaphore.Semaphore;
import Model.Semaphore.SemaphoreManager;
import Model.Semaphore.SemaphoreState;
import Model.SemaphoreController.SemaphoreController;
import Model.Train.TrainManager;
import View.TrainFrame;
import View.TrainInterfacePanel;
import View.TrainPanel;

public class Application {
	private static final int DEFAULT_SEMAPHORE_LEFT_X = 200;
	private static final int DEFAULT_SEMAPHORE_LEFT_Y = 180;
	private static final int DEFAULT_SEMAPHORE_RIGHT_X = 582;
	private static final int DEFAULT_SEMAPHORE_RIGHT_Y = 180;
	
	
	static public TrainFrame frm;
	static public JFrame interfaceFrame;
	
	public static void main(String[] args) {
		TrainModel model = new TrainModel(new TrainManager(), new SemaphoreManager());
		frm = createFrame("Train Manager Application");
		TrainPanel panel = createTrainPanel(model);
		frm.add(panel);
		frm.setVisible(true);
		InterfaceController controller = new InterfaceController(model,panel.getWidth());
		interfaceFrame = createInterfaceFrame(controller);
		
		interfaceFrame.setVisible(true);
		interfaceFrame.pack();
		interfaceFrame.setSize(interfaceFrame.getWidth() + 100, interfaceFrame.getHeight());
		
		addSemaphores(panel.getTrainModel().getSemaphoreManager(), panel.getTrainModel().getTrainManager());

		panel.repaint();
	}
	
	private static TrainPanel createTrainPanel(TrainModel model) {
		TrainPanel panel = new TrainPanel(model);
		return panel;
	}
	
	private static JFrame createInterfaceFrame(InterfaceController controller) {
		JFrame frame = new JFrame("Train Interface");
		TrainInterfacePanel panel = new TrainInterfacePanel(controller);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(frm.getX(), frm.getY());
		frame.add(panel);
		return frame;
	}
	
	private static void addSemaphores(SemaphoreManager semaphoreManager, TrainManager trainManager) {
		Semaphore leftSem = new Semaphore(SemaphoreState.closed, new Point(DEFAULT_SEMAPHORE_RIGHT_X,DEFAULT_SEMAPHORE_RIGHT_Y));
		Semaphore rightSem = new Semaphore(SemaphoreState.closed, new Point(DEFAULT_SEMAPHORE_LEFT_X,DEFAULT_SEMAPHORE_LEFT_Y));
		semaphoreManager.addSemaphore(leftSem);
		semaphoreManager.addSemaphore(rightSem);
		SemaphoreController controller = new SemaphoreController(leftSem,rightSem);
		trainManager.addObserver(controller);
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
