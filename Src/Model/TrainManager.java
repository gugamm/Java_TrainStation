package Model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class TrainManager extends Observable implements Observer{  
	/**********************PRIVATE VARIABLE DECLARATION**********************/
	private List<Train> trains;
	private static final double SAFE_DISTANCE = 30.0;
	
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
		updateTrainStatus((Train)o);
		setChanged();
		notifyObservers(o); //Notify passing the train that has changed
	}
	
	private Train getFrontTrain(Train t) {
		Train front;
		int index = getTrainIndex(t);
		if (index < 0)
			return null;
		
		for (int i = index - 1; i >= 0; i--) {
			front = getTrain(i);
			if (front.getDirection() == t.getDirection())
				return front;
		}
		return null;
	}
	
	private boolean isDistanceSafe(Train front, Train back) {
		double distance;
		int disX = front.getPosition().x - back.getPosition().x;
		int disY = front.getPosition().y - back.getPosition().y;
		distance = Math.sqrt(disX * disX + disY * disY);
		return (distance > SAFE_DISTANCE);
	}
	
	private void updateTrainStatus(Train t) {
		Train front = getFrontTrain(t);
		if (front == null) {
			t.setStatus(TrainStatus.inmotion);
		} else {
			if (isDistanceSafe(front,t)) {
				t.setStatus(TrainStatus.inmotion);
			} else {
				t.setStatus(TrainStatus.stationary);
			}
		}
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
