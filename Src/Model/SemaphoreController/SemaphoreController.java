package Model.SemaphoreController;

import java.util.Observable;
import java.util.Observer;

import Model.Semaphore.Semaphore;
import Model.Train.Train;
import Model.Train.TrainDirection;
import Model.Train.TrainInformation;

//Control the state of the semaphores
public class SemaphoreController implements Observer{
	private StateInterface currentState;
	
	private Semaphore leftSem;
	private Semaphore rightSem;
	
	private int leftTrains;
	private int rightTrains;
	
	public SemaphoreController(Semaphore leftSem, Semaphore rightSem) {
		this.leftSem = leftSem;
		this.rightSem = rightSem;
		
		leftTrains = 0;
		rightTrains = 0;
		
		currentState = new OpenState(this);
	}
	
	public void addLeftTrain() {
		leftTrains++;
	}
	
	public void addRightTrain() {
		rightTrains++;
	}
	
	public void removeLeftTrain() {
		leftTrains--;
	}
	
	public void removeRightTrain() {
		rightTrains--;
	}
	
	public int getRightTrainsCount() {
		return rightTrains;
	}
	
	public int getLeftTrainsCount() {
		return leftTrains;
	}
	
	public StateInterface getCurrentState() {
		return currentState;
	}
	
	public void setCurrentState(StateInterface state) {
		currentState = state;
	}
	
	public Semaphore getLeftSem() {
		return leftSem;
	}
	
	public Semaphore getRightSem() {
		return rightSem;
	}
	
	public void createTrain(TrainInformation tInformation) {
		Train train = tInformation.getTrain();
		TrainDirection direction = train.getDirection();
		
		switch(direction) {
		case right : addRightTrain();
			break;
		case left : addLeftTrain();
			break;
		}
		currentState.createTrain(tInformation);
	}
	
	public void destroyTrain(TrainInformation tInformation) {
		Train train = tInformation.getTrain();
		TrainDirection direction = train.getDirection();
		
		switch(direction) {
		case right : removeRightTrain();
			break;
		case left : removeLeftTrain();
			break;
		}
		currentState.destroyTrain(tInformation);
	}
	
	public void moveTrain(TrainInformation tInformation) {
		currentState.moveTrain(tInformation);
	}
	
	@Override
	public void update(Observable semMan, Object tInformation) {
		if (tInformation == null) {
			return;
		}
		
		TrainInformation information = (TrainInformation)tInformation;
		
		if (information.getCreated())
			createTrain(information);
		
		if (information.getDestroyed())
			destroyTrain(information);
		
		moveTrain(information);
	}
		
}
