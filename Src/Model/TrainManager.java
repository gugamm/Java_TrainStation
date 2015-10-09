package Model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class TrainManager extends Observable implements Observer{  
	/**********************PRIVATE VARIABLE DECLARATION**********************/
	private List<Train> trains;
	
	/**********************PUBLIC METHODS DECLARATION**********************/
	public TrainManager () {
		trains = new ArrayList<Train>();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg != null) {
			String text = (String)arg;
			if (text == "Finished") {
				removeTrain((Train)o);
			}
		}
		setChanged();
		notifyObservers(o); //Notify passing the train that has changed
	}

	public Train addTrain(TrainDirection direction, int endOfLine, int speed, int width, int height, Point position) {
		Train train = new Train(direction,endOfLine,speed,width,height,position);
		train.addObserver(this);
		trains.add(train);
		return train;
	}
	
	public Train addTrain(TrainDirection direction, int endOfLine, int speed, int width, int height, int x, int y) {
		return addTrain(direction,endOfLine,speed,width,height,new Point(x,y));
	}
	
	public Train addTrain(TrainDirection direction, int endOfLine, int speed, Point position) {
		Train train = new Train(direction,endOfLine,speed,position);
		train.addObserver(this);
		trains.add(train);
		return train;
	}
	
	public Train addTrain(TrainDirection direction, int endOfLine, int speed, int x, int y) { 
		return addTrain(direction,endOfLine,speed,new Point(x,y));
	}
	
	public void addTrain(Train train) {
		train.addObserver(this);
		trains.add(train);
	}
	
	public boolean removeTrain(Train t) {
		boolean removed = trains.remove(t);
		if (removed) {
			t.deleteObserver(this);
		}
		return removed;
	}
	
	public boolean removeTrain(int index) {
		Train train = trains.remove(index);
		if (train != null) {
			train.deleteObserver(this);
		}
		return (train != null);
	}
	
	public void clearTrains() {
		for (int i = 0; i < getTrainCount(); i++) {
			removeTrain(0);
		}
	}

	public int getTrainIndex(Train t) {
		return trains.indexOf(t);
	}
	
	public int getTrainCount() {
		return trains.size();
	}
	
	public Train getTrain(int index) {
		return trains.get(index);
	}
}
