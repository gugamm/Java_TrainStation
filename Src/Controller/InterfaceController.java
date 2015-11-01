package Controller;

import java.awt.Point;

import javax.swing.JPanel;

import Model.Railroad.TrainModel;
import Model.Train.Train;
import Model.Train.TrainDirection;

public class InterfaceController {
	private TrainModel model;
	private JPanel view;
	private int endOfLine;
	
	private int getEndOfLine(TrainDirection direction) {
		if (direction == TrainDirection.right)
			return endOfLine;
		return 0;
	}
	
	public InterfaceController(TrainModel model, int endOfLine) {
		this.model = model;
		this.endOfLine = endOfLine;
	}
	
	public void setView(JPanel view) {
		this.view = view;
	}
	
	public JPanel getView() {
		return this.view;
	}
	
	public void addTrain(TrainDirection direction, int speed, Point position) {
		Train t = new Train(direction,getEndOfLine(direction),speed,position);
		model.getTrainManager().addTrain(t);
		new Thread(t).start();
	}
}
