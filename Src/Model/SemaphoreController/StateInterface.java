package Model.SemaphoreController;

import Model.Train.TrainInformation;

public interface StateInterface {
	public void createTrain(TrainInformation tInformation);
	public void destroyTrain(TrainInformation tInformation);
	public void moveTrain(TrainInformation tInformation);
}
