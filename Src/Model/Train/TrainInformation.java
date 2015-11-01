package Model.Train;

public class TrainInformation {
	private Train train;
	private boolean created;
	
	public TrainInformation(Train train, boolean created) {
		this.train = train;
		this.created = created;
	}
	
	public Train getTrain() {
		return train;
	}
	
	public boolean getCreated() {
		return created;
	}
}
