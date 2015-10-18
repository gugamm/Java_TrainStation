package Model.SemaphoreController;

import Model.Semaphore.SemaphoreState;
import Model.Train.TrainInformation;

//This class represents that both semaphores are opened
public class OpenState implements StateInterface{
	private SemaphoreController controller;
	
	public OpenState(SemaphoreController controller) {
		this.controller = controller;
		controller.getLeftSem().setState(SemaphoreState.open);
		controller.getRightSem().setState(SemaphoreState.open);
	}
	
	@Override
	public void createTrain(TrainInformation tInformation) {
		switch(tInformation.getTrain().getDirection()) {
		case right : controller.setCurrentState(new RightOpenState(controller));
					 break;
		case left :  controller.setCurrentState(new LeftOpenState(controller));
					 break;
		}
	}

	@Override
	public void destroyTrain(TrainInformation tInformation) {
	}

	@Override
	public void moveTrain(TrainInformation tInformation) {
	}
}
