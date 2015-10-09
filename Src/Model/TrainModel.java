package Model;

import java.util.Observable;
import java.util.Observer;

public class TrainModel extends Observable implements Observer{

	/**********************PRIVATE VARIABLE DECLARATION**********************/
	private TrainManager trainManager;
	private SemaphoreManager semaphoreManager;
	
	/**********************PUBLIC METHODS DECLARATION**********************/
	public TrainModel (TrainManager trainManager, SemaphoreManager semaphoreManager) {
		this.trainManager = trainManager;
		this.semaphoreManager = semaphoreManager;
		if (trainManager != null) 
			trainManager.addObserver(this);
		if (semaphoreManager != null)
			semaphoreManager.addObserver(this);
	}

	@Override
	public void update(Observable o, Object obj) {
		setChanged();
		notifyObservers(obj); //Notify Passing The Train/Semaphore That Has Changed
	}

	public void setSemaphoreManager(SemaphoreManager semaphoreManager) {
		this.semaphoreManager = semaphoreManager;
	}
	
	public void setTrainManager(TrainManager trainManager) {
		this.trainManager = trainManager;
	}
	
	public TrainManager getTrainManager() {
		return trainManager;
	}
	
	public SemaphoreManager getSemaphoreManager() {
		return semaphoreManager;
	}
}
