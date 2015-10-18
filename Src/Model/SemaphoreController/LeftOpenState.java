package Model.SemaphoreController;

import Model.Semaphore.SemaphoreState;
import Model.Train.Train;
import Model.Train.TrainDirection;
import Model.Train.TrainInformation;
import Model.Train.TrainStatus;

public class LeftOpenState implements StateInterface{
	private SemaphoreController controller;
	
	public LeftOpenState(SemaphoreController controller) {
		this.controller = controller;
		controller.getRightSem().setState(SemaphoreState.closed);
		controller.getLeftSem().setState(SemaphoreState.open);
	}
	
	@Override
	public void createTrain(TrainInformation tInformation) {
	}

	@Override
	public void destroyTrain(TrainInformation tInformation) {
		if (controller.getLeftTrainsCount() == 0 && controller.getRightTrainsCount() == 0)
			controller.setCurrentState(new OpenState(controller));
		
		if (controller.getLeftTrainsCount() == 0 && controller.getRightTrainsCount() > 0)
			controller.setCurrentState(new RightOpenState(controller));	
	}

	@Override
	public void moveTrain(TrainInformation tInformation) {
		Train train = tInformation.getTrain();
		TrainDirection direction = train.getDirection();
		int xPos = train.getPosition().x;
		if (direction == TrainDirection.left) {
			if (595 <= xPos && xPos <= 627 && train.getStatus() == TrainStatus.waitingSemaphore) {
				train.setStatus(TrainStatus.inmotion);
			}
		} else if (direction == TrainDirection.right){
			if (171 <= xPos && xPos <= 197) {
				train.setStatus(TrainStatus.waitingSemaphore);
			}
		}
	}
}
